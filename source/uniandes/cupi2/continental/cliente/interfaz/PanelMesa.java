/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelMesa.java 1013 2007-08-20 23:04:33Z camil-ji $
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uniandes.cupi2.continental.cliente.mundo.Carta;

/**
 * Panel que contiene las dos barajas del juego.
 */
public class PanelMesa extends JPanel implements MouseListener
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serialización.
     */
    private static final long serialVersionUID = 8478165499089244999L;

    /**
     * Constante con la ruta donde se encuentran las imagenes de las cartas.
     */
    private final static String RUTA = ".//data//cartas//";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación.
     */
    private InterfazContinental principal;

    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------

    /**
     * Etiqueta que representa a la baraja inicial.
     */
    private JLabel lblBaraja1;

    /**
     * Etiqueta que representa a la baraja donde se descartan las cartas.
     */
    private JLabel lblBaraja2;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel.
     * @param ventanaPrincipal Ventana principal de la aplicación.
     */
    public PanelMesa( InterfazContinental ventanaPrincipal )
    {
        try
        {
            principal = ventanaPrincipal;
            GridLayout layout = new GridLayout( 0, 2 );
            layout.setHgap( 10 );
            layout.setVgap( 20 );
            this.setLayout( layout );
            this.setBackground( new Color( 0, 128, 0 ) );
            BufferedImage bImagen;

            lblBaraja1 = new JLabel( "" );
            lblBaraja1.setPreferredSize( new Dimension( 100, 150 ) );
            lblBaraja1.setHorizontalAlignment( JLabel.RIGHT );
            lblBaraja1.setVerticalAlignment( JLabel.CENTER );
            lblBaraja1.addMouseListener( this );
            bImagen = ImageIO.read( new File( RUTA + "cover.png" ) );
            lblBaraja1.setIcon( new ImageIcon( bImagen ) );
            add( lblBaraja1 );

            lblBaraja2 = new JLabel( "" );
            lblBaraja2.setPreferredSize( new Dimension( 100, 150 ) );
            lblBaraja2.setHorizontalAlignment( JLabel.LEFT );
            lblBaraja2.setVerticalAlignment( JLabel.CENTER );
            lblBaraja2.addMouseListener( this );
            bImagen = ImageIO.read( new File( RUTA + "no-cover.png" ) );
            lblBaraja2.setIcon( new ImageIcon( bImagen ) );
            add( lblBaraja2 );

            desactivarBarajas( );
        }
        catch( IOException e )
        {
            System.out.println( e.getMessage( ) );
        }

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Modifica la baraja donde se descartan las cartas.
     * @param carta Nueva carta descartada.
     * @throws IOException si ocurre algún error al leer la imagen de la carta.
     */
    public void modificarBaraja2( Carta carta ) throws IOException
    {
        BufferedImage bImagen;
        if( carta == null )
            bImagen = ImageIO.read( new File( RUTA + "no-cover.png" ) );
        else
            bImagen = ImageIO.read( new File( RUTA + carta.darImagen( ) ) );
        lblBaraja2.setIcon( new ImageIcon( bImagen ) );
    }

    /**
     * Activa las barajas en la mesa.
     */
    public void activarBarajas( )
    {
        lblBaraja1.setEnabled( true );
        lblBaraja2.setEnabled( true );
    }

    /**
     * Desactiva las barajas en la mesa.
     */
    public void desactivarBarajas( )
    {
        lblBaraja1.setEnabled( false );
        lblBaraja2.setEnabled( false );
    }

    /**
     * Método para manejar los clicks del mouse.
     * @param e Evento del mouse.
     */
    public void mouseClicked( MouseEvent e )
    {
        if( e.getButton( ) == MouseEvent.BUTTON1 )
        {
            Object o = e.getSource( );
            if( ( ( JLabel )o ).isEnabled( ) )
            {
                if( o.equals( lblBaraja1 ) )
                {
                    principal.pedirCartaBarajaInicial( );
                }
                else if( o.equals( lblBaraja2 ) )
                {
                    principal.pedirCartaBarajaJugada( );
                }
            }
        }
    }

    /**
     * Método para manejar una acción cuando el mouse entre a una zona especifica.
     * @param e Evento del mouse.
     */
    public void mouseEntered( MouseEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar una acción cuando el mouse salga de una zona especifica.
     * @param e Evento del mouse.
     */
    public void mouseExited( MouseEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar una acción cuando el mouse ha sido presionado.
     * @param e Evento del mouse.
     */
    public void mousePressed( MouseEvent e )
    {
        if( e.getButton( ) == MouseEvent.BUTTON1 )
        {
            try
            {
                Object o = e.getSource( );
                if( ( ( JLabel )o ).isEnabled( ) )
                {
                    if( o.equals( lblBaraja1 ) )
                    {
                        BufferedImage bImagen = ImageIO.read( new File( RUTA + "inv-cover.png" ) );
                        lblBaraja1.setIcon( new ImageIcon( bImagen ) );
                    }
                    else if( o.equals( lblBaraja2 ) )
                    {

                    }
                }
            }
            catch( Exception ex )
            {
                JOptionPane.showMessageDialog( this, ex.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }

    }

    /**
     * Método para manejar una acción cuando el mouse ha sido soltado.
     * @param e Evento del mouse.
     */
    public void mouseReleased( MouseEvent e )
    {
        if( e.getButton( ) == MouseEvent.BUTTON1 )
        {
            try
            {
                Object o = e.getSource( );
                if( o.equals( lblBaraja1 ) )
                {
                    BufferedImage bImagen = ImageIO.read( new File( RUTA + "cover.png" ) );
                    lblBaraja1.setIcon( new ImageIcon( bImagen ) );
                }
                else if( o.equals( lblBaraja2 ) )
                {

                }
            }
            catch( Exception ex )
            {
                System.out.println( ex.getMessage( ) );
            }
        }

    }

}
