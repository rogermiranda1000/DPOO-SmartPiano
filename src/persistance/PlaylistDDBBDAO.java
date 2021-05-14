package persistance;

import entities.List;
import entities.Song;

public class PlaylistDDBBDAO implements PlaylistDAO {
    private final DDBBAccess ddbb;

    public PlaylistDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean createPlaylist(List list) {
        return false;
    }

    @Override
    public boolean removePlaylist(List list) {
        return false;
    }

    @Override
    public boolean addSongPlaylist(List list, Song song) {
        return false;
    }

    @Override
    public boolean removeSongPlaylist(List list, Song song) {
        return false;
    }
}
