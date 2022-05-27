/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: InterfazContinental.java 1014 2007-08-20 23:12:56Z camil-ji $
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uniandes.cupi2.continental.cliente.mundo.ContinentalException;
import uniandes.cupi2.continental.cliente.mundo.JugadorContinental;
import uniandes.cupi2.continental.cliente.mundo.ThreadConectar;
import uniandes.cupi2.continental.cliente.mundo.ThreadEnviarDatosVictoria;
import uniandes.cupi2.continental.cliente.mundo.ThreadEnviarJugada;
import uniandes.cupi2.continental.cliente.mundo.ThreadEsperarCarta;
import uniandes.cupi2.continental.cliente.mundo.ThreadEsperarJugada;

/**
 * Ventana principal de la aplicación.
 */
public class InterfazContinental extends JFrame implements WindowListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serialización.
     */
    private static final long serialVersionUID = -3722924491294788719L;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Dirección del servidor.
     */
    private String servidor;

    /**
     * Nombre del usuario.
     */
    private String usuario;

    /**
     * Puerto del servidor.
     */
    private int puerto;

    /**
     * Clase principal del mundo.
     */
    private JugadorContinental jugador;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Panel con las extensiones.
     */
    private PanelExtensionCliente panelExtension;

    /**
     * Panel con el juego de cartas del jugador.
     */
    private PanelJuego panelJuego;

    /**
     * Panel con las barajas del juego.
     */
    private PanelMesa panelMesa;

    /**
     * Panel con una imagen.
     */
    private PanelImagen panelImagen;

    /**
     * Dialogo para conectarse al servidor.
     */
    private DialogoConectar dialogoConectar;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la interfaz para un cliente del juego.
     */
    public InterfazContinental( )
    {
        jugador = new JugadorContinental( );

        dialogoConectar = new DialogoConectar( this );
        dialogoConectar.addWindowListener( this );
        dialogoConectar.setVisible( true );

        setTitle( "Juego Continental: " + jugador.darEstado( ) );
        // Construye la forma
        getContentPane( ).setLayout( new BorderLayout( ) );
        setSize( 800, 620 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        panelImagen = new PanelImagen( );
        add( panelImagen, BorderLayout.NORTH );

        JPanel panelCentral = new JPanel( new BorderLayout( ) );

        panelJuego = new PanelJuego( );
        panelCentral.add( panelJuego, BorderLayout.CENTER );
        panelMesa = new PanelMesa( this );
        panelCentral.add( panelMesa, BorderLayout.NORTH );

        add( panelCentral, BorderLayout.CENTER );

        panelExtension = new PanelExtensionCliente( this );
        add( panelExtension, BorderLayout.SOUTH );

        setLocationRelativeTo( null );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Configura los datos de la conexión.
     * @param pServidor Nombre del servidor.
     * @param pPuerto Puerto del servidor.
     * @param pUsuario Nombre del usuario.
     */
    public void configurarDatosConexion( String pServidor, int pPuerto, String pUsuario )
    {
        servidor = pServidor;
        puerto = pPuerto;
        usuario = pUsuario;
        dialogoConectar.dispose( );
        conectar( );

    }

    /**
     * Conecta al cliente con el servidor. <br>
     * El proceso de conexión al servidor se hace en un hilo aparte usando la clase ThreadConectar.
     */
    private void conectar( )
    {
        ThreadConectar t = new ThreadConectar( jugador, this, usuario, servidor, puerto );
        t.start( );
    }

    /**
     * Actualiza la interfaz gráfica.
     * post: se actualiza el estado del cliente.
     */
    public void actualizarInterfaz( )
    {
        try
        {
            setTitle( "Juego continental: " + jugador.darEstado( ) );
            panelJuego.modificarJuego( jugador.darJuego( ) );
            panelMesa.modificarBaraja2( jugador.darJuego( ).darCartaBarajaJugada( ) );
            if( jugador.darEstado( ).equals( JugadorContinental.JUEGO_TERMINADO ) )
            {
                panelExtension.desactivar( );
            }

        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Continental", JOptionPane.INFORMATION_MESSAGE );
        }

    }

    /**
     * Espera la jugada del oponente. <br>
     * El proceso para esperar una jugada se hace en un hilo aparte usando la clase ThreadEsperarJugada.
     */
    public void esperarJugada( )
    {
        panelExtension.desactivar( );
        ThreadEsperarJugada t = new ThreadEsperarJugada( jugador, this );
        t.start( );

    }

    /**
     * Realiza una jugada. <br>
     * El proceso para realizar una jugada se hace en un hilo aparte usando la clase ThreadEnviarJugada.
     */
    public void realizarJugada( )
    {
        if( jugador.darCartaTemporal( ) == null )
        {
            JOptionPane.showMessageDialog( this, "Debe seleccionar una carta para descartar.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        }
        else
        {
            ThreadEnviarJugada t = new ThreadEnviarJugada( jugador, this );
            t.start( );
        }

    }

    /**
     * Pide una carta al servidor de la baraja inicial. <br>
     * El proceso para esperar una jugada se hace en un hilo aparte usando la clase ThreadEsperarCarta.
     */
    public void pedirCartaBarajaInicial( )
    {
        if( jugador.darEstado( ) == JugadorContinental.ESPERANDO_JUGADA )
        {
            JOptionPane.showMessageDialog( this, "Todavía no es su turno.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        }
        else
        {
            if( jugador.seleccionoCarta( ) )
            {
                JOptionPane.showMessageDialog( this, "Ya seleccionó una carta.", "Continental", JOptionPane.INFORMATION_MESSAGE );
            }
            else
            {
                panelExtension.activarTerminarTurno( );
                ThreadEsperarCarta t = new ThreadEsperarCarta( jugador, this, ThreadEsperarCarta.INICIAL );
                t.start( );
            }
        }
    }

    /**
     * Pide una carta al servidor de la baraja donde se ubican las cartas ya jugadas. <br>
     * El proceso para esperar una jugada se hace en un hilo aparte usando la clase ThreadEsperarCarta.
     */
    public void pedirCartaBarajaJugada( )
    {
        if( jugador.darEstado( ) == JugadorContinental.ESPERANDO_JUGADA )
        {
            JOptionPane.showMessageDialog( this, "Todavía no es su turno.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        }
        else
        {
            if( jugador.seleccionoCarta( ) )
            {
                JOptionPane.showMessageDialog( this, "Ya seleccionó una carta.", "Continental", JOptionPane.INFORMATION_MESSAGE );
            }
            else
            {
                panelExtension.activarTerminarTurno( );
                jugador.darJuego( ).modificarCartaBarajaJugada( null );
                ThreadEsperarCarta t = new ThreadEsperarCarta( jugador, this, ThreadEsperarCarta.JUGADA);
                t.start( );
            }
        }
    }

    /**
     * Desactiva el botón jugar.
     */
    public void desactivarBotonJugar( )
    {
        panelExtension.desactivarTerminarTurno( );
    }

    /**
     * Muestra un mensaje con el error enviado por una excepción.
     * @param e Excepción con el error.
     */
    public void mostrarError( ContinentalException e )
    {
        JOptionPane.showMessageDialog( this, e.getMessage( ), "Continental", JOptionPane.ERROR_MESSAGE );
        actualizarInterfaz( );
    }

    /**
     * Valida si el jugador tiene un juego correcto. <br>
     * El proceso para validar la victoria se hace en un hilo aparte usando la clase ThreadEnviarDatosVictoria.
     */
    public void validarVictoria( )
    {
        ThreadEnviarDatosVictoria t = new ThreadEnviarDatosVictoria( jugador, this );
        t.start( );

    }

    /**
     * Informa si la victoria fue válida.
     * @param victoriaValida Indica si la victoria del oponente fue válida.
     */
    public void victoria( boolean victoriaValida )
    {
        if( victoriaValida )
            JOptionPane.showMessageDialog( this, "Felicitaciones, has ganado la partida.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        else
            JOptionPane.showMessageDialog( this, "Lo sentimos, no has ganado la partida.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        panelMesa.desactivarBarajas( );
    }

    /**
     * Muestra la información de la victoria del oponente.
     * @param victoriaValida Indica si la victoria del oponente fue válida.
     */
    public void mostrarInformacionGanador( boolean victoriaValida )
    {
        if( victoriaValida )
            JOptionPane.showMessageDialog( this, "Lo sentimos, " + jugador.darNombreOponente( ) + " ha ganado la partida.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        else
            JOptionPane.showMessageDialog( this, "Felicitaciones, has ganado la partida. " + jugador.darNombreOponente( ) + " cantó una victoria inválida.", "Continental", JOptionPane.INFORMATION_MESSAGE );
        panelMesa.desactivarBarajas( );
    }

    /**
     * Actualiza la información mostrada en la ventana sobre el oponente.
     */
    public void actualizarDatosOponente( )
    {
        panelImagen.modificarDatosOponente( jugador.darNombreOponente( ), jugador.darNumVictoriasOponente( ), jugador.darNumDerrotasOponente( ) );

    }

    /**
     * Activa las barajas en la mesa.
     */
    public void activarBarajas( )
    {
        panelMesa.activarBarajas( );

    }

    /**
     * Desactiva las barajas en la mesa.
     */
    public void desactivarBarajas( )
    {
        panelMesa.desactivarBarajas( );

    }
    
    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Método para la extensión 1.
     */
    public void reqFuncOpcion1( )
    {
        String resultado = jugador.metodo1( );
        JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Método para la extensión 2.
     */
    public void reqFuncOpcion2( )
    {
        String resultado = jugador.metodo2( );
        JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz.
     * @param args Argumentos del llamado. No se requiere ninguno.
     */
    public static void main( String[] args )
    {

        InterfazContinental interfaz = new InterfazContinental( );
        interfaz.setVisible( true );
    }

    // -----------------------------------------------------------------
    // Métodos de WindowListener
    // -----------------------------------------------------------------

    /**
     * Método para manejar los eventos de una ventana al momento de cerrarse.
     * @param e Evento de la ventana.
     */
    public void windowClosing( WindowEvent e )
    {
        this.dispose( );
        System.exit( 0 );
    }

    /**
     * Método para manejar los eventos de una ventana al momento de activarse.
     * @param e Evento de la ventana.
     */
    public void windowActivated( WindowEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar los eventos de una ventana cuando ha sido cerrada.
     * @param e Evento de la ventana.
     */
    public void windowClosed( WindowEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar los eventos de una ventana cuando no esta activa.
     * @param e Evento de la ventana
     */
    public void windowDeactivated( WindowEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar los eventos de una ventana.
     * @param e Evento de la ventana.
     */
    public void windowDeiconified( WindowEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar los eventos de una ventana.
     * @param e Evento de la ventana.
     */
    public void windowIconified( WindowEvent e )
    {
    	// No se implementa.
    }

    /**
     * Método para manejar los eventos de una ventana al momento abrirse.
     * @param e Evento de la ventana.
     */
    public void windowOpened( WindowEvent e )
    {
    	// No se implementa.
    }

}