package controller;

import view.PlayingSongNotifier;

public interface MenuEvent {
    void toggleLoop();

    void toggleRandom();

    void togglePlaying();

    void setPlayingSongListner(PlayingSongNotifier notifier);
}
