package view;

import entities.Note;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ChangeKeyButton extends GenericButton implements ActionListener {
    private Note nota;
    private String name;
    private byte octava;

    private char key;

    private KeyChanger kC;

    public ChangeKeyButton(KeyChanger kC, byte octava, Note nota) {
        super(nota.name());
        //this.setText(name);
        this.addActionListener(this);

        this.kC = kC;
        this.name = nota.name();
        this.octava = octava;
        this.nota = nota;
    }

    private boolean pressKey(String note) {
        String input = JOptionPane.showInputDialog(this, "Enter the new key: ");
        if(input != null && input.length() == 1) {
            this.key = input.charAt(0);
            return true;

        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pressKey(name)) kC.changeKey(this.nota, this.octava, this.key);
        else JOptionPane.showMessageDialog(this, "You have to enter one character!","Wrong input!", JOptionPane.ERROR_MESSAGE);
    }
}
