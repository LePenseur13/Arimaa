/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arimaa;

import java.util.ArrayList;

/**
 *
 * @author Marcus Gabler
 */
public class History {
    
    // -------------------------------------------------------------------------
    // -------------------------- Attribute ------------------------------------
    // -------------------------------------------------------------------------
    
    private ArrayList<Spielfeld> history = new ArrayList();
    private ArrayList<Spielfeld> verboteneStellungen = new ArrayList();
    private Spielfeld letzterEintrag;
    
    private int figurenAnzahl;
    private int grenze;      // index bis zur letzten Figurenanzahländerung
    
    private Koord letzteStartKoord;             // benötigt für pull
    private Spielfigur zuletztgezogeneFigur;    // benötigt für pull
    
    // -------------------------------------------------------------------------
    // ---------------------- Konstruktoren ------------------------------------
    // -------------------------------------------------------------------------
    
    public History(){
        history = new ArrayList();
        verboteneStellungen = new ArrayList();
        grenze = 0;
    }
    
    
    // -------------------------------------------------------------------------
    // --------------------------- METHODEN ------------------------------------
    // -------------------------------------------------------------------------
    
    public static void main( String[] args ) {
        
        History h = new History();
        Spielfeld sp = new Spielfeld();
        
        h.addEntry( sp );
        System.out.println( "1\n" + h.getVerboteneStellungen() );
        h.addEntry( sp );
        System.out.println( "2\n" + h.getVerboteneStellungen() );
        
        sp.set( new Koord( "a1" ), new Spielfigur( Farbe.Silber, Typ.Katze ) );
        
        h.addEntry( sp );
        System.out.println( "3\n" + h.getVerboteneStellungen() );
        
        //sp.set( new Koord( "a1" ), new Spielfigur(Farbe.Silber, Typ.Katze) );
        
        h.addEntry( sp );
        System.out.println( "4\n" + h.getVerboteneStellungen() );
        
        sp.flip( new Koord( "a1" ), new Koord( "a2" ) );
        
        
        h.addEntry( sp );
        System.out.println( "5\n" + h.getVerboteneStellungen() );
        
        sp.set( new Koord( "b4" ), new Spielfigur( Farbe.Silber, Typ.Katze ) );
        
        h.addEntry( sp );
        System.out.println( "6\n" + h.getVerboteneStellungen() );
        
        sp.flip( new Koord( "a2" ), new Koord( "a3" ) );
        
        h.addEntry( sp );
        System.out.println( "7\n" + h.getVerboteneStellungen() );
        
        sp.del( new Koord( "a3" ) );
        
        h.addEntry( sp );
        System.out.println( "8\n" + h.getVerboteneStellungen() );
        
    }
    
    // -------------------------------------------------------------------------
    // --------------------------- ADDENTRY ------------------------------------
    // -------------------------------------------------------------------------
    
    
    /**
     * Fügt eien Eintrag zur History hinzu
     * @param spielfeld 
     */
    public void addEntry( Spielfeld spielfeld ){
        
        if ( spielfeld.getFigurenAnzahl() < this.figurenAnzahl ){
            
            grenze = history.size() - 1;
            this.figurenAnzahl = spielfeld.getFigurenAnzahl();
        }
        
        letzterEintrag = spielfeld.copy();
        history.add( letzterEintrag );
        figurenAnzahl = letzterEintrag.getFigurenAnzahl();
        
        
        
        calculateVerboteneStellungen();
    }
    
    
    // -------------------------------------------------------------------------
    // ------------------------ VERBOTENE STELLUNGEN ---------------------------
    // -------------------------------------------------------------------------
    
    
    /**
     * Ermittelt alle verbotenen Stellungen 
     * Regel: eine Stellung darf nie 3 Mal auftreten
     */
    private void calculateVerboteneStellungen() {
        
        verboteneStellungen.clear();
        
        // letzte Stellung ist immer verboten
        verboteneStellungen.add( letzterEintrag );
        
        // Es ist nür nötig bis zu grenze 
        // da ab dort eine andere Anzahl an 
        // Figuren auf dem Feld ist
        for( int i = history.size() - 1; i > grenze; i-- ) {
            for( int j = i - 1; j >= grenze; j-- ) {
                
                // Falls zwei Spielfelder ident sind 
                // werden sie zu den verbotenen Stellungen hinzugefügt
                if( history.get( i ).equals( history.get( j ) ) 
                        && ! verboteneStellungen.contains( history.get( i ) ) ) {

                    verboteneStellungen.add( history.get( i ) );
                }
            }
        }

    }
    
    // -------------------------------------------------------------------------
    // ------------------------ GETTER, SETTER ---------------------------------
    // -------------------------------------------------------------------------
    
    
    public Koord getLetzteStartKoord() {
        return letzteStartKoord;
    }

    public void setLetzteStartKoord(Koord letzteStartKoord) {
        this.letzteStartKoord = letzteStartKoord;
    }

    public Spielfigur getZuletztgezogeneFigur() {
        return zuletztgezogeneFigur;
    }

    public void setZuletztgezogeneFigur(Spielfigur zuletztgezogeneFigur) {
        this.zuletztgezogeneFigur = zuletztgezogeneFigur;
    }

    public Spielfeld getLetzterEintrag() {
        return letzterEintrag;
    }

    public ArrayList<Spielfeld> getVerboteneStellungen() {
        return verboteneStellungen;
    }
    
    
    
    // -------------------------------------------------------------------------
    // --------------------------- TOSTRING ------------------------------------
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "History{" + "history=" + history + ", verboteneStellungen=" + verboteneStellungen + ", letzterEintrag=" + letzterEintrag + ", figurenAnzahl=" + figurenAnzahl + ", i=" + grenze + ", letzteStartKoord=" + letzteStartKoord + ", zuletztgezogeneFigur=" + zuletztgezogeneFigur + '}';
    }
    
}
