package view;

import controller.TeclaEvent;
import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tecla extends JPanel implements MouseListener, KeyListener {
    private static final Color PLAY_COLOR = new Color(68, 108, 234);
    private static final Color PRESS_COLOR = new Color(99, 99, 99);

    private final Note note;
    private final int ocatava;

    private final TeclaEvent kC;
    private char keyAssocieted;
    private final Color color;

    public Tecla(TeclaEvent p, Note note, boolean isBlack, int octava) {
        this.note = note;
        this.kC = p;
        this.ocatava = octava;

        this.setPreferredSize(new Dimension(40,200));
        this.setLayout(new BorderLayout());
        this.add(new JLabel(note.toString()), BorderLayout.SOUTH);

        this.color = (isBlack ? new Color(0, 0, 0) : new Color(255, 255, 255));
        this.setBackground(this.color);

        this.addMouseListener(this);
        this.addKeyListener(this);
    }

    public Note getNote(){
        return this.note;
    }

    public Tecla setKeyAssocieted(char keyAssocieted) {
        this.keyAssocieted = keyAssocieted;
        return this;
    }

    public void playNote(){
        this.setBackground(Tecla.PLAY_COLOR);
    }

    public void stopNote(){
        this.setBackground(this.color);
    }

    private void press() {
        this.setBackground(Tecla.PRESS_COLOR);
        kC.isPressed(this.note, this.ocatava);
    }

    private void release() {
        this.setBackground(this.color);
        kC.isNotPressed(this.note, this.ocatava);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!this.contains(e.getPoint())) return;

        this.press();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO fora de la zona de la tecla?

        this.release();
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.keyAssocieted != e.getKeyChar()) return;

        this.press();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.keyAssocieted != e.getKeyChar()) return;

        this.release();
    }
}
