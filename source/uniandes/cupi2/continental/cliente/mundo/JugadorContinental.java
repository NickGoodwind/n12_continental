/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: JugadorContinental.java 1013 2007-08-20 23:04:33Z camil-ji $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Equipo Cupi2 2014
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.cliente.mundo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Clase que representa a un jugador. 
 * <b>inv:</b><br>
 * estado pertenece a {REALIZANDO_JUGADA, ESPERANDO_JUGADA, ESPERANDO_OPONENTE, JUEGO_TERMINADO}.<br>
 */
public class JugadorContinental
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Indica que el jugador se encuentra realizando una jugada.
     */
    public static final String REALIZANDO_JUGADA = "Realizando jugada";

    /**
     * Indica que el jugador se encuentra Esperando la jugada del oponente.
     */
    public static final String ESPERANDO_JUGADA = "Esperando jugada";

    /**
     * Indica que el jugador se encuentra esperando un oponente.
     */
    public static final String ESPERANDO_OPONENTE = "Esperando oponente";

    /**
     * Indica que el juego ha terminado.
     */
    public static final String JUEGO_TERMINADO = "Juego terminado";

    /**
     * Mensaje para enviar la informaci�n de una carta.
     */
    public static final String CARTA = "CARTA";

    /**
     * Mensaje para enviar la informaci�n del jugador.
     */
    public static final String JUGADOR = "JUGADOR:";

    /**
     * Mensaje para enviar la informaci�n de una victoria.
     */
    public static final String VICTORIA = "VICTORIA";

    /**
     * Mensaje para pedir una carta de la baraja inicial.
     */
    public static final String BARAJA_INICIAL = "BARAJA:INICIAL";

    /**
     * Mensaje para pedir una carta de la baraja jugada.
     */
    public static final String BARAJA_JUGADA = "BARAJA:JUGADA";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Nombre del jugador.
     */
    private String nombre;

    /**
     * Canal usado para comunicarse con el servidor.
     */
    private Socket canal;

    /**
     * Flujo que env�a los datos al servidor a trav�s del socketServidor.
     */
    private PrintWriter outWriter;

    /**
     * Flujo de donde se leen los datos que llegan del servidor a trav�s del socketServidor.
     */
    private BufferedReader inReader;

    /**
     * Indica el fin de juego.
     */
    private boolean finDeJuego;

    /**
     * Juego actual del jugador.
     */
    private Juego juego;

    /**
     * Estado del jugador.
     */
    private String estado;

    /**
     * Estado que muestra si el jugador ya escogi� una carta.
     */
    private boolean seleccionoCarta;

    /**
     * Nombre del oponente.
     */
    private String nombreOponente;

    /**
     * N�mero de derrotas del oponente.
     */
    private String numDerrotasOponente;

    /**
     * N�mero de victorias del oponente.
     */
    private String numVictoriasOponente;

    /**
     * Estado que indica si el oponente cant� una victoria v�lida.
     */
    private boolean victoriaValidaOponente;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del jugador .
     * <b> post: </b> Se inicializa finDeJuego en falso. finDeJuego == false.<br>
     * Se inicializa el juego del jugador. juego != null. 
     * Se inicializa el estado del jugador en esperando oponente. estado == ESPERANDO_OPONENTE 
     * Se inicializa selecciono carta en falso. seleccionoCarta = false.
     */
    public JugadorContinental( )
    {
        finDeJuego = false;
        juego = new Juego( );
        estado = ESPERANDO_OPONENTE;
        seleccionoCarta = false;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Devuelve el estado del jugador.
     * @return Estado del jugador.
     */
    public String darEstado( )
    {
        return estado;
    }

    /**
     * Devuelve el juego del jugador. <b> 
     * pre: </b> El juego se encuentra inicializado. juego != null. <br>
     * @return Juego del jugador.
     */
    public Juego darJuego( )
    {
        return juego;
    }

    /**
     * Devuelve la carta a descartar por el jugador. <b> 
     * pre: </b> El juego se encuentra inicializado. juego != null. <br>
     * @return Carta a descartar.
     */
    public Carta darCartaTemporal( )
    {
        return juego.darCartaTemporal( );
    }

    /**
     * Nombre del oponente.
     * @return Nombre del oponente.
     */
    public String darNombreOponente( )
    {
        return nombreOponente;
    }

    /**
     * Devuelve el numero de derrotas del oponente.
     * @return Numero de derrotas del oponente.
     */
    public String darNumDerrotasOponente( )
    {
        return numDerrotasOponente;
    }

    /**
     * Devuelve el numero de victorias del oponente.
     * @return Numero de victorias del oponente.
     */
    public String darNumVictoriasOponente( )
    {
        return numVictoriasOponente;
    }

    /**
     * Devuelve si la victoria del oponente fue valida.
     * @return True si la victoria del oponente fue valida, false de lo contrario.
     */
    public boolean darVictoriaValidaOponente( )
    {
        return victoriaValidaOponente;
    }

    /**
     * Devuelve si el juego se ha terminado.
     * @return True si el juego ha terminado, false de lo contrario.
     */
    public boolean juegoTerminado( )
    {
        return finDeJuego;
    }

    /**
     * Indica si el jugador ha tomado una carta de alguna baraja.
     * @return True si el jugador a tomado una carta, false de lo contrario.
     */
    public boolean seleccionoCarta( )
    {
        return seleccionoCarta;
    }

    /**
     * Modifica la carta a descartar por el jugador.
     * @param carta Carta a descartar.
     */
    public void modificarCartaTemporal( Carta carta )
    {
        juego.modificarCartaTemporal( carta );
    }

    /**
     * Conectarse el cliente al servidor del juego.
     * @param nombreJugador Nombre del jugador. nombreJugador != null.
     * @param dirServ Direcci�n del servidor. dirServ != null.
     * @param puertoServ Puerto del servidor. puertoServ != null.
     * @throws ContinentalException Si se produce alg�n error al conectarse al servidor.
     */
    public void conectar( String nombreJugador, String dirServ, int puertoServ ) throws ContinentalException
    {
        nombre = nombreJugador;

        try
        {
            // Conectar al servidor
            canal = new Socket( dirServ, puertoServ );
            outWriter = new PrintWriter( canal.getOutputStream( ), true );
            inReader = new BufferedReader( new InputStreamReader( canal.getInputStream( ) ) );

            // iniciar el encuentro
            iniciarEncuentro( );

            // Recibir el juego
            recibirJuego( );
        }
        catch( UnknownHostException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "No fue posible establecer una conexi�n al servidor. " );
        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "No fue posible establecer una conexi�n al servidor. " );
        }

    }

    /**
     * Env�a al servidor los mensajes necesarios para iniciar un encuentro y recibe la informaci�n del oponente.
     * <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * @throws ContinentalException Se lanza esta excepci�n si hay un problema leyendo del canal.
     */
    private void iniciarEncuentro( ) throws ContinentalException
    {

        try
        {
            finDeJuego = false;

            // Enviar el nombre del jugador
            outWriter.println( JUGADOR + nombre );

            // Leer la informaci�n del jugador
            // INFO:<nombre>:<ganados>:<perdidos>
            String[] datosJugador;
            datosJugador = inReader.readLine( ).split( ":" );
            nombreOponente = datosJugador[ 1 ];
            numVictoriasOponente = datosJugador[ 2 ];
            numDerrotasOponente = datosJugador[ 3 ];

        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al recibir los datos del oponente: " + e.getMessage( ) );
        }

    }

    /**
     * Recibe del servidor las 7 cartas del juego, luego la primera carta destapada de la baraja y el turno que le corresponde .
     * <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * @throws ContinentalException Si ocurre un error al recibir los datos del servidor.
     */
    public void recibirJuego( ) throws ContinentalException
    {

        try
        {
            // Recibe las 7 cartas del juego
            for( int i = 0; i < 7; i++ )
            {
                String[] datosCarta = inReader.readLine( ).split( ":" );
                juego.agregarCarta( datosCarta[ 1 ], datosCarta[ 2 ], datosCarta[ 3 ] );
            }

            // Recibe la primera carta de la baraja
            String[] datosCarta = inReader.readLine( ).split( ":" );
            juego.modificarCartaBarajaJugada( datosCarta[ 1 ], datosCarta[ 2 ], datosCarta[ 3 ] );

            // Recibe a qui�n le corresponde el primer turno
            String[] datosPrimerTurno = inReader.readLine( ).split( ":" );
            if( datosPrimerTurno[ 1 ].equals( nombre ) )
                estado = REALIZANDO_JUGADA;
            else
                estado = ESPERANDO_JUGADA;
            verificarInvariante( );
        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al recibir las cartas: " + e.getMessage( ) );
        }
    }

    /**
     * Espera la jugada del oponente, puede ser VICTORIA o la carta que jug� este. <br>
     * <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * @throws ContinentalException Si ocurre un error al recibir la jugada del oponente, si se pierde la conexi�n con el oponente.
     */
    public void esperarJugada( ) throws ContinentalException
    {
        String[] datos;
        try
        {
            String jugada = inReader.readLine( );
            if( jugada == null )
            {
                estado = JUEGO_TERMINADO;
                throw new ContinentalException( "Se ha perdido la conexi�n con el oponente." );
            }
            datos = jugada.split( ":" );

            if( datos[ 0 ].equals( "VICTORIA" ) )
            {
                estado = JUEGO_TERMINADO;
                esperarDatosVictoria( datos );
            }
            else if( datos[ 0 ].equals( CARTA ) )
            {
                juego.modificarCartaBarajaJugada( datos[ 1 ], datos[ 2 ], datos[ 3 ] );
                estado = REALIZANDO_JUGADA;
            }
            verificarInvariante( );
        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al recibir la carta jugada por el oponente: " + e.getMessage( ) );
        }
    }

    /**
     * Espera los datos de la victoria del oponente, si la victoria fue valida, recibe el juego con el que gano el oponente. 
     * <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * <b> post: </b> la variable finDeJuego es true y la variable victoriaValidaOponente indica si la victoria del oponente fue v�lida. <br>
     * @param datos Cadena que indica si la victoria fue valida o no.
     * @throws ContinentalException Si ocurre un error al recibir las datos de la victoria del oponente.
     */
    private void esperarDatosVictoria( String[] datos ) throws ContinentalException
    {
        try
        {
            if( !datos[ 1 ].equals( "FALSA" ) )
            {
                ArrayList<Carta> cartas = new ArrayList<Carta>( );
                for( int i = 0; i < 7; i++ )
                {
                    String[] datosCarta = inReader.readLine( ).split( ":" );
                    cartas.add( new Carta( datosCarta[ 1 ], datosCarta[ 2 ], datosCarta[ 3 ] ) );
                }
                victoriaValidaOponente = true;
            }
            else
                victoriaValidaOponente = false;

            finDeJuego = true;
        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al recibir los datos de la victoria" );
        }
    }

    /**
     * Env�a los datos de la victoria al servidor <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * @return True si el juego es valido, false de lo contrario.
     * @throws ContinentalException Si ocurre un error al enviar la informaci�n de la victoria.
     */
    public boolean enviarDatosVictoria( ) throws ContinentalException
    {

        try
        {
            outWriter.println( VICTORIA );

            // Se recibe el juego del oponente, no se realiza alguna operaci�n con las cartas, se deja para extensi�n del ejercicio.

            for( int i = 0; i < juego.darJuego( ).size( ); i++ )
            {
                Carta carta = ( Carta )juego.darJuego( ).get( i );
                outWriter.println( carta.toString( ) );
            }
            String[] datos = inReader.readLine( ).split( ":" );
            estado = JUEGO_TERMINADO;

            verificarInvariante( );

            if( datos[ 1 ].equals( "OK" ) )
                return true;
            else
                return false;

        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al enviar los datos de la Victoria" );
        }
    }

    /**
     * Env�a la carta jugada al servidor y actualiza el estado a ESPERANDO_JUGADA. <b> 
     * pre: </b> El estado del jugador se encuentra REALIZANDO_JUGADA. <br>
     * Se encuentra establecida una conexi�n con el servidor. <br>
     * <b> post: </b> estado == ESPERANDO_JUGADA. <br>
     * seleccionoCarta == false. <br>
     * Se modifica la carta temporal del juego a null. <br>
     * Se modifica la carta de la baraja jugada del juego con la carta jugada. <br>
     * 
     */
    public void realizarJugada( )
    {
        if( juego.darCartaTemporal( ) != null )
        {
            Carta carta = juego.darCartaTemporal( );
            outWriter.println( carta.toString( ) );
            juego.modificarCartaBarajaJugada( carta );
            estado = ESPERANDO_JUGADA;
            seleccionoCarta = false;
            juego.modificarCartaTemporal( null );
            verificarInvariante( );
        }
    }

    /**
     * Pide al servidor una carta de la baraja inicial y la coloca en la carta temporal del juego. <br>
     * <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * El estado del jugador se encuentra REALIZANDO_JUGADA. <br>
     * <b> post: </b> seleccionoCarta == true. <br>
     * @throws ContinentalException Si ocurre alg�n error al recibir la carta del servidor.
     */
    public void pedirCartaBarajaInicial( ) throws ContinentalException
    {
        try
        {
            seleccionoCarta = true;
            outWriter.println( BARAJA_INICIAL );

            String[] datosCarta = inReader.readLine( ).split( ":" );
            juego.modificarCartaTemporal( datosCarta[ 1 ], datosCarta[ 2 ], datosCarta[ 3 ] );
        }
        catch( Exception e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al pedir carta de la baraja inicial: " + e.getMessage( ) );
        }
    }

    /**
     * Pide al servidor una carta de la baraja jugada y la coloca en la carta temporal del juego. 
     * <b> pre: </b> Se encuentra establecida una conexi�n con el servidor. <br>
     * El estado del jugador se encuentra REALIZANDO_JUGADA. <br>
     * <b> post: </b> seleccionoCarta == true. <br>
     * @throws ContinentalException Si ocurre alg�n error al recibir la carta del servidor.
     */
    public void pedirCartaBarajaJugada( ) throws ContinentalException
    {
        try
        {
            seleccionoCarta = true;
            outWriter.println( BARAJA_JUGADA );

            String[] datosCarta = inReader.readLine( ).split( ":" );
            juego.modificarCartaTemporal( datosCarta[ 1 ], datosCarta[ 2 ], datosCarta[ 3 ] );

        }
        catch( IOException e )
        {
            estado = JUEGO_TERMINADO;
            finDeJuego = true;
            verificarInvariante( );
            throw new ContinentalException( "Error al pedir carta de la baraja jugada: " + e.getMessage( ) );
        }
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase.<br>
     * <b>inv:</b> estado pertenece a {REALIZANDO_JUGADA, ESPERANDO_JUGADA, ESPERANDO_OPONENTE, JUEGO_TERMINADO}.<br>
     */
    private void verificarInvariante( )
    {
        assert ( estado.equals( REALIZANDO_JUGADA ) || estado.equals( ESPERANDO_JUGADA ) || estado.equals( ESPERANDO_OPONENTE ) || estado.equals( JUEGO_TERMINADO ) ) : "El estado no es v�lido";

    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1.
     * @return respuesta1.
     */
    public String metodo1( )
    {
        return "Respuesta 1.";
    }

    /**
     * M�todo para la extensi�n2.
     * @return respuesta2.
     */
    public String metodo2( )
    {
        return "Respuesta 2.";
    }

}
