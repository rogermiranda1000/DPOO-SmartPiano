package controller;

public interface MenuEvent {
    boolean toggleLoop();

    int currentSongPos();

    boolean playing();

    void exitSession();

}
