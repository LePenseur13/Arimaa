/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arimaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.abs;
import java.util.ArrayList;


/**
 *
 * @author JosefChristoph, Marcus Gabler

 */
public class Arimaa {

    // Attribute
    private Spielfeld spielfeld;
    
    //Konstruktoren
    public Arimaa() {
        spielfeld = new Spielfeld();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ///*
        Arimaa a = new Arimaa();
        System.out.println( a.spielfeld.toString() );
        a.spielfeld.set( benutzerEingabe(), new Spielfigur( "Gold", "Katze" ) );
        System.out.println( a.spielfeld.toString() );
        a.zug( Farbe.Gold );
        //*/
        /*
        Farbe f1 = Farbe.valueOf( "Gold" );
        Farbe f2 = Farbe.getValue( "Gold" );
        System.out.println( f1 == f2 );
        */
    }

    // Methoden
    
    /**
     *
     * @param farbe
     */
    public void zug( Farbe farbe ) {
        String startKoord;
        String zielKoord;
        
        String cacheStartKoord = null;
        //String cacheZielKoord = null;
        
        ArrayList<String> zielKoords;
        
        boolean valid;
        
        
        Spielfigur figur;
        
        for ( int schritt = 1; schritt <= 4; schritt++ ) {
            
            // Start Koordinate
            do {
                System.out.println( "Start: " );
                startKoord = benutzerEingabe();
                
                figur = spielfeld.get( startKoord );
                valid = false;
                zielKoords = new ArrayList<>();
                
                // gültig wenn auf dem Feld eine Figur steht
                if( figur != null ) {
                    
                    // freies Nachbarfeld
                    String[] koords = getNeighbourKoords( startKoord );
                    
                    for ( String koord : koords ) {

                            // Zielfeld => leeres Nachbarfeld
                            if( spielfeld.get( koord ).getFarbe() == null ){
                                valid = true;
                                zielKoords.add( koord );
                            }
                        
                    }
                    
                    
                    // Figur des anderen Spielers
                    if( valid && figur.getFarbe() != farbe ) {
                        
                        // alle möglichen Zielkoordinaten
                        zielKoords = new ArrayList<>();
                        
                        // Pull
                        
                        // Im ersten Schritt nicht möglich => cache != null
                        if( cacheStartKoord != null ) {
                            
                            // Wenn es möglich ist diese Figur nachzuziehen
                            // d.h. wenn das ehemalige Feld der Figur 
                            // ein Nachbarfeld dieser Figur ist
                            
                            if( isNeighbourKoord( startKoord, cacheStartKoord ) ){
                                valid = true;
                                zielKoords.add( cacheStartKoord ); // Zielfeld
                            }
                        }
                        
                        // Push
                        
                        // nur möglich in den ersten 3 Schritten
                        if( schritt < 4 ) {
                            
                            // prüft ob ein Nachbar eine eigene Figur ist
                            // d.h. ob eine Figur vorhanden ist die schieben könnte
                            for ( String koord : koords ) {
                            
                                // Nachbarfigur, die schieben könnte
                                if( spielfeld.get( koord ).getFarbe() == farbe ){
                                    valid = true;
                                    zielKoords.add( koord ); // Zielfeld
                                }
                            }
                        }
                    }
                }
            } while( valid );
            
            // Ziel Koordinate
            do {
                System.out.println( "Ziel: " );
                zielKoord = benutzerEingabe();
                
                // gültig wenn ein Nachbar von startKoord
                valid = ! isNeighbourKoord( startKoord, zielKoord);
            
            
            
            } while( valid );
            
            spielfeld.flip( startKoord, zielKoord); // Schritt Ausführung
            
            // Fallenfelder - Methode
            
            System.out.println( spielfeld.toString() );
        }
    }
    
    /**
     * Liest solange Koordinaten ein,
     * bis eine passende Startkoordinate eingelsesen wird
     * Startkoordiate muss ein Feld mit einer Figur sein
     * @return Startkoordinate
     */
    public String getStartKoord() {
        String startKoord;
        
        do {
                startKoord = benutzerEingabe();
                // gültig wenn auf dem Feld eine Figur steht
            } while( spielfeld.get( startKoord ).equals( null ) );
        
        return startKoord;
    }
     
    /**
     * Liest solange Koordinaten ein,
     * bis eine passende Zielkoordinate eingelsesen wird
     * Zielkoordiate muss ein Nachbarfeldfeld von startKoord sein
     * @return Startkoordinate
     */
    public String getZielKoord() {
        String startKoord;
        
        do {
                startKoord = benutzerEingabe();
                // gültig wenn auf dem Feld eine Figur steht
            } while( ! isNeighbourKoord(startKoord, startKoord) );
        
        return startKoord;
    }
            
    /**
     * liest solange von der Konsole ein, 
     * bis eine passende Benutzereingabe erfolgt
     * ==> Koordinate ( [a-hA-H][1-8] zB.: a1 )
     *
     * @return Benutzereingabe( Koordinate )
     * @throws IllegalArgumentException
     */
    private static String benutzerEingabe() {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input;
        
        do {
            try {
                input = br.readLine();
                
            } catch ( IOException ex ) {
                throw new IllegalArgumentException( "Eingabevorgang abgebrochen!" );
            }
            
        } while( ! Spielfeld.validKoord( input ) ); // Prüft ob der gegebene String eine gültige Koordinate ist
        
        return input;
    }
    
    /**
     * Prüft ob die zwei Koordinaten benachbart sind
     * @param koord1
     * @param koord2
     * @return benachbart?
     * @throws IllegalArgumentException 
     */
    private static boolean isNeighbourKoord( String koord1, String koord2 ) throws IllegalArgumentException{
        
        // Prüft ob Koordinaten gültig sind
        if( ! Spielfeld.validKoord( koord1 ) || ! Spielfeld.validKoord( koord2 ) ) {
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
    private static String[] getNeighbourKoords( String koord ) throws IllegalArgumentException{
        
        // Prüft ob Koordinate gültig ist
        if( ! Spielfeld.validKoord( koord ) ) {
            throw new IllegalArgumentException( "Ungültige Koordinate!" );
        }
        
        String[] arr = new String[ 4 ];
        String cache;
        
        // liker Nachbar
        cache = Character.toString( (char) ( koord.charAt( 0 ) - 1 ) ) + Character.toString( koord.charAt( 1 ) );
        if( ! Spielfeld.validKoord( cache ) ) cache = "";
        arr[ 0 ] = cache;
        
        // unterer Nachbar
        cache = Character.toString( koord.charAt( 0 ) ) + Character.toString( (char) ( koord.charAt( 1 ) - 1 ) );
        if( ! Spielfeld.validKoord( cache ) ) cache = "";
        arr[ 0 ] = cache;
        
        // rechter Nachbar
        cache = Character.toString( (char) ( koord.charAt( 0 ) + 1 ) ) + Character.toString( koord.charAt( 1 ) );
        if( ! Spielfeld.validKoord( cache ) ) cache = "";
        arr[ 0 ] = cache;
        
        
       // oberer Nachbar
        cache = Character.toString( koord.charAt( 0 ) ) + Character.toString( (char) ( koord.charAt( 1 ) + 1 ) );
        if( ! Spielfeld.validKoord( cache ) ) cache = "";
        arr[ 0 ] = cache;
        
        return arr;
    }
}
