package controller;

import entities.List;
import entities.Song;
import view.PlayingSongNotifier;

public interface PlaylistBarEvent {
    void toggleLoop();
    void toggleRandom();
    void togglePlaying();
    void setPlayingSongListner(PlayingSongNotifier notifier);
}
