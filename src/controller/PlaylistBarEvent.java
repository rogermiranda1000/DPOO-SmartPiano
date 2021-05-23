package controller;

import view.PlayingSongNotifier;

/**
 * Interface tasked with actions related to the music player
 */
public interface PlaylistBarEvent {

    /**
     * Toggles the looping state of the playlist
     */
    void toggleLoop();

    /**
     * Toggles the random play state of the song / playlist
     */
    void toggleRandom();

    /**
     * Toggles the playing state of the song / playlist
     */
    void togglePlaying();

    /**
     * Sets the object that will be notified for playing song events
     * @param notifier Object that will be notified
     */
    void setPlayingSongListner(PlayingSongNotifier notifier);

    /**
     * The next song in the playlist should play
     */
    void nextSong();

    /**
     * The previous song in the playlist should play
     */
    void previousSong();
}
