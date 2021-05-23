package view;

import entities.Song;

/**
 * Notifies song related actions, such as deletions and insertions
 */
public interface SongsMenuNotifier {

    /**
     * The song couldn't be deleted
     * @param song Song which we tried to delete
     */
    void unableToDeleteSong(Song song);

    /**
     * The song was deleted
     * @param song Song which we tried to delete
     */
    void songDeleted(Song song);

    /**
     * The song couldn't be added
     * @param song Song which we tried to add
     */
    void unableToAddSong(Song song);

    /**
     * The song was added
     * @param song Song which we tried to add
     */
    void songAdded(Song song);
}
