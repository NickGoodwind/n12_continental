package uniandes.cupi2.servidor.mundo;

import java.util.ArrayList;
import java.util.Collections;

import uniandes.cupi2.continental.cliente.mundo.Carta;

/**
 * @author NickGoodwind
 * Clase que modela una nueva baraja <br>
 * <b>inv:</b><br>
 * cartas != null <br>
 */
public class Baraja
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	
	/**
	 * Las cartas de la baraja
	 */
	private ArrayList<Carta> cartas;
	
    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	/**
	 * Método constructor de la clase que se encarga de crear una nueva baraja e iniciar el arreglo de las cartas
	 */
	public Baraja( )
	{
		cartas = new ArrayList<Carta>( );
		verificarInvariante( );
	}
	
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	
	/**
	 * Método encargado de retornar la primera carta de la baraja y eliminarla de ella
	 * @return carta La primera carta de la baraja
	 */
	public Carta darCartaDeEncima( )
	{
		Carta carta = cartas.get( 0 );
		cartas.remove( 0 );
		return carta;
	}
	
	/**
	 * Método encargado de retornar la lista de las cartas de la baraja
	 * @return cartas Las cartas de esta baraja
	 */
	public ArrayList<Carta> darCartas( )
	{
		return cartas;
	}

	/**
	 * Método encargado de retornar la cantidad de cartas de la baraja
	 * @return la cantidad de cartas de la bajara
	 */
	public int darNumCartas( )
	{
		return cartas.size( );
	}
	
	/**
	 * Método encargado de adicionar una carta al final de la baraja
	 * @param carta La carta a adicionar
	 */
	public void adicionarCartaAlFinal( Carta carta )
	{
		cartas.add( carta );
	}

	/**
	 * Método encargado de adicionar una carta al principio de la baraja
	 * @param carta La carta  adicionar
	 */
	public void adicionarCartaEncima( Carta carta )
	{
		cartas.add( 0, carta );
	}

	/**
	 * TODO
	 * Método encargado de barajar las cartas de la baraja
	 */
	public void barajar( )
	{
		Collections.shuffle( cartas );
	}

	/**
	 * Método encargado de comprobar si un juego es válido para cantar victoria
	 * @return true si el juego es váliso, false en caso contrario
	 */
	public boolean valido( )
	{
		boolean condicionT = false;
		boolean condicionE = false;
		ordenar( );
		
		int cantRep = 0;
		for( int i = 0; i < cartas.size( ) && !condicionT; i++ )
		{
			cantRep = 0;
			boolean repetidas = true;
			Carta primera = cartas.get( i );
			for( int j = i+1 ; j < cartas.size( ) && repetidas; j++ )
			{
				Carta otra = cartas.get( j );
				if( primera.darRepresentacion().equals(otra.darRepresentacion( ) ) )
				{
					cantRep ++;
				}
				else if( cantRep < 2 )
				{
					repetidas = false;
				}
			}
			
			if( cantRep == 2 )
			{
				condicionT = true;
				cartas.remove( i );
				cartas.remove( i );
				cartas.remove( i );
			}
		}
		ordenar( );
		
		Carta carta = cartas.get( 0 );
		int num = carta.darNumero(  );
		for( int i = 1; i < cartas.size( ); i++ )
		{
			if( cartas.get( i ).darNumero( ) == num+i )
			{
				condicionE = true;
			}
			else
			{
				condicionE = false;
			}
				
		}
		
		if( condicionE && condicionT )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 */
	public void ordenar( )
	{
		for( int i = cartas.size( ); i > 0; i-- )
		{
			for( int j = 0; j < i-1; j++ )
			{
				Carta primera = cartas.get( j );
				Carta segunda = cartas.get( j+1 );
				if( primera.comparar( segunda ) > 0 )
				{
					cartas.set( j, segunda );
					cartas.set( j+1, primera );
				}
			}
		}
	}

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase <br>
     * <b>inv:</b><br>
     * cartas != null <br>
     */
    private void verificarInvariante( )
    {        
        assert cartas != null : "La lista de cartas no debería ser null";         
    }
}
