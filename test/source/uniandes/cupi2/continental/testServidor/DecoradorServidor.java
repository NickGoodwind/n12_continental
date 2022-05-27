package uniandes.cupi2.continental.testServidor;

import java.io.*;
import java.net.*;
import java.sql.*;

import uniandes.cupi2.servidor.mundo.AdministradorJugadores;
import uniandes.cupi2.servidor.mundo.Continental;
import uniandes.cupi2.servidor.mundo.Encuentro;

/**
 * Esta clase sirve para enriquecer el comportamiento de la clase Continental para así facilitar la construcción de las pruebas automáticas.
 */
public class DecoradorServidor extends Continental
{

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * atributo que modela si ya se inició un encuentro
     */
    private boolean encuentroIniciado;

    /**
     * El tiempo máximo (en milisegundos) que se va a esperar para que se cumplan las diferentes fases de la inicialización de un encuentro
     */
    private long timeout;

    /**
     * El mensaje en caso de algún fallo por error
     */
    private String mensajeFalla;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Método constructor de la clase encargado de crear un servidor de prueba
     * @param archivo El archivo de configuración del servidor 
     * @param tiempoTimeout Tiempo de espera de las distintas fases de inicialización
     * @throws Exception Cuando hay problemas con el archivo de propiedades o hay problemas en la conexión a la base de datos
     * @throws SQLException Cuando hay problemas conectando el almacén a la base de datos.
     */
    public DecoradorServidor( String archivo, long tiempoTimeout ) throws SQLException, Exception
    {
        super( archivo );

        encuentroIniciado = false;
        timeout = tiempoTimeout;
        mensajeFalla = null;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Método encargado de iniciar un encuentro haciendo uso de la clase DecoradorEncuentro. <br>
     * Después de que se intenta iniciar el encuentro, se espera hasta que el encuentro esté iniciado efectivamente, o hasta que haya un timeout. <br>
     * Si el timeout se cumple, entonces en el atributo falla queda un mensaje que explica el error.
     * @param nuevoEncuentro El nuevo encuentro a ser iniciado 
     */
    protected void iniciarEncuentro( Encuentro nuevoEncuentro )
    {
        try
        {
            Socket sJugador1 = nuevoEncuentro.darSocketJugador1( );
            Socket sJugador2 = nuevoEncuentro.darSocketJugador2( );
            AdministradorJugadores adminResultados = nuevoEncuentro.darAdministradorResultados( );

            DecoradorEncuentro encuentroDecorado = new DecoradorEncuentro( sJugador1, sJugador2, adminResultados );

            super.iniciarEncuentro( encuentroDecorado );

            long tInicio = System.currentTimeMillis( );
            long tFinal = tInicio + timeout;

            while( !encuentroDecorado.estaIniciado( ) && System.currentTimeMillis( ) < tFinal )
            {
                try
                {
                    Thread.sleep( 100 );
                }
                catch( InterruptedException e1 )
                {
                }
            }

            if( !encuentroDecorado.estaIniciado( ) )
                mensajeFalla = "El encuentro no se inició en el tiempo disponible";
            else
                encuentroIniciado = true;
        }
        catch( IOException e )
        {
            mensajeFalla = "Hubo un error iniciando el encuentro: " + e.getMessage( );
        }
    }

    /**
     * Método encargado de saber si se inicio o no un encuentro. <br>
     * Este método espera hasta que el encuentro se inicie o hasta que se cumpla el timeout establecido.
     * @return Retorna true si se inició un encuentro. Retorna false en caso contrario.
     */
    public boolean seInicioEncuentro( )
    {
        long tInicio = System.currentTimeMillis( );
        long tFinal = tInicio + timeout;

        while( !encuentroIniciado && mensajeFalla == null && System.currentTimeMillis( ) < tFinal )
        {
            try
            {
                Thread.sleep( 100 );
            }
            catch( InterruptedException e )
            {
                e.printStackTrace( );
            }
        }

        return encuentroIniciado;
    }

    /**
     * Método encargado de retornar el mensaje que explica porqué hubo un fallo
     * @return Se retornó el mensaje con la cuasa de la falla. Si no ha habido ninguna falla, se retorno null
     */
    public String darFallo( )
    {
        return mensajeFalla;
    }
}

