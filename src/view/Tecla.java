package view;

import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tecla extends JPanel implements MouseListener, KeyListener {
    private final Note note;
    private final int ocatava;

    private final JPanel tecla = new JPanel();
    private final KeyController kC;
    private char keyAssocieted;
    private final boolean isBlack;

    public Tecla(Piano p, Note note, boolean isBlack, int octava) {
        this.note = note;
        this.kC = p;
        this.ocatava = octava;
        this.isBlack = isBlack;

        this.setPreferredSize(new Dimension(40,200));
        this.setLayout(new BorderLayout());
        this.add(new JLabel(note.toString()), BorderLayout.SOUTH);

        if(isBlack) this.setBackground(new Color(0, 0, 0));
        else this.setBackground(new Color(255, 255, 255));

        this.addMouseListener(this);
        this.addKeyListener(this);
        p.addKeyListener(this);

    }

    public Note getNote(){
        return this.note;
    }

    public char getKey() {
        return keyAssocieted;
    }

    public int getOctava(){
        return this.ocatava;
    }

    public void setKeyAssocieted(char keyAssocieted) {
        this.keyAssocieted = keyAssocieted;
    }

    public void playNote(){
        this.setBackground(new Color(68, 108, 234));
    }

    public void stopNote(){
        if(isBlack) this.setBackground(new Color(0, 0, 0));
        else this.setBackground(new Color(255, 255, 255));
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.contains(e.getPoint())){
            this.setBackground(new Color(99, 99, 99));
            kC.isPressed(this.note, this.ocatava);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isBlack) this.setBackground(new Color(0, 0, 0));
        else this.setBackground(new Color(255, 255, 255));
        kC.isNotPressed(this.note, this.ocatava);
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) {
        if(this.note.toString().charAt(0) == e.getKeyChar()){
            System.out.println("NOTA: " + note + " - Octava: " + ocatava);
        }
        /*System.out.println(e.getKeyChar());
        kC.keyBoardPressed(e.getKeyChar());*/
    }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
