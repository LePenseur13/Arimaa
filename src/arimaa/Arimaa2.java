/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

//import gui.spielfeld;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author JosefChristoph
 */
public class Arimaa2 {
    
    // -------------------------------------------------------------------------
    // -------------------------- Attribute ------------------------------------
    // -------------------------------------------------------------------------
    
    public Spielfeld spielfeld;
    
    private Spielphase spielphase;
    private Farbe activePlayer;
    private int schritt;

    private Spielfigur cache;
    private History history;

    
    private Koord start;
    private Koord ziel;
    
    ArrayList<Koord> zielKoords;
    
    //private final spielfeld guiReferenz;
    
    // -------------------------------------------------------------------------
    // ---------------------- Konstruktoren ------------------------------------
    // -------------------------------------------------------------------------
    
    public Arimaa2( /*spielfeld spielfeld*/ ) {
        //guiReferenz = spielfeld;
        this.spielfeld = new Spielfeld();
        
        spielphase = Spielphase.Aufstellen;
        
        activePlayer = Farbe.Gold;
        schritt = 1;
        
        history = new History( spielfeld );
        
        figurenAufsBrett();
        
        // Anzeigen
        //guiReferenz.generiereFeld( spielfeld );
        print();
    }
    
    // -------------------------------------------------------------------------
    // --------------------------- METHODEN ------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arimaa2 a = new Arimaa2();
        a.print();
        
        Farbe gewinner;
        
