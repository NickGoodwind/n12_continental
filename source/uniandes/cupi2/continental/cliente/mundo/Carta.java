/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: Carta.java 1013 2007-08-20 23:04:33Z camil-ji $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_continental
 * Autor: Equipo Cupi2 2014
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.continental.cliente.mundo;

/**
 * Clase que representa una carta de una baraja.
 * <b>inv:</b><br>
 * palo != null <br>
 * palo pertenece a {PICAS, CORAZONES, DIAMANTES, TREBOLES}<br>
 * representacion != null <br>
 * representacion pertenece a {AS, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K}<br>
 * imagen != null <br>
 */
public class Carta
{

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Palo de la carta.
     */
    private String palo;

    /**
     * Representación de la carta.
     */
    private String representacion;

    /**
     * Nombre de la imagen de la carta.
     */
    private String imagen;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor de la clase.
     * @param pPalo palo de la carta.  pPalo.equals(PICAS) ||  pPalo.equals(TREBOLES) ||  pPalo.equals(CORAZONES) || pPalo.equals(DIAMANTES)
     * @param pRepresentacion representación de la carta. AS,2,3,4,5,6,7,8,9,10,J,Q,K
     * @param pImagen Imagen de la carta
     */
    public Carta( String pPalo, String pRepresentacion, String pImagen )
    {
        palo = pPalo;
        representacion = pRepresentacion;
        imagen = pImagen;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Devuelve el palo de la carta.
     * @return Palo de la carta.
     */
    public String darPalo( )
    {
        return palo;
    }

    /**
     * Devuelve la representación de la carta.
     * @return Representación de la carta.
     */
    public String darRepresentacion( )
    {
        return representacion;
    }
    
    /**
     * Devuelve la representación numerica de la carta.
     * @return Representación numerica de la carta.
     */
    public int darNumero( )
    {
       if( representacion.equals( "AS" ) )
            return 1;
        else if( representacion.equals( "J" ) )
            return 11;
        else if( representacion.equals( "Q" ) )
            return 12;
        else if( representacion.equals( "K" ) )
            return 13;
        else
            return Integer.valueOf( representacion ).intValue( );
    }

    /**
     * Devuelve la imagen de la carta.
     * @return Imagen de la carta.
     */
    public String darImagen( )
    {
        return imagen;
    }

    /**
     * Devuelve la información de la carta en una cadena con el formato solicitado por el servidor.
     * @return Información de la carta.
     */
    public String toString( )
    {
        return "CARTA:" + palo + ":" + representacion + ":" + imagen;
    }

    /**
     * Compara dos cartas por su representación.
     * @param carta Carta con la cual se va a comparar.
     * @return Devuelve 0 si las cartas tienen la misma numeración. <br>
     *         Devuelve -1 si la carta dada por parámetro tiene una valor "MAYOR" para la numeración. <br>
     *         Devuelve 1 si la carta dada por parámetro tiene una valor "MENOR" para la numeración. <br>
     */
    public int comparar( Carta carta )
    {

        if( representacion.equals( carta.darRepresentacion( ) ) )
            return 0;
        else if( darNumeroRepresentacion( representacion ) < darNumeroRepresentacion( carta.darRepresentacion( ) ) )
            return -1;
        else
            return 1;
    }

    /**
     * Devuelve el numero de la representación de la carta.
     * @param pRepresentacion representación de la carta.
     * @return numero de la representación de la carta.
     */
    private int darNumeroRepresentacion( String pRepresentacion )
    {
        if( pRepresentacion.equals( "AS" ) )
            return 1;
        else if( pRepresentacion.equals( "J" ) )
            return 11;
        else if( pRepresentacion.equals( "Q" ) )
            return 12;
        else if( pRepresentacion.equals( "K" ) )
            return 13;
        else
            return Integer.valueOf( pRepresentacion ).intValue( );
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase <b>inv:</b><br>
     * palo != null <br>
     * representacion != null <br>
     * imagen != null <br>
     * 
     */
    private void verificarInvariante( )
    {
        assert palo != null : "Palo nulo.";
        assert representacion != null : "Representación nula.";
        assert imagen != null : "Imagen nula.";
        assert ( palo.equals( "PICAS" ) || palo.equals( "CORAZONES" ) || palo.equals( "DIAMANTES" ) || palo.equals( "TREBOLES" ) ) : "El palo no es válido";
        assert ( representacion.equals( "AS" ) || representacion.equals( "1" ) || representacion.equals( "2" ) || representacion.equals( "3" ) || representacion.equals( "4" ) || representacion.equals( "5" ) || representacion.equals( "6" )
                || representacion.equals( "7" ) || representacion.equals( "8" ) || representacion.equals( "9" ) || representacion.equals( "10" ) || representacion.equals( "J" ) || representacion.equals( "Q" ) || representacion.equals( "k" ) ) : "La representación no es válida.";
    }
}
