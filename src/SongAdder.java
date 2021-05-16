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
    private static final String NAME = "Megalovania";
    private static final String URL = "http://www.vgmusic.com/new-files/UT-Megalovania.mid";

    public static void main (String[] args) {
        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig(), 1);
            try {
                URL url = new URL(URL);
                new SongDDBBDAO(ddbb).addVirtualSong(MIDIFactory.getSong(NAME, "admin", new Date(), url));
            } catch (IOException |InvalidMidiDataException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
