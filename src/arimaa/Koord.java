/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

/**
 * 
 * @author JosefChristoph
 */
public class Koord {
    
    public int x;
    public int y;

    public Koord( int x, int y ) {
        // Falls Koordinate ungültig ist wird eine Exception geworfen
        if( ! Koord.valid( x, y ) ) throw new IllegalArgumentException( "Ungültige Koordinaten!" );
        
        this.x = x;
        this.y = y;
    }
    
    public Koord( String koord ) {
        // Falls Koordinate ungültig ist wird eine Exception geworfen
        if( ! valid( koord ) ) throw new IllegalArgumentException( "Ungültige Koordinaten!" );
        
        koord = koord.toUpperCase();
        this.x = koord.charAt( 0 ) - 'A';
        this.y = koord.charAt( 1 ) - '1';
    }

    /**
     * prüft ob die gegebene Koordinate gültig ist
     * @param x
     * @param y
     * @return gültig?
     */
    public static boolean valid( int x, int y ) {
        boolean xValid = 0 <= x && x < 7;
        boolean yValid = 0 <= y && x < 7;
        
        return xValid && yValid;
        
    }
    
    /**
     * prüft ob die gegebene Koordinate gültig ist
     * @param koord
     * @return gültig?
     */
    public static boolean valid( String koord ) {
        return koord.matches( "[a-hA-H][1-8]" );
        
    }
}
