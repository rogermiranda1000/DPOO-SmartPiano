package view;

import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Piano extends JPanel implements KeyController, KeyListener {

    public static final boolean ISWHITE = true;

    private ArrayList<JPanel> jP = new ArrayList<>();
    private JPanel jP2 = new JPanel();
    private final String[] notes = {""};
    private ArrayList<Tecla> keys = new ArrayList<>();

    //TODO: Fer el que diu en aquest tutorial però amb quadrats per fer que les notes caiguin. Link: https://www.youtube.com/watch?v=tHNWIWxRDDA
    Piano(){

        drawPiano();
        //this.addMouseListener(this);
        this.setBackground(Color.magenta);
        this.addKeyListener(this);

    }

    private void drawPiano(){
        for (int i = 0; i < 24; i++) {

            String nota = Note.getNote(i % 12).toString();

            if(nota.charAt(nota.length() - 1) == 'X'){
                keys.add(new Tecla(this, nota, ISWHITE));
                this.add(keys.get(i), BorderLayout.SOUTH);
            }else {
                keys.add(new Tecla(this, nota, !ISWHITE));
                this.add(keys.get(i), BorderLayout.SOUTH);
            }
        }
    }

    private void changeKeys(){
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).setKeyAssocieted(Note.getNote(i % 12).toString());
        }
    }

    //TODO: funció bucle que actualitzi les notes/tecles



    /*@Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("[X]: " + x + " [Y]: " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }*/


    @Override
    public boolean isPressed(String id) {
        System.out.println("PRESSED " + id);
        return true;
    }

    @Override
    public boolean isNotPressed(String id) {
        System.out.println("UNPRESSED " + id);
        return true;
    }

    @Override
    public boolean keyBoardPressed(char nota) {
        System.out.println("TIPED " + nota);
        return true;
    }

    @Override
    public boolean keyBoardNotPressed(char nota) {
        System.out.println("UNTIPED " + nota);
        return false;
    }

    public void hola(){

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("1");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("2");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("3");
    }
}
