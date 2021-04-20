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
    public static SongNote[] getSong(File path) throws IOException, InvalidMidiDataException {
        ArrayList<SongNote> notes = new ArrayList<>();

        Sequence sequence = MidiSystem.getSequence(path);

        double tickLenght = (double) sequence.getMicrosecondLength()/sequence.getTickLength(); // temps [us] per tick

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
                        notes.add(new SongNote(tick, (sm.getCommand() == NOTE_ON), velocity, (byte)(key/12), Note.getNote(key)));
                    }
                    //else System.out.println("Command:" + sm.getCommand());
                }
                //else System.out.println("Other message: " + message.getClass());
                // TODO tempo
            }
        }

        Collections.sort(notes);
        return notes.toArray(new SongNote[0]);
    }

    public static void generateSong(SongNote[] notes) {
        // TODO
    }

    public static void main(String[] args) throws Exception {
        MIDIFactory.getSong(new File("test.mid"));
        System.out.println();

    }
}
