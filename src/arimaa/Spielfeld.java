/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

import java.util.Arrays;

/**
 *
 * @author Alexander Holzinger
 */
public class Spielfeld {
    // Attribute
    final private Spielfigur[][] Feld;
    
    //Konstruktoren
    public Spielfeld() {
        Feld = new Spielfigur[ 8 ][ 8 ];
    }
    
    private Spielfeld( Spielfigur[][] Feld ) {
        this.Feld = Feld;
    }
    
    public Spielfigur getKorrds(int [] koords){
        return Feld[koords[0]][koords[1]];
    }
    
    public int anzahl(){
        return 0;
    }
    
    // Methoden
    /**
     *
     * @param AnderesFeld
     * @return 
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public boolean equals( Spielfeld AnderesFeld ) {
        return Arrays.equals( this.Feld, AnderesFeld.Feld );
    }
    
    /**
     * Gibt eine Kopie des Spielfelds zurück
     * 
     * @return
     * @author Alexander Holzinger
     * @version 1.0
     */
    public Spielfeld copy() {
        return new Spielfeld(this.Feld.clone());
    }
    
    /**
     * Diese Methode setzt eine Spielfigur figur an die Stelle koord 
     * 
     * @param koord
     * @param figur
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public void set( String koord, Spielfigur figur ) throws IndexOutOfBoundsException {
        int[] koords = koordsValid( koord );
        Feld[ koords[ 0 ] ][ koords[ 1 ] ] = figur;
    }
    
    /**
     * Diese Methode gibt die Figur an der Stelle koord zurück
     * 
     * @param koord
     * @return Gibt die Spielfigur an den Koordinaten koord zurück
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public Spielfigur getFigur( String koord ) throws IndexOutOfBoundsException {
        int[] koords = koordsValid( koord );
        return Feld[ koords[ 0 ] ][ koords[ 1 ] ];
    }
    
    public int [] getKoords( String koord ) throws IndexOutOfBoundsException {
        int[] koords = koordsValid( koord );
        return koords;
    }
    
    public Spielfigur get( int [] koords ) throws IndexOutOfBoundsException {
        return Feld[ koords[ 0 ] ][ koords[ 1 ] ];
    }
    
    public Spielfigur get( int x, int y ) throws IndexOutOfBoundsException {
        return Feld[ x ][ y ];
    }
    
    /**
     * Diese Methode löscht eine Figur an der Stelle koord
     * 
     * @param koord Die Koordinaten der zu löschenden Spielfigur
     * @return Die Gelöschte Spielfigur
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 2.0
     */
    public Spielfigur del( String koord ) throws IndexOutOfBoundsException {
        Spielfigur tmp = getFigur( koord );
        set( koord, null );
        return tmp;
    }
    
    /**
     * Diese Methode tauscht zwei Spielfiguren an den Koordinaten koord1 und koord2
     * 
     * @param koord1
     * @param koord2
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public void flip( String koord1, String koord2 ) throws IndexOutOfBoundsException {
        int[] koords1 = koordsValid( koord1 );
        int[] koords2 = koordsValid( koord2 );
        
        // Tauschen mit Hilfsvariable (tmp)
        Spielfigur tmp = Feld[ koords1[ 0 ] ][ koords1[ 1 ] ];
        Feld[ koords1[ 0 ] ][ koords1[ 1 ] ] = Feld[ koords2[ 0 ] ][ koords2[ 1 ] ];
        Feld[ koords2[ 0 ] ][ koords2[ 1 ] ] = tmp;
    }
    
    /**
     * Diese Methode gibt die Koordinaten im X-/Y-Format zurück (z.B.: 5 / 7), welches direkt mit
     * der Implementierung des Arrays kompatibel ist
     * 
     * @param koord Die Koordinaten im Format, dass von Schach bekannt ist (z.B.: A1, C7)
     * @return Ein int[ 2 ] mit der X- und Y-Koordinate
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public int[] koordsValid( String koord ){
        // Überprüfung
        if ( koord.charAt( 0 ) > 72 || koord.charAt( 0 ) < 65 ) throw new IndexOutOfBoundsException( "Ungültige Koordinaten!" );
        if ( koord.charAt( 1 ) > 56 || koord.charAt( 1 ) < 49 ) throw new IndexOutOfBoundsException( "Ungültige Koordinaten!" );
        
        // Buchstaben und Zahlen zu Indizes umformen
        int[] koords = new int[ 2 ];
        koords[ 0 ] = koord.toUpperCase().charAt( 0 ) - 65;
        koords[ 1 ] = koord.charAt( 1 ) - 49;
        return koords;
    }
}