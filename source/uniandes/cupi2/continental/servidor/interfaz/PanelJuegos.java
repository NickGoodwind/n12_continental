package uniandes.cupi2.continental.servidor.interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.servidor.mundo.Encuentro;

/**
 * @author MUNDOSALUD
 *
 */
public class PanelJuegos extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	private final static String ACTUALIZAR = "Actualizar";

	private InterfazServidor principal;
	
	private JList<Encuentro> lista;
	
	private JScrollPane scroll;
	
	private JButton actualizar;
	
	/**
	 * @param p
	 */
	public PanelJuegos( InterfazServidor p )
	{
		principal = p;
		
		setLayout( new BorderLayout( ) );
		setBorder( new TitledBorder( "Juegos actuales" ) );
		
		lista = new JList<Encuentro>( );
		scroll = new JScrollPane( lista );
		
		actualizar = new JButton( "Actualizar" );
		actualizar.addActionListener( this );
		actualizar.setActionCommand( ACTUALIZAR );
		
		add( scroll, BorderLayout.CENTER );
		add( actualizar, BorderLayout.SOUTH );
	}

	/**
	 * @param pLista
	 */
	public void actualizar( ArrayList<Encuentro> pLista )
	{
		lista.setListData( (Encuentro[])pLista.toArray( ) );
	}

	/**
	 * @param e
	 */
	public void actionPerformed( ActionEvent e )
	{
		String comand = e.getActionCommand( );
		if( comand.equals( ACTUALIZAR ) )
		{
			principal.actualizarJuegos( );
		}
	}
}
