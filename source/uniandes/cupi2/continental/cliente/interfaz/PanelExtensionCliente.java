/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: PanelExtensionCliente.java 1010 2007-08-17 00:57:38Z camil-ji $
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Panel de manejo de extensiones.
 */
public class PanelExtensionCliente extends JPanel implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serialización.
     */
    private static final long serialVersionUID = -2368091935222716496L;

    /**
     * Comando opción 1.
     */
    private static final String OPCION_1 = "OPCION_1";

    /**
     * Comando opción 2.
     */
    private static final String OPCION_2 = "OPCION_2";

    /**
     * Comando terminar turno.
     */
    private static final String JUGAR = "Jugar";

    /**
     * Comando victoria.
     */
    private static final String VICTORIA = "Victoria";

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
     * Botón opción 1.
     */
    private JButton btnOpcion1;

    /**
     * Botón opción 2.
     */
    private JButton btnOpcion2;

    /**
     * Botón Opción 3
     */
    private JButton btnTerminarTurno;

    /**
     * Botón Opción 4
     */
    private JButton btnVictoria;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel.
     * @param ventanaPrincipal Ventana principal.
     */
    public PanelExtensionCliente( InterfazContinental ventanaPrincipal )
    {
        principal = ventanaPrincipal;

        setBorder( new TitledBorder( "Opciones" ) );
        setLayout( new GridLayout( 1, 4 ) );

        // Botón opción 1
        btnOpcion1 = new JButton( "Opción 1" );
        btnOpcion1.setActionCommand( OPCION_1 );
        btnOpcion1.addActionListener( this );
        add( btnOpcion1 );

        // Botón opción 2
        btnOpcion2 = new JButton( "Opción 2" );
        btnOpcion2.setActionCommand( OPCION_2 );
        btnOpcion2.addActionListener( this );
        add( btnOpcion2 );

        // Botón terminar turno
        btnTerminarTurno = new JButton( "Terminar turno" );
        btnTerminarTurno.setActionCommand( JUGAR );
        btnTerminarTurno.addActionListener( this );
        add( btnTerminarTurno );

        // Botón cantar victoria
        btnVictoria = new JButton( "Cantar victoria" );
        btnVictoria.setActionCommand( VICTORIA );
        btnVictoria.addActionListener( this );
        add( btnVictoria );

        desactivar( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones.
     * @param e Acción que generó el evento.
     */
    public void actionPerformed( ActionEvent e )
    {
        if( OPCION_1.equals( e.getActionCommand( ) ) )
        {
            principal.reqFuncOpcion1( );
        }
        else if( OPCION_2.equals( e.getActionCommand( ) ) )
        {
            principal.reqFuncOpcion2( );
        }
        else if( JUGAR.equals( e.getActionCommand( ) ) )
        {
            principal.realizarJugada( );
        }
        else if( VICTORIA.equals( e.getActionCommand( ) ) )
        {
            principal.validarVictoria( );
        }
    }

    /**
     * Activa el botón de terminar turno y desactiva el botón de victoria.
     */
    public void activarTerminarTurno( )
    {
        btnTerminarTurno.setEnabled( true );
        btnVictoria.setEnabled( false );
    }

    /**
     * Desactiva el botón de terminar turno y activa el botón de victoria.
     */
    public void desactivarTerminarTurno( )
    {
        btnTerminarTurno.setEnabled( false );
        btnVictoria.setEnabled( true );
    }

    /**
     * Desactiva el botón de terminar turno y de victoria.
     */
    public void desactivar( )
    {
        btnTerminarTurno.setEnabled( false );
        btnVictoria.setEnabled( false );
    }

}
