package view;

/**
 * Manages when a new song is playing
 */
public interface PlayingSongNotifier {

    /**
     * Notifies that a new song is playing
     * @param songName Name of the new song
     */
    void newSongPlaying(String songName);
}
