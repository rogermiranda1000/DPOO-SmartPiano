package view;

import controller.RecordingEvent;
import controller.TeclaEvent;
import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Piano extends JPanel implements ActionListener, TeclaNotifier {
    private static final String TEXT_START_RECORDING = "Start recording";
    private static final String TEXT_SAVE_RECORDING = "Save recording";

    public static final boolean IS_BLACK = true;
    public static final boolean IS_WHITE = false;
    private static final int NUM_OCTAVES = 2;
    private static final int INIT_OCTAVA = 3;

    private final Tecla[] keys;
    private final JButton record;
    private final RecordingEvent recordingEvent;

    public Piano(TeclaEvent event, RecordingEvent recording) {
        this.keys = new Tecla[12 * Piano.NUM_OCTAVES];
        this.recordingEvent = recording;

        record = new JButton(Piano.TEXT_START_RECORDING);
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
        if(e.getSource() == record) {
            boolean isRecording = this.record.getText().equalsIgnoreCase(Piano.TEXT_SAVE_RECORDING); // si el botò mostra 'save recording' la canço s'està guardant
            isRecording = !isRecording; // toggle
            this.record.setText(isRecording ? Piano.TEXT_SAVE_RECORDING : Piano.TEXT_START_RECORDING);
            this.recordingEvent.startRecording(isRecording);
        }
    }
}
