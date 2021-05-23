package controller;

import entities.Song;

import java.util.ArrayList;

/**
 * Interfaces tasked with actions done in the "Songs" tab
 */
public interface SongsEvent {

    /**
     * Returns all the playlist from the logged user
     * @return The names of the playlists
     */
    ArrayList<String> getPlaylists();

    /**
     * Returns all the songs the logged user should be able to see
     * @return Private songs the user created and all the public songs
     */
    ArrayList<Song> getUserSongs();

    /**
     * Deletes the specified song from the database
     * @param song Song to delete
     */
    void deleteSong(Song song);

    /**
     * Adds a song to a playlist
     * @param song Song to add
     * @param list Playlist to add the song in
     */
    void addSongPlaylist(Song song, String list);
}
