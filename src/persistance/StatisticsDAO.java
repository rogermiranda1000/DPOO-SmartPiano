package persistance;

import entities.Song;

/**
 * Interface used for getting data to generate statistics from and adding song plays
 */
public interface StatisticsDAO {

    /**
     * Returns the evolution of the plays per hour
     * @return An array of values representing the number of total plays for each hour in the last 24 hours,
     * the last value is the current hour
     */
    int[] getSongStatistics();

    /**
     * Returns the evolution of the time listened per hour
     * @return An array of values representing the number of seconds listened for each hour in the last 24 hours,
     * the last value is the current hour
     */
    int[] getTimeStatistics();

    /**
     * Adds a play of a certain length to the specified song
     * @param nick Username of the logged user
     * @param song Song to add the play to
     * @param seconds Seconds this play lasted for
     * @return True if the operation was successful
     */
    boolean addListen(String nick, Song song, int seconds);

    /**
     * Returns the top 5 most popular songs
     * @param plays Array which will be filled with the plays of each song, from most popular to least
     * @return Array of songs, from most popular to least
     */
    Song[] getTop5(int[] plays);

    /**
     * Delete the user's statistics
     * @param s User name
     * @return If the statistics from the player was deleted (true) or something happened (false)
     */
    boolean deletePlayerStatistics(String s);

    /**
     * Deletes the plays from a specified song
     * @param s Song to delete the plays from
     * @return True if the operation was successful
     */
    boolean deleteStatistics(Song s);
}
