package persistance;

import entities.List;
import entities.Song;

public interface PlaylistDAO {
    boolean createPlaylist(List list);
    boolean removePlaylist(List list);

    boolean addSongPlaylist(List list, Song song);
    boolean removeSongPlaylist(List list, Song song);


}
