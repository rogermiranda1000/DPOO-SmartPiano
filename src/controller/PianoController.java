package controller;

import entities.KeyboardConstants;
import entities.Song;
import entities.SongNote;
import model.NotePlayer;
import view.PianoNotifier;

import java.util.ArrayList;

/**
 * Class tasked with recording user's songs and playing songs on the keyboard
 */
public class PianoController implements SongValidator, SongEnder {

    /**
     * Length of every tick [us],
     * tick_length [us/tick] * 1 [tick] = 10^3 [us]   (every tick is 1ms)
     */
    public static final double TICK_LENGTH = 1000;

    /**
     * True if the octaves the user can see are silenced
     */
    private boolean songSilenced;

    /**
     * True if the keys pressed are being recorded
     */
    private boolean recording;

    /**
     * Object that will play the keys pressed by the user
     */
    private final NotePlayer notePlayer;

    /**
     * Object that will play the notes of the song
     */
    private NotePlayer songPlayer;

    /**
     * List of notes the user has recorded
     */
    private ArrayList<SongNote> songNotes;

    /**
     * Moment the user started recording
     */
    private long startTime;

    /**
     * Connects the PianoController with the view
     */
    private PianoNotifier pianoPresser;

    /**
     * Keyboard volume (0 = silent, 1 = max volume)
     */
    private float volume;

    /**
     * Initializes the controller resetting the variables
     */
    public PianoController() {
        this.songSilenced = false;
        this.recording = false;

        // Single keys player
        this.notePlayer = new NotePlayer();
        this.songPlayer = null;
        this.songNotes = null;
        this.volume = 1;
        this.notePlayer.setVolume(this.volume);
    }

    /**
     * Adds an object to connect the PianoController with the view
     * @param sv Connects the PianoController with the view
     */
    public void addEventListener(PianoNotifier sv) {
        this.pianoPresser = sv;
    }

    /**
     * Stops the song that was playing
     */
    public void closeCurrentSong() {
        if (this.songPlayer != null) this.songPlayer.closePlayer();

        this.pianoPresser.unpressAllKeys(); // release all notes from piano
    }

    /**
     * Closes the song that is playing and starts a new one
     * @param s Song to play
     */
    public void playSong(Song s) {
        if (this.songPlayer != null) this.closeCurrentSong();

        this.songPlayer = new NotePlayer(s, this.volume, this, this);
        this.songPlayer.start();
    }

    /**
     * Starts the recording
     */
    public void startRecording() {
        if (this.recording) return;
        this.recording = true;
        this.startTime = System.currentTimeMillis();
        this.songNotes = new ArrayList<>();
    }

    /**
     * Stops the recording
     */
    public void stopRecording() {
        this.recording = false;
    }

    // TODO interficies?
    /**
     * Silences the piano
     */
    public void mute() {
        this.songSilenced = true;
    }

    /**
     * Un-silences the piano
     */
    public void unMute() {
        this.songSilenced = false;
    }

    /**
     * Plays a note and adds it to the song if recording
     * @param note Note to be played and added
     */
    public void addNote(SongNote note) {
        this.notePlayer.executeNote(note);
        if (!this.recording) return;
        this.songNotes.add(new SongNote(Math.round(System.currentTimeMillis() - this.startTime),
                note.isPressed(), note.getVelocity(), note.getOctave(), note.getNote()));
    }

    /**
     * Plays a note from the song
     * @param note Note
     */
    @Override
    public void requestNote(SongNote note) {
        if (note.getOctave() >= KeyboardConstants.INIT_OCTAVE && note.getOctave() < (KeyboardConstants.INIT_OCTAVE + KeyboardConstants.NUM_OCTAVES) && this.pianoPresser != null) this.pianoPresser.pressKey(note);
        if (this.songSilenced && (note.getOctave() >= KeyboardConstants.INIT_OCTAVE && note.getOctave() < (KeyboardConstants.INIT_OCTAVE + KeyboardConstants.NUM_OCTAVES))) return;
        this.notePlayer.executeNote(note);
    }

    /**
     * Returns the recorded song only with notes
     * @return If the song recorded notes returns the song, null otherwise
     */
    public ArrayList<SongNote> getSongNotes() {
        return this.songNotes;
    }

    /**
     * Changes the volume of the piano
     * @param volume Volume of the song (o = silence, 1 = max volume)
     */
    public void setVolume(float volume) {
        this.volume = volume;
        if (this.volume > 1) this.volume = 1;
        else if (this.volume < 0) this.volume = 0;
        this.notePlayer.setVolume(this.volume);
    }

    /**
     * The song that was playing ended
     */
    @Override
    public void songEnded() {
        this.pianoPresser.unpressAllKeys();
    }
}