package persistance;

import entities.List;
import entities.Song;
import entities.User;

import java.util.ArrayList;

/**
 * Interface used for accessing the user's configuration
 */
public interface PlaylistDAO {

    /**
     * Checks if a playlist exists in the database
     * @param list Playlist to look for
     * @return True if it exists, false if it doesn't and null if there was an error
     */
    Boolean existsPlaylist(List list);

    /**
     * Creates a playlist in the database
     * @param list Playlist to create
     * @return True if the operation was successful
     */
    boolean createPlaylist(List list);

    /**
     * Removes a playlist from the database
     * @param list Playlist to remove
     * @return True if the operation was successful
     */
    boolean removePlaylist(List list);

    /**
     * Adds a song to a playlist in the database
     * @param list Playlist to add the song to
     * @param song Song to add
     * @return True if the operation was successful
     */
    boolean addSongPlaylist(List list, Song song);

    /**
     * Removes a song from a playlist in the database
     * @param list Playlist to remove a song from
     * @param song Song to remove
     * @return True if the operation was successful
     */
    boolean removeSongPlaylist(List list, Song song);

    /**
     * Removes the relation between a song and it's playlists
     * @param song Song to remove the connections from
     * @return True if the operation was successful
     */
    boolean removeSongAllPlaylists(Song song);

    /**
     * Returns all the playlists from a user
     * Attention: The songs inside the playlist only contain the necessary information to find them (name, author, creation date); They don't have notes
     * @param user User that owns the playlists
     * @return The user's playlists
     */
    ArrayList<List> getPlaylists(User user);
}
