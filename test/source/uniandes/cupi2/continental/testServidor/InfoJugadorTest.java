/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: InfoJugadorTest.java 1010 2007-08-17 00:57:38Z camil-ji $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Equipo Cupi2 2014
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.testServidor;

import uniandes.cupi2.servidor.mundo.InfoJugador;
import junit.framework.TestCase;

/**
 * Clase usada para verificar que los métodos de la clase InfoJugador estén correctamente implementados.
 */
public class InfoJugadorTest extends TestCase
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase sobre la cual se va a realizar las pruebas.
     */
    private InfoJugador infoJugador;
 
    // -----------------------------------------------------------------
    // Escenarios
    // -----------------------------------------------------------------    
        
    /**
     * Escenario 1: Construye un nuevo InfoJugador.
     */
    private void setupEscenario1( )
    {
        infoJugador = new InfoJugador("Jugador",0,0);
    }

    // -----------------------------------------------------------------
    // Casos de prueba
    // -----------------------------------------------------------------
    
    /**
     * Prueba 1: Verifica que los atributos queden correctamente inicializados.
     */
    public void testInfoJugador( )
    {
     
        setupEscenario1( );
        assertTrue( "Error al dar el nombre.", infoJugador.darNombre( ).equals( "Jugador" ) );
        assertTrue( "Error al dar victorias.", infoJugador.darVictorias( ) == 0 );
        assertTrue( "Error al dar derrotas.", infoJugador.darDerrotas( ) == 0 );
    }
}
