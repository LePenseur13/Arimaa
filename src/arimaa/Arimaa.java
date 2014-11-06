/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arimaa;

/**
 *
 * @author JosefChristoph
 */
public class Arimaa {
    
    int [][] spielfeld; 

    public void entferneTote(){
        
    }
    
    public int game(){
        int teamGewonnen = 0;
        int spieler = 0;
        while (teamGewonnen == 0){
            zug(spieler);
            
            teamGewonnen = welchesTeamHatGewonnen();
        }
        return teamGewonnen;
    }
    
    private void zug(int spieler){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // test
        int i = 13;
        // this is awesome
    }
    
}