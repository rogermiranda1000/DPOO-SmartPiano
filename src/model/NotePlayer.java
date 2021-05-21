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

public class NotePlayer extends Thread {
    private static final int INSTRUMENT = 0;
    private static final int SPEED = 1;

    private final ArrayList<SongNote> notes;
    private final double tickLength;
    private final MidiChannel[] channels;
    private final Synthesizer synth;
    private long tick;
    private int lastSecondSent;

    private boolean paused;
    private boolean alive;
    private float volume;

    private final SongEnder songEnder;
    private final SongValidator songValidator;
    private final Object notified; //Utilitzat per fer el notify

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

    public NotePlayer(Song song, float volume, SongEnder songEnder) {
        this(song, volume, songEnder, null);
    }

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
        this.alive = false;
    }

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

    public void executeNote(SongNote note) {
        if (note.isPressed()) {
            this.channels[INSTRUMENT].noteOn(note.getId(), Math.round(note.getVelocity()*volume));
        } else {
            this.channels[INSTRUMENT].noteOff(note.getId(), note.getVelocity());
        }
    }

    /**
     * Dóna els segons que han passat des de l'últim cop que s'ha cridat la funció
     * @return valor corresponent al segon que han passat
     */
    public int getCurrentSecond() {
        int second = (int)Math.round(tick * tickLength/1000/1000);
        int r = second - lastSecondSent;
        this.lastSecondSent = second;
        return r;
    }
}
