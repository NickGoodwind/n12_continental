package uniandes.cupi2.continental.servidor.interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @author MUNDOSALUD
 *
 */
public class PanelOpciones extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	private static final String OPCION_1 = "Opcion 1";
	
	private final static String OPCION_2 = "Opcion 2";
	
	private InterfazServidor principal;
	
	private JButton opcion1;
	
	private JButton opcion2;
	
	
	/**
	 * @param p
	 */
	public PanelOpciones( InterfazServidor p )
	{
		principal = p;
		
		setLayout( new GridLayout( 1, 2 ) );
		setBorder( new TitledBorder( "Opciones" ) );
		
		opcion1 = new JButton( "Opción 1" );
		opcion1.addActionListener( this );
		opcion1.setActionCommand( OPCION_1 );
		
		opcion2 = new JButton( "Opción 2" );
		opcion2.addActionListener( this );
		opcion2.setActionCommand( OPCION_2 );
		
		add( opcion1 );
		add( opcion2 );
	}


	/**
	 * @param e El evento
	 */
	public void actionPerformed( ActionEvent e )
	{
		String comando = e.getActionCommand( );
		if( comando.equals( OPCION_1 ) )
		{
			principal.reqFuncOpcion1( );
		}
		else if( comando.equals( OPCION_2 ) )
		{
			principal.reqFuncOpcion2( );
		}
	}

}
