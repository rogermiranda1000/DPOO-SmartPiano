package controller;

import entities.Song;
import entities.SongNote;
import model.NotePlayer;

public class PianoController {
    private boolean songSilenced;
    private boolean recording;
    private final NotePlayer notePlayer;
    private Song song;
    private long startTime;
    private float volume;

    public PianoController() {
        this.songSilenced = false;
        this.recording = false;
        this.notePlayer = new NotePlayer();
        this.song = null;
    }

    /**
     * Starts the recording
     */
    public void startRecording() {
        if (this.recording) return;
        this.recording = true;
        this.startTime = System.currentTimeMillis();
        this.song = new Song();
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
        this.song.addNote(new SongNote(System.currentTimeMillis() - this.startTime,
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
    public Song getSong() {
        if (this.song == null || this.song.getNotes().size() < 1) return null;
        return this.song;
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
