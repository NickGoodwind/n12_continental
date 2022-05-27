/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: CartaTest.java 1010 2007-08-17 00:57:38Z camil-ji $
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
import uniandes.cupi2.continental.cliente.mundo.Carta;
import junit.framework.TestCase;

/**
 * Clase usada para verificar que los métodos de la clase Carta estén correctamente implementados.
 */
public class CartaTest extends TestCase
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase sobre la cual se van a realizar las pruebas
     */
    private Carta carta;

    // -----------------------------------------------------------------
    // Escenarios
    // -----------------------------------------------------------------
    
    /**
     * Construye una nueva carta.
     */
    private void setupEscenario1( )
    {
        carta = new Carta( "PICAS", "2", "2picas.png" );
    }

    // -----------------------------------------------------------------
    // Casos de prueba
    // -----------------------------------------------------------------
    
    /**
     * Prueba 1: Verifica que el constructor esté correctamente implementado.
     */
    public void testCarta( )
    {
        setupEscenario1( );
        assertTrue( "Error al dar el palo.", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al dar la representación.", carta.darRepresentacion( ).equals( "2" ) );
        assertTrue( "Error al dar la imagen.", carta.darImagen( ).equals( "2picas.png" ) );
    }

    /**
     * Prueba 2: Verifica que comparar esté correctamente implementado.
     */
    public void testCompararCarta( )
    {
        setupEscenario1( );

        Carta carta2 = new Carta( "PICAS", "AS", "apicas.png" );
        Carta carta3 = new Carta( "PICAS", "3", "3picas.png" );

        assertTrue( "Error al compara una carta con valor igual.", carta.comparar( carta ) == 0 );
        assertTrue( "Error al compara una carta con un valor menor.", carta.comparar( carta2 ) == 1 );
        assertTrue( "Error al compara una carta con un valor mayor.", carta.comparar( carta3 ) == -1 );
    }
}
