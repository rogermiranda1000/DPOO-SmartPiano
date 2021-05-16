package model;

import controller.SongEnder;
import entities.Song;
import entities.SongNote;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

public class NotePlayer extends Thread {
    private static final int INSTRUMENT = 0;
    private static final int SPEED = 1;

    private final ArrayList<SongNote> notes;
    private final double tickLength;
    private final MidiChannel[] channels;
    private final Synthesizer synth;

    private boolean paused;
    private boolean alive;
    private float volume;

    private SongEnder songEnder;
    private final Object notified; //Utilitzat per fer el notify

    public NotePlayer(Song song, float volume, SongEnder songEnder) {
        this.paused = false;
        this.alive = true;
        this.notified = new Object();
        this.notes = song.getNotes();
        this.tickLength = song.getTickLength();
        this.volume = volume;
        this.songEnder = songEnder;
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
        this(new Song(), 1, null);
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

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public synchronized boolean getAlive() {
        return this.alive;
    }

    /**
     * Força l'eliminació del thread
     */
    public synchronized void closePlayer() {
        if (this.alive) {
            this.alive = false;
            this.synth.close();
            this.songEnder = null;
        }
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

        // es tanca & notifica
        if (this.getAlive()) {
            this.synth.close();
            if (this.songEnder != null) this.songEnder.songEnded();
        }
    }

    public void executeNote(SongNote note) {
        if (note.isPressed()) {
            this.channels[INSTRUMENT].noteOn(note.getId(), Math.round(note.getVelocity()*volume));
        } else {
            this.channels[INSTRUMENT].noteOff(note.getId(), note.getVelocity());
        }
    }
}
