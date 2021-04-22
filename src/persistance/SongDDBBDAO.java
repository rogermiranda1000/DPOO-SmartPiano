package persistance;

import business.SongDAO;
import entities.Song;

public class SongDDBBDAO implements SongDAO {
    private final DDBBAccess ddbb;

    public SongDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addSong(Song song) {
        return false;
    }

    @Override
    public boolean deleteSong(Song song) {
        return false;
    }
}
