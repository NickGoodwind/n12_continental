package uniandes.cupi2.continental.testServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase es usada para poder esperar una conexión a un socket local en un thread diferente al principal
 */
public class AyudantePruebasEncuentro extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es el socket con la conexión que se establece
     */
    private Socket socket;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Crea un socket que espera una conexión
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

