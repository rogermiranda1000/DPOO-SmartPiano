package controller;

import entities.Song;

import java.util.ArrayList;

public interface PlaylistEvent {
    /**
     * Retorna totes les playlists del usuari actual
     * @return Tots els noms de les playlists
     */
    ArrayList<String> getPlaylists();

    /**
     * Retorna les cançons que formen una playlist
     * @param playlist Nom de la playlist a buscar
     * @return Cançons formant playlist
     */
    ArrayList<Song> getSongs(String playlist);

    void deletePlaylist(String playlist);

    void playPlaylist(String valueOf);

    void deleteSongsFromPlaylist(String value);
}
