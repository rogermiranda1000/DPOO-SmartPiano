package persistance;

import entities.Song;

public interface StatisticsDAO {
    public int[] getSongStatistics();
    public int[] getTimeStatistics();
    public boolean addListen(Song song, int seconds);
}
