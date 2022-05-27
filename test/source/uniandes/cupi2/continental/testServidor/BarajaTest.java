/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: BarajaTest.java 1010 2007-08-17 00:57:38Z camil-ji $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import uniandes.cupi2.continental.cliente.mundo.Carta;
import uniandes.cupi2.servidor.mundo.Baraja;
import junit.framework.TestCase;

/**
 * Clase usada para verificar que los métodos de la clase Baraja estén correctamente implementados.
 */
public class BarajaTest extends TestCase
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Ruta donde se encuentra el archivo con la definición de las cartas.
     */
    private final static String RUTA = "./data/cartas.properties";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase sobre la cual se realizaran las pruebas.
     */
    private Baraja baraja;

    // -----------------------------------------------------------------
    // Escenarios
    // -----------------------------------------------------------------

    /**
     * Escenario 1:Construye una nueva baraja con los datos encontrados en el archivo cartas.properties.
     */
    private void setupEscenario1( )
    {
        try
        {
            baraja = new Baraja( );
            FileInputStream fis = new FileInputStream( new File( RUTA ) );
            Properties propiedades = new Properties( );
            propiedades.load( fis );

            String dato;
            String palo;
            String numeracion;
            String imagen;

            String aux;
            dato = "total.cartas";
            aux = propiedades.getProperty( dato );
            int cantidad = Integer.parseInt( aux );

            for( int cont = 1; cont <= cantidad; cont++ )
            {
                dato = "carta" + cont + ".palo";
                palo = propiedades.getProperty( dato );

                dato = "carta" + cont + ".numeracion";
                numeracion = propiedades.getProperty( dato );

                dato = "carta" + cont + ".imagen";
                imagen = propiedades.getProperty( dato );

                Carta carta = new Carta( palo, numeracion, imagen );
                baraja.adicionarCartaAlFinal( carta );

                fis.close( );
            }
        }
        catch( IOException e )
        {
            fail( );
        }
    }

    /**
     * Escenario 2: Construye una baraja vacía
     * 
     */
    private void setupEscenario2( )
    {
        baraja = new Baraja( );
    }
    
    /**
     * Escenario 3: Contruye una baraja con 7 cartas que no es válida para ganar
     */
    private void setupEscenario3( )
    {
    	baraja = new Baraja( );
    	try
        {
            baraja = new Baraja( );
            FileInputStream fis = new FileInputStream( new File( "./data/cartas3.properties" ) );
            Properties propiedades = new Properties( );
            propiedades.load( fis );

            String dato;
            String palo;
            String numeracion;
            String imagen;

            String aux;
            dato = "total.cartas";
            aux = propiedades.getProperty( dato );
            int cantidad = Integer.parseInt( aux );

            for( int cont = 1; cont <= cantidad; cont++ )
            {
                dato = "carta" + cont + ".palo";
                palo = propiedades.getProperty( dato );

                dato = "carta" + cont + ".numeracion";
                numeracion = propiedades.getProperty( dato );

                dato = "carta" + cont + ".imagen";
                imagen = propiedades.getProperty( dato );

                Carta carta = new Carta( palo, numeracion, imagen );
                baraja.adicionarCartaAlFinal( carta );

                fis.close( );
            }
        }
        catch( IOException e )
        {
            fail( );
        }
    }
    
    /**
     * Escenario 3: Contruye una baraja con 7 cartas que es válida para ganar
     */
    private void setupEscenario4( )
    {
    	baraja = new Baraja( );
    	try
        {
            baraja = new Baraja( );
            FileInputStream fis = new FileInputStream( new File( "./data/cartas4.properties" ) );
            Properties propiedades = new Properties( );
            propiedades.load( fis );

            String dato;
            String palo;
            String numeracion;
            String imagen;

            String aux;
            dato = "total.cartas";
            aux = propiedades.getProperty( dato );
            int cantidad = Integer.parseInt( aux );

            for( int cont = 1; cont <= cantidad; cont++ )
            {
                dato = "carta" + cont + ".palo";
                palo = propiedades.getProperty( dato );

                dato = "carta" + cont + ".numeracion";
                numeracion = propiedades.getProperty( dato );

                dato = "carta" + cont + ".imagen";
                imagen = propiedades.getProperty( dato );

                Carta carta = new Carta( palo, numeracion, imagen );
                baraja.adicionarCartaAlFinal( carta );

                fis.close( );
            }
        }
        catch( IOException e )
        {
            fail( );
        }
    }

    // -----------------------------------------------------------------
    // Casos de prueba
    // -----------------------------------------------------------------
    
    /**
     * Prueba 1
     */
    public void testAdicionarCartaAlFinal( )
    {

        setupEscenario2( );

        baraja.adicionarCartaAlFinal( new Carta( "PICAS", "2", "2picas.png" ) );
        baraja.adicionarCartaAlFinal( new Carta( "PICAS", "AS", "apicas.png" ) );

        ArrayList cartas = ( ArrayList )baraja.darCartas( );
        Carta carta = ( Carta )cartas.get( 1 );
        assertTrue( "Error al Agregar Carta al final", cartas.size( ) == 2 );
        assertTrue( "Error al Agregar Carta al final", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al Agregar Carta al final", carta.darRepresentacion( ).equals( "AS" ) );
        assertTrue( "Error al Agregar Carta al final", carta.darImagen( ).equals( "apicas.png" ) );
    }

    /**
     * Prueba 2
     */
    public void testAdicionarCartaEncima( )
    {

        setupEscenario2( );

        baraja.adicionarCartaEncima( new Carta( "PICAS", "2", "2picas.png" ) );
        baraja.adicionarCartaEncima( new Carta( "PICAS", "AS", "apicas.png" ) );

        ArrayList cartas = ( ArrayList )baraja.darCartas( );
        Carta carta = ( Carta )cartas.get( 0 );
        assertTrue( "Error al Agregar Carta encima", cartas.size( ) == 2 );
        assertTrue( "Error al Agregar Carta encima", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al Agregar Carta encima", carta.darRepresentacion( ).equals( "AS" ) );
        assertTrue( "Error al Agregar Carta encima", carta.darImagen( ).equals( "apicas.png" ) );
    }

    /**
     * Prueba 3
     */
    public void testDarCartaEncima( )
    {

        setupEscenario2( );

        baraja.adicionarCartaEncima( new Carta( "PICAS", "2", "2picas.png" ) );
        baraja.adicionarCartaEncima( new Carta( "PICAS", "AS", "apicas.png" ) );

        Carta carta = baraja.darCartaDeEncima( );
        assertTrue( "Error al dar carta de encima", baraja.darNumCartas( ) == 1 );
        assertTrue( "Error al dar carta de encima", carta.darPalo( ).equals( "PICAS" ) );
        assertTrue( "Error al dar carta de encima", carta.darRepresentacion( ).equals( "AS" ) );
        assertTrue( "Error al dar carta de encima", carta.darImagen( ).equals( "apicas.png" ) );
    }

    /**
     * Prueba 4
     */
    public void testBarajar( )
    {

        setupEscenario1( );

        Baraja copia = baraja;
        copia.barajar( );
        int cont = 0;
        while( copia.darNumCartas( ) != 0 )
        {
            if( copia.darCartaDeEncima( ).comparar( baraja.darCartaDeEncima( ) ) == 0 )
            {
                cont++;
            }
        }
        assertTrue( "Error al barajar", cont != 52 );
    }
    
    /**
     * Prueba 5
     */
    public void testValidar( )
    {
    	setupEscenario3( );
    	
    	boolean condicion = baraja.valido( );
    	
    	assertTrue( "Error en la cantidad de cartas", baraja.darNumCartas( ) == 4 );
    	assertFalse( "No debería validar la victoria", condicion );
    }
    
    /**
     * Prueba 6
     */
    public void testValidar2( )
    {
    	setupEscenario4( );
    	
    	boolean condicion = baraja.valido( );
    	
    	assertTrue( "Error en la cantidad de cartas", baraja.darNumCartas( ) == 4 );
    	assertEquals( "No debería validar la victoria", true, condicion );
    }
    
    /**
     * Prueba 7
     */
    public void testOrdenar( )
    {
    	setupEscenario3( );
    	baraja.ordenar( );
    	Carta carta = baraja.darCartaDeEncima( );
    	assertTrue( "Error al dar carta de encima", carta.darRepresentacion( ).equals( "AS" ) );
    	baraja.darCartaDeEncima( );
    	baraja.darCartaDeEncima( );
    	Carta carta2 = baraja.darCartaDeEncima( );
    	assertTrue( "Error al dar carta de encima", carta2.darRepresentacion( ).equals( "J" ) );
    	baraja.darCartaDeEncima( );
    	baraja.darCartaDeEncima( );
    	Carta carta3 = baraja.darCartaDeEncima( );
    	assertTrue( "Error al dar carta de encima", carta3.darRepresentacion( ).equals( "K" ) );
    }
}
