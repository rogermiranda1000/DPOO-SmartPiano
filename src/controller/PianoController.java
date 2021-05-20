package controller;

import entities.SongNote;
import model.NotePlayer;

import java.util.ArrayList;

public class PianoController {
    /**
     * us / tick
     * tick_length [us/tick] * 1 [tick] = 10^3 [us]   (cada tick son 1ms)
     */
    private static final double TICK_LENGTH = 1000;
    private boolean songSilenced;
    private boolean recording;
    private final NotePlayer notePlayer;
    private ArrayList<SongNote> songNotes;
    private long startTime;
    private float volume;

    public PianoController() {
        this.songSilenced = false;
        this.recording = false;
        this.notePlayer = new NotePlayer();
        this.songNotes = null;
        this.volume = 1;
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
        this.notePlayer.executeNote(new SongNote(note.getTick(), note.isPressed(), (byte)Math.round(note.getVelocity() * this.volume), note.getOctave(), note.getNote()));
        if (!this.recording) return;
        this.songNotes.add(new SongNote(Math.round((System.currentTimeMillis() - this.startTime)),
                note.isPressed(), note.getVelocity(), note.getOctave(), note.getNote()));
    }

    /**
     * Plays a note from the song
     * @param note Note
     */
    public void playSongNote(SongNote note) {
        if (this.songSilenced && (note.getOctave() <= 4 || note.getOctave() >= 3)) return;
        this.notePlayer.executeNote(new SongNote(note.getTick(), note.isPressed(), (byte)Math.round(note.getVelocity() * this.volume), note.getOctave(), note.getNote()));
    }

    /**
     * Returns the recorded song only with notes
     * @return If the song recorded notes returns the song, null otherwise
     */
    public ArrayList<SongNote> getSongNotes() {
        if (this.songNotes == null || this.songNotes.size() < 1) return null;
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
        if (this.volume < 0) this.volume = 0;
    }
}