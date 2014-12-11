/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import arimaa.Arimaa2;
import arimaa.Farbe;
import arimaa.Koord;
import arimaa.Spielfeld;
import arimaa.Spielfigur;
import arimaa.Typ;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;
import javax.print.event.PrintJobEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
    //String path = "C:\\Users\\Marcus\\Documents\\GitHub\\Arimaa\\src\\icons\\";
    String path = "C:\\Users\\Marcus\\Documents\\GitHub\\Arimaa\\src\\icons_new\\";
    String path2 = "C:\\Users\\Marcus\\Documents\\GitHub\\Arimaa\\src\\backgrounds\\";
    
    boolean showKoords = false;
    
    //Eingabevariablen
    FeldPanel panelPressed;
    FeldPanel panelReleased;

    Arimaa2 arimaa;
    Spielfeld spielfeld;
    
    Spielfigur cursor;

    /**
     * Creates new form spielfeld
     */
    public spielfeld() {
        initComponents();
        setBackground(Color.white);
        setSize(500, 500);
        arimaa = new Arimaa2(this);
        spielfeld = arimaa.spielfeld;
        //arimaa.print();
        //spielfeld = arimaa.getSpielFeld();
        //spielfeld.set(new Koord("A1"), new Spielfigur(Farbe.valueOf("Gold"),Typ.valueOf("Elefant")));
        //generiereFeld(spielfeld);
    }

    public void generiereFeld(Spielfeld spielfeld) {
        Random r = new Random();
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
                    current.setBackground(Color.WHITE);
                } else if (j == 0) {
                    current.add(new JLabel(Integer.toString(i)));
                    current.setBackground(koords);
                } else {/*
                    if (((i + j) % 2) == 0) {
                        current.setBackground(gold);
                        current.background = gold;
                    } else {
                        current.setBackground(silber);
                        current.background = silber;
                    }*/
                    //current.add(new JLabel(new ImageIcon(path2+"background"+r.nextInt(12))));
                    //current.repaint();
                    int count = r.nextInt(11);
                    count++;
                    String background = path2+"background"+count+".png";
                    current.setBackground(background);
                    current.repaint();
                }
                current.setSize(50, 50);
                if (i > 0 && j > 0 && i <= 8 && j <= 8) {
                    Koord k = new Koord(j-1, i-1);
                    if (spielfeld.get(k) != null) {
                        printIcon(current, j-1, i-1);
                    }
                    if (showKoords){
                       current.add(new JLabel(current.x + ", " + current.y)); 
                    }
                }
                //current.updateUI();
                add(current);
            }  
        }
    }
    
    public void generiereFeldUpdate(Spielfeld spielfeld){
        Random r = new Random();
        int i=1;
        if (spielfeld == null){
            spielfeld = this.spielfeld;
        }
        for (FeldPanel [] fpArr : chessboard){
            for (FeldPanel fp : fpArr){
                int x = fp.x;
                int y = fp.y;
                if (x >= 0 && y >= 0){
                    Spielfigur sf = spielfeld.get(new Koord(x, y));                    
                    if (sf != null){
                        fp.removeAll();
                        printIcon(fp, x, y);
                        fp.updateUI();
                    } else {
                        fp.removeAll();
                        //fp.background = fp.background;
                        fp.repaint();
                    }
                    if (((fp.x + 1) % 3) == 0 && ((fp.y + 1) % 3) == 0) {
                        //fp.background = new Color(220, 50, 50);
                        fp.setBackground(path2+"death"+i+".png");
                        fp.repaint();
                        i++;
                    }
                    if (showKoords){
                       fp.add(new JLabel(fp.x + ", " + fp.y)); 
                    }
                    updateUI();
                }
            }
        }
    }
    
    public void generiereFeldUpdate(Koord k1, Koord k2){
        
    }

    public void printIcon(FeldPanel current, int i, int j) {
        Koord k = new Koord(i, j);
        Spielfigur sf = spielfeld.get(k);
        setzeFigur(current, sf.getTyp().name(), sf.getFarbe().name(), i, j);
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
        try {
            Koord k = new Koord(x, y);
            cursor = spielfeld.get(k);
            boolean showCursor = arimaa.setKords(k);
            /*if (showCursor){
                cursor = spielfeld.get(k);
            } else*/ if (arimaa.getCache() != null){
                cursor = arimaa.getCache();
            } else {
                cursor = null;
                formMouseMoved(evt);
            }
            formMouseMoved(evt);
            //generiereFeld(spielfeld);
            generiereFeldUpdate(null);
        } catch (IllegalArgumentException e){
            
        }
        
    }

    public void setCursor(Spielfigur sf){
        cursor = sf;
    }
    
    public void resetCursor(){
        cursor = null;
    }
    
    private void formMousePressed(java.awt.event.MouseEvent evt){
        int x = evt.getX();
        int y = evt.getY();
        panelPressed = (FeldPanel) getComponentAt(x, y);
        x = panelPressed.x;
        y = panelPressed.y;
        try {
            Koord k = new Koord(x, y);
            boolean showCursor = arimaa.setKords(k);
            if (showCursor){
                cursor = spielfeld.get(k);
            } else if (arimaa.getCache() != null){
                cursor = arimaa.getCache();
            } else {
                cursor = null;
            }
            formMouseMoved(evt);
            //generiereFeld(spielfeld);
            generiereFeldUpdate(null);
        } catch (IllegalArgumentException e){
            
        }
    }
    
    public boolean aufstellenFertig(){
        boolean b =  arimaa.fertig();
        if (b){
            //removeAll();
            generiereFeldUpdate(spielfeld);
        }
        return b;
    }
    
    public void spielEndeErreicht(Farbe gewinner){
        removeAll();
        setLayout(new BorderLayout());
        JLabel jl = new JLabel("Farbe " + gewinner.name() + " hat gewonnen!");
        jl.setVerticalTextPosition(getWidth()/2-jl.getWidth());
        jl.setVerticalTextPosition(getHeight()/2-jl.getHeight());
        add(jl);
    }
    
    }//GEN-LAST:event_formMouseReleased
/*
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        
    }//GEN-LAST:event_formMousePressed
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
