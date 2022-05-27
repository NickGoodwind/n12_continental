package uniandes.cupi2.continental.testServidor;

import java.io.*;
import java.net.*;
import java.util.*;

import uniandes.cupi2.continental.cliente.mundo.JugadorContinental;
import uniandes.cupi2.servidor.mundo.AdministradorJugadores;
import uniandes.cupi2.servidor.mundo.Encuentro;
import junit.framework.*;

/**
 * Esta clase verifica la implementación de los métodos de la clase Encuentro, y la forma en la que se procesan los mensajes
 */
public class EncuentroTest extends TestCase
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * El socket usado para comunicarse por el jugador 1
     */
    protected Socket socketJugador1;

    /**
     * El socket usado para comunicarse por el jugador 2
     */
    protected Socket socketJugador2;

    /**
     * El stream que envía los datos del jugador 1 al encuentro
     */
    private PrintWriter out1;

    /**
     * Es el stream de donde se leen los datos que llegan por el socket del jugador 1
     */
    private BufferedReader in1;

    /**
     * El stream que envía los datos del jugador 2 al encuentro
     */
    private PrintWriter out2;

    /**
     * Es el stream de donde se leen los datos que llegan por el socket del jugador 2
     */
    private BufferedReader in2;

    /**
     * El administrador de resultados usado por las pruebas
     */
    private AdministradorJugadores adminResultados;

    /**
     * El encuentro sobre el que se realizan las pruebas
     */
    private Encuentro encuentro;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Construye un escenario en el cual un par de sockets se conectan a un encuentro y simulan ser clientes
     */
    private void setupEscenario1( )
    {
        try
        {
            // Crear el socket del lado del servidor, que espera una conexión
            AyudantePruebasEncuentro ayudante1 = new AyudantePruebasEncuentro( );
            ayudante1.start( );

            // Darle tiempo al socket de prepararse
            Thread.sleep( 200 );

            socketJugador1 = new Socket( "localhost", 8888 );
            out1 = new PrintWriter( socketJugador1.getOutputStream( ), true );
            in1 = new BufferedReader( new InputStreamReader( socketJugador1.getInputStream( ) ) );

            // Crear el socket del lado del servidor, que espera una conexión
            AyudantePruebasEncuentro ayudante2 = new AyudantePruebasEncuentro( );
            ayudante2.start( );

            // Darle tiempo al socket de prepararse
            Thread.sleep( 200 );

            socketJugador2 = new Socket( "localhost", 8888 );
            out2 = new PrintWriter( socketJugador2.getOutputStream( ), true );
            in2 = new BufferedReader( new InputStreamReader( socketJugador2.getInputStream( ) ) );

            // Construir el Encuentro
            Socket socketEncuentro1 = ayudante1.darSocket( );
            Socket socketEncuentro2 = ayudante2.darSocket( );
            
            encuentro = new Encuentro( socketEncuentro1, socketEncuentro2, darAdministradorResultados( ) );
            encuentro.start( );
            
            // Revisar que la fase inicial del encuentro se lleve a cabo correctamente
            out1.println( JugadorContinental.JUGADOR + "jugador1" );
            out2.println( JugadorContinental.JUGADOR + "jugador2" );
            
            String infoJugador1 = in1.readLine( );
            String infoJugador2 = in1.readLine( );
            
            assertTrue( "El mensaje enviado no tiene el formato correcto: " + infoJugador1, infoJugador1.startsWith( "INFO" + ":jugador1" ) );
            assertTrue( "El mensaje enviado no tiene el formato correcto: " + infoJugador2, infoJugador2.startsWith( "INFO" + ":jugador2" ) );
            
            infoJugador2 = in2.readLine( );
            infoJugador1 = in2.readLine( );
            
            assertTrue( "El mensaje enviado no tiene el formato correcto: " + infoJugador2, infoJugador2.startsWith( "INFO" + ":jugador2" ) );
            assertTrue( "El mensaje enviado no tiene el formato correcto: " + infoJugador1, infoJugador1.startsWith( "INFO" + ":jugador1" ) );
            
            String turno1 = in1.readLine( );
            String turno2 = in2.readLine( );
            
            assertEquals( "El turno del jugador 1 no es el correcto", "PRIMERTURNO", turno1 );
            assertEquals( "El turno del jugador 2 no es el correcto", "PRIMERTURNO", turno2 );
            
            assertFalse( "El encuentro no debería aparecer como terminado", encuentro.encuentroTerminado( ) );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
            fail( "Hubo problemas creando el escenario: " + e.getMessage( ) );
        }
    }

    /**
     * Elimina los objetos y las conexiones construidos para el escenario 1
     */
    private void terminarEscenario1( )
    {
        try
        {
            // Enviar la jugada final
            out1.println( "0,0" );
            in2.readLine( );
            out2.println( Encuentro.FIN_JUEGO );
            in1.readLine( );

            // Leer el resultado final del encuentro
            String resultado1 = in1.readLine( );
            String resultado2 = in2.readLine( );

            assertEquals( "El ganador del encuentro está equivocado", Encuentro.GANADOR + ":jugador1", resultado1 );
            assertEquals( "El ganador del encuentro está equivocado", Encuentro.GANADOR + ":jugador1", resultado2 );

            assertTrue( "El encuentro debería aparecer como terminado", encuentro.encuentroTerminado( ) );

            out1.close( );
            in1.close( );
            socketJugador1.close( );

            out2.close( );
            in2.close( );
            socketJugador2.close( );

            adminResultados.desconectarBD( );
        }
        catch( Exception e )
        {
            fail( "No se debería lanzar una excepción desconectando: " + e.getMessage( ) );
        }
    }

    /**
     * Si no existe ya, construye un administrador de resultados y lo retorna. Si ya existía uno, lo retorna.
     * @return adminResultados
     */
    private AdministradorJugadores darAdministradorResultados( )
    {
        if( adminResultados == null )
        {
            File directorioData = new File( "./test/data" );
            System.setProperty( "derby.system.home", directorioData.getAbsolutePath( ) );
            Properties configuracion = new Properties( );
            configuracion.setProperty( "admin.db.url", "jdbc:derby:testAdmin;create=true" );
            configuracion.setProperty( "admin.test.url", "jdbc:derby:testAdmin" );
            configuracion.setProperty( "admin.db.driver", "org.apache.derby.jdbc.EmbeddedDriver" );
            configuracion.setProperty( "admin.db.shutdown", "jdbc:derby:;shutdown=true" );
            configuracion.setProperty( "admin.db.path", "./test/data" );

            // Construir el administrador
            adminResultados = new AdministradorJugadores( configuracion );
            try
            {
                adminResultados.conectarABD( );
            }
            catch( Exception e3 )
            {
                fail( "No se pudo conectar el administrador a la BD" );
            }
        }

        return adminResultados;
    }

    /**
     * Verifica que el escenario se construya y se destruya exitosamente
     * <b> Métodos a probar: </b> <br>
     * setupEscenario1, terminarEscenario1. <br>
     * <b> Objetivo: </b> Probar que la preparación y eliminación del escenario de prueba funcionen correctamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. La preparación y eliminación del escenario de prueba debería funcionar sin problema.
     */
    public void testEncuentro( )
    {
        setupEscenario1( );

        terminarEscenario1( );
    }

    /**
     * Revisa el comportamiento del encuentro cuando se envían mensajes entre los jugadores. <br>    
     * <b> Objetivo: </b> Probar que los mensajes entre los participantes del encuentro se envíen correctamente. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al enviar un mensaje por uno de los jugadores, el mensaje que recibe el otro debe ser el mismo.
     * 
     */
    public void testEnviarJugada( )
    {
        setupEscenario1( );

        try
        {
            // Enviar el mensaje
            String mensajeJugadaEnviado1 = Encuentro.JUGADA + ":1;1";
            out1.println( mensajeJugadaEnviado1 );
            String mensajeJugadaRecibido1 = in2.readLine( );
            assertEquals( "El mensaje fue cambiado por el encuentro", mensajeJugadaEnviado1, mensajeJugadaRecibido1 );

            // Enviar la respuesta
            String mensajeRespuestaEnviada1 = Encuentro.AGUA;
            out2.println( mensajeRespuestaEnviada1 );
            String mensajeRespuestaRecibida1 = in1.readLine( );
            assertEquals( "La respuesta fue cambiado por el encuentro", mensajeRespuestaEnviada1, mensajeRespuestaRecibida1 );

            // Enviar el mensaje en el otro sentido
            String mensajeJugadaEnviado2 = Encuentro.JUGADA + ":1;1";
            out2.println( mensajeJugadaEnviado2 );
            String mensajeJugadaRecibido2 = in1.readLine( );
            assertEquals( "El mensaje fue cambiado por el encuentro", mensajeJugadaEnviado2, mensajeJugadaRecibido2 );

            // Enviar la respuesta en el otro sentido
            String mensajeRespuestaEnviada2 = Encuentro.IMPACTO + ":Galera;false";
            out1.println( mensajeRespuestaEnviada2 );
            String mensajeRespuestaRecibida2 = in2.readLine( );
            assertEquals( "La respuesta fue cambiado por el encuentro", mensajeRespuestaEnviada2, mensajeRespuestaRecibida2 );
        }
        catch( Exception e3 )
        {
            fail( "Hubo una falla inesperada: " + e3.getMessage( ) );
        }

        terminarEscenario1( );
    }
}

