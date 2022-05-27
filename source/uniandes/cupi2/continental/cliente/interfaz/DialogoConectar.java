/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: DialogoConectar.java 1010 2007-08-17 00:57:38Z camil-ji $
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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Diálogo para conectar el cliente al servidor del juego.
 */
public class DialogoConectar extends JDialog implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante de serialización.
     */
    private static final long serialVersionUID = -4436546417032269683L;

    /**
     * Constante conectar.
     */
    private final static String CONECTAR = "Conectar";

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
     * Etiqueta servidor.
     */
    private JLabel lblServidor;

    /**
     * Etiqueta puerto.
     */
    private JLabel lblPuerto;

    /**
     * Etiqueta usuario.
     */
    private JLabel lblUsuario;

    /**
     * Campo de texto servidor.
     */
    private JTextField txtServidor;

    /**
     * Campo de texto puerto.
     */
    private JTextField txtPuerto;

    /**
     * Campo de texto usuario.
     */
    private JTextField txtUsuario;

    /**
     * Botón ingresar.
     */
    private JButton btnIngresar;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del diálogo.
     * @param ventanaPrincipal Ventana principal de la aplicación.
     */
    public DialogoConectar( InterfazContinental ventanaPrincipal )
    {
        super( ventanaPrincipal, true );
        principal = ventanaPrincipal;

        setTitle( "Conectar" );
        setLayout( new BorderLayout( ) );

        JPanel panelInfo = new JPanel( );
        panelInfo.setPreferredSize( new Dimension( 400, 100 ) );

        GridLayout layout = new GridLayout( 3, 2 );

        layout.setVgap( 10 );
        layout.setHgap( -150 );
        panelInfo.setLayout( layout );

        lblServidor = new JLabel( "Servidor: " );
        panelInfo.add( lblServidor );
        txtServidor = new JTextField( "localhost" );
        panelInfo.add( txtServidor );

        lblPuerto = new JLabel( "Puerto: " );
        panelInfo.add( lblPuerto );
        txtPuerto = new JTextField( "9999" );
        panelInfo.add( txtPuerto );

        lblUsuario = new JLabel( "Usuario: " );
        panelInfo.add( lblUsuario );
        txtUsuario = new JTextField( );
        panelInfo.add( txtUsuario );

        add( panelInfo, BorderLayout.NORTH );

        btnIngresar = new JButton( CONECTAR );
        btnIngresar.setActionCommand( CONECTAR );
        btnIngresar.addActionListener( this );
        add( btnIngresar, BorderLayout.SOUTH );

        setLocationRelativeTo( null );
        pack( );

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones
     * @param e Acción que generó el evento.
     */
    public void actionPerformed( ActionEvent e )
    {

        if( e.getActionCommand( ).equals( CONECTAR ) )
        {
            try
            {
                String servidor = txtServidor.getText( );
                String usuario = txtUsuario.getText( );
                int puerto = Integer.valueOf( txtPuerto.getText( ) );

                if( servidor.equals( "" ) || usuario.equals( "" ) )
                {
                    JOptionPane.showMessageDialog( this, "Debe ingresar los datos.", "Conectar", JOptionPane.ERROR_MESSAGE );
                }
                else
                {
                    this.setTitle( "Conectar: Conectando...." );
                    principal.configurarDatosConexion( servidor, puerto, usuario );
                }
            }
            catch( NumberFormatException ex )
            {
                JOptionPane.showMessageDialog( this, "Formato del puerto no valido.", "Conectar", JOptionPane.ERROR_MESSAGE );
            }

        }
    }

}
