package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tecla extends JPanel implements MouseListener, KeyListener {

    private JPanel tecla = new JPanel();
    private String id;
    private KeyController kC;
    private String keyAssocieted;

    public Tecla(Piano p, String id, boolean isWhite) {
        this.id = id;
        this.kC = p;

        this.setPreferredSize(new Dimension(40,200));
        this.setLayout(new BorderLayout());
        this.add(new JLabel(id), BorderLayout.SOUTH);

        if(isWhite) this.setBackground(new Color(0, 0, 0));
        else this.setBackground(new Color(255, 255, 255));

        this.addMouseListener(this);
        p.addMouseListener(this);
        this.addKeyListener(this);
        p.addKeyListener(this);
    }

    public void setKeyAssocieted(String keyAssocieted) {
        this.keyAssocieted = keyAssocieted;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.contains(e.getPoint())){
            this.setBackground(new Color(255, 0, 0));
            kC.isPressed(id);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setBackground(new Color(0, 64, 255));
        kC.isNotPressed(id);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("2");
        kC.keyBoardPressed(e.getKeyChar());

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("HOLA");
        kC.keyBoardPressed(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("HEY");
        kC.keyBoardNotPressed(e.getKeyChar());
    }
}
