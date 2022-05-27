/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ThreadEsperarCarta.java 1012 2007-08-20 22:59:25Z camil-ji $
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
 * Hilo de ejecuci�n cuando se espera una carta del servidor.
 */
public class ThreadEsperarCarta extends Thread
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Representa a la baraja inicial.
     */
    public final static String INICIAL = "INICIAL";

    /**
     * Representa a la baraja jugada.
     */
    public final static String JUGADA = "JUGADA";

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
     * Baraja de la cual se pidi� una carta.
     */
    private String baraja;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para recibir una carta.
     * @param juego Jugador de continental.
     * @param interfaz Ventana principal de la aplicaci�n.
     * @param nBaraja Baraja de la cual se pidi� una carta.
     */
    public ThreadEsperarCarta( JugadorContinental juego, InterfazContinental interfaz, String nBaraja )
    {
        super( );

        jugador = juego;
        principal = interfaz;
        baraja = nBaraja;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que realiza el env�o del mensaje para pedir una carta y espera la respuesta con la carta del servidor. <br>
     * Cuando se tiene informaci�n sobre el resultado de la carta entonces se actualiza la interfaz.<br>
     */
    public void run( )
    {

        try
        {
            if( baraja.equals( INICIAL ) )
                jugador.pedirCartaBarajaInicial( );
            else if( baraja.equals( JUGADA ) )
                jugador.pedirCartaBarajaJugada( );

            principal.actualizarInterfaz( );
        }
        catch( ContinentalException e )
        {
            principal.mostrarError( e );
        }

    }
}
