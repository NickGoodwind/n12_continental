/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ThreadEsperarJugada.java 1012 2007-08-20 22:59:25Z camil-ji $
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
 * Hilo de ejecución que espera la jugada del oponente.
 */
public class ThreadEsperarJugada extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Referencia al juego
     */
    private JugadorContinental jugador;

    /**
     * Referencia a la ventana principal de la aplicación
     */
    private InterfazContinental principal;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para esperar la jugada.
     * @param juego Referencia al juego. juego != null.
     * @param interfaz Referencia a la ventana principal de la aplicación. interfaz != null.
     */
    public ThreadEsperarJugada( JugadorContinental juego, InterfazContinental interfaz )
    {
        super( );
        jugador = juego;
        principal = interfaz;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecución del hilo que espera la jugada del oponente. <br>
     * Cuando se tiene información sobre la jugada del oponente entonces se actualiza la interfaz.<br>
     * Si el juego debe terminar entonces muestra quien fue el ganador y termina el encuentro y la conexión al servidor.
     */
    public void run( )
    {
        try
        {
            jugador.esperarJugada( );
            if( jugador.juegoTerminado( ) )
            {
                principal.mostrarInformacionGanador( jugador.darVictoriaValidaOponente( ) );
                principal.actualizarInterfaz( );
            }
            else
            {
                principal.desactivarBotonJugar( );
                principal.actualizarInterfaz( );
            }
        }
        catch( ContinentalException e )
        {
            principal.mostrarError( e );
            principal.desactivarBarajas( );
        }

    }
}
