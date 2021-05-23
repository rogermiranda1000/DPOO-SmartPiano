package persistance;

import entities.Song;

import java.util.ArrayList;

/**
 * Interface used for accessing the songs
 */
public interface SongDAO {

    /**
     * Adds a song to the database
     * @param song Song to add
     * @return True if the operation was successful
     */
    boolean addSong(Song song);

    /**
     * Adds a song with a virtual creator to the database
     * @param song Song to be added
     * @return True if the operation was successful
     */
    boolean addVirtualSong(Song song);

    /**
     * Deletes a song from the database
     * @param song Song to be deleted
     * @return True if the operation was successful
     */
    boolean deleteSong(Song song);

    /**
     * Checks if the specified song exists in the database
     * @param song Song to look for
     * @return True if the song exists
     */
    boolean existsSong(Song song);

    /**
     * Deletes all the songs created by the specified user
     * @param user User to delete the songs from
     * @return True if the operation was successful
     */
    boolean deleteUserSongs(String user);

    /**
     * Checks if the registered user is the author of the song
     * @param song Song to check
     * @param name Registered user to check
     * @return If it's the author (true), or not (false); null if something went wrong
     */
    Boolean isAuthor(Song song, String name);

    /**
     * Given a song with basic attributes (name, date, author), obtains the rest of the information
     * @param song Song to fill
     * @return True if the operation was successful
     */
    boolean updateSong(Song song);

    /**
     * Obtains all the songs from the user and the public ones
     * /!\ Only the basic information of every song is loaded (there are no notes)
     * @param loggedUser User logged in the app
     * @return The songs the user should be able to see
     */
    ArrayList<Song> getAccessibleSongs(String loggedUser);
}
