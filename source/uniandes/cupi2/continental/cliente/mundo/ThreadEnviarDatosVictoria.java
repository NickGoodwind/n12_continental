/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ThreadEnviarDatosVictoria.java 1012 2007-08-20 22:59:25Z camil-ji $
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
 * Hilo de ejecuci�n para enviar los datos cuando se declara una victoria.
 */
public class ThreadEnviarDatosVictoria extends Thread
{

    /**
     * Referencia al juego.
     */
    private JugadorContinental jugador;

    /**
     * Referencia a la ventana principal de la aplicaci�n.
     */
    private InterfazContinental principal;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    /**
     * Construye un nuevo hilo para validar una victoria.
     * @param juego Referencia al juego. juego != null.
     * @param interfaz Referencia a la ventana principal de la aplicaci�n. interfaz != null.
     */
    public ThreadEnviarDatosVictoria( JugadorContinental juego, InterfazContinental interfaz )
    {
        super( );
        jugador = juego;
        principal = interfaz;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que env�a los datos del juego al servidor para validar la victoria.<br>
     */
    public void run( )
    {
        try
        {
            if( jugador.enviarDatosVictoria( ) )
            {
                principal.victoria( true );
            }
            else
            {
                principal.victoria( false );
            }
            principal.actualizarInterfaz( );
        }
        catch( ContinentalException e )
        {
            principal.mostrarError( e );
        }
    }
}
