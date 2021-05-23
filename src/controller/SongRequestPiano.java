package controller;

import entities.Song;

/**
 * Interface for requesting a song to play on the piano
 */
public interface SongRequestPiano {

    /**
     * Requests a specified song to play on the piano
     * @param song Song to play
     */
    void requestSongInPiano(Song song);
}
