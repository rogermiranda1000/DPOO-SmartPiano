package view;

import controller.TeclaEvent;
import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class that constitutes the piano and reacts to the user's input, can be of two types: black and white
 */
public class Tecla extends JPanel implements MouseListener, KeyListener {

    /**
     * Color of the key when it should be played (a song is playing in the piano)
     */
    private static final Color PLAY_COLOR = new Color(68, 108, 234);

    /**
     * Color of the key when it is being pressed by the user
     */
    private static final Color PRESS_COLOR = new Color(99, 99, 99);

    /**
     * Note associated to this key
     */
    private final Note note;

    /**
     * Octave of the note associated to this key
     */
    private final int ocatava;

    /**
     * Event to notify the user's actions to
     */
    private final TeclaEvent kC;

    /**
     * Associated character this key should react to
     */
    private char keyAssocieted;

    /**
     * Default color of this key (black or white)
     */
    private final Color color;

    /**
     * Indicates if the related character on the keyboard is being pressed
     */
    private boolean isPressing;

    /**
     * Indicates if the key is being pressed by the mouse
     */
    private boolean isHolding;

    /**
     * Configures the key, size, color and its listeners
     * @param p Event to notify the user's actions to
     * @param note Note this key is associated to
     * @param isBlack Indicates if this key should be black (true = black, false = white)
     * @param octava Octave of the note associated to this key
     */
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

    /**
     * Returns the note this key represents
     * @return The note his key represents
     */
    public Note getNote(){
        return this.note;
    }

    /**
     * Saves a new character this key should react to
     * @param keyAssocieted Character this key should react to
     * @return Itself
     */
    public Tecla setKeyAssociated(char keyAssocieted) {
        this.keyAssocieted = keyAssocieted;
        return this;
    }

    /**
     * Sets the key's color to it's "PLAY_COLOR"
     */
    public void playNote(){
        this.setBackground(Tecla.PLAY_COLOR);
    }

    /**
     * Sets the key's color to it's default state
     */
    public void stopNote(){
        this.setBackground(this.color);
    }

    /**
     * Sets the key's color to it's "Press_COLOR" and notifies it's been pressed
     */
    private void press() {
        this.setBackground(Tecla.PRESS_COLOR);
        kC.isPressed(this.note, this.ocatava);
    }

    /**
     * Sets the key's color to it's default state and notifies it's no longer pressed
     */
    private void release() {
        this.setBackground(this.color);
        kC.isNotPressed(this.note, this.ocatava);
    }

    /**
     * Called when the mouse is clicked
     * @param e Event that triggered this function
     */
    @Override
    public void mouseClicked(MouseEvent e) { }

    /**
     * Called when the key is pressed, if it was clicking the key now the key is pressed
     * @param e Event that triggered this function
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(!this.contains(e.getPoint())) return;

        this.isHolding = true;
        this.press();
    }

    /**
     * Called when the mouse is released, if it was being held now it releases
     * @param e Event that triggered this function
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(!this.isHolding) return; // TODO compatibilitat amb pantalles tactils (pulsacions simultanies clicant)

        this.release();
    }

    /**
     * Called when the mouse enters the object
     * @param e Event that triggered this function
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /**
     * Called when the mouse exits the object
     * @param e Event that triggered this function
     */
    @Override
    public void mouseExited(MouseEvent e) { }

    /**
     * Called when a key is typed
     * @param e Event that triggered this function
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * Called when a key is pressed, if the key should react to it, it does
     * @param e Event that triggered this function
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (this.keyAssocieted != e.getKeyChar()) return;
        if (this.isPressing) return; // ja s'ha apretat i no s'ha deixat anar (l'event es repeteix varis cops)

        this.isPressing = true;
        this.press();
    }

    /**
     * Called when a key is released, if the key should react to it, it does
     * @param e Event that triggered this function
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (this.keyAssocieted != e.getKeyChar()) return;

        this.isPressing = false;
        this.release();
    }
}
