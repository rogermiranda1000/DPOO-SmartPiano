package controller;

import entities.Song;

/**
 * Interface tasked with informing when a song should be added to the database
 */
public interface SongNotifier {

    /**
     * Adds a song to the database
     * @param song Song to add
     */
    void addSong(Song song);
}
