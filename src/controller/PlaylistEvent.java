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
    ArrayList<Song> getPlaylistSongs(String playlist);

    /**
     * Solicita afegir una playlist
     * @param playlist Nom de la playlist
     */
    void addPlaylist(String playlist);

    /**
     * Solicita eliminar una playlist
     * @param playlist Nom de la playlist
     */
    void removePlaylist(String playlist);

    /**
     * Solicita eliminar una canço d'una playlist
     * @param playlist Nom de la playlist on està la canço
     * @param song Canço a eliminar
     */
    void removeSongPlaylist(String playlist, Song song);

    /**
     * Solicita reproduir una playlist
     * @param playlist Nom de la playlist a reproduir
     */
    void requestPlayList(String playlist);
}
