package uniandes.cupi2.continental.testServidor;

import uniandes.cupi2.servidor.mundo.Continental;

/**
 * Esta clase es usada por las pruebas de la clase ServidorBatallaNaval.<br>
 * Cuando se inicia un Thread con esta clase, esta se encarga de hacer que el servidor quede listo para recibir conexiones.
 */
public class AyudantePruebasServidor extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es el servidor sobre el que se realizan las pruebas
     */
    private Continental servidor;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Método constrauctor de la clase que construye el ayudante para las pruebas
     * @param servidorBN El servidor que será probado
     */
    public AyudantePruebasServidor( Continental servidorBN )
    {
        servidor = servidorBN;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Método encargado de cargar el servidor y dejarlo listo para recibir conexiones
     */
    public void run( )
    {
        while( true )
        {
            servidor.recibirConexiones( );
        }
    }

    /**
     * Método encargado de detener el servidor
     */
    public void detener( )
    {
        interrupt( );
    }
}

