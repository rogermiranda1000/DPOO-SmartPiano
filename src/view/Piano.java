package view;

import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Piano extends JPanel implements KeyController {

    public static final boolean ISBLACK = true;

    private ArrayList<JPanel> jP = new ArrayList<>();
    private JPanel jP2 = new JPanel();
    private final String[] notes = {""};
    private ArrayList<Tecla> keys = new ArrayList<>();

    //TODO: Fer el que diu en aquest tutorial però amb quadrats per fer que les notes caiguin. Link: https://www.youtube.com/watch?v=tHNWIWxRDDA
    Piano(){

        drawPiano();
        //this.addMouseListener(this);
        this.setBackground(Color.magenta);
        //this.addKeyListener(this);

    }

    private void drawPiano(){
        int octava = 1;
        for (int i = 0; i < 24; i++) {

            String nota = Note.getNote(i % 12).toString();

            if(i == 11) octava = 2;
            if(nota.charAt(nota.length() - 1) == 'X'){
                Tecla temp = new Tecla(this, Note.getNote(i % 12), ISBLACK, octava);
                keys.add(temp);
                this.add(temp, BorderLayout.SOUTH);
            }else {
                Tecla temp = new Tecla(this, Note.getNote(i % 12), !ISBLACK, octava);
                keys.add(temp);
                this.add(temp, BorderLayout.SOUTH);
            }
        }
    }

    //TODO: funció bucle que actualitzi les notes/tecles
    public void changeKey(Note n, char letter, int octava){
        int i = 0;

        while(i < 24){
            if(n.equals(keys.get(i).getNote()) && keys.get(i).getOctava() == octava){
                keys.get(i).setKeyAssocieted(letter);
                break;
            }

            i++;
        }
    }

    public void playNote(char key){
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i).getKey() == key) keys.get(i).playNote();
        }
    }

    public void stopNote(char key){
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i).getKey() == key) keys.get(i).stopNote();
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
    public boolean isPressed(Note note) {
        System.out.println("PRESSED " + note.toString());
        return true;
    }

    @Override
    public boolean isNotPressed(Note note) {
        System.out.println("UNPRESSED " + note.toString());
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

    /*@Override
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
    }*/
}
