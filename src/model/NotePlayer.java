package model;

import controller.SongEnder;
import controller.SongValidator;
import entities.Song;
import entities.SongNote;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

/**
 * Class tasked with triggering a note or playing a whole song, should be started to play the song
 */
public class NotePlayer extends Thread {

    /**
     * The instrument being used (0 = default piano)
     */
    private static final int INSTRUMENT = 0;

    /**
     * Constant to change the speed at which the songs play (1 = standard)
     */
    private static final int SPEED = 1;

    /**
     * Notes to play, sorted by tick
     */
    private final ArrayList<SongNote> notes;

    /**
     * The time [us] every tick represents
     */
    private final double tickLength;

    /**
     * The channels of the MIDI file
     */
    private final MidiChannel[] channels;

    /**
     * Synthesiser the class will use to play the notes
     */
    private final Synthesizer synth;

    /**
     * Tick of the next song that should play
     */
    private long tick;

    /**
     * The second of the song when someone requested the current second
     */
    private int lastSecondSent;

    /**
     * True if the player is paused
     */
    private boolean paused;

    /**
     * True if the player hasn't been closed
     */
    private boolean alive;

    /**
     * Volume the notes should be played in, used as a scalar value (1 = volume of the note, 0 = silent)
     */
    private float volume;

    /**
     * Object to notify when the song ends
     */
    private final SongEnder songEnder;

    /**
     * Object to validate a note play to
     */
    private final SongValidator songValidator;

    /**
     * Object that wll receive the notifications (used for un-pausing)
     */
    private final Object notified;

    /**
     * Creates a NotePlayer with the given attributes and initializes the necessary tools to play a song when started
     * @param song Song to play when started
     * @param volume Volume to play the song in (1 = volume of the note, 0 = silent)
     * @param songEnder Object to notify when the song ends
     * @param songValidator Object to validate a note play to
     */
    public NotePlayer(Song song, float volume, SongEnder songEnder, SongValidator songValidator) {
        this.paused = false;
        this.alive = true;
        this.notified = new Object();
        this.notes = song.getNotes();
        this.tickLength = song.getTickLength();
        this.volume = volume;
        this.songEnder = songEnder;
        this.songValidator = songValidator;
        this.tick = 0;
        this.lastSecondSent = 0;
        MidiChannel[] channels = null;
        Synthesizer synth = null;
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
        }
        this.channels = channels;
        this.synth = synth;
    }

    /**
     * Creates a NotePlayer with the given attributes (null songValidator) and initializes the necessary tools to play a song when started
     * @param song Song to play when started
     * @param volume Volume to play the song in (1 = volume of the note, 0 = silent)
     * @param songEnder Object to notify when the song ends
     */
    public NotePlayer(Song song, float volume, SongEnder songEnder) {
        this(song, volume, songEnder, null);
    }

    /**
     * Creates a NotePlayer with the given attributes (null songEnder) and initializes the necessary tools to play a song when started
     * @param song Song to play when started
     * @param volume Volume to play the song in (1 = volume of the note, 0 = silent)
     * @param songValidator Object to validate a note play to
     */
    public NotePlayer(Song song, float volume, SongValidator songValidator) {
        this(song, volume,  null, songValidator);
    }

    /**
     * Constructor del singleton
     */
    public NotePlayer() {
        this(new Song(), 1, null, null);
    }

    /**
     * Funció que canvia l'estat de pausa de la cançó
     * @param p Booleà que indica si es pausa
     */
    public void setPlay(boolean p) {
        if (!p == this.paused) return;

        synchronized (this.notified) {
            this.paused = !p;
            if (p) this.notified.notify();
        }

    }

    /**
     * Sets a new volume for the song to play in
     * @param volume Volume to play the song in (1 = volume of the note, 0 = silent)
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Returns if the thread is active
     * @return True if the player hasn't been closed
     */
    public synchronized boolean getAlive() {
        return this.alive;
    }

    /**
     * Forces the deletion of the thread
     */
    public synchronized void closePlayer() {
        this.alive = false;
    }

    /**
     * Starts playing the loaded song
     */
    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        this.tick = 0;
        int i = 0;
        while (i < notes.size() && this.getAlive()) {
            // Esperem fins al seguent event
            try {
                synchronized (this.notified) {
                    if (this.paused) this.notified.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (notes.get(i).getTick() > tick) {
                try {
                    Thread.sleep((long)((notes.get(i).getTick() - tick) * tickLength / (1000 * SPEED)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick = notes.get(i).getTick();
            }

            //Reproduim els events d'aquest tick
            if (this.getAlive()) {
                while (i < notes.size() && notes.get(i).getTick() == tick) {
                    if (this.songValidator == null) executeNote(notes.get(i++));
                    else this.songValidator.requestNote(notes.get(i++));
                }
            }
        }

        // es tanca & notifica
        if (this.getAlive()) {
            // si ja s'ha tancat vol dir que ha sigut forçat -> no notifiquis
            if (this.songEnder != null) this.songEnder.songEnded();
        }
        this.synth.close();
    }

    /**
     * Executes a single note (at the saved volume)
     * @param note Note to play
     */
    public void executeNote(SongNote note) {
        if (note.isPressed()) {
            this.channels[INSTRUMENT].noteOn(note.getId(), Math.round(note.getVelocity()*volume));
        } else {
            this.channels[INSTRUMENT].noteOff(note.getId(), note.getVelocity());
        }
    }

    /**
     * Returns the number of seconds that elapsed since this function was last called (or since the song started)
     * @return Seconds elapsed since this function was last called
     */
    public int getCurrentSecond() {
        int second = (int)Math.round(tick * tickLength/1000/1000);
        int r = second - lastSecondSent;
        this.lastSecondSent = second;
        return r;
    }
}
