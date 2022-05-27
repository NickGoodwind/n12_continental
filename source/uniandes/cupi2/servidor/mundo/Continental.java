package uniandes.cupi2.servidor.mundo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import uniandes.cupi2.continental.cliente.mundo.ContinentalException;

/**
 * @author NickGoodwind
 * Clase que modela el servidor del juego Continental y recibe las conexiones para crear los encuentros. <br>
 * <b>inv:</b><br>
 * receptor!= null <br>
 * config!=null <br>
 * adminResultados!=null <br>
 * encuentros!=null <br>
 */
public class Continental
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	
	/**
	 * Las propiedades que cargan el servidor y la base de datos
	 */
	private Properties configuracion;
	
	/**
	 * El arreglo de encuentro que se juegan actualmente
	 */
	private Collection<Encuentro> encuentros;
	
	/**
	 * El administrador de la información de los jugadores que estan jugando o han jugado
	 */
	private AdministradorJugadores admon;

	/**
	 * El punto por medio del cual se solicitan las conexiones
	 */
	private ServerSocket receptor;
	
	/**
	 * El canal para establecer una comunicación con un jugador que está en espera de un oponente. <br>
     * Si no hay jugadores en espera, este canal debe ser null.
	 */
	private Socket enEspera;
	
    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	/**
     * Inicia el servidor y deja listo el administrador de resultados
	 * @param propiedades El arcivo de propiedades que tiene la configuración del servidor - propiedades != null
     * @throws Exception Cuando hay problemas con el archivo de propiedades o hay problemas en la conexión a la base de datos
     * @throws SQLException Cuando hay problemas conectando el almacén a la base de datos.
	 * @throws ContinentalException Cuando hay problemas en la carga de los datos
     */
	public Continental( String propiedades ) throws SQLException, ContinentalException, Exception
	{
		encuentros = new ArrayList<Encuentro>( );
		try
		{
	        FileInputStream fis = new FileInputStream( propiedades );
	        configuracion = new Properties( );
	        configuracion.load( fis );
	        fis.close( );
		}
		catch( Exception e )
		{
			throw new ContinentalException( "Error al cargar las propiedades del administardor" + e.getMessage( ) );
		}
		admon = new AdministradorJugadores( configuracion );
		admon.conectarABD( );
		admon.iniciarTabla( );
		verificarInvariante( );
	}
	
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	
	/**
     * Método encargado de recibir todas las conexiones entrantes y crear los encuentros cuando fuera necesario.
     */
    public void recibirConexiones( )
    {
        String aux = configuracion.getProperty( "servidor.puerto" );
        int puerto = Integer.parseInt( aux );
        try
        {
            receptor = new ServerSocket( puerto );
            while( true )
            {
                Socket socketNuevoCliente = receptor.accept( );
                crearEncuentro( socketNuevoCliente );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace( );
        }
        finally
        {
            try
            {
                receptor.close( );
            }
            catch( IOException e )
            {
                e.printStackTrace( );
            }
        }
    }
    
    /**
     * Método encargado de intentar crear un nuevo encuentro con el jugador que se acaba de conectar. <br>
     * Si no se tiene ya un oponente, entonces el jugador queda en espera de que otra persona se conecte.
     * @param nuevoJ El canal que permite la comunicación con el nuevo cliente - socket != null
     * @throws IOException Se lanza esta excepción si se presentan problemas de comunicación
     */
    synchronized private void crearEncuentro( Socket nuevoJ ) throws IOException
    {
        if( enEspera == null )
        {
            enEspera = nuevoJ;
        }
        else
        {
            Socket jug1 = enEspera;
            Socket jug2 = nuevoJ;
            enEspera = null;
            try
            {
                Encuentro nuevo = new Encuentro( jug1, jug2, admon );
                iniciarEncuentro( nuevo );
            }
            catch( IOException e )
            {
                try
                {
                    jug1.close( );
                }
                catch( IOException e1 )
                {
                    e.printStackTrace( );
                }
                try
                {
                    jug2.close( );
                }
                catch( IOException e2 )
                {
                    e.printStackTrace( );
                }
                e.printStackTrace( );
            }
        }
        verificarInvariante();
    }
    
    /**
     * Método encargado de agregar el encuentro a la lista de encuentros e iniciarlo
     * @param nuevo Un encuentro que no ha sido inicializado ni agregado a la lista de encuentros - nuevoEncuentro != null
     */
    protected void iniciarEncuentro( Encuentro nuevo )
    {
        encuentros.add( nuevo );
        nuevo.start( );
    }
    
	/**
	 * Método encargado de retirnar el administrador de los datos de los jugadores
	 * @return admon El administrador de la información de los jugadores
	 */
	public AdministradorJugadores darAdministrador( )
	{
		return admon;
	}

	/**
	 * Método encargado de retornar la lista de los juegos que se están llevando a cabo en este momento. <br>
     * Si había encuentros en la lista que ya habían terminado deben ser eliminados.
	 * @return encuentros La lista de los encuentros
	 */
	public Collection<Encuentro> actualizarJuegos( )
	{
		 ArrayList<Encuentro> listaActualizada = new ArrayList<Encuentro>( );
		 Iterator<Encuentro> iter = encuentros.iterator( );
		 while( iter.hasNext( ) )
		 {
			 Encuentro e = ( Encuentro )iter.next( );
			 if( !e.encuentroTerminado( ) )
			 {
				 listaActualizada.add( e );
			 }
		 }
		 encuentros = listaActualizada;
		 return encuentros;
	}
    
    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

	/**
	 * Método de extensión 1
	 * @return respuesta 1
	 */
	public String opcion1( )
	{
		return "Respuesta 1";
	}

	/**
	 * Método de extensión 2
	 * @return respuesta 2
	 */
	public String opcion2( )
	{
		return "Respuesta 2";
	}
	
    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase <br>
     * <b>inv:</b><br>
     * receptor!= null <br>
     * config!=null <br>
     * adminResultados!=null <br>
     * encuentros!=null <br>
     */
    private void verificarInvariante( )
    {        
        assert receptor != null : "Canal inválido";
        assert configuracion != null : "Conjunto de propiedades inválidas";
        assert admon != null : "El administrador de resultados no debería ser null";
        assert encuentros != null : "La lista de encuentros no debería ser null";            
    }
}
