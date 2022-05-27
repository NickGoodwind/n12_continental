package uniandes.cupi2.continental.testServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase es usada para poder esperar una conexi�n a un socket local en un thread diferente al principal
 */
public class AyudantePruebasEncuentro extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es el socket con la conexi�n que se establece
     */
    private Socket socket;

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Crea un socket que espera una conexi�n
     */
    public void run( )
    {
        try
        {
            ServerSocket ssocket = new ServerSocket( 8888 );
            socket = ssocket.accept( );
            ssocket.close( );
        }
        catch( IOException e )
        {
            e.printStackTrace( );
        }
    }

    /**
     * Retorna el socket conectado
     * @return socket
     */
    public Socket darSocket( )
    {
        return socket;
    }
}

