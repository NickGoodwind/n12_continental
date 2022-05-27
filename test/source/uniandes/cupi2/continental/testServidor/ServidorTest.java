package uniandes.cupi2.continental.testServidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

import uniandes.cupi2.continental.cliente.mundo.JugadorContinental;
import uniandes.cupi2.servidor.mundo.AdministradorJugadores;
import uniandes.cupi2.servidor.mundo.Encuentro;
import junit.framework.TestCase;

/**
 * @author NickGoodwind
 * Esta es la clase usada para verificar los métodos de la clase Continental.
 */
public class ServidorTest extends TestCase
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * El tiempo máximo que se va a esperar a que se inicien los encuentros
     */
    private static final long TIMEOUT = 1000;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * La clase donde se harán las pruebas
     */
    private DecoradorServidor servidor;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Método constructor de la clase que crea un nuevo servidor para ejecutar las pruevas
     */
    private void setupEscenario1( )
    {
        try
        {
            servidor = new DecoradorServidor( "./test/data/servidor.properties", TIMEOUT );
        }
        catch( Exception e )
        {
            fail( "No debería haber problemas construyendo el servidor" );
        }
    }

    /**
     * Método que es llamado al terminar la ejecución de cada prueba
     */
    protected void tearDown( ) throws Exception
    {   
        servidor.darAdministrador( ).desconectarBD( );
    }

    /**
     * Método encargado de verificar si cuando se construye el servidor la conexión con la base de datos se establezce correctamente. <br>
     * <b> Métodos a probar: </b> <br>
     * DecoradorServidor. <br>
     * <b> Objetivo: </b> Probar que al iniciar el servidor, la conexión con la base de datos haya sido establecida. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al crear el servidor, el administrado de resultados no debería ser null.
     */
    public void testServidorContinental( )
    {
        setupEscenario1( );

        AdministradorJugadores admin = servidor.darAdministrador( );
        assertNotNull( "El administrador de resultados no puede ser null", admin );
    }

    /**
     * Método encargado de verificar el método darListaActualizadaEncuentros. <br>
     * Usando la clase AyudantePruebasServidor para construir un Thread aparte en el que un Servidor espera conexiones entrantes, desde 
     * esta clase de pruebas se establecen conexiones al servidor para construir encuentros. <br>
     *  <b> Métodos a probar: </b> <br>
     * darListaActualizadaEncuentros. <br>
     * <b> Objetivo: </b> Probar que método darlistaActualizadaEncuentros, retorne los encuentros que realmente existen. <br>
     * <b> Resultados esperados: </b> <br>
     * 1. Al pedir la lista de encuentros, ésta sólo debe contener los encuentros que se están ejecutando en el momento.     
     */
    public void testDarListaActualizadaEncuentros( )
    {
        setupEscenario1( );

        AyudantePruebasServidor ayudante = new AyudantePruebasServidor( servidor );
        ayudante.start( );
        try
        {
            Socket s1 = new Socket( "localhost", 9998 );
            PrintWriter pw1 = new PrintWriter( s1.getOutputStream( ), true );

            Socket s2 = new Socket( "localhost", 9998 );
            PrintWriter pw2 = new PrintWriter( s2.getOutputStream( ), true );

            pw1.println( JugadorContinental.JUGADOR + "pablo" );
            pw2.println( JugadorContinental.JUGADOR + "mario" );

            servidor.seInicioEncuentro( );

            Collection<Encuentro> lista = servidor.actualizarJuegos( );
            assertTrue( "No se creó el encuentro", lista.size( ) == 1 );
            Encuentro e1 = ( Encuentro )lista.toArray( )[ 0 ];
            String[] strE1 = e1.toString( ).split( "," );
            String nombre1 = strE1[0];
            String nombre2 = strE1[1];
            assertTrue( "En el encuentro no aparecen los nombres de los jugadores", nombre1.endsWith( "pablo" ) );
            assertTrue( "En el encuentro no aparecen los nombres de los jugadores", nombre2.endsWith( "mario" ) );
        
            ayudante.detener( );     
        }
        catch( IOException e )
        {	
            ayudante.detener( );
            fail( "No debería fallar: " + e.getMessage( ) );
        } 
    }
}
