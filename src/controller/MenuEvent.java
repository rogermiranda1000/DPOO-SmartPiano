package controller;

public interface MenuEvent {
    void toggleLoop();

    void toggleRandom();

    void togglePlaying();

    String getActualSong();
}
