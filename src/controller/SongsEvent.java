package controller;

import entities.Song;

import java.util.ArrayList;

public interface SongsEvent {
    /**
     * Obtè les cançons del usuari i les públiques
     * @return Cançons que l'usuari loguejat té acces
     */
    ArrayList<Song> getUserSongs();
}
