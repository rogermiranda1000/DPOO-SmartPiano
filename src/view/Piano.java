package view;

import controller.RecordingEvent;
import controller.TeclaEvent;
import entities.KeyboardConstants;
import entities.Note;
import entities.SongNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that contains the piano keys, the note bindings and some extra buttons
 */
public class Piano extends JPanel implements ActionListener, PianoNotifier {

    /**
     * Text for the record button when it's not recording
     */
    private static final String TEXT_START_RECORDING = "Start recording";

    /**
     * Text for the record button when it's recording
     */
    private static final String TEXT_SAVE_RECORDING = "Save recording";

    /**
     * Text for the mute button when it's un-muted
     */
    private static final String TEXT_MUTE_PIANO = "Mute shown octaves";

    /**
     * Text for the mute button when it's muted
     */
    private static final String TEXT_UNMUTE_PIANO = "Unmute shown octaves";

    /**
     * Associated value to BLACK pieces
     */
    public static final boolean IS_BLACK = true;

    /**
     * Associated value to WHITE pieces
     */
    public static final boolean IS_WHITE = false;

    /**
     * List of keys the piano has
     */
    private final Tecla[] keys;
    private final JButton record;
    private final JButton mute;
    private final RecordingEvent recordingEvent;

    public Piano(TeclaEvent event, RecordingEvent recording) {
        this.keys = new Tecla[12 * KeyboardConstants.NUM_OCTAVES];
        this.recordingEvent = recording;

        this.setLayout(new BorderLayout());

        JPanel btn = new JPanel();
        btn.setLayout(new BoxLayout(btn, BoxLayout.PAGE_AXIS));
        btn.setBackground(ColorConstants.BACKGROUND.getColor());

        record = new JButton(Piano.TEXT_START_RECORDING);
        record.addActionListener(this);
        record.setFocusable(false);
        btn.add(record);

        this.mute = new JButton(Piano.TEXT_MUTE_PIANO);
        this.mute.addActionListener(this);
        this.mute.setFocusable(false);
        btn.add(this.mute);

        this.add(btn, BorderLayout.NORTH);

        this.setBackground(ColorConstants.BACKGROUND.getColor());

        JPanel jPtemp = new JPanel();
        jPtemp.setBackground(ColorConstants.BACKGROUND.getColor());

        // draw piano
        for (int i = 0; i < this.keys.length; i++) {
            int octava = (i/12) + KeyboardConstants.INIT_OCTAVA;
            String nota = Note.getNote(i % 12).toString();

            Tecla temp = new Tecla(event, Note.getNote(i % 12), (nota.charAt(nota.length() - 1) == 'X') ? IS_BLACK : IS_WHITE, octava).setKeyAssocieted('t');
            this.keys[i] = temp;
            jPtemp.add(temp);
            this.addKeyListener(temp); // per alguna rao li hem d'afegir el KeyListener (potser culpa del request focus?)
        }

        this.add(jPtemp, BorderLayout.SOUTH);
    }

    /**
     * Get the key from the piano
     * @param note Key
     * @param octava Octave (from INIT_OCTAVA to INIT_OCTAVA+NUM_OCTAVES)
     * @return Coincident key
     */
    private Tecla getKey(Note note, int octava) {
        return this.keys[12*(octava - KeyboardConstants.INIT_OCTAVA) + note.ordinal()];
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
            this.changeKey(Note.getNote(i), KeyboardConstants.INIT_OCTAVA + (i/12), binds[i]);
        }
    }

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
        else if (e.getSource() == this.mute) {
            boolean isMuted = this.mute.getText().equalsIgnoreCase(Piano.TEXT_UNMUTE_PIANO); // si el botó mostra 'desmutejar', està mutejat
            isMuted = !isMuted; // toggle
            this.mute.setText(isMuted ? Piano.TEXT_UNMUTE_PIANO : Piano.TEXT_MUTE_PIANO);
            if (isMuted) this.recordingEvent.muteSong();
            else this.recordingEvent.unmuteSong();
        }
    }

    @Override
    public void unpressAllKeys() {
        for (Tecla t : this.keys) t.stopNote();
    }

    @Override
    public void pressKey(SongNote key) {
        Tecla note = this.keys[(key.getOctave()-KeyboardConstants.INIT_OCTAVA)*12 + key.getNote().ordinal()];
        if (key.isPressed()) note.playNote();
        else note.stopNote();
    }
}
