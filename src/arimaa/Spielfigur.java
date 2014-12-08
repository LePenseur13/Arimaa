/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

/**
 * Änderungen Version 1.1: Javadoc Kommentare hinzugefügt
 * 
 * @author Alexander Holzinger
 * @version 1.1
 */
public class Spielfigur {
    // Attribute
    private final Typ typ;
    private final Farbe farbe;
    
    //Konstruktoren
    
    /**
     * 
     * @param farbe
     * @param typ 
     */
    public Spielfigur( String farbe, String typ ) {
        this.farbe = Farbe.valueOf( farbe );
        this.typ = Typ.valueOf( typ );
    }
    
    public Spielfigur( Farbe farbe, Typ typ ) {
        this.farbe = farbe;
        this.typ = typ;
    }
    
    //Methoden
    /**
     * 
     * @param andereFigur
     * @return 
     */
    public boolean isStronger( Spielfigur andereFigur ) {
        return typ.isStronger( andereFigur.typ );
    }
    
    /**
     * 
     * @return farbe
     */
    public Farbe getFarbe() {
        return farbe;
    }
    
    /**
     * 
     * @return typ
     */
    public Typ getTyp() {
        return typ;
    }
    
    /**
     * 
     * @param andereFigur
     * @return 
     */
    public int compareTo( Spielfigur andereFigur ) {
        return typ.compare( andereFigur.typ );
    }
    
    /**
     * 
     * @return 
     */

    public String toString() {
        return farbe.name().substring( 0, 1) + typ.ordinal();
    } 
    
    public Spielfigur getCopy() {
        return new Spielfigur( farbe.toString(), typ.toString() );
    }
}
