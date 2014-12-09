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
    ArrayList< Spielfeld > histoy = new ArrayList();
    Spielfeld spielfeld;
    Spielfeld letzterPruefungsEintrag;
    int living;
    
    //Konstruktoren
    public History(Spielfeld spielfeld){
        this.spielfeld = spielfeld;
        this.living = spielfeld.getFigurenAnzahl();
    }
    
    public void addEntry(){
        histoy.add(spielfeld.copy());
        if (spielfeld.getFigurenAnzahl() < this.living){
            this.living = spielfeld.getFigurenAnzahl();
            letzterPruefungsEintrag = histoy.get(histoy.size()-1);
        }
    }
    
    /*
        Methode geht liste vom neuesten eintrag bis zum anfang der liste durch,
        bricht aber auch beim erreichen des eintrags mit der letzten ver-
        änderung (der spielfiguren-anzahl) ab.
    */
    public boolean isValid(Spielfeld spielfeld){
        for (int i = histoy.size()-1; i >= 0; i-- ){
            Spielfeld sf = histoy.get(i);
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
    
    
}
