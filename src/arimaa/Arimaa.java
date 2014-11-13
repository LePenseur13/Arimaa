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
        a.spielfeld.set( "c5", new Spielfigur( "Gold", "Elefant" ) );
        a.spielfeld.set( "b2", new Spielfigur( "Gold", "Kamel" ) );
        a.spielfeld.set( "d4", new Spielfigur( "Gold", "Kaninchen" ) );
        a.spielfeld.set( "c4", new Spielfigur( "Silber", "Kamel" ) );
        System.out.println( a.spielfeld.toString() );
        a.zug2( Farbe.Gold );
        //*/
        /*
        Farbe f1 = Farbe.valueOf( "Gold" );
        Farbe f2 = Farbe.getValue( "Gold" );
        System.out.println( f1 == f2 );
        */
    }

    // Methoden
    
    /**
     * Ein vollständiger Zug, 4 Schritte und jeweils die Benutzereinabe
     * @param farbe
     */
    public void zug( Farbe farbe ) {
        String startKoord;
        String zielKoord;
        
        String lastStartKoord = "";
        String nextZielKoord = "";
        
        ArrayList<String> zielKoords;
        
        boolean valid;
        
        
        
        Spielfigur figur;
        
        for ( int schritt = 1; schritt <= 4; schritt++ ) {
            
            // Start Koordinate
            do {
                System.out.println( "Start: " );
                startKoord = benutzerEingabe( "[a-hA-H][1-8]");
                
                figur = spielfeld.get( startKoord );
                
                valid = false;
                zielKoords = new ArrayList<>();
                
                // gültig wenn auf dem Feld eine Figur steht
                if( figur != null ) {
                    
                    // freies Nachbarfeld ?
                    ArrayList<String> neighbourKoords = getNeighbourKoords( startKoord );
                    
                    for ( String koord : neighbourKoords ) {

                        // Zielfeld => leeres Nachbarfeld
                        if( spielfeld.get( koord ) == null ){
                            valid = true;
                            zielKoords.add( koord );
                        } 
                    }
                    
                    // falls im forherigen Schritt eine Figur des Gegners geschoben wurde
                    // muss nun eine eigene Figur deren ursprünglichen Platz einnehmen
                    if( ! nextZielKoord.isEmpty() ) {
                        
                        // ist es eine Figur der eigenen Farbe ?
                        if( spielfeld.get( startKoord ).getFarbe() == farbe 
                            && isNeighbourKoord( startKoord, nextZielKoord ) ) {
                            
                            // nur das gespeicherte Feld ist gültig
                            zielKoords.clear();
                            zielKoords.add( nextZielKoord );
                            
                            // wird nicht mehr benötigt
                            nextZielKoord = "";
                            
                        } else {
                            valid = false;
                        }
                    }
                    
                    // Figur des anderen Spielers
                    if( valid && figur.getFarbe() != farbe ) {
                        
                        // alle möglichen Zielkoordinaten
                        
                        
                        // Pull
                        
                        // Im ersten Schritt nicht möglich => cache != null
                        if( ! lastStartKoord.isEmpty() ) {
                            
                            // Wenn es möglich ist diese Figur nachzuziehen
                            // d.h. wenn das ehemalige Feld der Figur 
                            // ein Nachbarfeld dieser Figur ist
                            
                            if( isNeighbourKoord( startKoord, lastStartKoord ) ){
                                valid = true;
                                zielKoords.clear();
                                zielKoords.add( lastStartKoord ); // Zielfeld
                            }
                        }
                        
                        // Push
                        
                        // nur möglich in den ersten 3 Schritten
                        if( schritt < 4 ) {
                            
                            // prüft ob ein Nachbar eine eigene Figur ist
                            // d.h. ob eine Figur vorhanden ist die schieben könnte
                            for ( String koord : neighbourKoords ) {
                                
                                // koord muss nicht eine Figur enthalten
                                // Nachbarfigur, die schieben könnte
                                if( spielfeld.get( koord ) != null && spielfeld.get( koord ).getFarbe() == farbe ) {
                                    valid = true;
                                    
                                    // nächste Figur muss dieses Feld einnehmen
                                    nextZielKoord = startKoord;
                                }
                            }
                        }
                    }
                }
            } while( ! valid );
            
            // Ziel Koordinate
            do {
                System.out.println( "Ziel: " );
                zielKoord = benutzerEingabe( "[a-hA-H][1-8]");
                
                // Koordinate muss eine mögliche Zielkoordinate sein
            } while( ! zielKoords.contains( zielKoord ) );
            
            spielfeld.flip( startKoord, zielKoord); // Schritt Ausführung
            
            // Fallenfelder - Methode
            
            System.out.println( spielfeld.toString() );
        }
    }
    
    public void zug2( Farbe farbe ) {
        String startKoord;
        String zielKoord;
        String zielKoordPush;
        
        String lastStartKoord = "";
        int lastStrenght = -1;
        
        ArrayList<String> zielKoords;
        
        boolean valid;
        
        Spielfigur figur;
        
        for ( int schritt = 1; schritt <= 4; schritt++ ) {
            
            // Start Koordinate
            do {
                System.out.println( "Start: " );
                startKoord = benutzerEingabe( "([a-hA-H][1-8])|end");
                
                // end beendet den Zug
                if( startKoord.equals( "end" ) ) return; 
                
                figur = spielfeld.get( startKoord );
                
                valid = false;
                zielKoords = new ArrayList<>();
                
                boolean festgehalten = false;
                boolean beschuetzt = false;
                
                // gültig wenn auf dem Feld eine Figur steht
                if( figur != null ) {
                    
                    // freies Nachbarfeld ?
                    ArrayList<String> neighbourKoords = getNeighbourKoords( startKoord );
                    
                    for ( String koord : neighbourKoords ) {

                        // Zielfeld => leeres Nachbarfeld
                        if( spielfeld.get( koord ) == null ){
                            valid = true;
                            zielKoords.add( koord );
                            
                        } else if( spielfeld.get( koord ).getFarbe() != farbe 
                                && figur.getTyp().ordinal() > spielfeld.get( koord ).getTyp().ordinal() ){
                            
                            ArrayList<String> neighbourKoords2 = getNeighbourKoords( koord );
                            
                            for( String koord2 : neighbourKoords2 ) {
                                
                                if( spielfeld.get( koord2 ) == null ) {
                                    valid = true;
                                }
                            }
                        }
                        
                        else if( spielfeld.get( koord ).getFarbe() != farbe 
                                && spielfeld.get( koord ).isStronger( figur ) ) {
                            
                            festgehalten = true; 
                            
                        } else if( spielfeld.get( koord ).getFarbe() == farbe ){
                            beschuetzt = true;
                        }   
                    }
                    
                    if( festgehalten && ! beschuetzt ) {
                        valid = false;
                    }
                    
                    // Figur des anderen Spielers
                    if( valid && figur.getFarbe() != farbe ) {
                        
                        // Pull
                        
                        // Im ersten Schritt nicht möglich => cache != null
                        if( ! lastStartKoord.isEmpty() ) {
                            
                            // Wenn es möglich ist diese Figur nachzuziehen
                            // d.h. wenn das ehemalige Feld der Figur 
                            // ein Nachbarfeld dieser Figur ist
                            // und wenn diese Figur schwächer ist
                            if( isNeighbourKoord( startKoord, lastStartKoord ) 
                                    && figur.getTyp().ordinal() < lastStrenght ){
                                zielKoords.clear();
                                zielKoords.add( lastStartKoord ); // Zielfeld
                            } else {
                                
                                valid = false;
                            }
                            
                        } else {
                            
                            valid = false;
                        }
                    }
                }
            } while( ! valid );
            
            // Ziel Koordinate
            do {
                System.out.println( "Ziel: " );
                zielKoord = benutzerEingabe( "[a-hA-H][1-8]");
                
                //Push
                
                // Nur bis zum 3. Schritt möglich
                // Falls Zielfeld eine Figur des Gegners
                if( schritt < 4 && spielfeld.get( zielKoord ) != null
                    && spielfeld.get( zielKoord ).getFarbe() != farbe 
                    && figur.getTyp().ordinal() > spielfeld.get( zielKoord ).getTyp().ordinal() ) {
                    
                    ArrayList<String> koords = getNeighbourKoords( zielKoord );
                    
                    for( String koord : koords ) {
                        
                        // Falls Figur geschoben werden kann
                        if( spielfeld.get( koord ) == null ){
                                
                            zielKoords.clear();
                            zielKoords.add( zielKoord );
                            
                            do {
                                
                                System.out.println( "Ziel( Figur des Gegners): " );
                                zielKoordPush = benutzerEingabe( "[a-hA-H][1-8]");
                                
                            } while( ! isNeighbourKoord( zielKoord, zielKoordPush ) 
                                    && spielfeld.get( zielKoordPush) == null );
                            
                            spielfeld.flip( zielKoord, zielKoordPush ); // Push ausführen
                            schritt++;
                            break;
                        }
                    }
                }
                
                // Koordinate muss eine mögliche Zielkoordinate sein
            } while( ! zielKoords.contains( zielKoord ) );
            
            spielfeld.flip( startKoord, zielKoord); // Schritt Ausführung
            
            // Fallenfelder - Methode
            
            if( figur.getFarbe() == farbe ) {
                lastStartKoord = startKoord;
                lastStrenght = figur.getTyp().ordinal();
                
            } else {
                
                lastStartKoord = "";
                lastStrenght = -1;
            }
            
            
            System.out.println( spielfeld.toString() );
        }
    }
    
    
    /**
     * liest solange von der Konsole ein, 
     * bis eine passende Benutzereingabe erfolgt
     * ==> Koordinate ( [a-hA-H][1-8] zB.: a1 )
     *
     * @return Benutzereingabe( Koordinate )
     * @throws IllegalArgumentException
     */
    private static String benutzerEingabe() throws IllegalArgumentException {
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
     * liest solange von der Konsole ein, 
     * bis eine passende Benutzereingabe erfolgt
     * ==> regex
     *
     * @return Benutzereingabe
     * @throws IllegalArgumentException
     */
    private static String benutzerEingabe( String regex ) {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input;
        
        do {
            try {
                input = br.readLine();
                
            } catch ( IOException ex ) {
                throw new IllegalArgumentException( "Eingabevorgang abgebrochen!" );
            }
            
        } while( ! input.matches( regex ) ); // Prüft ob der gegebene String eine gültige Koordinate ist
        
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
    private static ArrayList<String> getNeighbourKoords( String koord ) throws IllegalArgumentException{
        
        // Prüft ob Koordinate gültig ist
        if( ! Spielfeld.validKoord( koord ) ) {
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
