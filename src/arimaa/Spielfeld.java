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
    
    // -------------------------------------------------------------------------
    // -------------------------- Attribute ------------------------------------
    // -------------------------------------------------------------------------
    
    private Spielfigur[][] feld;
    
    // -------------------------------------------------------------------------
    // ---------------------- Konstruktoren ------------------------------------
    // -------------------------------------------------------------------------
    
    
    public Spielfeld() {
        feld = new Spielfigur[ 8 ][ 8 ];
    }
    
    private Spielfeld( Spielfigur[][] Feld ) {
        this.feld = Feld;
    }
    
    
    
    // -------------------------------------------------------------------------
    // --------------------------- METHODEN ------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Spielfeld s = new Spielfeld();
        System.out.println( s );
        s.set( new Koord( 0, 1 ), new Spielfigur( Farbe.Silber, Typ.Katze ) );
        System.out.println( s );
        System.out.println( s.getFigurenAnzahl() );
        System.out.println( Arrays.toString( Spielfeld.getNeighbourKoords( new Koord( 7, 7 ) ).toArray() ) );
    }
    
    // -------------------------------------------------------------------------
    // ------------------- GET, SET, DELETE ------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * EIne Spielfigur wird an der Koordinate koord platziert 
     * @param koord
     * @param figur 
     */
    public void set( Koord koord, Spielfigur figur ) {
        feld[ koord.x ] [ koord.y ] = figur;
    }
    
    /**
     * giebt die Figur auf der jeweiligen Koordinate zurück
     * @param koord
     * @return Spielfigur
     */
    public Spielfigur get( Koord koord ) {
        return feld[ koord.x ][ koord.y ];
    }
    
    /**
     * Löscht Figur auf der jeweiligen Koordinate
     * @param koord
     * @return gelöschte Figur
     */
    public Spielfigur del( Koord koord ) {
        Spielfigur tmp = get( koord );
        set( koord, null );
        return tmp;
    }
    
    // -------------------------------------------------------------------------
    // ------------------- FLIP, FIGURENANZAHL ------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * Diese Methode tauscht zwei Spielfiguren an den Koordinaten koord1 und koord2
     * @param koord1
     * @param koord2 
     */
    public void flip( Koord koord1, Koord koord2 )  {

        // Tauschen mit Hilfsvariable (tmp)
        Spielfigur tmp = feld[ koord1.x ][ koord1.y ];
        feld[ koord1.x ][ koord1.y ] = feld[ koord2.x ][ koord2.y ];
        feld[ koord2.x][ koord2.y ] = tmp;
        
    }
    
    
    /**
     * 
     * @return Anzahl der Figuren des Felds
     * @author Alexander Holzinger
     * @version 1.0
     */
    public int getFigurenAnzahl() {
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
    
    // -------------------------------------------------------------------------
    // --------------------------- NEIGHBOURs ----------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * Prüft ob die zwei Koordinaten benachbart sind
     * @param koord1
     * @param koord2
     * @return benachbart?
     */
    public static boolean isNeighbourKoord( Koord koord1, Koord koord2 ) {
        
        
        // Wenn in derselben Spalte, dann gleich 0 ( => a1 a2; |a - a| = 0 )
        // Wenn in Nachbarspalten, dann gleich 1 ( => a1 b2; |a - b| = 1 )
        int diff1 = abs( koord1.x - koord2.x );
        int diff2 = abs( koord1.y - koord2.y );
        
        // Nachbarn sind zwei Felder, wenn sie entweder in gleichen Spalten 
        // und in benachbarten Zeilen sind oder umgekehrt
        return diff1 + diff2 == 1;
        
    }
    
    
    /**
     * gibt alle Nachbarkoordinaten zurück
     * @param koord
     * @return Nachbarkoordinaten
     */
    public static ArrayList<Koord> getNeighbourKoords( Koord koord ) {
        
        ArrayList<Koord> koords = new ArrayList<>();
        Koord cache;
        
        // linker Nachbar
        try {
            koords.add( new Koord( koord.x - 1, koord.y ) );
            
        } catch( IllegalArgumentException e ) {
            // Nothing happends
        }
        
        // unterer Nachbar
        try {
            koords.add( new Koord( koord.x, koord.y - 1 ) );
            
        } catch( IllegalArgumentException e ) {
            // Nothing happends
        }
        
        // rechter Nachbar
        try {
            koords.add( new Koord( koord.x + 1, koord.y ) );
            
        } catch( IllegalArgumentException e ) {
            // Nothing happends
        }
        
        
       // oberer Nachbar
        try {
            koords.add( new Koord( koord.x, koord.y + 1 ) );
            
        } catch( IllegalArgumentException e ) {
            // Nothing happends
        }
        
        return koords;
    }
    
    /**
     * gibt alle gültigen Nachbarfiguren zurück
     * @param koord
     * @return Nachbarfiguren
     */
    public ArrayList<Spielfigur> getNeighbours( Koord koord ) {
        
        ArrayList<Spielfigur> neighbours = new ArrayList<>();
        
        ArrayList<Koord> neighbourKoords = getNeighbourKoords( koord );
        
        // Prüft alle Nachbarfelder auf vorhandene Figuren
        for( Koord neighbourKoord : neighbourKoords ) {
            
            if( get( neighbourKoord ) != null ) {
                
                neighbours.add( get( neighbourKoord ) );
            }
        }
        
        return neighbours;
        
    }
    
    // -------------------------------------------------------------------------
    // ------------------- EQUALS, COPY, TOSTRING -------------------------------
    // -------------------------------------------------------------------------
    
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
     * @return Kopie des  Spielfelds
     * @author Alexander Holzinger
     * @version 1.0
     */
    public Spielfeld copy() {
        Spielfigur[][] feld2 = feld.clone();
        
        for (int i = 0; i < feld.length; i++) {
            
            feld2[ i ] = feld[ i ].clone();
        }
        
        return new Spielfeld( feld2 );
        
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
    
}
