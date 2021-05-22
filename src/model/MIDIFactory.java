package model;

import entities.Note;
import entities.Song;
import entities.SongNote;

import javax.sound.midi.*;
import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MIDIFactory {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    /**
     * Donat un fitxer .mid extreu les tecles
     *
     * @param song     Nom de la canço
     * @param author   Autor creador de la canço
     * @param creation Temps de creació
     * @param url      Ruta del midi
     * @return Conço (array de notes)
     * @throws IOException              Error al obrir el fitxer (file not found?)
     * @throws InvalidMidiDataException Error al tractar el fitxer com MIDI (.zip?)
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
                        if (velocity == 0) continue; // TODO només si NOTE_OFF?
                        SongNote current = new SongNote(tick, (sm.getCommand() == NOTE_ON), velocity, (byte) (key / 12), Note.getNote(key));
                        if (!current.isPressed()) {
                            if (MIDIFactory.isPressedBefore(r, current)) r.addNote(current);
                        }
                        else {
                            if (MIDIFactory.isReleasedBefore(r, current)) r.addNote(current);
                        }
                    }
                    //else System.out.println("Command:" + sm.getCommand());
                }
                /*else if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;
                    if (mm.getType() == 0x01) {
                        // la metadata és de tipus 'Text'
                        System.out.println("Text: " + new String(mm.getData(), StandardCharsets.ISO_8859_1));
                    }
                    else if (mm.getType() == 0x03) {
                        // la metadata és de tipus 'Name'
                        System.out.println("Name: " + new String(mm.getData(), StandardCharsets.ISO_8859_1));
                    }
                    else if (mm.getType() == 0x04) {
                        // la metadata és de tipus 'Instrument'
                    }
                    else System.out.println("Other meta message");
                }
                else System.out.println("Other message: " + message.getClass());*/
            }
        }

        r.sort();
        return r;
    }

    /**
     * Checks if a note is pressed before unpressing it
     * @param s The song to check
     * @param sn The note to check
     * @return If it was pressed (true), or not (false)
     */
    private static boolean isPressedBefore(Song s, SongNote sn) {
        ArrayList<SongNote> notes = s.getNotes();
        for (int last = notes.size()-1; last >= 0; last--) {
            SongNote current = notes.get(last);
            if (current.equals(sn)) return current.isPressed();
        }
        return false; // si s'ha arribat al final -> no s'ha premut
    }

    /**
     * Checks if a note is released/unpressed before pressing it
     * @param s The song to check
     * @param sn The note to check
     * @return If it was released/unpressed (true), or not (false)
     */
    private static boolean isReleasedBefore(Song s, SongNote sn) {
        ArrayList<SongNote> notes = s.getNotes();
        for (int last = notes.size()-1; last >= 0; last--) {
            SongNote current = notes.get(last);
            if (current.equals(sn)) return (!current.isPressed());
        }
        return true; // si s'ha arribat al final -> no s'ha premut
    }
}
