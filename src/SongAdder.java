import model.MIDIFactory;
import persistance.*;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

public class SongAdder {
    /*private static final String []SONG = {"Megalovania", "http://www.vgmusic.com/new-files/UT-Megalovania.mid"};
    private static final String []SONG = {"L'himne del Carbassot 100% oficial 1 sol link mega", "https://www.midiworld.com/download/5023"};
    private static final String []SONG = {"Mii Channel", "https://www.midiworld.com/download/5037"};
    private static final String []SONG = {"L'himne d'Espanya", "https://www.midiworld.com/download/4163"};
    private static final String []SONG = {"All star", "https://www.midiworld.com/download/854"};*/
    private static final String []SONG = {"Windows startup", "https://www.midiworld.com/download/4806"};

    public static void main (String[] args) {
        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig(), 1);
            try {
                URL url = new URL(SONG[1]);
                new SongDDBBDAO(ddbb).addVirtualSong(MIDIFactory.getSong(SONG[0], "admin", new Date(), url));
            } catch (IOException |InvalidMidiDataException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
