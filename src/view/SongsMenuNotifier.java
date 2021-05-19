package view;

import entities.Song;

/**
 * Notificadors del menu Songs
 */
public interface SongsMenuNotifier {
    void unableToDeleteSong(Song song);
    void songDeleted(Song song);

    void unableToAddSong(Song song);
    void songAdded(Song song);
}
