package persistance;

import entities.Song;

public interface SongDAO {
    boolean addSong(Song song);
    boolean addVirtualSong(Song song);
    boolean deleteSong(Song song);
    boolean existsSong(Song song);
    //Song getSong()
}
