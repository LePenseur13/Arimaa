/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import arimaa.Arimaa;
import arimaa.Spielfeld;
import arimaa.Spielfigur;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
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
    
    //Feldgeneration
    Color koords = Color.WHITE;
    Color gold = new Color(212, 154, 78);
    Color silber = Color.white;
    String[] typen = {"Elefant", "Kamel", "Pferd", "Hund", "Katze", "Kaninchen"};
    String[] typensetzen = {"Elefant", "Kamel", "Pferd", "Pferd", "Hund", "Hund", "Katze", "Katze"};
    int typenSetzenIndex = 0;
    String path = "C:\\Users\\Marcus\\Documents\\GitHub\\Arimaa\\src\\icons\\";
    
    //Eingabevariablen
    FeldPanel panelPressed;
    FeldPanel panelReleased;

    Arimaa arimaa;
    Spielfeld spielfeld;
    
    Spielfigur cursor;

    /**
     * Creates new form spielfeld
     */
    public spielfeld() {
        initComponents();
        setBackground(Color.white);
        arimaa = new Arimaa(this);
        arimaa.print();
        spielfeld = arimaa.getSpielFeld();
        spielfeld.set("A1", new Spielfigur("Gold", "Elefant"));
    }

    public void generiereFeld(Spielfeld spielfeld) {
        if (spielfeld == null){
            spielfeld = this.spielfeld;
        }
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
                    if (spielfeld.get(j-1, i-1) != null) {
                        printIcon(current, j-1, i-1);
                    } else {
                        current.add(new JLabel(current.x + ", " + current.y));
                    }
                }
                //current.updateUI();
                add(current);
            }  
        }
    }

    public void printIcon(FeldPanel current, int i, int j) {
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
        current.typ = typ;
        current.farbe = farbe;
        current.add(jl);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
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
        
    }//GEN-LAST:event_formMouseClicked

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        formMouseMoved(evt);
    }//GEN-LAST:event_formMouseDragged

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (cursor != null) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(path + cursor.getTyp().name() + cursor.getFarbe().name() + ".png");
            Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
            setCursor(c);
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_formMouseMoved

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        int x = evt.getX();
        int y = evt.getY();
        panelReleased = (FeldPanel) getComponentAt(x, y);
        x = panelReleased.x;
        y = panelReleased.y;
        //arimaa.setKoords(x, y);
    }

    public void setCursor(Spielfigur sf){
        cursor = sf;
    }
    
    public void resetCursor(){
        cursor = null;
    }
    
    private void formMousePressed(java.awt.event.MouseEvent evt){
        cursor = new Spielfigur("Gold", "Elefant");
        int x = evt.getX();
        int y = evt.getY();
        panelPressed = (FeldPanel) getComponentAt(x, y);
        x = panelPressed.x;
        y = panelPressed.y;
        //arimaa.setKoord(x, y);
    }
    }//GEN-LAST:event_formMouseReleased
/*
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        
    }//GEN-LAST:event_formMousePressed
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
