/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

/**
 *
 * @author Alexander Holzinger
 */
class Spielfigur {
    // Attribute
    private Enum Typ;
    private Farbe Farbe;
    
    //Konstruktoren
    public Spielfigur( String Farbe, String Typ ) {
        
    }
    
    public Farbe getFarbe(){
        return this.Farbe;
    }
    
    //Methoden
}

enum Farbe {
    Silber, Gold;
    
    //Methoden
    public Farbe getValue( String str ) {
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
    
    public Farbe getValue( Character c ){
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

enum Typ {
    Elefant( 6 ), Kamel( 5 ), Pferd( 4 ), Hund( 3 ), Katze( 2 ), Kaninchen( 1 );
    private final Integer Stärke;
    
    //Konstruktor um Stärke festzulegen
    Typ( Integer Stärke ) {
        this.Stärke = Stärke;
    }
    
    public Typ getValue( String str ) {
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
    
    public int compare( Typ t ) {
        return this.Stärke - t.Stärke;
    }
    
    public boolean isStronger( Typ t ) {
        return this.compare( t ) > 0;
    }
}