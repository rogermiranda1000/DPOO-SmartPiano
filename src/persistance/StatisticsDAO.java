package persistance;

import entities.Song;

public interface StatisticsDAO {
    int[] getSongStatistics();
    int[] getTimeStatistics();
    boolean addListen(String nick, Song song, int seconds);
    Song[] getTop5(int[] plays);
    boolean deleteStatistics(Song s);
}
