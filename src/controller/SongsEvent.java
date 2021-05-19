package controller;

import entities.Song;

import java.util.ArrayList;

public interface SongsEvent {
    /**
     * Retorna totes les playlists del usuari actual
     * @return Tots els noms de les playlists
     */
    ArrayList<String> getPlaylists();
    /**
     * Obtè les cançons del usuari i les públiques
     * @return Cançons que l'usuari loguejat té acces
     */
    ArrayList<Song> getUserSongs();

    /**
     * Elimina la canço sol·licitada
     * @param song Canço a eliminar
     */
    void deleteSong(Song song);
}
