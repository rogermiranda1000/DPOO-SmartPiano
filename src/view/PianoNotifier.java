package view;

import entities.SongNote;

/**
 * Manages actions regarding the piano keys
 */
public interface PianoNotifier {

    /**
     * Un-presses all the piano keys
     */
    void unpressAllKeys();

    /**
     * A note should be pressed in the piano
     * @param key Note to play
     */
    void pressKey(SongNote key);

    /**
     * Asks the user for the song information
     */
    void requestSongInformation();
}
