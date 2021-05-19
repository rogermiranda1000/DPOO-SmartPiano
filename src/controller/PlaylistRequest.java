package controller;

import entities.List;

/**
 * Sol·licitud a MusicController per reproduir una playlist
 */
public interface PlaylistRequest {
    void requestPlaylist(List list);
}