        do {
            System.out.println( a.activePlayer );
            
            try{
                a.setKords( new Koord( Arimaa2.benutzerEingabe() ) );
            } catch( IllegalArgumentException e ){
                // Do nothing
            }
            
            gewinner = a.gewinner();
            
            if( a.spielphase.equals( Spielphase.Aufstellen) ) {
                a.aufstellenFertig();
            }
            
        } while( gewinner == null );
    }
    
    /**
     * Schnittstelle für die GUI
     * @param koord
     * @return ob gültig
     */
    public boolean setKords( Koord koord ) {
        
        boolean valid = false;
        
        switch( spielphase) {
            
            case Aufstellen: {
                valid = checkKoordsAufstellung( koord );
                
                if( valid ) {
                    
                    setKoord( koord );
                }
                
                break;
            }
            
            case Spielen: {
                valid = checkKoordsGame( koord );
                
                if( valid ) {
                    
                    setKoord( koord );
                }
                
                break;
            }
        }
        
        // Ziel - Koord gegeben -> Schritt ausführen
        if( ziel != null ) {
            
            makeMove();
            
            resetKoords();
            
            // Anzeigen
            //guiReferenz.generiereFeld( spielfeld );
            
            print();
            
        }
        
        return valid;
    }   
    
    // -------------------------------------------------------------------------
    // ------------------ Aufstellung ------------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * Prüft alle Koordinaten während der 1.Phase (Aufstellung)
     * @return ob gültig
     */
    private boolean checkKoordsAufstellung( Koord koord ) {
        
        boolean validity = true;
        
        // Falls es eine Startkoordinate ist
        if( start == null ) {
            
            // Dort muss sich eine Figur befinden
            if( spielfeld.get( koord ) == null ) {
                validity = false;
            }
        }
        
        // Je nachdem wer dran ist darf man nur die ersten 3 Reihen benutzen
        
        if( activePlayer.equals( Farbe.Gold ) ) {
            
            // Darf nur die ersten 3 Reihen benutzen
            if( koord.y > 2 ) {
                validity = false;
            }
        } else {
            
            // Darf nur die letzten 3 Reihen benutzen
            if( koord.y < 5 ) {
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
    private boolean aufstellenFertig() {
        
        boolean valid = checkAufstellenFertig();
        
        if( valid ) {
            
            // restliche Kaninchen aufstellen
            kaninchenAufstellen( activePlayer );
        
            
            // In jedem Fall ist nun der andere Spieler an der Reihe
            changeActivePlayer();
            
            // Falls nun Gold wieder an der Reihe ist beginnt Phase 2 Spielen
            if( activePlayer.equals( Farbe.Gold ) ) {
            
                spielphase = Spielphase.Spielen;
                
            } else {
                
                // Silberne Figuren aufs Spielbrett geben
                figurenAufsBrett();
                
            }
        
            // Anzeigen
            //guiReferenz.generiereFeld( spielfeld );
            print();
        
        }
        
        return valid;
        
    }
    
    /**
     * Prüft ob fertig aufgestellt wurde
     * d.h auf den ersten bzw letzten 2 Reihen
     * d.h auf der 3 bzw 6 Reihe keine Figuren
     * @return valid
     */
    private boolean checkAufstellenFertig() {
        
        boolean validity = true;
        
        int y;
        if( activePlayer.equals( Farbe.Gold ) ) {
            
            y = 2; // 3. Reihe
        } else {
            
            y = 5; // 6. Reihe
        }
        
        for ( int x = 0; x < 8; x++  ){
            
            // Falls dort eine Figur sein sollte 
            if( spielfeld.get( new Koord( x, y ) ) != null ) {
                
                validity = false;
                break;
            }
        }
        
        return validity;
    }
    
    /**
     * Je nachdem welcher Spieler mit dem Aufstellen an der Reihe ist
     * werden seine Figuren aufs Spielfeld gelegt
     */
    private void figurenAufsBrett() {
        
        // Stack mit sämtlichen Figuren exclusive Kaninchen
        Stack<Spielfigur> figuren = new Stack<>();
        figuren.push( new Spielfigur( activePlayer, Typ.Elefant ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Kamel ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Pferd ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Pferd ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Hund ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Hund ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Katze ) );
        figuren.push( new Spielfigur( activePlayer, Typ.Katze ) );

        int y;
        
        if( activePlayer.equals( Farbe.Gold ) ) {
            
            y = 2; // 3. Reihe
            
        } else {
            
            y = 5; // 6. Reihe
        }
        
        for( int x = 0; x < 8; x++ ) {
            
            spielfeld.set( new Koord( x, y ), figuren.pop() );
        }
        
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
                Koord koord = new Koord( x, y );
                // Falls leer mit Kaninchen befüllen
                if ( spielfeld.get( koord ) == null ){
                    
                    spielfeld.set( koord, kaninchen);
                }
            }
        }
    }
    
    // -------------------------------------------------------------------------
    // ------------------------- Game ------------------------------------------
    // -------------------------------------------------------------------------
    
    private boolean checkKoordsGame( Koord koord ) {
        boolean validity = true;
        
        return validity; 
    
    }
    
    private boolean schrittFertig() {
        
        return true;
    }
    
    private boolean checkSchrittFertig() {
        
        return true;
    }
    
    /**
     * ermittelt die jeweiligen Zielkoordinaten für die 
     * Startkoordinate koord
     * @param koord
     * @return zielKoords
     */
    private ArrayList<Koord> getZielKoords( Koord koord ) {
        ArrayList<Koord> zielKoords = new ArrayList<>();
        
        Spielfigur figur = spielfeld.get( koord );
        
        // Es muss eine Figur zum Ziehen da sein
        if( figur == null ) return zielKoords;
        
        // Je nachdem ob es eine eigene oder eine fremde Figur ist 
        // eigene Figur
        if( figur.getFarbe().equals( activePlayer ) ) {
            
            // Darf die Figur sich bewegen oder ist sie festgehalten
            if( isMoveable( koord ) ) {
               
                
                
            }

        } else {
            
            // fremde Figur
            // d.h. Pull
            if( isPullable( koord ) ) {
                
                // der Ausgangspunkt der letzten Figur 
                // muss das Ziel von dieser sein
                zielKoords.add( history.getLetzteStartKoord() );
                
            }
            
        }
        
        
        return zielKoords;
    }
    
    /**
     * Prüft ob die gegebene Koordinate eine Figur 
     * enthält, die man hinter der letztgezogenen Figur 
     * nachziehen kann
     * @param koord
     * @return 
     */
    private boolean isPullable( Koord koord ) {
        
        // falls koord ein Nachbarfeld von der letzten startKoord 
        boolean neighbour = Spielfeld.isNeighbourKoord( koord, history.getLetzteStartKoord() );
        
        // AND
        // falls die Figur schwächer als die zuletztgezogene Figur ist
        boolean weaker = history.getZuletztgezogeneFigur().isStronger( spielfeld.get( koord ) );
        
        return neighbour && weaker;
        
    }
    
    /**
     * Ermitelt ob eine Figur überhaupt fahren darf
     * d.h. ob sie festgehlten wird 
     * @return isMoveable
     */
    private boolean isMoveable( Koord koord ) {
        
        ArrayList<Koord> neighbourKoords = Spielfeld.getNeighbourKoords( koord );
        
        Spielfigur figur = spielfeld.get( koord );
        
        boolean festgehalten = false;
        boolean beschuetzt = false;
        
        for( Koord neighbourKoord : neighbourKoords ) {
            
            // Festgehalten ?
            if( spielfeld.get( neighbourKoord ).getFarbe() != activePlayer 
                && spielfeld.get( neighbourKoord ).isStronger( figur ) ) {
                            
                festgehalten = true; 
                            
            } else if( spielfeld.get( neighbourKoord ).getFarbe() == figur.getFarbe() ){
                
                // hat eine Figur der eigenen Farbe als Nachbar
                beschuetzt = true;
                
            }   
            
        }
        
        // Nur wenn festgehalten und nicht beschützt dann false
        
        return ! ( festgehalten && ! beschuetzt );
    }
    
    public Spielfigur getCache() {
        return cache;
    }
    
    /**
     * Ruft je nachdem in welcher Phase man ist 
     * die zugehörige Methode auf
     * @return ob wircklich fertig
     */
    public boolean fertig() {
        
        // Aufstellen
        
        if( spielphase.equals( Spielphase.Aufstellen ) ) {
            
            return aufstellenFertig();
        }
        
        // Schritt
        return schrittFertig();

    }
    
    /**
     * führt einen Schritt aus
     * Ausnahme Schieben wird berücksichtigt
     * 2.Phase -> zählt Schritte mit und wenn nötig wechselt Spieler
     */
    private void makeMove() {
        
        // Ausnahme: cache gesetzt -> Schieben
        if( cache != null ){
            
            // setzt Figur auf Zielfeld
            spielfeld.set( ziel, cache );
            cache = null;
         

        } else {
            // andernfalls normaler Verlauf
            
            // in der 2. Phase zählt Schritte mit
            if( spielphase.equals( Spielphase.Spielen ) ) {
            
                // Im Falle des Schiebens 
                // d.h. wenn auf ziel eine Figur steht
                if( spielfeld.get( ziel ) != null ) {
                
                    // Figur wird vom Spielfeld genommen
                    cache = spielfeld.del( ziel );
                    
                }
                
                // eintrag in History 
                history.setLetzteStartKoord( start );
                history.setZuletztgezogeneFigur( spielfeld.get( start ) );
                
                
                // zählt dern Schritt hoch
                nextSchritt();
                
            }
        
        // Schlussendlich der Tatsächliche Schritt

        spielfeld.flip( start, ziel );
        
        } 
    }
    
    private void nextSchritt() {
        
        // Falls dies schon der 4. Schritt war
        // Wird SChritt zurückgesetzt und der Spieler gewechselt
        if( schritt == 4 ) {
            
            schritt = 1;
            changeActivePlayer();
        
        } else {
            
            // 
            schritt++;
        }
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
        for( int x = 0; x < 8; x++ ) {
            
            Spielfigur figur = spielfeld.get( new Koord( x, 7 ) );
            
            if( figur != null && figur.getFarbe().equals( farbe ) && figur.getTyp().equals( Typ.Kaninchen ) ) {
                
                return farbe;
            }
        }
        
        farbe = Farbe.Silber;
        
        // Prüft ob sich in der ersten Reihe 
        // ein silbernes Kaninchen befindet
        for( int x = 0; x < 8; x++ ) {
            
            Spielfigur figur = spielfeld.get( new Koord( x, 0 ) );
            
            if( figur != null && figur.getFarbe().equals( farbe ) && figur.getTyp().equals( Typ.Kaninchen ) ) {
                
                return farbe;
            }
        }
        
        return null;
    }
    
    /**
     * setzt alle gespeicherten Koordinaten zurück
     */
    private void resetKoords() {
        start = null;
        ziel = null;
        
    }
    
    /**
     * speichert die Koordinate in die richtige Variable
     */
    private void setKoord( Koord koord ) {
        
        // Wenn start schon gesetzt wurde 
        // ODER
        // Falls chache eine Figur enthält wird Ziel gesetzt
        if( start != null || cache != null ) {
            
            ziel = koord;
            
        } else {
            
            // in allen anderen Fällen wird logischerweise start gesetzt
            start = koord;
        }
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
        
        try {
            input = br.readLine();
                
        } catch ( IOException ex ) {
             throw new IllegalArgumentException( "Eingabevorgang abgebrochen!" );
        }
        
        return input;
    }
}
