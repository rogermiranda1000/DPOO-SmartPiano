package view;

import entities.SongNote;

public interface PianoNotifier {
    void unpressAllKeys();
    void pressKey(SongNote key);

    /**
     * Asks the user for the song information
     */
    void requestSongInformation();
}
