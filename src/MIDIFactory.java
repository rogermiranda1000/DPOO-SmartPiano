import entities.Note;
import entities.Song;
import entities.SongNote;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;

public class MIDIFactory {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    /**
     * Donat un fitxer .mid extreu les tecles
     * @param path Ruta del fitxer
     * @return Conço (array de notes)
     * @throws IOException Error al obrir el fitxer
     * @throws InvalidMidiDataException Error al tractar el fitxer com MIDI
     */
    public static Song getSong(File path) throws IOException, InvalidMidiDataException {
        Sequence sequence = MidiSystem.getSequence(path);

        double tickLenght = (double) sequence.getMicrosecondLength()/sequence.getTickLength();

        Song r = new Song(tickLenght);

        for (Track track : sequence.getTracks()) {
            //System.out.println("Track " + trackNumber + ": size = " + track.size());

            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);

                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    // If the message is a note press / release
                    if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {
                        long tick = event.getTick();
                        int key = sm.getData1();
                        byte velocity = (byte)sm.getData2();
                        r.addNote(new SongNote(tick, (sm.getCommand() == NOTE_ON), velocity, (byte)(key/12), Note.getNote(key)));
                    }
                    //else System.out.println("Command:" + sm.getCommand());
                }
                //else System.out.println("Other message: " + message.getClass());
            }
        }

        return r;
    }

    public static void generateSong(SongNote[] notes) throws MidiUnavailableException {
        // TODO
        MidiChannel[] channels;
        int INSTRUMENT = 0; // 0 is a piano

        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        channels = synth.getChannels();

        // * start playing a note
        //channels[INSTRUMENT].noteOn(id(note), VOLUME );
        // * wait
        //Thread.sleep( duration );
        // * stop playing a note
        //channels[INSTRUMENT].noteOff(id(note));

        synth.close();
    }

    public static void main(String[] args) throws Exception {
        MIDIFactory.getSong(new File("test.mid"));
        System.out.println();

    }
}
