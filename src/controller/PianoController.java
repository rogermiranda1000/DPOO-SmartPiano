package controller;

import entities.Song;
import entities.SongNote;
import model.NotePlayer;

import java.util.ArrayList;

public class PianoController implements SongValidator {
    /**
     * us / tick
     * tick_length [us/tick] * 1 [tick] = 10^3 [us]   (cada tick son 1ms)
     */
    private static final double TICK_LENGTH = 1000;
    private static final int OCTAVA_INICIAL = 3;
    private static final int NUM_OCTAVES = 2;
    private boolean songSilenced;
    private boolean recording;
    private final NotePlayer notePlayer;
    private NotePlayer songPlayer;
    private ArrayList<SongNote> songNotes;
    private long startTime;

    /**
     * Keyboard volume
     */
    private float volume;

    public PianoController() {
        this.songSilenced = false;
        this.recording = false;

        /**
         * Single keys player
         */
        this.notePlayer = new NotePlayer();
        this.songPlayer = null;
        this.songNotes = null;
        this.volume = 1;
        this.notePlayer.setVolume(this.volume);
    }

    public void playSong(Song s) {
        if (this.songPlayer != null) this.songPlayer.closePlayer();

        this.songPlayer = new NotePlayer(s, this.volume, this);
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
        this.songSilenced = true;
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
        if (this.songSilenced && (note.getOctave() >= PianoController.OCTAVA_INICIAL && note.getOctave() < (PianoController.OCTAVA_INICIAL + PianoController.NUM_OCTAVES))) return;
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
}