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
    private ArrayList< Spielfeld > history = new ArrayList();
    private Spielfeld spielfeld;
    private Spielfeld letzterPruefungsEintrag;
    private int figurenAnzahl;
    private int i;
    
    private Koord letzteStartKoord;
    private Spielfigur zuletztgezogeneFigur;
    
    //Konstruktoren
    public History(Spielfeld spielfeld){
        this.spielfeld = spielfeld;
        this.figurenAnzahl = spielfeld.getFigurenAnzahl();
    }
    
    public void addEntry(){
        history.add( spielfeld.copy() );
        
        if ( spielfeld.getFigurenAnzahl() < this.figurenAnzahl ){
            i = history.size() - 1;
            this.figurenAnzahl = spielfeld.getFigurenAnzahl();
            letzterPruefungsEintrag = history.get( history.size() - 1 );
        }
    }
    
    /*
        Methode geht liste vom neuesten eintrag bis zum anfang der liste durch,
        bricht aber auch beim erreichen des eintrags mit der letzten ver-
        änderung (der spielfiguren-anzahl) ab.
    */
    public boolean isValid(Spielfeld spielfeld){
        for (int i = history.size()-1; i >= 0; i-- ){
            Spielfeld sf = history.get(i);
            //wenn formation schon vorkommt
            if (sf.equals(spielfeld)){
                return false;
            }
            //Wenn EIntrag mit letzter Spielfiguren-Anzahl-Änderung erreicht
            if (sf.equals(letzterPruefungsEintrag)){
                i = -1;
            }
        }
        return true;
    }

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
    
    
    
    @Override
    public String toString() {
        return "History{" + "history=" + history + ", spielfeld=" + spielfeld + ", letzterPruefungsEintrag=" + letzterPruefungsEintrag + ", living=" + figurenAnzahl + '}';
    }
    
    
}
