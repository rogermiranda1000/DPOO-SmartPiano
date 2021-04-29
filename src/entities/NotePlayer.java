package entities;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

public class NotePlayer implements Runnable {
    private ArrayList<SongNote> notes;
    private double tickLength;
    private MidiChannel[] channels;
    private Synthesizer synth;
    public static final int INSTRUMENT = 0;
    public static final int SPEED = 1;

    public NotePlayer(Song song) throws MidiUnavailableException {
        this.notes = song.getNotes();
        this.tickLength = song.getTickLength();

        this.synth = MidiSystem.getSynthesizer();
        this.synth.open();
        this.channels = this.synth.getChannels();
    }

    @Override
    public void run() {
        long tick = 0;
        int i = 0;
        while (i < notes.size()) {
            // Esperem fins al seguent event
            if (notes.get(i).getTick() > tick) {
                try {
                    Thread.sleep((long)((notes.get(i).getTick() - tick) * tickLength / (1000 * SPEED)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick = notes.get(i).getTick();
            }
            //Reproduim els events d'aquest tick
            while (i < notes.size() && notes.get(i).getTick() == tick) {
                executeNote(notes.get(i++));
            }
        }
    }

    public void closeSynth() {
        this.synth.close();
    }

    public void executeNote(SongNote note) {
        if (note.isPressed()) {
            this.channels[INSTRUMENT].noteOn(id(note), note.getVelocity() );
        } else {
            this.channels[INSTRUMENT].noteOff(id(note), note.getVelocity() );
        }
    }

    private int id(SongNote note) {
        return (note.getOctave() + 1) * 12 + note.getNote().ordinal();
    }
}
