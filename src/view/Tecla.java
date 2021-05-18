package view;

import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tecla extends JPanel implements MouseListener, KeyListener{

    private JPanel tecla = new JPanel();
    private Note note;
    private int ocatava;
    private KeyController kC;
    private char keyAssocieted;
    private boolean isBlack;

    public Tecla(Piano p, Note note, boolean isBlack, int octava) {
        this.note = note;
        this.kC = p;
        this.ocatava = octava;
        this.isBlack = isBlack;

        MyKeyListener mKL = new MyKeyListener(p);

        this.setPreferredSize(new Dimension(40,200));
        this.setLayout(new BorderLayout());
        this.add(new JLabel(note.toString()), BorderLayout.SOUTH);

        if(isBlack) this.setBackground(new Color(0, 0, 0));
        else this.setBackground(new Color(255, 255, 255));

        this.addMouseListener(this);
        p.addMouseListener(this);
        this.addKeyListener(this);
        p.addKeyListener(this);

    }

    public Note getNote(){
        return this.note;
    }

    public int getOctava(){
        return this.ocatava;
    }

    public void setKeyAssocieted(char keyAssocieted) {
        this.keyAssocieted = keyAssocieted;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.contains(e.getPoint())){
            this.setBackground(new Color(99, 99, 99));
            kC.isPressed(note);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isBlack) this.setBackground(new Color(0, 0, 0));
        else this.setBackground(new Color(255, 255, 255));
        kC.isNotPressed(note);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(this.note.toString().charAt(0) == e.getKeyChar()){
            System.out.println("NOTA: " + note + " - Octava: " + ocatava);
        }
        /*System.out.println(e.getKeyChar());
        kC.keyBoardPressed(e.getKeyChar());*/

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*System.out.println(e.getKeyChar());
        System.out.println(e.getKeyCode());
        kC.keyBoardPressed(e.getKeyChar());*/
    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*System.out.println("HEY");
        kC.keyBoardNotPressed(e.getKeyChar());*/
    }
}
