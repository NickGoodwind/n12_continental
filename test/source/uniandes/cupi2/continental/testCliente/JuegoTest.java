/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: JuegoTest.java 1013 2007-08-20 23:04:33Z camil-ji $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Equipo Cupi2 2014
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.testCliente;

import java.util.ArrayList;

import junit.framework.TestCase;
import uniandes.cupi2.continental.cliente.mundo.*;

/**
 * Clase usada para verificar que los métodos de la clase Juego estén correctamente implementados.
 */
public class JuegoTest extends TestCase
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase sobre la cual se realizaran las pruebas.
     */
    private Juego juego;

    // -----------------------------------------------------------------
    // Escenarios
    // -----------------------------------------------------------------
    
    /**
     * Escenario 1: Construye un nuevo juego.
     */
    private void setupEscenario1( )
    {
        juego = new Juego( );
    }
    
    // -----------------------------------------------------------------
    // Casos de prueba
    // -----------------------------------------------------------------

    /**
     * Prueba 1: Verifica que agregarCarta esté correctamente implementado.
     */
    public void testAgregarCarta( )
    {
        setupEscenario1( );
        juego.agregarCarta( "PICAS", "2", "2picas.png" );

        ArrayList cartas = juego.darJuego( );
        Carta carta = ( Carta )cartas.get( 0 );
        assertTrue( "Error al agregar carta. El tamaño de la barajo no cambió.", cartas.size( ) == 1 );
        assertTrue( "Error al agregar carta. Debería ser de picas.", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al agregar carta. Debería ser un 2.", carta.darRepresentacion( ).equals( "2" ) );
        assertTrue( "Error al agregar carta. La imagen no coincide.", carta.darImagen( ).equals( "2picas.png" ) );
    }

    /**
     * Prueba 2: Verifica que modificarCartaTemporal esté correctamente implementado.
     */
    public void testModificarCartaTemporal( )
    {
        setupEscenario1( );
        juego.modificarCartaTemporal( "PICAS", "2", "2picas.png" );

        Carta carta = juego.darCartaTemporal( );
        assertTrue( "Error al modificar carta temporal. Debería ser de picas.", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al modificar carta temporal. Debería ser un 2.", carta.darRepresentacion( ).equals( "2" ) );
        assertTrue( "Error al modificar carta temporal. La imagen no coincide.", carta.darImagen( ).equals( "2picas.png" ) );
    }

    /**
     * Prueba 3: Verifica que modificarCartaTemporal esté correctamente implementado.
     */
    public void testModificarCartaTemporal2( )
    {
        setupEscenario1( );
        juego.modificarCartaTemporal( new Carta( "PICAS", "2", "2picas.png" ) );

        Carta carta = juego.darCartaTemporal( );
        assertTrue( "Error al modificar carta temporal. Debería ser de picas.", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al modificar carta temporal. Debería ser un 2.", carta.darRepresentacion( ).equals( "2" ) );
        assertTrue( "Error al modificar carta temporal. La imagen no coincide.", carta.darImagen( ).equals( "2picas.png" ) );
    }

    /**
     * Prueba 4: Verifica que modificarCartaBarajaJugada esté correctamente implementado.
     */
    public void testModificarCartaBarajaJugada( )
    {
        setupEscenario1( );
        juego.modificarCartaBarajaJugada( "PICAS", "2", "2picas.png" );

        Carta carta = juego.darCartaBarajaJugada( );
        assertTrue( "Error al modificar carta baraja jugada. Debería ser de picas.", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al modificar carta baraja jugada. Debería ser un 2.", carta.darRepresentacion( ).equals( "2" ) );
        assertTrue( "Error al modificar carta baraja Jugada. La imagen no coincide.", carta.darImagen( ).equals( "2picas.png" ) );
    }

    /**
     * Prueba 5: Verifica que modificarCartaBarajaJugada esté correctamente implementado.
     */
    public void testModificarCartaBarajaJugada2( )
    {
        setupEscenario1( );
        juego.modificarCartaBarajaJugada( new Carta( "PICAS", "2", "2picas.png" ) );

        Carta carta = juego.darCartaBarajaJugada( );
        assertTrue( "Error al modificar carta baraja jugada. Debería ser de picas.", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al modificar carta baraja jugada. Debería ser un 2.", carta.darRepresentacion( ).equals( "2" ) );
        assertTrue( "Error al modificar carta baraja Jugada. La imagen no coincide.", carta.darImagen( ).equals( "2picas.png" ) );
    }
}
