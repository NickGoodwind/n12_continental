package uniandes.cupi2.servidor.mundo;

/**
 * @author NickGoodwind
 * Clase que modela a un jugador que se conect� al servidor para jugar batalla naval y est� participando en un encuentro.<br>
 * <b>inv:</b><br>
 * registroJugador != null<br>
 */
public class JugadorRemoto
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * La informaci�n del jugador antes de iniciar el encuentro
     */
    private InfoJugador info;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el objeto para mantener la informaci�n del jugador remoto. <br>
     * Al iniciar el encuentro, el puntaje es 0.
     * @param registro El registro del jugador antes de empezar el encuentro actual - registro != null
     */
    public JugadorRemoto( InfoJugador registro )
    {
        info = registro;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el registro del jugador
     * @return registroJugador
     */
    public InfoJugador darRegistroJugador( )
    {
        return info;
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase<br>
     * <b>inv</b><br>
     * registroJugador != null<br>
     */
    private void verificarInvariante( )
    {
        assert ( info != null ) : "El registros de un jugador no puede ser null";
    }
}