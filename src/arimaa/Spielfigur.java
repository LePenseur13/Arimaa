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
    
    // -------------------------------------------------------------------------
    // -------------------------- Attribute ------------------------------------
    // -------------------------------------------------------------------------
    
    private final Typ typ;
    private final Farbe farbe;
    
    // -------------------------------------------------------------------------
    // ---------------------- Konstruktoren ------------------------------------
    // -------------------------------------------------------------------------
    
    public Spielfigur( Farbe farbe, Typ typ ) {
        this.farbe = farbe;
        this.typ = typ;
    }
    
    // -------------------------------------------------------------------------
    // --------------------------- METHODEN ------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Spielfigur sf = new Spielfigur( Farbe.Gold, Typ.Elefant );
        System.out.println( sf );
        System.out.println( sf.isStronger( new Spielfigur( Farbe.Silber, Typ.Elefant )) );
    }
    
    // -------------------------------------------------------------------------
    // ----------------------------- GETTER ------------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * @return farbe
     */
    public Farbe getFarbe() {
        return farbe;
    }
    
    /**
     * @return typ
     */
    public Typ getTyp() {
        return typ;
    }
    
    
    // -------------------------------------------------------------------------
    // --------------------------- ISSTRONGER ----------------------------------
    // -------------------------------------------------------------------------
    
    /**
     * ob this stärker ist als die Vergleichsfigur
     * @param figur
     * @return stärker?
     */
    public boolean isStronger( Spielfigur figur ) {
        return typ.isStronger( figur.typ );
    }

    // -------------------------------------------------------------------------
    // --------------------------- TOSTRING ------------------------------------
    // -------------------------------------------------------------------------
    
    @Override
    public String toString() {
        return farbe.name().substring( 0, 1) + typ.ordinal();
    } 
}
