/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arimaa;

/**
 *
 * @author Marcus
 */
public class Arimaa {
    Spielfeld spielfeld;
    String [] death = new String []{"C3", "F3", "C6", "F6"};
    
    public Arimaa() {
        
    }
    
    //Gerichtsverfahren
    public void entferneFiguren(){
        //Am Ende des Verfahrens wird entschieden, ob das Opfer für schuldig erklärt wird
        //und aus dem öfffentlichen Lebe in eine Irrenanstalt für geistig behinderte
        //eingeliefert wird.
        boolean figurWirdEntfernt = true;
        //Jede TodesStelle wird untersucht
        for (String todesStelle : death){
            Spielfigur opfer = spielfeld.getFigur(todesStelle);
            if (opfer != null){
                //Das Opfer ist die erste Person die Aussagt
                Farbe opferFarbe = opfer.getFarbe();
                
                //Zeugen werden ins Gericht geholt
                int [] koords = spielfeld.getKoords(todesStelle);
                Spielfigur oben = spielfeld.get(koords[0], koords[1]-1);
                Spielfigur unten = spielfeld.get(koords[0], koords[1]+1);
                Spielfigur rechts = spielfeld.get(koords[0]+1, koords[1]);
                Spielfigur links = spielfeld.get(koords[0]-1, koords[1]);
                
                //Zeugen machen ihre Aussagen, es reicht wenn nur einer von ihnen 
                //für das Opfer aussagt, und die Aussage das Opfer entlastet
                //Zeuge 1
                figurWirdEntfernt = ! (oben != null && opferFarbe.equals(oben.getFarbe()));
                if (figurWirdEntfernt == true){
                    figurWirdEntfernt = ! (oben != null && opferFarbe.equals(oben.getFarbe()));
                }
                //Zeuge 2
                if (figurWirdEntfernt == true){
                    figurWirdEntfernt = ! (unten != null && opferFarbe.equals(unten.getFarbe()));
                }
                //Zeuge 3
                if (figurWirdEntfernt == true){
                    figurWirdEntfernt = ! (rechts != null && opferFarbe.equals(rechts.getFarbe()));
                }
                //Zeuge 4
                if (figurWirdEntfernt == true){
                    figurWirdEntfernt = ! (links != null && opferFarbe.equals(links.getFarbe()));
                }
                if (figurWirdEntfernt){
                    spielfeld.set(todesStelle, null);
                }
            }
        }
    }
    
    public void zug(Farbe f){
        
    }
    
     //-1 niemand
    //0 gold
    //1 silber
    public int hasWon(){
        Farbe f = Farbe.Gold;
        int setWinning = 0;
        int winning = -1;
        for (int j = 0;j <= 7; j += 7){
            for (int i = 0; i < 8; i++){
                Spielfigur sf = spielfeld.get(i, j);
                if (sf.getFarbe().equals(f) && sf.getTyp().equals(Typ.Kaninchen) ){
                    winning = setWinning;
                }
            }
            setWinning = 1;
            f = Farbe.Silber;
        }
        return winning;
    }
    
    public Farbe game(){
        spielfeld = new Spielfeld();
        Farbe f = Farbe.Gold;
        int farbe = 0;
        while (hasWon() == -1){
            zug(f);
            if (farbe == 0) {
                farbe = 1;
                f = Farbe.Silber;
            } else {
                farbe = 0;
                f = Farbe.Gold;
            }
        }
        if (hasWon() == 1){
            f = Farbe.Gold;
        } else {
            f = Farbe.Silber;
        }
        return f;
    }
    
    public static void main(String [] args){
        Arimaa arimaa = new Arimaa();
        Farbe winning = arimaa.game();
        System.out.println("Gewinner ist " + winning.toString());
    }
}