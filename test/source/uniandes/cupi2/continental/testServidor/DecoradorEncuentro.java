package uniandes.cupi2.continental.testServidor;

import java.io.IOException;
import java.net.Socket;

import uniandes.cupi2.continental.cliente.mundo.ContinentalException;
import uniandes.cupi2.servidor.mundo.AdministradorJugadores;
import uniandes.cupi2.servidor.mundo.Encuentro;

/**
 * Esta clase extiende el comportamiento de la clase encuentro para facilitar las pruebas de la clase Continental
 */
public class DecoradorEncuentro extends Encuentro
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Atributo que modela si el encuentro ya inicio
     */
    private boolean encuentroIniciado;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * M�todo constructor de la clase que crea un nuevo encuentro
     * @param cliente1 El canal de comunicaci�n con el cliente 1
     * @param cliente2 El canal de comunicaci�n con el cliente 2
     * @param administrador El administrador de los datos de los jugadores
     * @throws IOException Cuando hay problemas en la comunicaci�n
     */
    public DecoradorEncuentro( Socket cliente1, Socket cliente2, AdministradorJugadores administrador ) throws IOException
    {
        super( cliente1, cliente2, administrador );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * M�todo encargado de iniciar un encuentro y cambia el valor del atributo encuentroIniciado
     * @throws IOException Cuando hay problemas en la comunicaci�n
     * @throws ContinentalException Cuando hay problemas en la comunicaci�n
     */
    protected void iniciarEncuentro( ) throws IOException, ContinentalException
    {
        super.iniciarEncuentro( );
        encuentroIniciado = true;
    }

    /**
     * M�todo que retorna el estado del encuentro
     * @return true si ya se inicio, false en caso contrario
     */
    public boolean estaIniciado( )
    {
        return encuentroIniciado;
    }

    /**
     * M�todo que retorna el nombre del jugador 1
     * @return nombre del jugador 1
     */
    public String darNombreJugador1( )
    {
        return jugador1.darRegistroJugador( ).darNombre( );
    }

    /**
     * M�todo que retorna el nombre del jugador 2
     * @return nombre del jugador 2
     */
    public String darNombreJugador2( )
    {
        return jugador2.darRegistroJugador( ).darNombre( );
    }

}

