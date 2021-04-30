package model;

import entities.Song;

public interface SongDAO {
    boolean addSong(Song song);
    boolean deleteSong(Song song);
    boolean existsSong(Song song);
}
