/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import arimaa.Arimaa;
import arimaa.Farbe;
import arimaa.Spielfeld;
import arimaa.Spielfigur;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcus
 */
public class spielfeld extends javax.swing.JPanel {

    int SIZE = 9;
    FeldPanel[][] chessboard;
    
    //Spielfeldvariablen
    Color koords = Color.WHITE;
    Color gold = new Color(212, 154, 78);
    Color silber = Color.white;
    String[] typen = {"Elefant", "Kamel", "Pferd", "Hund", "Katze", "Kaninchen"};
    String path = "C:\\Users\\JosefChristoph\\Documents\\GitHub\\Arimaa\\src\\icons\\";
    
    //Eingabevariablen
    //Alle Phasen
    FeldPanel panelPressed;
    
    //Phase 1
    boolean setzeFiguren = true;
    String[] typenSetzen = {"Elefant", "Kamel", "Pferd", "Pferd", "Hund", "Hund", "Katze", "Katze"};
    Farbe farbeTypSetzen;
    int currentTyp = 0;
    
    //Phase 2
    boolean verschiebeFiguren = false;
    
    
    
    
    Arimaa arimaa;
    Spielfeld spielfeld;

    /**
     * Creates new form spielfeld
     */
    public spielfeld() {
        initComponents();
        setBackground(Color.white);
        //this.spielfeld = new Spielfeld();
        arimaa = new Arimaa();
        //arimaa.kaninchenAufstellen("Silber");
        //arimaa.kaninchenAufstellen("Gold");
        arimaa.print();
        spielfeld = arimaa.getSpielFeld();
        spielfeld.set("A1", new Spielfigur("Gold", "Elefant"));
        spielfeld.set("A2", new Spielfigur("Gold", "Katze"));
        spielfeld.set("B1", new Spielfigur("Gold", "Kamel"));
        spielfeld.set("B2", new Spielfigur("Gold", "Hund"));
        //repaint();
        startGame();
    }

    public void generiereFeld(Graphics g) {
        setLayout(new GridLayout(SIZE, SIZE));
        char c = 'a';
        chessboard = new FeldPanel[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                chessboard[i][j] = new FeldPanel();
                FeldPanel current = chessboard[i][j];
                current.setBorder(BorderFactory.createLineBorder(Color.black));
                current.setLayout(new FlowLayout(1));
                setKoords(current, j-1, i-1);
                if (i == 0) {
                    if (j != 0) {
                        current.add(new JLabel(Character.toString(c)));
                        c++;
                    }
                    current.setBackground(Color.BLACK);
                } else if (j == 0) {
                    current.add(new JLabel(Integer.toString(i)));
                    current.setBackground(koords);
                } else {
                    if (((i + j) % 2) == 0) {
                        current.setBackground(gold);
                        current.background = gold;
                    } else {
                        current.setBackground(silber);
                        current.background = silber;
                    }
                }
                current.setSize(50, 50);
                if (i > 0 && j > 0 && i <= 8 && j <= 8) {
                    arimaa.print();
                    if (spielfeld.get(j-1, i-1) != null) {
                        //spielfeld.get(j-1, i-1) != null
                        printIcon(g, current, j-1, i-1);
                    } else {
                        current.add(new JLabel(current.x + ", " + current.y));
                    }
                }
                add(current);
                //printKoords(current);
            }  
        }
        //updateUI();
    }

    public void printIcon(Graphics g, FeldPanel current, int i, int j) {
        Spielfigur sf = spielfeld.get(i, j);
        String[] typen = {"Elefant", "Kamel", "Pferd", "Hund", "Katze", "Kaninchen"};
        for (String typ : typen) {
            if (sf.compareTo(new Spielfigur("Gold", typ)) == 0) {
                setzeFigur(current, typ, sf.getFarbe().toString(), i, j);
                break;
            }
        } 
    }
    
    public void setKoords(FeldPanel current, int x, int y){
        current.x = x;
        current.y = y;
    }
    
    public void printKoords(FeldPanel current){
        current.add(new JLabel("x: " + current.x + ", y:" + current.y));
    }

    public void setzeFigur(FeldPanel current, String typ, String farbe, int x, int y) {
        ImageIcon icon = new ImageIcon(path + typ + farbe + ".png");
        JLabel jl = new JLabel(icon);
        current.empty = false;
        current.x = x;
        current.y = y;
        current.add(jl);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //generiereFeld(g);
    }
    
    public void startGame(){
        farbeTypSetzen = Farbe.Gold;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 437, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        int x = evt.getX();
        int y = evt.getY();
        panelPressed = (FeldPanel) getComponentAt(x, y);
        bestimmePhase();
    }//GEN-LAST:event_formMouseClicked

    public void bestimmePhase(){
        if (setzeFiguren){
            phaseFigurenSetzen();
        } else if (verschiebeFiguren){
            phaseFigurenVerschieben();
        }
    }
    
    public void phaseFigurenVerschieben(){
        
    }
    
    public void phaseFigurenSetzen(){
        if (currentTyp > 7){
                if (farbeTypSetzen == Farbe.Silber){
                    setzeFiguren = false;
                    verschiebeFiguren = true;
                } else {
                    currentTyp = 0;
                    farbeTypSetzen = Farbe.Silber;
                    setzeFigurenAction();
                }
            } else {
                setzeFigurenAction();
            }
    }
    
    public void setzeFigurenAction(){
        
        boolean gueltig = true;
        if (panelPressed.empty){
            if (farbeTypSetzen == Farbe.Gold){
                if (panelPressed.y != 0 && panelPressed.y != 1){
                    gueltig = false;
                }
            } else if (farbeTypSetzen == Farbe.Silber) {
                if (panelPressed.y != 6 && panelPressed.y != 7){
                    gueltig = false;
                }
            }
        } else {
            gueltig = false;
        }
        if (gueltig){
            String f;
            if (farbeTypSetzen == Farbe.Gold){
                f = "Gold";
            } else if (farbeTypSetzen == Farbe.Silber){
                f = "Silber";
            } else {
                f = "Gold";
            }
            
            /*
            spielfeld.set(fp.x, fp.y, new Spielfigur(f, typenSetzen[currentTyp]));
            JOptionPane.showMessageDialog(fp, spielfeld.get(fp.x, fp.y).getTyp());
            
            
            currentTyp++;
            arimaa.print();
            generiereFeld(null);
            */
            
            //Damit code nicht zu lang wird
            FeldPanel fp = panelPressed;
            spielfeld.set(fp.x, fp.y, new Spielfigur(f, typenSetzen[currentTyp]));
            //JOptionPane.showMessageDialog(fp, spielfeld.get(fp.x, fp.y).getTyp());
            currentTyp++;
            //repaint();
            printIcon(null, chessboard[fp.y+1][fp.x+1], fp.x, fp.y);
            chessboard[fp.y+1][fp.x+1].updateUI();
            arimaa.print();
            //updateUI();
            //generiereFeld(null);
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
