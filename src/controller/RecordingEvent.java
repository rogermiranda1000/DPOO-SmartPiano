package controller;

public interface RecordingEvent {
    /**
     * Notify to the controller that the user start recording
     * @param recording True if the user start recording, false if the user stops (save the recording)
     */
    void startRecording(boolean recording);

    /**
     * Notify the controller that the recorded song must be saved
     * @param name Song's name
     * @param isPublic Song's visibility
     */
    void saveRecordedSong(String name, boolean isPublic);

    /**
     * Mute the octave INIT_OCTAVA to (INIT_OCTAVA+NUM_OCTAVES)
     */
    void muteSong();

    /**
     * Unmute the octave INIT_OCTAVA to (INIT_OCTAVA+NUM_OCTAVES)
     */
    void unmuteSong();
}
