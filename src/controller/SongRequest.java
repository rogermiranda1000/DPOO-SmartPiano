package controller;

import entities.Song;

/**
 * Sol·licitud a MusicController per reproduir una canço
 */
public interface SongRequest {
    void requestSong(Song song);
}
