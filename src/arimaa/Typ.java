/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arimaa;

/**
 *
 * @author JosefChristoph, Alexander Holzinger
 * @version 1.0
 */
public enum Typ {
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
