/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelImagen.java 1010 2007-08-17 00:57:38Z camil-ji $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Equipo Cupi2 2014
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.cliente.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel con la imagen.
 */
public class PanelImagen extends JPanel
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serialización.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constante ruta de la imagen.
     */
    public final static String RUTA = "data/Encabezado.png";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Nombre del oponente.
     */
    private String nombreOponente;

    /**
     * Numero de victorias del oponente.
     */
    private String numVictorias;

    /**
     * Numero de derrotas del oponente.
     */
    private String numDerrotas;
    
    // -----------------------------------------------------------------
    // Atributos de Interfaz
    // -----------------------------------------------------------------

    /**
     * Etiqueta con la imagen.
     */
    private JLabel etiquetaImagen;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel.
     */
    public PanelImagen( )
    {
        nombreOponente = "";
        numDerrotas = "";
        numVictorias = "";
        setLayout( new BorderLayout( ) );
        setBackground( Color.WHITE );
        setPreferredSize( new Dimension( 800, 60 ) );
        etiquetaImagen = new JLabel( "" );
        etiquetaImagen.setHorizontalAlignment( JLabel.CENTER );
        etiquetaImagen.setVerticalAlignment( JLabel.CENTER );

        add( etiquetaImagen, BorderLayout.CENTER );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Método que pinta la información estadística del oponente.
     * @param g Componente donde pinta la información.
     */
    public void paintComponent( Graphics g )
    {
        ImageIcon icon = new ImageIcon( RUTA );
        g.drawImage( icon.getImage( ), 0, 0, null );
        setOpaque( false );
        g.setColor( Color.BLACK );
        g.setFont( new Font( "Arial", Font.BOLD, 12 ) );
        g.drawString( nombreOponente, 90, 18 );
        g.drawString( numVictorias, 90, 34 );
        g.drawString( numDerrotas, 90, 50 );
        setOpaque( false );
        super.paintComponent( g );
    }

    /**
     * Modifica los datos estadísticos del oponente.
     * @param nombre Nombre del oponente.
     * @param victorias Número de victorias del oponente.
     * @param derrotas Número de derrotas del oponente.
     */
    public void modificarDatosOponente( String nombre, String victorias, String derrotas )
    {
        nombreOponente = nombre;
        numVictorias = victorias;
        numDerrotas = derrotas;
        paintComponent( getGraphics( ) );
    }

}
