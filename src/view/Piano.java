package view;

import controller.RecordingEvent;
import controller.TeclaEvent;
import entities.Note;
import entities.SongNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Piano extends JPanel implements ActionListener, PianoNotifier {
    private static final int OCTAVA_INICIAL = 3;
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

    //TODO: connectar amb controller
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == record) {
            boolean isRecording = this.record.getText().equalsIgnoreCase(Piano.TEXT_SAVE_RECORDING); // si el botò mostra 'save recording' la canço s'està guardant
            isRecording = !isRecording; // toggle
            this.record.setText(isRecording ? Piano.TEXT_SAVE_RECORDING : Piano.TEXT_START_RECORDING);
            this.recordingEvent.startRecording(isRecording);

            if (!isRecording) {
                // no està grabant -> s'ha de guardar (o no)
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel text = new JLabel("Song name:");
                JCheckBox ck = new JCheckBox("Public: ");
                ck.setSelected(true); // per defecte public
                JTextField tF = new JTextField();

                panel.add(text);
                panel.add(tF);
                panel.add(ck);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add a new song", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION && tF.getText().length()>0) this.recordingEvent.saveRecordedSong(tF.getText(), ck.isSelected());
            }
        }
    }

    @Override
    public void unpressAllKeys() {
        for (Tecla t : this.keys) t.stopNote();
    }

    @Override
    public void pressKey(SongNote key) {
        Tecla note = this.keys[(key.getOctave()-Piano.OCTAVA_INICIAL)*12 + key.getNote().ordinal()];
        if (key.isPressed()) note.playNote();
        else note.stopNote();
    }
}
