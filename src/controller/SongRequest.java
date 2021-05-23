package controller;

import entities.Song;

/**
 * Interface tasked with requesting a song to play
 */
public interface SongRequest {

    /**
     * Requests a song to be played
     * @param song Song to play
     */
    void requestSong(Song song);
}
