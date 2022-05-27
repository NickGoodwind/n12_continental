/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ThreadConectar.java 1012 2007-08-20 22:59:25Z camil-ji $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Equipo Cupi2 2014
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.cliente.mundo;

import uniandes.cupi2.continental.cliente.interfaz.InterfazContinental;

/**
 * Hilo de ejecución cuando para conectar al cliente con el servidor.
 */
public class ThreadConectar extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Referencia al juego.
     */
    private JugadorContinental jugador;

    /**
     * Referencia a la ventana principal de la aplicación.
     */
    private InterfazContinental principal;

    /**
     * Nombre que utilizará el jugador.
     */
    private String nombre;

    /**
     * Dirección para localizar al servidor.
     */
    private String servidor;

    /**
     * Puerto a través del cual se realizará la conexión con el servidor.
     */
    private int puerto;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo para conectarse al servidor.
     * @param juego Referencia al juego. juego != null.
     * @param interfaz Referencia a la ventana principal de la aplicación. interfaz != null.
     * @param nombreJugador Nombre que utilizará el jugador. nombreJugador != null.
     * @param direccionServidor Dirección para localizar al servidor. direccionServidor != null.
     * @param puertoServidor Puerto a través del cual se realizará la conexión con el servidor. puertoServidor != null.
     */
    public ThreadConectar( JugadorContinental juego, InterfazContinental interfaz, String nombreJugador, String direccionServidor, int puertoServidor )
    {
        jugador = juego;
        principal = interfaz;
        nombre = nombreJugador;
        servidor = direccionServidor;
        puerto = puertoServidor;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecución del hilo que realiza la conexión con el servidor.<br>
     */
    public void run( )
    {

        try
        {
            jugador.conectar( nombre, servidor, puerto );
            principal.actualizarInterfaz( );
            principal.actualizarDatosOponente( );
            principal.activarBarajas( );
            if( jugador.darEstado( ).equals( JugadorContinental.ESPERANDO_JUGADA ) )
                principal.esperarJugada( );
            else
                principal.desactivarBotonJugar( );
        }
        catch( ContinentalException e )
        {
            principal.mostrarError( e );
        }

    }
}
