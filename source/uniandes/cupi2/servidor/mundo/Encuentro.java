package uniandes.cupi2.servidor.mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;

import uniandes.cupi2.continental.cliente.mundo.Carta;
import uniandes.cupi2.continental.cliente.mundo.ContinentalException;
import uniandes.cupi2.continental.cliente.mundo.JugadorContinental;

/**
 * @author NickGoodwind
 * Clase que modela un encuentro entre dos jugadores
 * <b>inv:</b><br>
 * !finJuego => socketJugador1 != null <br>
 * !finJuego => out1 != null <br>
 * !finJuego => in1 != null <br>
 * !finJuego => socketJugador2 != null <br>
 * !finJuego => out2 != null <br>
 * !finJuego => in2 != null <br>
 * jugador1 != null <br>
 * jugador2 != null <br>
 */
public class Encuentro extends Thread
{
	private static final String INFO = "INFO";
	
	private static final String PRIMER_TURNO = "PRIMERTURNO";
    
	
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	
	/**
	 * La baraja con todas las cartas
	 */
	private Baraja inicial;
	
	/**
	 * La baraja con las cartas descartadas
	 */
	private Baraja descartadas;
	
	/**
     * El canal del jugador 1
     */
    private Socket socketJugador1;

    /**
     * El flujo de salida del jugador 1
     */
    private PrintWriter out1;

    /**
     * El flujo de entrada del jugador 1
     */
    private BufferedReader in1;

    /**
     * El canal del jugador 2
     */
    private Socket socketJugador2;

    /**
     * El flujo de salida del jugador 2
     */
    private PrintWriter out2;

    /**
     * El flujo de entrada del jugador 2
     */
    private BufferedReader in2;

    /**
     * El jugador 1: visibilidad protegida, para facilitar las pruebas
     */
    protected JugadorRemoto jugador1;

    /**
     * El jugador 2: visibilidad protegida, para facilitar las pruebas
     */
    protected JugadorRemoto jugador2;

    /**
     * El atributo que modela si el juego termino
     */
    private boolean termino;

    /**
     * Es el administrador que permite registrar el resultado del encuentro sobre la base de datos y consultar la informaci�n de los jugadores
     */
    private AdministradorJugadores admon;


    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    
    /**
     * M�todo constructor de la clase encargado de establecer la comunicaci�n con los dos jugadores y dejar listo el escenario para iniciar
     * el encuentro
     * @param jug1 El socket para comunicarse con el jugador 1 - cliente1 != null
     * @param jug2 El socket para comunicarse con el jugador 2 - cliente2 != null
     * @param admin Es el objeto que permite consultar y registrar resultados sobre la base de datos - administrador != null
     * @throws IOException Se lanza esta excepci�n si hay problemas estableciendo la comunicaci�n con los dos jugadores
     */
	public Encuentro(Socket jug1, Socket jug2, AdministradorJugadores admin) throws IOException
	{
		inicial = new Baraja( );
		cargarCartas( inicial );
		inicial.barajar( );
		
		descartadas = new Baraja( );
		
		admon = admin;
		out1 = new PrintWriter( jug1.getOutputStream( ), true );
        in1 = new BufferedReader( new InputStreamReader( jug1.getInputStream( ) ) );
        socketJugador1 = jug1;

        out2 = new PrintWriter( jug2.getOutputStream( ), true );
        in2 = new BufferedReader( new InputStreamReader( jug2.getInputStream( ) ) );
        socketJugador2 = jug2;
        
        termino = false;
        verificarInvariante( );
	}
	

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------
	
	/**
	 * M�todo encargado de cargar todas las cartas del naipe a una baraja que entra por par�metro
	 * @param baraja La baraja a la que se quiere adicionar las cartas del naipe
	 */
	public void cargarCartas( Baraja baraja )
	{
		try
        {
            FileInputStream fis = new FileInputStream( new File( "./data/cartas.properties" ) );
            Properties propiedades = new Properties( );
            propiedades.load( fis );

            String dato;
            String palo;
            String numeracion;
            String imagen;

            String aux;
            dato = "total.cartas";
            aux = propiedades.getProperty( dato );
            int cantidad = Integer.parseInt( aux );

            for( int cont = 1; cont <= cantidad; cont++ )
            {
                dato = "carta" + cont + ".palo";
                palo = propiedades.getProperty( dato );

                dato = "carta" + cont + ".numeracion";
                numeracion = propiedades.getProperty( dato );

                dato = "carta" + cont + ".imagen";
                imagen = propiedades.getProperty( dato );

                Carta carta = new Carta( palo, numeracion, imagen );
                baraja.adicionarCartaAlFinal( carta );

                fis.close( );
            }
        }
        catch( IOException e )
        {
        	new ContinentalException( "Error al cargar los datos de las cartas" );
        }
	}
	
