import model.MIDIFactory;
import persistance.*;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

/**
 * Class that adds custom meme songs to the database
 */
public class SongAdder {

    /**
     * List with urls and song names of the meme songs to add
     */
    private static final String[][] SONGS = {
            {"Megalovania", "http://www.vgmusic.com/new-files/UT-Megalovania.mid"},
            {"L'himne del Carbassot 100% oficial 1 sol link mega", "https://www.midiworld.com/download/5023"},
            {"Mii Channel", "https://www.midiworld.com/download/5037"},
            {"L'himne d'Espanya", "https://www.midiworld.com/download/4163"},
            {"All star", "https://www.midiworld.com/download/854"},
            {"Windows startup", "https://www.midiworld.com/download/4806"},
            {"Living la Vida Loca", "https://www.midiworld.com/download/800"},
            {"River flows in you", "https://bitmidi.com/uploads/112145.mid"},
            {"Doom", "http://www.midishrine.com/midipp/mpc/Doom/level1.mid"},
            {"Coffin dance", "https://rogermiranda1000.com/personal/CoffinDance.mid"},
            {"Crab rave", "https://joeybabcock.me/blog/wp-content/uploads/2019/02/Crab-Rave_4.mid"}
    };

    /**
     * Secondary entry point to add the custom meme songs
     * @param args Arguments of the program (none used)
     * @throws ClassNotFoundException The database driver is not installed
     */
    public static void main (String[] args) throws ClassNotFoundException {
        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig(), 1);
            try {
                for (String[] song : SONGS) {
                    new SongDDBBDAO(ddbb).addVirtualSong(MIDIFactory.getSong(song[0], "admin", new Date(), new URL(song[1])));
                }
            } catch (IOException |InvalidMidiDataException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
