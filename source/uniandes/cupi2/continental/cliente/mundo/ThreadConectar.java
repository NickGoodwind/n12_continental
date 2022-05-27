/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ThreadConectar.java 1012 2007-08-20 22:59:25Z camil-ji $
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

import uniandes.cupi2.continental.cliente.interfaz.InterfazContinental;

/**
 * Hilo de ejecuci�n cuando para conectar al cliente con el servidor.
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
     * Referencia a la ventana principal de la aplicaci�n.
     */
    private InterfazContinental principal;

    /**
     * Nombre que utilizar� el jugador.
     */
    private String nombre;

    /**
     * Direcci�n para localizar al servidor.
     */
    private String servidor;

    /**
     * Puerto a trav�s del cual se realizar� la conexi�n con el servidor.
     */
    private int puerto;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo para conectarse al servidor.
     * @param juego Referencia al juego. juego != null.
     * @param interfaz Referencia a la ventana principal de la aplicaci�n. interfaz != null.
     * @param nombreJugador Nombre que utilizar� el jugador. nombreJugador != null.
     * @param direccionServidor Direcci�n para localizar al servidor. direccionServidor != null.
     * @param puertoServidor Puerto a trav�s del cual se realizar� la conexi�n con el servidor. puertoServidor != null.
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
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que realiza la conexi�n con el servidor.<br>
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
