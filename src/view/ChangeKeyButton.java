package view;

import entities.Note;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button used to change the user's binging to the piano keys
 */
public class ChangeKeyButton extends GenericButton implements ActionListener {

    /**
     * Note this button refers to
     */
    private Note nota;

    /**
     * The name to display on screen
     */
    private String name;

    /**
     * Octave this button corresponds to
     */
    private byte octava;

    /**
     * The character this note on this octave is associated to
     */
    private char key;

    /**
     * Object to notify changes in key associations to
     */
    private KeyChanger kC;

    /**
     * Initializes the button and it's variables
     * @param kC Interface to notify changes in configurations
     * @param octava Octave this button refers to
     * @param nota Note this button refers to
     */
    public ChangeKeyButton(KeyChanger kC, byte octava, Note nota) {
        super(nota.name());
        //this.setText(name);
        this.addActionListener(this);

        this.kC = kC;
        this.name = nota.name();
        this.octava = octava;
        this.nota = nota;
    }

    /**
     * Shows an option pane asking for a key and saves it
     * @return True if t least one character was entered
     */
    private boolean pressKey() {
        String input = JOptionPane.showInputDialog(this, "Enter the new key: ");
        if(input != null && input.length() == 1) {
            this.key = input.charAt(0);
            return true;

        }
        return false;
    }

    /**
     * Function called when the button is pressed
     * @param e Event that triggered this function
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(pressKey()) kC.changeKey(this.nota, this.octava, this.key);
        else JOptionPane.showMessageDialog(this, "You have to enter one character!","Wrong input!", JOptionPane.ERROR_MESSAGE);
    }
}
