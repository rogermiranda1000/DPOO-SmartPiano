package controller;

/**
 * Interface tasked with actions regarding the recording of songs and muting the song the user is trying to play on the piano
 */
public interface RecordingEvent {

    /**
     * Notifies the controller that the user started recording
     * @param recording True if the user started recording, false if the user stopped
     */
    void startRecording(boolean recording);

    /**
     * Notifies the controller that the recorded song must be saved
     * @param name Song's name
     * @param isPublic Song's visibility (true = public, false = private)
     */
    void saveRecordedSong(String name, boolean isPublic);

    /**
     * Mute the octave INIT_OCTAVE to (INIT_OCTAVE + NUM_OCTAVES)
     */
    void muteSong();

    /**
     * Unmute the octave INIT_OCTAVE to (INIT_OCTAVE + NUM_OCTAVES)
     */
    void unmuteSong();

    /**
     * Tells the Controller the user has stopped recording the piano song,
     * if the song is valid the Controller should notify the view thorough 'requestSongInformation()'.
     */
    void validateRecording();
}
