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
class Spielfigur {
    // Attribute
    private Typ typ;
    private Farbe farbe;
    
    //Konstruktoren
    /**
     * 
     * @param farbe
     * @param typ 
     */
    public Spielfigur( String farbe, String typ ) {
        this.farbe = this.farbe.getValue( farbe );
        this.typ = this.typ.getValue( typ );
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
     * @return 
     */
    public Farbe getFarbe() {
        return farbe;
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
    public Spielfigur getCopy() {
        return new Spielfigur( farbe.toString(), typ.toString() );
    }
}

/**
 * 
 * @author Alexander Holzinger
 * @version 1.0
 */
enum Farbe {
    Silber, Gold;
    
    //Methoden
    /**
     * 
     * @param str
     * @return 
     */
    public static Farbe getValue( String str ) {
        switch( str ){
            case "Silber":
            case "S":
            case "s":
                return Silber;
            case "Gold":
            case "G":
            case "g":
                return Gold;
            default:
                throw new IllegalArgumentException( "Ungültiger String!" );
        }
    }
    
    /**
     * 
     * @param c
     * @return 
     */
    public static Farbe getValue( Character c ){
        switch( c ){
            case 'S':
            case 's':
                return Silber;
            case 'G':
            case 'g':
                return Gold;
            default:
                throw new IllegalArgumentException( "Ungültiger Character!" );
        }
    }
}

/**
 * 
 * @author Alexander Holzinger
 * @version 1.0
 */
enum Typ {
    Elefant( 6 ), Kamel( 5 ), Pferd( 4 ), Hund( 3 ), Katze( 2 ), Kaninchen( 1 );
    private final Integer Stärke;
    
    /**
     * 
     * @param Stärke 
     */
    Typ( Integer Stärke ) {
        this.Stärke = Stärke;
    }
    
    /**
     * 
     * @param str
     * @return 
     */
    public static Typ getValue( String str ) {
        switch( str ) {
            case "Elefant":
                return Elefant;
            case "Kamel":
                return Kamel;
            case "Pferd":
                return Pferd;
            case "Hund":
                return Hund;
            case "Katze":
                return Katze;
            case "Kaninchen":
                return Kaninchen;
            default:
                throw new IllegalArgumentException( "Ungültiger String!" );
        }
    }
    
    /**
     * Gibt den Unterschied der Stärken, der beiden Typen an
     * 
     * @param t Typ mit dem verglichen wird
     * @return 
     */
    public int compare( Typ t ) {
        return this.Stärke - t.Stärke;
    }
    
    /**
     * 
     * @param t Typ mit dem verglichen wird
     * @return 
     */
    public boolean isStronger( Typ t ) {
        return this.compare( t ) > 0;
    }
}