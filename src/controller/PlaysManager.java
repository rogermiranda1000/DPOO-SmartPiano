package controller;

import entities.Song;

public interface PlaysManager {
    void addPlay(long secondsPlayed, Song song);
}
