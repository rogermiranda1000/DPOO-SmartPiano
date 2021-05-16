package controller;

import view.PlayingSongNotifier;

public interface PlaylistBarEvent {
    void toggleLoop();
    void toggleRandom();
    void togglePlaying();
    void setPlayingSongListner(PlayingSongNotifier notifier);
    void nextSong();
    void previousSong();
}
