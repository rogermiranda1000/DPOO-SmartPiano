package business;

import entities.Song;

public interface SongDAO {
    boolean addSong(Song song);
    boolean deleteSong(Song song);
    boolean exists(Song song);
}
