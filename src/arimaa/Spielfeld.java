/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

import java.util.Arrays;

/**
 *
 * @author Alexander Holzinger
 */
public class Spielfeld {
    //Attribute
    Spielfigur[][] Feld;
    
    //Methoden
    public boolean equals(Spielfeld AnderesFeld) {
        return Arrays.equals(this.Feld, AnderesFeld.Feld);
    }
    
    public void set(String koord, Spielfigur figur) {
        if (koord.charAt( 0 ) > 72 || koord.charAt( 0 ) < 65) throw new IndexOutOfBoundsException("Ungültige Koordinaten!");
        if (koord.charAt( 1 ) > 56 || koord.charAt( 1 ) < 49) throw new IndexOutOfBoundsException("Ungültige Koordinaten!");
        int x = koord.toUpperCase().charAt( 0 ) - 65;
        int y = koord.charAt( 1 ) - 49;
    }
}
