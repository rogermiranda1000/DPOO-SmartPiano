package model;

import entities.Note;
import entities.Song;
import entities.SongNote;

import javax.sound.midi.*;
import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.*;

/**
 * Class tasked with reading .mid files and turning them into Song objects with SongNotes
 */
public class MIDIFactory {

    /**
     * Constant representing a note press
     */
    public static final int NOTE_ON = 0x90;

    /**
     * Constant representing a note release
     */
    public static final int NOTE_OFF = 0x80;

    /**
     * Given a .mid file, extracts the SongNotes
     *
     * @param song Name of the song
     * @param author Name of the author of the song
     * @param creation Creation date
     * @param url Route to the MIDI file
     * @return Song object with all the notes
     * @throws IOException Error when opening the file (file not found?)
     * @throws InvalidMidiDataException Error when treating the file as MIDI (.zip?)
     */
    public static Song getSong(String song, String author, Date creation, URL url) throws IOException, InvalidMidiDataException {

        Sequence sequence = MidiSystem.getSequence(url);

        double tickLength = (double) sequence.getMicrosecondLength() / sequence.getTickLength();

        Song r = new Song(song, author, creation, tickLength, true);

        for (Track track : sequence.getTracks()) {
            //System.out.println("Track " + trackNumber + ": size = " + track.size());

            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);

                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    // If the message is a note press / release
                    if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {
                        long tick = event.getTick();
                        int key = sm.getData1();
                        byte velocity = (byte) sm.getData2();
                        r.addNote(new SongNote(tick, (sm.getCommand() == NOTE_ON), velocity, (byte) (key / 12), Note.getNote(key)));
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
}
