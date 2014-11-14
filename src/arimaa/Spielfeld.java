/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Alexander Holzinger
 * @version 1.0
 */
public class Spielfeld {
    // Attribute
    private Spielfigur[][] feld;
    
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
        return new Spielfeld( this.feld.clone() );
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
        int[] koords = getValidKoords( koord );
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
        int[] koords = getValidKoords( koord );
        return feld[ koords[ 0 ] ][ koords[ 1 ] ];
    }
    
    public int [] getKoords( String koord ) throws IndexOutOfBoundsException {
        int[] koords = getValidKoords( koord );
        return koords;
    }
    
    public Spielfigur get( int [] koords ) throws IndexOutOfBoundsException {
        return feld[ koords[ 0 ] ][ koords[ 1 ] ];
    }
    
    public Spielfigur get( int x, int y ) throws IndexOutOfBoundsException {
        return feld[ x ][ y ];
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
        int[] koords1 = getValidKoords( koord1 );
        int[] koords2 = getValidKoords( koord2 );
        
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
    public int[] getValidKoords( String koord ) throws IllegalArgumentException {
        koord = koord.toUpperCase();
        
        // Überprüfung
        if ( ! validKoord( koord ) ) throw new IllegalArgumentException( "Ungültige Koordinaten!" );
        
        // Buchstaben und Zahlen zu Indizes umformen
        int[] koords = new int[ 2 ];
        koords[ 0 ] = koord.charAt( 0 ) - 65;
        koords[ 1 ] = koord.charAt( 1 ) - 49;
        return koords;
    }
    
    /**
     * prüft ob die gegebene Koordinate gültig ist
     * @param koord
     * @return gültig?
     */
    public static boolean validKoord( String koord ) {
        return koord.matches( "[a-hA-H][1-8]" );
        
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        
        sb.append( "-----------------------------------------\n" );
        for ( int i = 7; i >= 0; i-- ) {
            
            sb.append( "| " );
            for ( int j = 0; j < 8; j++ ) {
                
                if( feld[ j ][ i ] == null ) {
                    
                    sb.append( "   | " );
                } else{
                    
                    sb.append( feld[ j ][ i ] + " | ");
                }
                
            }
            sb.append( "\n" );
            
            sb.append( "-----------------------------------------\n" );
        }
        
        return sb.toString();
    }
    
    
    /**
     * Prüft ob die zwei Koordinaten benachbart sind
     * @param koord1
     * @param koord2
     * @return benachbart?
     * @throws IllegalArgumentException 
     */
    public static boolean isNeighbourKoord( String koord1, String koord2 ) throws IllegalArgumentException{
        
        // Prüft ob Koordinaten gültig sind
        if( ! validKoord( koord1 ) || ! Spielfeld.validKoord( koord2 ) ) {
            throw new IllegalArgumentException( "Ungültige Koordinaten!" );
        }
        
        // Wenn in derselben Spalte, dann gleich 0 ( => a1 a2; |a - a| = 0 )
        // Wenn in Nachbarspalten, dann gleich 1 ( => a1 b2; |a - b| = 1 )
        int diff1 = abs( koord1.charAt( 0 ) - koord2.charAt( 0 ) );
        int diff2 = abs( koord1.charAt( 1 ) - koord2.charAt( 1 ) );
        
        // Nachbarn sind zwei Felder, wenn sie entweder in gleichen Spalten 
        // und in benachbarten Zeilen sind oder umgekehrt
        return diff1 + diff2 == 1;
    }
    
    
    /**
     * gibt alle Nachbarkoordinaten zurück
     * @param koord
     * @return Nachbarkoordinaten
     * @throws IllegalArgumentException 
     */
    public static ArrayList<String> getNeighbourKoords( String koord ) throws IllegalArgumentException{
        
        // Prüft ob Koordinate gültig ist
        if( ! validKoord( koord ) ) {
            throw new IllegalArgumentException( "Ungültige Koordinate!" );
        }
        
        ArrayList<String> koords = new ArrayList<>();
        String cache;
        
        // liker Nachbar
        cache = Character.toString( (char) ( koord.charAt( 0 ) - 1 ) ) + Character.toString( koord.charAt( 1 ) );
        if( Spielfeld.validKoord( cache ) ) {
            koords.add( cache );
        }
        
        // unterer Nachbar
        cache = Character.toString( koord.charAt( 0 ) ) + Character.toString( (char) ( koord.charAt( 1 ) - 1 ) );
        if( Spielfeld.validKoord( cache ) ) {
            koords.add( cache );
        }
        
        // rechter Nachbar
        cache = Character.toString( (char) ( koord.charAt( 0 ) + 1 ) ) + Character.toString( koord.charAt( 1 ) );
        if( Spielfeld.validKoord( cache ) ) {
            koords.add( cache );
        }
        
        
       // oberer Nachbar
        cache = Character.toString( koord.charAt( 0 ) ) + Character.toString( (char) ( koord.charAt( 1 ) + 1 ) );
        if( Spielfeld.validKoord( cache ) ) {
            koords.add( cache );
        }
        
        return koords;
    }
}
