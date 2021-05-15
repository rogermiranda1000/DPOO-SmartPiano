package persistance;

import entities.List;
import entities.Song;
import entities.User;

import java.util.ArrayList;

public interface PlaylistDAO {
    Boolean existsPlaylist(List list);
    boolean createPlaylist(List list);
    boolean removePlaylist(List list);

    boolean addSongPlaylist(List list, Song song);
    boolean removeSongPlaylist(List list, Song song);

    /**
     * Retorna totes les Playlist d'un usuari
     * Atenció: les songs de dins la playlist contenen només la informació necessaria per trobar-les (nom, autor, creació); no tenen tecles
     * @param user Usuari a consultar les playlist
     * @return Playlist del usuari
     */
    ArrayList<List> getPlaylists(User user);
}