	/**
     * M�todo encargado de retornar el canal con el jugador 1
     * @return socketJugador1 El canal con el jugador 1
     */
    public Socket darSocketJugador1( )
    {
        return socketJugador1;
    }

    /**
     * M�todo encargado de retornar el canal con el jugador 2
     * @return socketJugador1 El canal con el jugador 2
     */
    public Socket darSocketJugador2( )
    {
        return socketJugador2;
    }

    /**
     * Retorna el administrador de resultados en el que se registran los resultados del encuentro
     * @return adminResultados
     */
    public AdministradorJugadores darAdministradorResultados( )
    {
        return admon;
    }
    
	/**
	 * M�todo encargado de retornar el estado del encuetro
	 * @return true si termino, false en caso contrario
	 */
	public boolean encuentroTerminado( )
	{
		return termino;
	}
	
	/**
     * M�todo encargado de crear el encuentro y manejarlo mientras que este dure.<br>
     * El ciclo de vida de un encuentro tiene tres partes:<br>
     * 1. Iniciar el encuentro <br>
     * 2. Realizar el encuentro (permitir la comunicaci�n entre los clientes)<br>
     * 3. Terminar el encuentro y enviar la informaci�n sobre el ganador
     */
    public void run( )
    {
        try
        {
            iniciarEncuentro( );

            int actual = 1;

            while( !termino )
            {
                Baraja mano = procesarJugada( actual );

                if( mano != null && termino )
                {
                    terminarEncuentro( mano );
                }
                else
                {
                    actual = ( actual == 1 ) ? 2 : 1;
                }
            }
        }
        catch( Exception e )
        {
            termino = true;

            try
            {
                in1.close( );
                out1.close( );
                socketJugador1.close( );
            }
            catch( IOException e2 )
            {
                e2.printStackTrace( );
            }

            try
            {
                in2.close( );
                out2.close( );
                socketJugador2.close( );
            }
            catch( IOException e2 )
            {
                e2.printStackTrace( );
            }
        }
    }
    
    /**
     * M�todo encargado de iniciar un encuentro: <br>
     * 1. Lee la informaci�n con los nombres de los jugadores <br>
     * 2. Consulta los registros de los jugadores <br>
     * 3. Env�a a cada jugador tanto su informaci�n como la de su oponente <br>
     * 4. Le env�a a cada jugador un indicador para que sepa si es su turno de jugar. Siempre inicia el juego el primer jugador que se conect�. <br>
     * @throws BatallaNavalException Se lanza esta excepci�n si hay problemas con el acceso a la base de datos
     * @throws IOException Se lanza esta excepci�n si hay problemas en la comunicaci�n
     */
    protected void iniciarEncuentro( ) throws IOException, ContinentalException
    {
        String info1 = in1.readLine( );
        InfoJugador reg1 = consultarJugador( info1 );
        jugador1 = new JugadorRemoto( reg1 );
        String info2 = in2.readLine( );
        InfoJugador reg2 = consultarJugador( info2 );
        jugador2 = new JugadorRemoto( reg2 );

        enviarInformacion( out1, jugador2.darRegistroJugador( ) );
        enviarInformacion( out2, jugador1.darRegistroJugador( ) );
        
        enviarCartas( out1 );
        enviarCartas( out2 );
        
        Carta primera = inicial.darCartaDeEncima( );
        descartadas.adicionarCartaEncima( primera );
        out1.println( primera.toString( ) );
        out2.println( primera.toString( ) );

        out1.println( PRIMER_TURNO + ":" + jugador1.darRegistroJugador( ).darNombre( ) );
        out2.println( PRIMER_TURNO + ":" + jugador1.darRegistroJugador( ).darNombre( ) );
    }
    

