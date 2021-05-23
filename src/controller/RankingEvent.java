package controller;

import entities.Song;

/**
 * Interface tasked with requesting data to generate the statistics
 */
public interface RankingEvent {

    /**
     * Returns the evolution of the plays per hour
     * @return An array of values representing the number of total plays for each hour in the last 24 hours,
     * the last value is the current hour
     */
    int[] getSongsStatistics();

    /**
     * Returns the evolution of the time listened per hour
     * @return An array of values representing the number of seconds listened for each hour in the last 24 hours,
     * the last value is the current hour
     */
    int[] getTimeStatistics();

    /**
     * Returns the top 5 most popular songs
     * @param plays Array which will be filled with the plays of each song, from most popular to least
     * @return Array of songs, from most popular to least
     */
    Song[] getTop5(int[] plays);
}