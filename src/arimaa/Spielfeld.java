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
 * @version 1.0
 */
public class Spielfeld {
    // Attribute
    final private Spielfigur[][] feld;
    
    //Konstruktoren
    public Spielfeld() {
        feld = new Spielfigur[ 8 ][ 8 ];
    }
    
    private Spielfeld( Spielfigur[][] Feld ) {
        this.feld = Feld;
    }
    
    // Methoden
    /**
     *
     * @param AnderesFeld Feld mit dem verglichen wird
     * @return 
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public boolean equals( Spielfeld AnderesFeld ) {
        return Arrays.equals(this.feld, AnderesFeld.feld );
    }
    
    /**
     * 
     * @return Anzahl der Figuren des Felds
     * @author Alexander Holzinger
     * @version 1.0
     */
    public int getNumberFiguren() {
        int Figures = 0;
        for ( Spielfigur[] reihe: feld ) {
            for ( Spielfigur sf: reihe ) {
                if ( sf != null) {
                    Figures++;
                }
            }
        }
        return Figures;
    }
    
    /**
     * 
     * @return Kopie des  Spielfelds
     * @author Alexander Holzinger
     * @version 1.0
     */
    public Spielfeld copy() {
        return new Spielfeld(this.feld.clone());
    }
    
    /**
     * Diese Methode setzt eine Spielfigur figur an die Stelle koord 
     * 
     * @param koord Koordinaten auf die die Spielfiguren gesetzt wird
     * @param figur Figur, die auf die Koordinaten gesetzt werden
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public void set( String koord, Spielfigur figur ) throws IndexOutOfBoundsException {
        int[] koords = koordsValid( koord );
        feld[ koords[ 0 ] ][ koords[ 1 ] ] = figur;
    }
    
    /**
     * Diese Methode gibt die Figur an der Stelle koord zurück
     * 
     * @param koord Koordinaten der Figur
     * @return Gibt die Spielfigur an den Koordinaten koord zurück
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public Spielfigur get( String koord ) throws IndexOutOfBoundsException {
        int[] koords = koordsValid( koord );
        return feld[ koords[ 0 ] ][ koords[ 1 ] ];
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
        Spielfigur tmp = get( koord );
        set( koord, null );
        return tmp;
    }
    
    /**
     * Diese Methode tauscht zwei Spielfiguren an den Koordinaten koord1 und koord2
     * 
     * @param koord1 Koordinaten Spielfigur 1
     * @param koord2 Koordinaten Spielfigur 2
     * @throws IndexOutOfBoundsException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public void flip( String koord1, String koord2 ) throws IndexOutOfBoundsException {
        int[] koords1 = koordsValid( koord1 );
        int[] koords2 = koordsValid( koord2 );
        
        // Tauschen mit Hilfsvariable (tmp)
        Spielfigur tmp = feld[ koords1[ 0 ] ][ koords1[ 1 ] ];
        feld[ koords1[ 0 ] ][ koords1[ 1 ] ] = feld[ koords2[ 0 ] ][ koords2[ 1 ] ];
        feld[ koords2[ 0 ] ][ koords2[ 1 ] ] = tmp;
    }
    
    /**
     * Diese Methode gibt die Koordinaten im X-/Y-Format zurück (z.B.: 5 / 7), welches direkt mit
     * der Implementierung des Arrays kompatibel ist
     * 
     * @param koord Die Koordinaten im Format, dass von Schach bekannt ist (z.B.: A1, C7)
     * @return Ein int[ 2 ] mit der X- und Y-Koordinate
     * @throws IllegalArgumentException
     * @author Alexander Holzinger
     * @version 1.0
     */
    public int[] koordsValid( String koord ) throws IllegalArgumentException {
        koord = koord.toUpperCase();
        
        // Überprüfung
        if (! koord.matches( "[a-hA-H][1-8]" ) ) throw new IllegalArgumentException( "Ungültige Koordinaten!" );
        
        // Buchstaben und Zahlen zu Indizes umformen
        int[] koords = new int[ 2 ];
        koords[ 0 ] = koord.charAt( 0 ) - 65;
        koords[ 1 ] = koord.charAt( 1 ) - 49;
        return koords;
    }
}