    /**
     * M�todo encargado de obtener la informaci�n de un jugador a partir del mensaje que envi� cuando entr� al encuentro
     * @param info El mensaje que fue enviado - info es de la forma "JUGADOR:<nombre>"
     * @return Retorna la informaci�n del jugador
     * @throws BatallaNavalException Se lanza esta excepci�n si hay problemas consultando a la base de datos o se recibe un mensaje con un formato inesperado
     */
    private InfoJugador consultarJugador( String info ) throws ContinentalException
    {
        if( info.startsWith( JugadorContinental.JUGADOR ) )
        {
            String nombre = info.split( ":" )[ 1 ];
            try
            {
                InfoJugador reg1 = admon.consultarRegistroJugador( nombre );
                return reg1;
            }
            catch( SQLException e )
            {
                throw new ContinentalException( "Hubo un problema leyendo la informaci�n del jugador: " + e.getMessage( ) );
            }
        }
        else
        {
            throw new ContinentalException( "El mensaje no tiene el formato esperado" );
        }
    }
    
    /**
     * M�todo encargado de enviar la informaci�n registrada de un jugador usando uno de los streams que permiten la comunicaci�n con los 
     * clientes
     * @param out El stream a trav�s del cual se debe enviar la informaci�n - out es un stream abierto hacia uno de los jugadores
     * @param reg El registro que se va a transmitir - reg != null
     */
    private void enviarInformacion( PrintWriter out, InfoJugador reg )
    {
        String cadena = INFO + ":" + reg.darNombre( ) + ":" + reg.darVictorias( ) + ":" + reg.darDerrotas( );
        out.println( cadena );
    }
    
    private void enviarCartas( PrintWriter out )
    {
    	for( int i = 0; i < 7; i++ )
    	{
    		Carta carta = inicial.darCartaDeEncima( );
    		String nCarta = carta.toString( ); 
    		out.println( nCarta );
    	}
    }
    
    /**
     * M�todo encargado de procesar una jugada completa del juego, recibiendo y enviando los mensajes del juego <br>
     * Si con la jugada el encuentro termina, entonces el atributo termino del encuentro se convierte en true
     * @param atacante El n�mero del jugador que tiene el turno de atacar - atacante = 1 o atacante = 2
     * @return 
     * @throws ContinentalException Cuando hay problemas con la informaci�n que llega
     * @throws IOException Cuando hay problemas en la comunicaci�n
     */
    @SuppressWarnings({ "resource" })
    protected Baraja procesarJugada( int atacante ) throws IOException, ContinentalException
    {
        PrintWriter manoOut = ( atacante == 1 ) ? out1 : out2;
        PrintWriter noManoOut = ( atacante == 1 ) ? out2 : out1;

        BufferedReader manoIn = ( atacante == 1 ) ? in1 : in2;

        String mano = manoIn.readLine( );

        if( inicial.darNumCartas( ) > 0 )
        {
        	if(mano != null)
            {
            	if( mano.startsWith( JugadorContinental.BARAJA_INICIAL ) || mano.startsWith( JugadorContinental.BARAJA_JUGADA ) )
            	{
            		if( mano.equals( JugadorContinental.BARAJA_INICIAL ) )
            		{
            			manoOut.println( inicial.darCartaDeEncima( ).toString( ) );
            		}
            		else if( mano.equals( JugadorContinental.BARAJA_JUGADA ) )
            		{
            			manoOut.println( descartadas.darCartaDeEncima( ).toString( ) );
            		}
            		
            		String descarte = manoIn.readLine( );
            		if( descarte.startsWith( JugadorContinental.CARTA ) )
            		{
            			String[] datos = descarte.split( ":" );
            			descartadas.adicionarCartaEncima( new Carta( datos[1], datos[2], datos[3] ) );
            			noManoOut.println( descarte );
            			return procesarJugada( ( atacante == 1 ) ? 2 : 1 );
            		}
            		else
            		{
            			throw new ContinentalException( "Se esperaba una Carta pero se recibi� " + mano );
            		}
            	}
        		else if( mano.startsWith( JugadorContinental.VICTORIA ) )
        		{
        			termino = true;
    	            Baraja juego = new Baraja( );
        			for( int i = 0; i < 7; i++ )
        	        {
        	            String[] datos = manoIn.readLine( ).split( ":" );
        	            juego.adicionarCartaAlFinal( new Carta( datos[ 1 ], datos[ 2 ], datos[ 3 ] ) );
        	        }
        			return juego;
        		}
            	else
            	{
            		throw new ContinentalException( "Se esperaba una JUGADA pero se recibi� " + mano );	
            	}
            }
            else
            {
            	throw new ContinentalException( "Se esperaba una JUGADA pero se recibi� una cadena nula" );
            }
        }
        else
        {
        	for( int i = 0; i < descartadas.darNumCartas( ); i++ )
        	{
        		Carta carta = descartadas.darCartaDeEncima( );
        		inicial.adicionarCartaAlFinal( carta );
        	}
    		inicial.barajar( );
    		return procesarJugada( ( atacante == 1 ) ? 2 : 1 );
        }
    }
    
