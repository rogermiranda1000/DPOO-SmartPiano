package view;

public interface PlaylistMenuNotifier {
    void playlistCreated();
    void playlistNotCreated();

    void playlistDeleted();
    void playlistNotDeleted();

    void songDeletedFromPlaylist();
    void songNotDeletedFromPlaylist();
}
