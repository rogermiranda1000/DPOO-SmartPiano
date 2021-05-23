package view;

/**
 * Notifies playlist related actions, such as deletions and insertions
 */
public interface PlaylistMenuNotifier {

    /**
     * The new playlist was created
     */
    void playlistCreated();

    /**
     * The playlist couldn't be created
     */
    void playlistNotCreated();

    /**
     * The playlist was deleted
     */
    void playlistDeleted();

    /**
     * The playlist couldn't be deleted
     */
    void playlistNotDeleted();

    /**
     * The song was deleted from the playlist
     */
    void songDeletedFromPlaylist();

    /**
     * The song couldn't be deleted from the playlist
     */
    void songNotDeletedFromPlaylist();
}
