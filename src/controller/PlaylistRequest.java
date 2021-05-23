package controller;

import entities.List;

/**
 * Interface tasked with playing playlists
 */
public interface PlaylistRequest {

    /**
     * Asks the MusicController to play a playlist
     * @param list Playlist to play
     */
    void requestPlaylist(List list);
}
