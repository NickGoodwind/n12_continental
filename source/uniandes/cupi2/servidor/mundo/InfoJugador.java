package uniandes.cupi2.servidor.mundo;

/**
 * @author NickGoodwind
 * Esta clase mantiene la informaci�n del n�mero de de victorias y derrotas de un jugador <br>
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
	 * El n�mero de victorias del jugador
	 */
	private int victorias;
	
	/**
	 * El n�mero de derrotas del jugador
	 */
	private int derrotas;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	/**
	 * M�todo constructor de la clase encargado de crear un nuevo registro con el nombre, las victorias y las derrotas
	 * @param pNombre - El nombre del jugador - nombre != null
     * @param ganados - El n�mero de encuentros ganados - ganados >= 0
     * @param perdidos - El n�mero de encuentros perdidos - perdidos >= 0
	 */
	public InfoJugador(String pNombre, int ganados, int perdidos)
	{
		nombre = pNombre;
		victorias = ganados;
		derrotas = perdidos;
		verificarInvariante( );
	}
	
    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------
	
	/**
     * M�todo encargado de retornar el nombre del jugador
     * @return nombre
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * M�todo encargado de retirnar el n�mero de victorias del jugador
     * @return victorias
     */
    public int darVictorias( )
    {
        return victorias;
    }

    /**
     * M�todo encargado de retirnar el n�mero de derrotas del jugador
     * @return derrotas
     */
    public int darDerrotas( )
    {
        return derrotas;
    }
	
	/**
	 * M�todo encargado de retornar la informaci�n de un jugador en el formato correspondiente
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
        assert ( victorias >= 0 ) : "El n�mero de encuentros ganados debe ser mayor o igual a 0";
        assert ( derrotas >= 0 ) : "El n�mero de encuentros perdidos debe ser mayor o igual a 0";
    }	
}
