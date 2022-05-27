package uniandes.cupi2.servidor.mundo;

/**
 * @author NickGoodwind
 * Esta clase mantiene la información del número de de victorias y derrotas de un jugador <br>
 * <b>inv:</b><br>
 * nombreJugador != null<br>
 * encuentrosGanados >= 0<br>
 * encuentrosPerdidos >= 0<br>
 */
public class InfoJugador
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	
	/**
	 * El nombre del jugador
	 */
	private String nombre;
	
	/**
	 * El número de victorias del jugador
	 */
	private int victorias;
	
	/**
	 * El número de derrotas del jugador
	 */
	private int derrotas;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	/**
	 * Método constructor de la clase encargado de crear un nuevo registro con el nombre, las victorias y las derrotas
	 * @param pNombre - El nombre del jugador - nombre != null
     * @param ganados - El número de encuentros ganados - ganados >= 0
     * @param perdidos - El número de encuentros perdidos - perdidos >= 0
	 */
	public InfoJugador(String pNombre, int ganados, int perdidos)
	{
		nombre = pNombre;
		victorias = ganados;
		derrotas = perdidos;
		verificarInvariante( );
	}
	
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	
	/**
     * Método encargado de retornar el nombre del jugador
     * @return nombre
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * Método encargado de retirnar el número de victorias del jugador
     * @return victorias
     */
    public int darVictorias( )
    {
        return victorias;
    }

    /**
     * Método encargado de retirnar el número de derrotas del jugador
     * @return derrotas
     */
    public int darDerrotas( )
    {
        return derrotas;
    }
	
	/**
	 * Método encargado de retornar la información de un jugador en el formato correspondiente
	 * @return <nombre>:<victorias>:<derrotas>
	 */
	public String toString( )
	{
		return nombre + ": " + victorias + " ganados: " + derrotas + " perdidos";
	}
	
    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase<br>
     * <b>inv:</b><br>
     * nombreJugador != null<br>
     * encuentrosGanados >= 0<br>
     * encuentrosPerdidos >= 0<br>
     */
    private void verificarInvariante( )
    {
        assert ( nombre != null ) : "El nombre no puede ser null";
        assert ( victorias >= 0 ) : "El número de encuentros ganados debe ser mayor o igual a 0";
        assert ( derrotas >= 0 ) : "El número de encuentros perdidos debe ser mayor o igual a 0";
    }	
}
