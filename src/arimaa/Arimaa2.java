/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

import gui.spielfeld;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author JosefChristoph
 */
public class Arimaa2 {
    private Spielfeld spielfeld;
    private Spielphase spielphase;
    private Farbe activePlayer;
    private int schritt;

    private Spielfigur cache;

    
    private Integer startX;
    private Integer startY;
    
    private Integer zielX;
    private Integer zielY;
    
    private spielfeld guiReferenz;
    
    // Konstruktor
    
    public Arimaa2( spielfeld spielfeld ) {
        guiReferenz = spielfeld;
        this.spielfeld = new Spielfeld();
        
        spielphase = Spielphase.Aufstellen;
        
        activePlayer = Farbe.Gold;
        schritt = 1;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arimaa2 a = new Arimaa2(null);
        
    }
    
    /**
     * Schnittstelle für die GUI
     * @param x
     * @param y
     * @return ob gültig
     */
    public boolean setKords( int x, int y ) {
        boolean validity;
        if( spielphase.equals( Spielphase.Aufstellen ) ) {
            // ------ Aufstellen -----
            
            validity = checkKoordsAufstellung( x, y );
        
        } else {
            // ------ Game -----
            
            validity = checkKoordsGame( x, y );
            
            // Ausnahmefall Schieben
            // d.h. Ziel gesetzt und Ziel ist eine Figur
            if( zielX != null && spielfeld.get( zielX, zielY ) != null ) {
                
                // Figur speichern
                cache = spielfeld.get( zielX, zielY );
                
                // Figur vom spielfeld nehmen
                spielfeld.set( zielX, zielY, null );
                
                // Schritt ausführen
                spielfeld.flip( startX, startY, zielX, zielY );
                
                // Anzeigen
                guiReferenz.generiereFeld( spielfeld );
                
                deleteKoords();
            }
        }
        
        // Ziel - Koord gegeben -> Schritt ausführen
        if( zielX != null ) {
            
            // Im Falle des 
            spielfeld.flip( startX, startY, zielX, zielY );
            
            deleteKoords();
            
            // Anzeigen
            guiReferenz.generiereFeld( spielfeld );
            
        }
        
        return validity;
    }   
    
    // -------------------------------------------------------------------------
    // ------------------ Aufstellung ------------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * Prüft alle Koordinaten während der 1.Phase (Aufstellung)
     * @return ob gültig
     */
    private boolean checkKoordsAufstellung( int x, int y ) {
        
        boolean validity = true;
        
        // Falls es eine Startkoordinate ist
        if( startX == null ) {
            
            // Dort muss sich eine Figur befinden
            if( spielfeld.get( x, y ) == null ) {
                validity = false;
            }
        }
        
        // Je nachdem wer dran ist darf man nur die ersten 3 Reihen benutzen
        
        if( activePlayer.equals( Farbe.Gold ) ) {
            
            // Darf nur die ersten 3 Reihen benutzen
            if( y > 3 ) {
                validity = false;
            }
        } else {
            
            // Darf nur die letzten 3 Reihen benutzen
            if( y < 6 ) {
                validity = false;
            }
        }
        
        return validity;
    }
    
    /**
     * Wenn ein Spieler mit dem Aufstellen fertig ist
     * Spieler wechseln 
     * restliche Kaninchen aufstellen
     * eventuell die 2.Phase einleiten
     * anzeigen in der GUI aufrufen
     * 
     * @return valid
     */
    public boolean aufstellenFertig() {
        
        boolean valid = checkAufstellenFertig();
        
        if( valid ) {
            
            // restliche Kaninchen aufstellen
            kaninchenAufstellen( activePlayer );
        
            // Anzeigen
            guiReferenz.generiereFeld( spielfeld );
        
            // Falls auch Silber fertig ist beginnt Phase 2 Spielen
            if( activePlayer.equals( Farbe.Silber ) ) {
            
                spielphase = Spielphase.Spielen;
            }
        
            // In jedem Fall ist nur der andere Spieler an der Reihe
            changeActivePlayer();
        
        }
        
        return valid;
        
    }
    
    /**
     * Prüft ob fertig aufgestellt wurde
     * d.h auf den ersten bzw letzten 2 Reihen
     * d.h auf der 3 bzw 6 Reihe keine Figuren
     * @return validity
     */
    private boolean checkAufstellenFertig() {
        
        boolean validity = true;
        
        int y;
        if( activePlayer.equals( Farbe.Gold ) ) {
            
            y = 2;
        } else {
            
            y = 5;
        }
        
        for ( int x = 0; x < 8; x++  ){
            
            // Falls dort eine Figur sein sollte 
            if( spielfeld.get( x, y ) != null ) {
                
                validity = false;
                break;
            }
        }
        
        return validity;
    }
    
    /**
     * füllt die leeren Felder
     * mit Kaninch
     * 
     * @param farbe
     */
    private void kaninchenAufstellen( Farbe farbe ){
        Spielfigur kaninchen = new Spielfigur( farbe, Typ.Kaninchen );
        int[] range;
        
        if( farbe.equals( Farbe.Gold ) ) {
            range = new int[]{0, 1};
        } else {
            range = new int[]{6, 7};
        }
        
        for ( int x = 0; x < 8; x++ ){
            for ( int y : range ){
                
                // Falls leer mit Kaninchen befüllen
                if ( spielfeld.get( x, y ) == null ){
                    
                    spielfeld.set( x, y, kaninchen);
                }
            }
        }
    }
    
    // -------------------------------------------------------------------------
    // ------------------------- Game ------------------------------------------
    // -------------------------------------------------------------------------
    
    private boolean checkKoordsGame( int x, int y ) {
        boolean validity = true;
        
        return validity; 
    
    }

    public Spielfigur getCache() {
        return cache;
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
            
            Spielfigur figur = spielfeld.get( i, 7);
            
            if( figur != null && figur.getFarbe().equals( farbe ) && figur.getTyp().equals( Typ.Kaninchen ) ) {
                
                return farbe;
            }
        }
        
        farbe = Farbe.Silber;
        
        // Prüft ob sich in der ersten Reihe 
        // ein silbernes Kaninchen befindet
        for( int i = 0; i < 8; i++ ) {
            
            Spielfigur figur = spielfeld.get( i, 0);
            
            if( figur != null && figur.getFarbe().equals( farbe ) && figur.getTyp().equals( Typ.Kaninchen ) ) {
                
                return farbe;
            }
        }
        
        return null;
    }
    
    /**
     * setzt alle gespeicherten Koordinaten zurück
     */
    private void deleteKoords() {
        startX = null;
        startY = null;
        zielX = null;
        zielY = null;
        
    }
    
    /**
     * gibt das Spielfeld aus
     */
    public void print() {
        
        System.out.println( spielfeld.toString() );
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
}
