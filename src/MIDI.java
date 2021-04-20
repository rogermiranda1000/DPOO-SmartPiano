import javax.sound.midi.*;
import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class MIDI {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    public static void main(String[] args) throws Exception {
        Sequence sequence = MidiSystem.getSequence(new File("test.mid"));
        ArrayList<SongNote> notes = new ArrayList<>();

        for (Track track :  sequence.getTracks()) {
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
                        notes.add(new SongNote(tick, (sm.getCommand() == NOTE_ON), velocity, Note.getNote(key)));
                    }
                    //else System.out.println("Command:" + sm.getCommand());
                }
                //else System.out.println("Other message: " + message.getClass());
                // TODO tempo
            }
        }

        Collections.sort(notes);
        System.out.println();

    }
}
