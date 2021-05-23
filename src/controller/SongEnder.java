package controller;

/**
 * Interface tasked with notifying when a song finished playing
 */
public interface SongEnder {

    /**
     * The song that was played reached the end
     */
    void songEnded();
}