    /**
     * Realiza las actividades necesarias para terminar un encuentro: <br>
     * 1. Actualiza los registros de los jugadores en la base de datos <br>
     * 2. Env�a un mensaje a los jugadores advirtiendo sobre el fin del juego y el nombre del ganador <br>
     * 3. Cierra las conexiones a los jugadores
     * @throws BatallaNavalException Se lanza esta excepci�n si hay problemas con el acceso a la base de datos
     * @throws IOException Se lanza esta excepci�n si hay problemas en la comunicaci�n
     */
    protected void terminarEncuentro( Baraja mano ) throws ContinentalException, IOException
    {	
        // Actualizar el registro de los jugadores
        InfoJugador ganador = null;
        InfoJugador perdedor = null;
        String condicion = "";
    	
        if( mano.valido( ) )
        {
            ganador = jugador1.darRegistroJugador( );
            perdedor = jugador2.darRegistroJugador( );
            condicion = "OK";
        }
        else
        {
            ganador = jugador2.darRegistroJugador( );
            perdedor = jugador1.darRegistroJugador( );
            condicion = "FALSA";
        }
        try
        {
            admon.registrarVictoria( ganador.darNombre( ) );
            admon.registrarDerrota( perdedor.darNombre( ) );
        }
        catch( SQLException e )
        {
            throw new ContinentalException( "Error actualizando la informaci�n en la base de datos: " + e.getMessage( ) );
        }

        String cadenaGanador = JugadorContinental.VICTORIA + ":" + condicion;
        out1.println( cadenaGanador );
        out2.println( cadenaGanador );
        in1.close( );
        out1.close( );
        out2.close( );
        in2.close( );
        socketJugador1.close( );
        socketJugador2.close( );
    }
    
    /**
     * Retorna una cadena con la informaci�n del encuentro con el siguiente formato:<br>
     * <jugador1> ( <puntos> ) - <jugador2> ( <puntos> )
     * @return cadena
     */
    public String toString( )
    {
        InfoJugador j1 = jugador1.darRegistroJugador( );
        InfoJugador j2 = jugador2.darRegistroJugador( );

        String cadena = "Jugador 1: " + j1.darNombre( ) + ", Jugador 2: " + j2.darNombre( );
        return cadena;
    }
	
    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase
     * <b>inv:</b><br>
     * !finJuego => socketJugador1 != null <br>
     * !finJuego => out1 != null <br>
     * !finJuego => in1 != null <br>
     * !finJuego => socketJugador2 != null <br>
     * !finJuego => out2 != null <br>
     * !finJuego => in2 != null <br>
     * jugador1 != null <br>
     * jugador2 != null <br>
     */
    private void verificarInvariante( )
    {
        if( !termino )
        {
            assert socketJugador1 != null : "Canal inv�lido";
            assert out1 != null : "Flujo inv�lido";
            assert in1 != null : "Flujo inv�lido";
            assert socketJugador2 != null : "Canal inv�lido";
            assert out2 != null : "Flujo inv�lido";
            assert in2 != null : "Flujo inv�lido";
        }
        
        assert jugador1 != null : "Jugador nulo";
        assert jugador2 != null : "Jugador nulo";
    }

}
