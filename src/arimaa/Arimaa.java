/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arimaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 *
 * @author JosefChristoph, Marcus Gabler

 */
public class Arimaa {

    // Attribute
    private Spielfeld spielfeld;
    
    final String[] fallenFelder = { "c3", "f3", "c6", "f6" };
    
    private Farbe activePlayer;
    
    //Konstruktoren
    public Arimaa() {
        spielfeld = new Spielfeld();
        activePlayer = Farbe.Gold;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Arimaa a = new Arimaa();
        a.print();
        a.figurenSetzen( "Gold" );
        a.print();
        a.figurenSetzen( "Silber" );
        a.print();
        System.out.println( a.game() );
    }

    // Methoden
    
    /**
     * ein vollständiger Zug bestehend aus 4 Schritten,
     * der Benutzereingabe und allen Prüfungen
     * @param farbe 
     */
    public void zug( Farbe farbe ) {
        String startKoord;
        String zielKoord;
        String zielKoordPush;
        
        String lastStartKoord = "";
        int lastStrenght = -1;
        
        ArrayList<String> zielKoords = new ArrayList<>();;
        
        boolean valid = false;
        
        Spielfigur figur = null;
        
        for ( int schritt = 1; schritt <= 4; schritt++ ) {
            
            // Start Koordinate
            do {
                System.out.println( "Start: " );
                startKoord = benutzerEingabe( "([a-hA-H][1-8])|end");
                
                // end beendet den Zug
                // Prüfung ob sich etwas geändert hat -> History
                if( startKoord.equals( "end" ) 
                        && schritt > 1 ) return; 
                
                if( startKoord.equals( "end" ) ) continue;
                
                figur = spielfeld.get( startKoord );
                
                valid = false;
                zielKoords.clear();
                
                boolean festgehalten = false;
                boolean beschuetzt = false;
                
                // gültig wenn auf dem Feld eine Figur steht
                if( figur != null ) {
                    
                    // freies Nachbarfeld ?
                    ArrayList<String> neighbourKoords = Spielfeld.getNeighbourKoords( startKoord );
                    
                    for ( String koord : neighbourKoords ) {

                        // Zielfeld => leeres Nachbarfeld
                        if( spielfeld.get( koord ) == null ){
                            valid = true;
                            zielKoords.add( koord );
                            
                        } else if( spielfeld.get( koord ).getFarbe() != farbe 
                                && figur.getTyp().ordinal() > spielfeld.get( koord ).getTyp().ordinal() ){
                            
                            ArrayList<String> neighbourKoords2 = Spielfeld.getNeighbourKoords( koord );
                            
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
                            if( Spielfeld.isNeighbourKoord( startKoord, lastStartKoord ) 
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
                zielKoord = benutzerEingabe();
                
                //Push
                
                // Nur bis zum 3. Schritt möglich
                // Falls Zielfeld eine Figur des Gegners
                if( schritt < 4 && spielfeld.get( zielKoord ) != null
                    && spielfeld.get( zielKoord ).getFarbe() != farbe 
                    && figur.getTyp().ordinal() > spielfeld.get( zielKoord ).getTyp().ordinal() ) {
                    
                    ArrayList<String> koords = Spielfeld.getNeighbourKoords( zielKoord );
                    
                    for( String koord : koords ) {
                        
                        // Falls Figur geschoben werden kann
                        if( spielfeld.get( koord ) == null ){
                                
                            zielKoords.clear();
                            zielKoords.add( zielKoord );
                            
                            do {
                                
                                System.out.println( "Ziel( Figur des Gegners): " );
                                zielKoordPush = benutzerEingabe();
                                
                            } while( ! Spielfeld.isNeighbourKoord( zielKoord, zielKoordPush ) 
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
            
            fallenFelderCheck();
            
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
     * @param regex 
     * @return Benutzereingabe
     * @throws IllegalArgumentException
     */
    private static String benutzerEingabe( String regex ) throws IllegalArgumentException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input;
        
        do {
            try {
                input = br.readLine();
                
            } catch ( IOException ex ) {
                throw new IllegalArgumentException( "Eingabevorgang abgebrochen!" );
            }
            
        } while( ! input.matches( regex ) ); // Prüft ob der gegebene String dem regex entspricht
        
        return input;
    }
    
    
    
    
    //Gerichtsverfahren
    /**
     * Prüft alle Fallenfelder( c3, c6, f3, f6 )
     * ob eine Figur auf dem jeweiligen Feld steht
     * und ob sie durch eine gleichfarbige Figur gehalten wird
     * falls nicht wird sie entfernt 
     */
    public void fallenFelderCheck(){
        
        //Am Ende des Verfahrens wird entschieden, ob das Opfer für schuldig erklärt wird
        //und aus dem öfffentlichen Lebe in eine Irrenanstalt für geistig behinderte
        //eingeliefert wird.
        
        boolean schuldig = true;
        
        //Jede TodesStelle wird untersucht
        for ( String todesStelle : fallenFelder ){
            
            Spielfigur opfer = spielfeld.get( todesStelle );
            
            if ( opfer != null ){
                
                //Das Opfer ist die erste Person die Aussagt
                Farbe aussage = opfer.getFarbe();
                
                //Zeugen werden ins Gericht geholt
                ArrayList<Spielfigur> zeugen = spielfeld.getNeighbours( todesStelle );
                
                //Zeugen machen ihre Aussagen, es reicht wenn nur einer von ihnen 
                //für das Opfer aussagt und die Aussage des Opfer bestätig
                for( Spielfigur zeuge : zeugen ) {
                    
                    if( zeuge.getFarbe().equals( aussage ) ){
                        
                        schuldig = false;
                        break;
                    }
                }
                
                // Falls schuldig wird das Urteil vollstreckt
                if ( schuldig ){
                    
                    spielfeld.set(todesStelle, null);
                }
            }
        }
    }
    
    /**
     * lässt den spieler die Figuren,
     * außer Kaninchen, auf den
     * ersten beiden Reihen
     * aufstellen
     * 
     * leere Felder werden mit
     * Kaninchen aufgefüllt
     * 
     * @param farbe
     */
    public void figurenSetzen( String farbe ){
        
        // prüft ob eine gültige Farbe
        if( ! farbe.equals( "Gold" ) && ! farbe.equals( "Silber" ) ) {
            
            throw new IllegalArgumentException( "Keine gültige Farbe!" );
        }
        //array mit allen Spielfiguren außer Kaninchen
        Spielfigur[] figuren = { new Spielfigur( farbe, "Elefant" ),
                                 new Spielfigur( farbe, "Kamel" ),
                                 new Spielfigur( farbe, "Pferd" ),
                                 new Spielfigur( farbe, "Pferd" ),
                                 new Spielfigur( farbe, "Hund" ),
                                 new Spielfigur( farbe, "Hund" ),
                                 new Spielfigur( farbe, "Katze" ),
                                 new Spielfigur( farbe, "Katze" )
                                };
        
        String regex;
        
        if( farbe.equals( "Gold" ) ) {
            
            regex = "[a-hA-H][1-2]";
        } else {
            
            regex = "[a-hA-H][7-8]";
        }
        
        for ( Spielfigur figur : figuren ){            
            System.out.print( figur.getTyp() + ": " );
            spielfeld.set( benutzerEingabe( regex ), figur );
            System.out.println();
            System.out.println( spielfeld.toString() );
        }
        kaninchenAufstellen( farbe );
    }
    
    /**
     * füllt die leeren Felder
     * mit Kaninch
     * 
     * @param farbe
     */
    public void kaninchenAufstellen( String farbe ){
        Spielfigur kaninchen = new Spielfigur( farbe, "Kaninchen" );
        String[] sa = { "A", "B", "C", "D", "E", "F", "G", "H" };
        
        int i;
        int end;
        
        if( farbe.equals( "Gold" ) ) {
            
            i = 1;
            end = 2;
        } else {
            
            i = 7;
            end = 8;
        }
        
        for ( ; i <= end; i++ ){
            for ( String s : sa ){
                s += i;
                if ( spielfeld.get(s) == null ){
                    spielfeld.set(s, kaninchen);
                }
            } 
        }
    }
    
    /**
     * gibt die Frabe des Gewinners zurück
     * Im Falle eines noch nicht entschiedenen Spiels wird null zurückgegeben
     * @return Gewinner
     */
    public Farbe gewinner(){
        
        Farbe farbe = Farbe.Gold;
        
        // Prüft ob sich in der letzten Reihe 
        // ein goldenes Kaninchen befindet
        for( int i = 0; i < 8; i++ ) {
            
            Spielfigur figur = spielfeld.get( 7, i);
            
            if( figur != null && figur.getFarbe().equals( farbe ) && figur.getTyp().equals( Typ.Kaninchen ) ) {
                
                return farbe;
            }
        }
        
        farbe = Farbe.Silber;
        
        // Prüft ob sich in der ersten Reihe 
        // ein silbernes Kaninchen befindet
        for( int i = 0; i < 8; i++ ) {
            
            Spielfigur figur = spielfeld.get( 0, i);
            
            if( figur != null && figur.getFarbe().equals( farbe ) && figur.getTyp().equals( Typ.Kaninchen ) ) {
                
                return farbe;
            }
        }
        
        return null;
    }
    
    /**
     * Spielablauf
     * @return Gewinner
     */
    public Farbe game(){
        
        Farbe gewinner;
        
        do {
            zug( activePlayer );
            
            gewinner = gewinner();
            
            changeActivePlayer();
            
        } while( gewinner.equals( null ) );
        
        return gewinner;
    }
    
    /**
     * gibt das Spielfeld aus
     */
    public void print() {
        
        System.out.println( spielfeld.toString() );
    }
    
    /**
     * wechselt den Spieler
     */
    private void changeActivePlayer() {
        
        if( activePlayer.equals( Farbe.Gold) ) {
            
            activePlayer = Farbe.Silber;
        } else {
            
            activePlayer = Farbe.Gold;
        }
    }
}
