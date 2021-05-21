package controller;

import entities.KeyboardConstants;
import entities.Song;
import entities.SongNote;
import model.NotePlayer;
import view.PianoNotifier;

import java.util.ArrayList;

public class PianoController implements SongValidator, SongEnder {
    /**
     * us / tick
     * tick_length [us/tick] * 1 [tick] = 10^3 [us]   (cada tick son 1ms)
     */
    public static final double TICK_LENGTH = 1000;
    private boolean songSilenced;
    private boolean recording;
    private final NotePlayer notePlayer;
    private NotePlayer songPlayer;
    private ArrayList<SongNote> songNotes;
    private long startTime;

    /**
     * Connects the PianoController with the view
     */
    private PianoNotifier pianoPresser;

    /**
     * Keyboard volume
     */
    private float volume;

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

    public void addEventListener(PianoNotifier sv) {
        this.pianoPresser = sv;
    }

    public void closeCurrentSong() {
        this.songPlayer.closePlayer();

        this.pianoPresser.unpressAllKeys(); // release all notes from piano
    }

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
        if (note.getOctave() >= KeyboardConstants.INIT_OCTAVA && note.getOctave() < (KeyboardConstants.INIT_OCTAVA + KeyboardConstants.NUM_OCTAVES) && this.pianoPresser != null) this.pianoPresser.pressKey(note);
        if (this.songSilenced && (note.getOctave() >= KeyboardConstants.INIT_OCTAVA && note.getOctave() < (KeyboardConstants.INIT_OCTAVA + KeyboardConstants.NUM_OCTAVES))) return;
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
     * @param volume Value between 1 = full, 0 = silence
     */
    public void setVolume(float volume) {
        //TODO: Cridar això segons l'usuari carregat
        this.volume = volume;
        if (this.volume > 1) this.volume = 1;
        else if (this.volume < 0) this.volume = 0;
    }

    @Override
    public void songEnded() {
        this.pianoPresser.unpressAllKeys();
    }
}