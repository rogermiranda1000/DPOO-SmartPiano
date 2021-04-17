import javax.sound.midi.*;
import java.io.File;
import java.lang.String;
import java.util.ArrayList;

public class MIDI {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static void main(String[] args) throws Exception {
        Sequence sequence = MidiSystem.getSequence(new File("test.mid"));
        ArrayList<SongNote> notes = new ArrayList<>();
        
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();

            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                //System.out.print("@" + event.getTick() + " ");

                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    //System.out.print("Channel: " + sm.getChannel() + " ");

                    // If the message is a note press / release
                    if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {
                        long tick = event.getTick();
                        int key = sm.getData1();
                        int octave = Math.min(Math.max((key / 12)-1, 1), 2);    // octave must be either 1 or 2
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        byte velocity = (byte)sm.getData2();
                        //TODO: Add song to the list
                        //notes.add(new SongNote(tick, (sm.getCommand() == NOTE_ON), velocity, note + Note.Do1 + (12*octave)));
                        //System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    }
                    else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }

    }
}
