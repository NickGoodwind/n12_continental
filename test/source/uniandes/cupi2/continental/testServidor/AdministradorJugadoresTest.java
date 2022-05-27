/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: AdministradorJugadoresTest.java 1010 2007-08-17 00:57:38Z camil-ji $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Carlos Andr�s Vega - 18-mar-2007
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.testServidor;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import uniandes.cupi2.servidor.mundo.AdministradorJugadores;
import uniandes.cupi2.servidor.mundo.InfoJugador;
import junit.framework.TestCase;

/**
 * Clase usada para verificar que los m�todos de la clase AdministradorJugadores est�n correctamente implementados.
 */
public class AdministradorJugadoresTest extends TestCase
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase sobre la cual se van a realizar las pruebas.
     */
    private AdministradorJugadores administradorJugadores;

    /**
     * Conjunto de propiedades para configurar las pruebas.
     */
    private Properties configuracion;

    /**
     * Conexi�n usada para las pruebas.
     */
    private Connection conexionPruebas;

    // -----------------------------------------------------------------
    // Escenarios
    // -----------------------------------------------------------------

    /**
     * Escenario 1: Inicializa la base de datos y construye un nuevo AdministradorResultados conectado a la base de datos.
     */
    private void setupEscenario1( )
    {
        administradorJugadores = null;
        File directorioData = new File( "./test/data" );
        System.setProperty( "derby.system.home", directorioData.getAbsolutePath( ) );
        configuracion = new Properties( );
        configuracion.setProperty( "admin.db.url", "jdbc:derby:testAdmin;create=true" );
        configuracion.setProperty( "admin.test.url", "jdbc:derby:testAdmin" );
        configuracion.setProperty( "admin.db.driver", "org.apache.derby.jdbc.EmbeddedDriver" );
        configuracion.setProperty( "admin.db.shutdown", "jdbc:derby:;shutdown=true" );
        configuracion.setProperty( "admin.db.path", "./test/data" );

        // Conectar a la base de datos
        try
        {
            String driver = configuracion.getProperty( "admin.db.driver" );
            Class.forName( driver ).getDeclaredConstructor().newInstance( );
            String url = configuracion.getProperty( "admin.db.url" );
            conexionPruebas = DriverManager.getConnection( url );
        }
        catch( Exception e )
        {
            fail( "Fall� la conexi�n a la base de datos" );
        }

        try
        {
            // Crear la tabla de resultados si es necesario
            crearTablas( );
        }
        catch( SQLException e1 )
        {
            fail( "No se pudo crear la tabla" );
        }

        try
        {
            // Limpia todos los datos existentes e inserta datos iniciales para las pruebas
            inicializarTabla( );
        }
        catch( SQLException e2 )
        {
            fail( "No se pudo agregar valores a la tabla" );
        }

        // Construir el administrador
        administradorJugadores = new AdministradorJugadores( configuracion );
        try
        {
            administradorJugadores.conectarABD( );
        }
        catch( Exception e3 )
        {
            fail( "No se pudo conectar el administrador a la BD" );
        }
    }

    /**
     * Crea las tablas necesarias para el administrador de resultados.
     * @throws SQLException Si hay problemas creando la tabla.
     */
    private void crearTablas( ) throws SQLException
    {
        Statement s = conexionPruebas.createStatement( );

        boolean crearTabla = false;
        try
        {
            // Verificar si ya existe la tabla resultados
            s.executeQuery( "SELECT * FROM resultados" );
        }
        catch( SQLException se )
        {
            crearTabla = true;
        }

        if( crearTabla )
        {
            s.execute( "CREATE TABLE resultados (nombre varchar(32), victorias int, derrotas int, PRIMARY KEY (nombre))" );
        }
        s.close( );
    }

    /**
     * Limpia la tabla de resultados e inserta valores iniciales para las pruebas.
     * @throws SQLException Si hay problemas inicializando la tabla.
     */
    private void inicializarTabla( ) throws SQLException
    {
        Statement s = conexionPruebas.createStatement( );

        // Limpiar la tabla resultados
        s.executeUpdate( "DELETE FROM resultados" );

       
        // Insertar los datos iniciales
        s.execute( "INSERT INTO resultados (nombre, victorias, derrotas) VALUES ('Alicia', 1,2)" );
        s.execute( "INSERT INTO resultados (nombre, victorias, derrotas) VALUES ('Beto', 2,1)" );
        s.execute( "INSERT INTO resultados (nombre, victorias, derrotas) VALUES ('Carlos', 1,2)" );
        s.execute( "INSERT INTO resultados (nombre, victorias, derrotas) VALUES ('Daniel', 2,1)" );
    }

    /**
     * Este m�todo, que se llama despu�s de cada prueba, se encarga de detener el administrador y desconectarlo de la base de datos.
     * @throws Exception Si hay problemas en la desconexi�n.
     */
    protected void tearDown( ) throws Exception
    {
        // Desconectar el administrador de la base de datos
        try
        {
            if( administradorJugadores != null )
            {
                administradorJugadores.desconectarBD( );
            }
        }
        catch( Exception npe )
        {
            fail( "No se deber�a lanzar una excepci�n desconectando" );
        }
    }

    // -----------------------------------------------------------------
    // Casos de prueba
    // -----------------------------------------------------------------
    
    /**
     * Prueba 1: Verifica el m�todo consultarRegistrosJugadores revisando que los datos retornado correspondan a los insertados. <br>
     * <b> M�todos a probar: </b> <br>
     * consultarRegistrosJugadores. <br>
     * <b> Objetivo: </b> Probar que el m�todo consultarRegistrosJugaores() recupere correctamente los jugadores que han sido insertados. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al pedir la lista de jugadores, se deben obtener cada uno de los jugadores que ha sido insertado previamente.
     */
    public void testConsultarRegistrosJugadores( )
    {
        // Configuraci�n b�sica
        setupEscenario1( );

        // Consultar los Jugadores
        try
        {
            Collection<InfoJugador> jugadores = administradorJugadores.consultarRegistroJugadores( );
            assertEquals( "El n�mero de jugadores no es correcto", 4, jugadores.size( ) );

            String nom0, nom1, nom2, nom3;
            Iterator<InfoJugador> iter = jugadores.iterator( );

            InfoJugador j = ( InfoJugador )iter.next( );
            nom0 = j.darNombre( );
            assertTrue( "Se retorn� un jugador equivocado", j.darNombre( ).equals( "Alicia" ) || j.darNombre( ).equals( "Beto" ) || j.darNombre( ).equals( "Carlos" ) || j.darNombre( ).equals( "Daniel" ) );

            j = ( InfoJugador )iter.next( );
            nom1 = j.darNombre( );
            assertTrue( "Se retorn� un jugador equivocado", j.darNombre( ).equals( "Alicia" ) || j.darNombre( ).equals( "Beto" ) || j.darNombre( ).equals( "Carlos" ) || j.darNombre( ).equals( "Daniel" ) );

            j = ( InfoJugador )iter.next( );
            nom2 = j.darNombre( );
            assertTrue( "Se retorn� un jugador equivocado", j.darNombre( ).equals( "Alicia" ) || j.darNombre( ).equals( "Beto" ) || j.darNombre( ).equals( "Carlos" ) || j.darNombre( ).equals( "Daniel" ) );

            j = ( InfoJugador )iter.next( );
            nom3 = j.darNombre( );
            assertTrue( "Se retorn� un jugador equivocado", j.darNombre( ).equals( "Alicia" ) || j.darNombre( ).equals( "Beto" ) || j.darNombre( ).equals( "Carlos" ) || j.darNombre( ).equals( "Daniel" ) );

            assertFalse( "Se list� dos veces el mismo jugador", nom0.equals( nom1 ) || nom0.equals( nom2 ) || nom0.equals( nom3 ) || nom1.equals( nom2 ) || nom1.equals( nom3 ) || nom2.equals( nom3 ) );
        }
        catch( Exception e )
        {
            fail( "No se deber�a lanzar una excepci�n" );
        }
    }

    /**
     * Prueba 2: Verifica el m�todo consultarRegistroJugador para el caso en el que se quiere consultar la informaci�n de un<br>
     * jugador que ya exist�a en la base de datos. <br>
     * <b> M�todos a probar: </b> <br>
     * consultarRegistroJugador. <br>
     * <b> Objetivo: </b> Probar que el m�todo consultarRegistroJugador() recupere correctamente el registro de un jugador insertado<br>
     * previamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al pedir el registro de un jugador, su informaci�n debe corresponder a la insertada originalmente.
     * 
     */
    public void testConsultarRegistroJugadorViejo( )
    {
        // Configuraci�n b�sica
        setupEscenario1( );

        // Consultar un jugador viejo
        try
        {
            InfoJugador jugador = administradorJugadores.consultarRegistroJugador( "Alicia" );
            assertEquals( "El nombre del jugador es incorrecto", "Alicia", jugador.darNombre( ) );
            assertEquals( "El n�mero de encuentros ganados es incorrecto", 1, jugador.darVictorias( ) );
            assertEquals( "El n�mero de encuentros perdidos es incorrecto", 2, jugador.darDerrotas( ) );
        }
        catch( Exception e )
        {
            fail( "No se deber�a lanzar una excepci�n" );
        }
    }

    /**
     * Prueba 3: Verifica el m�todo consultarRegistroJugador para el caso en el que se quiere consultar la informaci�n <br>
     * de un jugador que no existe en la base de datos.<br>
     * En este caso el m�todo consultarRegistroJugador deber�a crear un nuevo registro con valores en 0. <br>
     * <b> M�todos a probar: </b> <br>
     * consultarRegistroJugador. <br>
     * <b> Objetivo: </b> Probar que el m�todo consultarRegistroJugador() cree un jugador cuando �ste no se encuentra registrado<br>
     * en la base de datos. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al pedir el registro de un jugador que no existe, �ste debe ser creado en la base de datos con los valores en cero.<br>
     * 2. Al registrar una victoria del nuevo jugador, s�lo debe cambiar su n�mero de encuentros ganados.
     * 
     */
    public void testConsultarRegistroJugadorNuevo( )
    {
        // Configuraci�n b�sica
        setupEscenario1( );

        // Consultar un jugador nuevo
        try
        {
            InfoJugador jugador = administradorJugadores.consultarRegistroJugador( "Edgar" );
            assertEquals( "El nombre del jugador es incorrecto", "Edgar", jugador.darNombre( ) );
            assertEquals( "El n�mero de encuentros ganados es incorrecto", 0, jugador.darVictorias( ) );
            assertEquals( "El n�mero de encuentros perdidos es incorrecto", 0, jugador.darDerrotas( ) );

            // Modificar el jugador
            administradorJugadores.registrarVictoria( "Edgar" );

            // Consultar nuevamente el jugador para verificar que se cre� un registro
            jugador = administradorJugadores.consultarRegistroJugador( "Edgar" );
            assertEquals( "El nombre del jugador es incorrecto", "Edgar", jugador.darNombre( ) );
            assertEquals( "El n�mero de encuentros ganados es incorrecto", 1, jugador.darVictorias( ) );
            assertEquals( "El n�mero de encuentros perdidos es incorrecto", 0, jugador.darDerrotas( ) );
        }
        catch( Exception e )
        {
            fail( "No se deber�a lanzar una excepci�n" );
        }
    }

    /**
     * Prueba 4: Verifica el m�todo registrarVictoria. <br>
     * <b> M�todos a probar: </b> <br>
     * registrarVictoria. <br>
     * <b> Objetivo: </b> Probar que el m�todo registrarVictoria() modifique correctamente el n�mero<br>
     * de encuentros ganados de un jugador. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al registrar la victoria de un jugador, su n�mero de encuentros ganados se debe incrementar en uno.
     */
    public void testRegistrarVictoria( )
    {
        // Configuraci�n b�sica
        setupEscenario1( );

        try
        {
            // Modificar el jugador
            administradorJugadores.registrarVictoria( "Alicia" );

            // Consultar el jugador para verificar que se modific� el registro
            InfoJugador jugador = administradorJugadores.consultarRegistroJugador( "Alicia" );
            assertEquals( "El nombre del jugador es incorrecto", "Alicia", jugador.darNombre( ) );
            assertEquals( "El n�mero de encuentros ganados es incorrecto", 2, jugador.darVictorias( ) );
            assertEquals( "El n�mero de encuentros perdidos es incorrecto", 2, jugador.darDerrotas( ) );
        }
        catch( Exception e )
        {
            fail( "No se deber�a lanzar una excepci�n" );
        }
    }

    /**
     * Prueba 5: Verifica el m�todo registrarDerrota. <br>
     * <b> M�todos a probar: </b> <br>
     * registrarDerrota. <br>
     * <b> Objetivo: </b> Probar que el m�todo registrarDerrota() recupere correctamente el n�mero de registro perdidos de un jugador. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al registrar la derrota de un jugador, su n�mero de encuentros perdidos se debe incrementar en uno.
     */
    public void testRegistrarDerrota( )
    {
        // Configuraci�n b�sica
        setupEscenario1( );

        try
        {
            // Modificar el jugador
            administradorJugadores.registrarDerrota( "Alicia" );

            // Consultar el jugador para verificar que se modific� el registro
            InfoJugador jugador = administradorJugadores.consultarRegistroJugador( "Alicia" );
            assertEquals( "El nombre del jugador es incorrecto", "Alicia", jugador.darNombre( ) );
            assertEquals( "El n�mero de encuentros ganados es incorrecto", 1, jugador.darVictorias( ) );
            assertEquals( "El n�mero de encuentros perdidos es incorrecto", 3, jugador.darDerrotas( ) );
        }
        catch( Exception e )
        {
            fail( "No se deber�a lanzar una excepci�n" );
        }
    }
}
