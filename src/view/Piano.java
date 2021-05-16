package view;

import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Piano extends JPanel implements KeyController {

    public static final boolean ISWHITE = true;

    private ArrayList<JPanel> jP = new ArrayList<>();
    private JPanel jP2 = new JPanel();
    private final String[] notes = {""};

    //TODO: Fer el que diu en aquest tutorial però amb quadrats per fer que les notes caiguin. Link: https://www.youtube.com/watch?v=tHNWIWxRDDA
    Piano(){

        drawPiano();
        //this.addMouseListener(this);
        this.setBackground(Color.magenta);



    }

    private void drawPiano(){
        for (int i = 0; i < 24; i++) {

            String nota = Note.getNote(i % 12).toString();

            if(nota.charAt(nota.length() - 1) == 'X') this.add(new Tecla(this, nota, ISWHITE), BorderLayout.SOUTH);
            else this.add(new Tecla(this, nota, !ISWHITE));
        }
    }

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
        System.out.println("SUPER " + id);
        return true;
    }

    @Override
    public boolean isNotPressed(String id) {
        return false;
    }

    @Override
    public boolean keyBoardPressed(String id) {
        return false;
    }

    @Override
    public boolean keyBoardNotPressed(String id) {
        return false;
    }

    public void hola(){

    }
}
