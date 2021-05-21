package view;

import controller.TeclaEvent;
import entities.Config;
import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Piano extends JPanel implements ActionListener, TeclaNotifier {
    public static final boolean IS_BLACK = true;
    public static final boolean IS_WHITE = false;
    private static final int NUM_OCTAVES = 2;
    private static final int INIT_OCTAVA = 3;

    private final Tecla[] keys;
    private final JButton record;

    public Piano(TeclaEvent event) {
        this.keys = new Tecla[12 * Piano.NUM_OCTAVES];

        record = new JButton("Record");
        record.addActionListener(this);
        record.setFocusable(false);
        this.add(record, BorderLayout.NORTH);
        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // draw piano
        for (int i = 0; i < this.keys.length; i++) {
            int octava = (i/12) + Piano.INIT_OCTAVA;
            String nota = Note.getNote(i % 12).toString();

            Tecla temp = new Tecla(event, Note.getNote(i % 12), (nota.charAt(nota.length() - 1) == 'X') ? IS_BLACK : IS_WHITE, octava).setKeyAssocieted('t');
            this.keys[i] = temp;
            this.add(temp, BorderLayout.SOUTH);
            this.addKeyListener(temp); // per alguna rao li hem d'afegir el KeyListener (potser culpa del request focus?)
        }
    }

    /**
     * Get the key from the piano
     * @param note Key
     * @param octava Octave (from INIT_OCTAVA to INIT_OCTAVA+NUM_OCTAVES)
     * @return Coincident key
     */
    private Tecla getKey(Note note, int octava) {
        return this.keys[12*(octava - Piano.INIT_OCTAVA) + note.ordinal()];
    }

    /**
     * Funció bucle que actualitza les notes/tecles
     * @param note Tecla a cambiar
     * @param octava Octava; de 1 a NUM_OCTAVES
     * @param letter Nova tecla a escoltar
     */
    public void changeKey(Note note, int octava, char letter) {
        this.getKey(note, octava).setKeyAssocieted(letter);
    }

    /**
     * Update all the keys
     * @param binds Keyboard keys binded to the piano keys
     */
    public void loadConfig(char[] binds) {
        for (int i = 0; i < binds.length; i++) {
            this.changeKey(Note.getNote(i), Piano.INIT_OCTAVA + (i/12), binds[i]);
        }
    }

    @Override
    public void playNote(Note note, int octava) {
        this.getKey(note, octava).playNote();
    }

    @Override
    public void stopNote(Note note, int octava){
        this.getKey(note, octava).stopNote();
    }

    //TODO: connectar amb controller
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == record) System.out.println("Connectar amb controller amb PianoRecorder");
    }
}
