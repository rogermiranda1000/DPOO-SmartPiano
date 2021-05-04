package model;

import entities.Song;
import entities.SongNote;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

public class NotePlayer extends Thread {
    private static NotePlayer globalNotePlayer = new NotePlayer();
    private static final int INSTRUMENT = 0;
    private static final int SPEED = 1;

    private final ArrayList<SongNote> notes;
    private final double tickLength;
    private final MidiChannel[] channels;
    private final Synthesizer synth;

    private boolean paused;
    private boolean alive;

    private final Object notified; //Utilitzat per fer el notify

    public NotePlayer(Song song) {
        this.paused = false;
        this.alive = true;
        this.notified = new Object();
        this.notes = song.getNotes();
        this.tickLength = song.getTickLength();
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
     * Constructor del singleton
     */
    public NotePlayer() {
        this(new Song("","",null, 1));
    }

    /**
     * Funció que canvia l'estat de pausa de la cançó
     * @param p Booleà que indica si es pausa
     */
    public void setPlay(boolean p) {
        synchronized (this.notified) {
            this.paused = !p;
            if (p) this.notified.notify();
        }

    }

    public synchronized boolean getAlive() {
        return this.alive;
    }

    private void closeSynth() {
        this.synth.close();
    }

    public synchronized void closePlayer() {
        this.alive = false;
        this.closeSynth();
    }

    public static void closeSinglePlayer() {
        NotePlayer.globalNotePlayer.closeSynth();
    }

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        long tick = 0;
        int i = 0;
        while (i < notes.size() && this.getAlive()) {
            // Esperem fins al seguent event
            if (notes.get(i).getTick() > tick) {
                try {
                    Thread.sleep((long)((notes.get(i).getTick() - tick) * tickLength / (1000 * SPEED)));
                    synchronized (this.notified) {
                        if (this.paused) this.notified.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick = notes.get(i).getTick();
            }
            //Reproduim els events d'aquest tick
            if (this.getAlive()) {
                while (i < notes.size() && notes.get(i).getTick() == tick) {
                    executeNote(notes.get(i++));
                }
            }
        }
        this.closeSynth();
    }

    public void executeNote(SongNote note) {
        if (note.isPressed()) {
            this.channels[INSTRUMENT].noteOn(note.getId(), note.getVelocity());
        } else {
            this.channels[INSTRUMENT].noteOff(note.getId(), note.getVelocity());
        }
    }

    public static void executeSingleNote(SongNote note) {
         NotePlayer.globalNotePlayer.executeNote(note);
    }
}
