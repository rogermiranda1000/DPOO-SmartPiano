package controller;

public interface MenuEvent {
    void toggleLoop();

    int currentSongPos();

    boolean playing();
}
