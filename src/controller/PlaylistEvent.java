package controller;

import entities.Song;

import java.util.ArrayList;

/**
 * Interface tasked with actions related to the "Playlists" tab
 */
public interface PlaylistEvent {

    /**
     * Returns the playlists of the logged user
     * @return The names of all the playlists
     */
    ArrayList<String> getPlaylists();

    /**
     * Returns all the songs in a playlist
     * @param playlist Name of the playlist to look inside
     * @return Cançons formant playlist
     */
    ArrayList<Song> getPlaylistSongs(String playlist);

    /**
     * Adds a playlist to the database
     * @param playlist Name of the playlist
     */
    void addPlaylist(String playlist);

    /**
     * Deletes a playlist from the logged user
     * @param playlist Name of the playlist
     */
    void removePlaylist(String playlist);

    /**
     * Deletes a song from a specifiedplaylist
     * @param playlist Name of the playlist where the song is in
     * @param song Song to delete
     */
    void removeSongPlaylist(String playlist, Song song);

    /**
     * Plays a playlist
     * @param playlist Name of the playlist to play
     */
    void requestPlayList(String playlist);
}
