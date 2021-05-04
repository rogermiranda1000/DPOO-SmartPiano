package model;

import entities.Note;
import entities.Song;
import entities.SongNote;

import javax.sound.midi.*;
import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.*;

public class MIDIFactory {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    /**
     * Donat un fitxer .mid extreu les tecles
     * @param song Nom de la canço
     * @param author Autor creador de la canço
     * @param creation Temps de creació
     * @param url Ruta del midi
     * @return Conço (array de notes)
     * @throws IOException Error al obrir el fitxer (file not found?)
     * @throws InvalidMidiDataException Error al tractar el fitxer com MIDI (.zip?)
     */
    public static Song getSong(String song, String author, Date creation, URL url) throws IOException, InvalidMidiDataException {
        Sequence sequence = MidiSystem.getSequence(url);

        double tickLenght = (double) sequence.getMicrosecondLength()/sequence.getTickLength();

        Song r = new Song(song, author, creation, tickLenght);

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
                /*else if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;
                    if (mm.getType() == 1) {
                        // la metadata és de tipus 'Text'
                        System.out.println(new String(mm.getData(), StandardCharsets.ISO_8859_1));
                    }
                }*/
                //else System.out.println("Other message: " + message.getClass());
            }
        }

        r.sort();
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
        MIDIFactory.getSong("name", "author", new Date(), new URL("https://www.mutopiaproject.org/ftp/AscherJ/alice/alice.mid"));
        System.out.println();

    }
}
