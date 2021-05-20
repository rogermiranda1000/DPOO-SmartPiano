package view;

import entities.Note;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeKeyButton extends GenericButton implements ActionListener {

    private Note nota;
    private String name;
    private int octava;

    private KeyChanger kC;

    public ChangeKeyButton(KeyChanger kC, int octava, Note nota) {
        super(nota.name());
        //this.setText(name);
        this.addActionListener(this);

        this.kC = kC;
        this.name = nota.name();
        this.octava = octava;
        this.nota = nota;
    }

    private void pressKey(String note) {
        JOptionPane.showMessageDialog(this, "Press a key to set up " + note + "." + "\nOr press OK to cancel.", "Key recorder", JOptionPane.PLAIN_MESSAGE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        kC.changeKey(this.nota, 'a', this.octava);
        pressKey(name);
    }
}
