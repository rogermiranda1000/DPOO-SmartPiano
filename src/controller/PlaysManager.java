package controller;

import entities.Song;

public interface PlaysManager {
    void addPlay(int secondsPlayed, Song song);
}
