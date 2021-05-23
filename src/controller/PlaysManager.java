package controller;

import entities.Song;

/**
 * Interface tasked with adding plays data to the database
 */
public interface PlaysManager {

    /**
     * Adds a play of a certain duration to a song from the logged user
     * @param secondsPlayed Seconds the play lasted for
     * @param song Song the user was listening to
     */
    void addPlay(int secondsPlayed, Song song);
}
