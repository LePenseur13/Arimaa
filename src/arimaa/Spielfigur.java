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
        this.farbe = Farbe.valueOf( farbe );
        this.typ = Typ.valueOf( typ );
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
        return farbe.name().substring( 0, 2) + " " + typ.ordinal();
    } 
    
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
    Kaninchen, Katze, Hund, Pferd, Kamel, Elefant;

    
    
    /**
     * Gibt den Unterschied der Stärken, der beiden Typen an
     * 
     * @param t Typ mit dem verglichen wird
     * @return 
     */
    public int compare( Typ t ) {
        return this.ordinal() - t.ordinal();
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
