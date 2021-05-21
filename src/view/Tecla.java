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
    private boolean isPressing;
    private boolean isHolding;

    public Tecla(TeclaEvent p, Note note, boolean isBlack, int octava) {
        this.note = note;
        this.kC = p;
        this.ocatava = octava;
        this.isPressing = false;
        this.isHolding = false;

        this.setPreferredSize(new Dimension(40,200));
        this.setLayout(new BorderLayout());

        JLabel jL = new JLabel(note.toString());
        jL.setForeground(isBlack ? Color.WHITE : Color.BLACK);
        this.add(jL, BorderLayout.SOUTH);

        this.color = (isBlack ? Color.BLACK : Color.WHITE);
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

        this.isHolding = true;
        this.press();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!this.isHolding) return; // TODO compatibilitat amb pantalles tactils (pulsacions simultanies clicant)

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
        if (this.isPressing) return; // ja s'ha apretat i no s'ha deixat anar (l'event es repeteix varis cops)

        this.isPressing = true;
        this.press();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.keyAssocieted != e.getKeyChar()) return;

        this.isPressing = false;
        this.release();
    }
}
