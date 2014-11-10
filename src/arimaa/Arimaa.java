/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arimaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author JosefChristoph, Marcus Gabler

 */
public class Arimaa {

    // Attribute
    private Spielfeld spielfeld;
    
    //Konstruktoren
    public Arimaa() {
        spielfeld = new Spielfeld();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        Arimaa a = new Arimaa();
        System.out.println( a.spielfeld.toString() );
        a.spielfeld.set( benutzerEingabe(), new Spielfigur( "Gold", "Katze" ) );
        System.out.println( a.spielfeld.toString() );
        */
        Farbe f1 = Farbe.valueOf( "Gold" );
        Farbe f2 = Farbe.getValue( "Gold" );
        System.out.println( f1 == f2 );
    }

    // Methoden
    
    /**
     *
     * @param farbe
     */
    public void zug( Farbe farbe ) {
        String startKoord;
        String zielKoord;
        
        for ( int i = 1; i <= 4; i++ ) {
            
            do {
                startKoord = benutzerEingabe();
                // gültig wenn auf dem Feld eine Figur steht
            } while( spielfeld.get( startKoord ).equals( null ) );
            
            do {
                zielKoord = benutzerEingabe();
                // gültig wenn auf dem Feld eine Figur steht
            } while( spielfeld.get( zielKoord ).equals( null ) );
            
            
        }
    }
    
    /**
     * liest solange von der Konsole ein, 
     * bis eine passende Benutzereingabe erfolgt
     * ==> Koordinate ( [a-hA-H][1-8] zB.: a1 )
     *
     * @return Benutzereingabe( Koordinate )
     * @throws IllegalArgumentException
     */
    private static String benutzerEingabe() {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input;
        
        do {
            try {
                input = br.readLine();
                
            } catch ( IOException ex ) {
                throw new IllegalArgumentException( "Eingabevorgang abgebrochen!" );
            }
            
        } while( ! input.matches( "[a-hA-H][1-8]" ) ); // Prüft ob der gegebene String passt
        
        return input;
    }

}
