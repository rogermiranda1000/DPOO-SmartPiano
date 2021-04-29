package entities;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

public class NotePlayer implements Runnable {
    private static final int INSTRUMENT = 0;
    private static final int SPEED = 1;

    private final ArrayList<SongNote> notes;
    private final double tickLength;
    private final MidiChannel[] channels;
    private final Synthesizer synth;

    public NotePlayer(Song song) throws MidiUnavailableException {
        this.notes = song.getNotes();
        this.tickLength = song.getTickLength();

        this.synth = MidiSystem.getSynthesizer();
        this.synth.open();
        this.channels = this.synth.getChannels();
    }

    @Override
    @SuppressWarnings("BusyWait")
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

    // TODO ??
    public void closeSynth() {
        this.synth.close();
    }

    public void executeNote(SongNote note) {
        if (note.isPressed()) {
            this.channels[INSTRUMENT].noteOn(note.getId(), note.getVelocity());
        } else {
            this.channels[INSTRUMENT].noteOff(note.getId(), note.getVelocity());
        }
    }

    /*public static void executeNote(SongNote note, double tickLength) throws MidiUnavailableException {
        new NotePlayer(tickLength).executeNote(note);
    }*/
}
