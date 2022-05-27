package uniandes.cupi2.continental.servidor.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uniandes.cupi2.servidor.mundo.Continental;
import uniandes.cupi2.servidor.mundo.Encuentro;
import uniandes.cupi2.servidor.mundo.InfoJugador;

/**
 * @author MUNDOSALUD
 *
 */
public class InterfazServidor extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Continental mundo;
	
	private PanelOpciones opciones;
	
	private PanelJuegos juegosActuales;
	
	private PanelEstadisticas estadisticas;
	
	/**
	 * @param pMundo 
	 * 
	 */
	public InterfazServidor( Continental pMundo )
	{
		mundo = pMundo;
		opciones = new PanelOpciones( this );
		juegosActuales = new PanelJuegos( this );
		estadisticas = new PanelEstadisticas( this );
		
		setLayout( new BorderLayout( ) );
		setSize( 500, 600 );
		
		JPanel centro = new JPanel( );
		centro.setLayout( new GridLayout( 2, 1 ) );
		
		centro.add( juegosActuales );
		centro.add( estadisticas );
		
		add( centro, BorderLayout.CENTER );
		add( opciones, BorderLayout.SOUTH );
	}
	
	/**
	 * 
	 */
	public void actualizarJuegos( )
	{
		ArrayList<Encuentro> lista = (ArrayList<Encuentro>) mundo.actualizarJuegos( );
		juegosActuales.actualizar( lista );
	}

	/**
	 * 
	 */
	public void actualizarEstadisticas( ) 
	{
        try
        {
    		ArrayList<InfoJugador> lista = mundo.darAdministrador( ).consultarRegistroJugadores( );
    		estadisticas.actualizar( lista );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Hubo un error consultando la lista de jugadores:\n" + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
	}
	
    /**
     * Cierra la ventana y la aplicación
     */
    public void dispose( )
    {
        super.dispose( );
        try
        {
            mundo.darAdministrador( ).desconectarBD( );
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
        System.exit( 0 );
    }

	/**
	 * TODO
	 */
	public void reqFuncOpcion1()
	{
		String mensaje = mundo.opcion1( );
		JOptionPane.showMessageDialog(this, "Respuesta", mensaje, JOptionPane.INFORMATION_MESSAGE );
	}

	/**
	 * 
	 */
	public void reqFuncOpcion2()
	{
		String mensaje = mundo.opcion2( );
		JOptionPane.showMessageDialog(this, "Respuesta", mensaje, JOptionPane.INFORMATION_MESSAGE );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
        {
            String archivoPropiedades = "./data/servidor.properties";
            Continental mundo = new Continental( archivoPropiedades );

            InterfazServidor interfaz = new InterfazServidor( mundo );
            interfaz.setVisible( true );
            mundo.recibirConexiones( );
        }
        catch( Exception e )
        {            
            e.printStackTrace( );
        }
	}
}
