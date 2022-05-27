/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: Juego.java 1013 2007-08-20 23:04:33Z camil-ji $
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

import java.util.ArrayList;

/**
 * Clase que representa el juego de cartas que posee un jugador 
 * <b>inv: </b><br>
 * cartasEnJuego contiene máximo 7 cartas.<br>
 */
public class Juego
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Cartas que posee el jugador.
     */
    private ArrayList cartasEnJuego;

    /**
     * Carta que el jugador va a descartar.
     */
    private Carta temporal;

    /**
     * Carta que se encuentra en la baraja donde se descartan las cartas.
     */
    private Carta cartaBarajaJugada;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del juego <b> 
     * post: </b> Se inicializaron cartas en juego. cartasEnJuego != null. <br>
     * Se inicializa la carta temporal en null. temporal == null. <br>
     * Se inicializa la carta de la baraja jugada en null. cartaBarajaJugada == null.
     * 
     */
    public Juego( )
    {
        cartasEnJuego = new ArrayList( );
        temporal = null;
        cartaBarajaJugada = null;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Agrega una carta al juego. 
     * <b> pre: </b> La lista de cartas se encuentra inicializada. cartasEnJuego != null <br>
     * <b> post: </b> Se adiciona una nueva carta a la lista de cartas del jugador. <br>
     * @param palo Palo de la carta. palo != null.
     * @param representacion Representación de la carta. representacion != null.
     * @param imagen Ruta de la imagen que representa a la carta. imagen != null.
     */
    public void agregarCarta( String palo, String representacion, String imagen )
    {
        cartasEnJuego.add( new Carta( palo, representacion, imagen ) );
        verificarInvariante( );
    }

    /**
     * Devuelve la lista que contiene las cartas del juego. <b> 
     * pre: </b> La lista de cartas se encuentra inicializada. cartasEnJuego != null. <br>
     * @return Cartas que posee el jugador.
     */
    public ArrayList darJuego( )
    {
        return cartasEnJuego;
    }

    /**
     * Devuelve la carta temporal.
     * @return Carta carta que el jugador puede descartar.
     */
    public Carta darCartaTemporal( )
    {
        return temporal;
    }

    /**
     * Modificar la carta temporal.
     * @param palo Palo de la carta. palo != null.
     * @param representacion Representación de la carta. representacion != null.
     * @param imagen Ruta de la imagen que representa a la carta. imagen != null.
     */
    public void modificarCartaTemporal( String palo, String representacion, String imagen )
    {
        temporal = new Carta( palo, representacion, imagen );
    }

    /**
     * Modifica la carta temporal.
     * @param carta Nueva carta para modificar la carta temporal.
     */
    public void modificarCartaTemporal( Carta carta )
    {
        temporal = carta;
    }

    /**
     * Devuelve la carta de la baraja jugada.
     * @return Carta de la baraja jugada.
     */
    public Carta darCartaBarajaJugada( )
    {
        return cartaBarajaJugada;
    }

    /**
     * Modifica la carta actual de la baraja jugada.
     * @param palo Palo de la carta. palo != null.
     * @param representacion Representación de la carta. representacion != null.
     * @param imagen Ruta de la imagen que representa a la carta. imagen != null.
     */
    public void modificarCartaBarajaJugada( String palo, String representacion, String imagen )
    {
        cartaBarajaJugada = new Carta( palo, representacion, imagen );
    }

    /**
     * Modifica la carta actual de la baraja jugada.
     * @param carta Nueva carta para modificar la cata de la baraja jugada.
     */
    public void modificarCartaBarajaJugada( Carta carta )
    {
        cartaBarajaJugada = carta;

    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verificar el invariante de la clase Juego.<br>
     * <b>inv: </b><br>
     * cartasEnJuego contiene máximo 7 cartas. <br>
     */
    private void verificarInvariante( )
    {
        assert ( cartasEnJuego.size( ) <= 7 ) : "No puede contener más de siete cartas.";
    }

}
