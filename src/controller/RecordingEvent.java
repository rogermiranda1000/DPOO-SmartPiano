package controller;

public interface RecordingEvent {
    /**
     * Notify to the controller that the user start recording
     * @param recording True if the user start recording, false if the user stops (save the recording)
     */
    void startRecording(boolean recording);
}
