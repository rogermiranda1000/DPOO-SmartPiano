package persistance;

import entities.Song;

public interface StatisticsDAO {
    int[] getSongStatistics();
    int[] getTimeStatistics();
    boolean addListen(String nick, Song song, int seconds);
    Song[] getTop5(int[] plays);

    /**
     * Delete the user's statistics
     * @param s User name
     * @return If the statistics from the player was deleted (true) or something happened (false)
     */
    boolean deletePlayerStatistics(String s);
    boolean deleteStatistics(Song s);
}
