package persistance;

import entities.Song;
import entities.User;

import java.util.ArrayList;

public interface SongDAO {
    boolean addSong(Song song);
    boolean addVirtualSong(Song song);
    boolean deleteSong(Song song);
    boolean existsSong(Song song);

    /**
     * Donada una canço amb la informació bàsica (nom, data, autor) obtè tota la demés informació
     * @param song Canço a omplenar
     * @return Si s'ha realitzat exitosament (true), o no (false)
     */
    boolean updateSong(Song song);

    /**
     * Obtè les cançons del usuari i les públiques
     * /!\ La informació de les cançons és la bàsica (no hi ha tecles)
     * @param loggedUser Usuari
     * @return Cançons visibles per l'usuari
     */
    ArrayList<Song> getAccessibleSongs(String loggedUser);
}