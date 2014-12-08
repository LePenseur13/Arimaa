/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

import gui.spielfeld;

/**
 *
 * @author JosefChristoph
 */
public class Arimaa2 {
    private Spielfeld spielfeld;
    private int schritt;
    private Farbe activePlayer;
    private Spielfigur cache;
    private Spielphase spielphase;
    
    private Integer startX;
    private Integer startY;
    
    private Integer zielX;
    private Integer zielY;
    
    private spielfeld guiReferenz;
    
    // Konstruktor
    
    public Arimaa2( spielfeld spielfeld ) {
        guiReferenz = spielfeld;
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
     */
    public void aufstellenFertig() {
        
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
    
    /**
     * füllt die leeren Felder
     * mit Kaninch
     * 
     * @param farbe
     */
    public void kaninchenAufstellen( Farbe farbe ){
        Spielfigur kaninchen = new Spielfigur( farbe, Typ.Kaninchen );
        int[] range;
        
        if( farbe.equals( Farbe.Gold ) ) {
            range = new int[]{0, 1};
        } else {
            range = new int[]{7, 8};
        }
        
        for ( int x : range ){
            for ( int y = 0; y < 8; y++ ){
                
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
     * setzt alle gespeicherten Koordinaten zurück
     */
    private void deleteKoords() {
        startX = null;
        startY = null;
        zielX = null;
        zielY = null;
        
    }
}
