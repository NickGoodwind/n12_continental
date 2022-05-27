package uniandes.cupi2.servidor.mundo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Esta es la clase que se encarga de registrar los resultados de los encuentros sobre la base de datos. <br>
 * @author NickGoodwind
 * <b>inv:</b><br>
 * config!=null <br>
 */
public class AdministradorJugadores
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	
	/**
	 * La conexi�n con el servidor
	 */
	private Connection conexion;
	
	/**
	 * Las propiedades del servidor
	 */
	private Properties propiedades;
	
    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

	/**
	 * Contructor de la clase encargado de cargar las propiedades del servidor y dejarlo listo para conectarse
	 * @param configuracion Las propiedades que configuran el servidor Debe tener las propiedades "admin.db.path", "admin.db.driver", 
	 * "admin.db.url" y "admin.db.shutdown"
	 */
	public AdministradorJugadores(Properties configuracion)
	{
		propiedades = configuracion;
		File data = new File( propiedades.getProperty( "admin.db.path" ) );
        System.setProperty( "derby.system.home", data.getAbsolutePath( ) );
	}
	
    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

	/**
     * M�todo encargado de conectar el administrador a la base de datos
     * @throws SQLException Cuando hay problemas realizando la operaci�n
     * @throws Exception Cuando hay problemas con los controladores
     */
    public void conectarABD( ) throws SQLException, Exception
    {
        String driver = propiedades.getProperty( "admin.db.driver" );
        Class.forName( driver ).getDeclaredConstructor().newInstance( );

        String url = propiedades.getProperty( "admin.db.url" );
        conexion = DriverManager.getConnection( url );
        verificarInvariante();
    }
    
    /**
     * M�todo encargado de crear la tabla para guardar los resultados. Si la tabla ya estaba creada entonces no hace nada. <br>
     * @throws SQLException Cuando hay problemas creando la tabla
     */
    public void iniciarTabla( ) throws SQLException
    {
        Statement mensajero = conexion.createStatement( );
        boolean crearTabla = false;
        try
        {
            mensajero.executeQuery( "SELECT * FROM resultados WHERE 1=2" );
        }
        catch( SQLException se )
        {
            crearTabla = true;
        }
        if( crearTabla )
        {
            mensajero.execute( "CREATE TABLE resultados (nombre varchar(32), victorias int, derrotas int, PRIMARY KEY (nombre))" );
        }
        mensajero.close( );
        verificarInvariante();
    }
    
    /**
     * M�todo encargado de detener la base de datos y desconectar el administrador
     * @throws SQLException Cuando hay problemas realizando la operaci�n
     */
    public void desconectarBD( ) throws SQLException
    { 
        conexion.close( );
        String down = propiedades.getProperty( "admin.db.shutdown" );
        try
        {
            DriverManager.getConnection( down );
        }
        catch( SQLException e )
        {
        	
        }
        verificarInvariante();
    }
    
	/**
	 * M�todo encargao de retornar el registro de todos los jugadores que se han conectado
	 * @return registro El registro de todos los jugadores
	 * @throws SQLException Cuando hay problemas con la comunicaci�n con la base de datos 
	 */
    public ArrayList<InfoJugador> consultarRegistroJugadores( ) throws SQLException
    {
        ArrayList<InfoJugador> registros = new ArrayList<InfoJugador>( );
        String comando = "SELECT nombre, victorias, derrotas FROM resultados";
        Statement mensajero = conexion.createStatement( );
        ResultSet resultado = mensajero.executeQuery( comando );
        while( resultado.next( ) )
        {
            String nombre = resultado.getString( 1 );
            int ganados = resultado.getInt( 2 );
            int perdidos = resultado.getInt( 3 );

            InfoJugador registro = new InfoJugador( nombre, ganados, perdidos );
            registros.add( registro );
        }
        resultado.close( );
        mensajero.close( );
        return registros;
    }
    
    /**
     * M�todo encargado de consultar la informaci�n de un jugador (encuentros ganados y encuentros perdidos).
     * Si no se encuentra un registro del jugador en la base de datos, entonces se crea uno nuevo.
     * @param nombre El nombre del jugador del cual se est� buscando informaci�n - nombre != null
     * @return Retorna el registro de victorias y derrotas del jugador
     * @throws SQLException Se lanza esta excepci�n si hay problemas en la comunicaci�n con la base de datos
     */
    public InfoJugador consultarRegistroJugador( String nombre ) throws SQLException
    {
        InfoJugador registro = null;
        String comando = "SELECT victorias, derrotas FROM resultados WHERE nombre ='" + nombre + "'";
        Statement mensajero = conexion.createStatement( );
        ResultSet resultado = mensajero.executeQuery( comando );

        if( resultado.next( ) )
        {
            int victorias = resultado.getInt( 1 );
            int derrotas = resultado.getInt( 2 );
            registro = new InfoJugador( nombre, victorias, derrotas );
            resultado.close( );
        }
        else
        {
            resultado.close( );
            String insert = "INSERT INTO resultados (nombre, victorias, derrotas) VALUES ('" + nombre + "', 0, 0)";
            mensajero.execute( insert );
            registro = new InfoJugador( nombre, 0, 0 );
        }
        mensajero.close( );
        verificarInvariante();
        return registro;
    }

    /**
     * M�todo encargado de registrar una victoria a un jugador
     * @param nombre El nombre del jugador al cual se le va a registrar la victoria - nombre != null && corresponde a un registro en la base de datos
     * @throws SQLException Cuando hay problemas en la comunicaci�n con la base de datos
     */
    public void registrarVictoria( String nombre ) throws SQLException
    {
        String comando = "UPDATE resultados SET victorias = victorias+1 WHERE nombre ='" + nombre + "'";
        Statement mensajero = conexion.createStatement( );
        mensajero.executeUpdate( comando );
        mensajero.close( );
        verificarInvariante();
    }

    /**
     * M�todo encargado de registrar una derrota a un jugador
     * @param nombre El nombre del jugador al cual se le va a registrar la derrota - nombre != null && corresponde a un registro en la base de datos
     * @throws SQLException Cuando hay problemas en la comunicaci�n con la base de datos
     */
    public void registrarDerrota( String nombre ) throws SQLException
    {
        String comando = "UPDATE resultados SET derrotas = derrotas+1 WHERE nombre ='" + nombre + "'";
        Statement mensajero = conexion.createStatement( );
        mensajero.executeUpdate( comando );
        mensajero.close( );
        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------
    
    /**
     * Verifica el invariante de la clase <br>
     * <b>inv:</b><br>    
     * config!=null <br>
     */
	private void verificarInvariante( )
	{
		assert propiedades != null : "Conjunto de propiedades inv�lidas";   
	}
}
