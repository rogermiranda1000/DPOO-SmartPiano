package persistance;

import entities.List;
import entities.Song;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that manages database operations regarding a user's configuration
 */
public class PlaylistDDBBDAO implements PlaylistDAO {

    /**
     * Object used to access the database
     */
    private final DDBBAccess ddbb;

    /**
     * Initiates the class, saving the DDBBAccess
     * @param ddbb Object used to access the database
     */
    public PlaylistDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    /**
     * Creates a playlist in the database
     * @param list Playlist to create
     * @return True if the operation was successful
     */
    @Override
    public boolean createPlaylist(List list) {
        try {
            if (this.ddbb.runSentence("INSERT INTO Lists(name, author) VALUES (?, (SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ?) );",
                    list.getName(), list.getCreator()) > 0) {
                // obté l'últim ID insertat (el de Songs)
                ResultSet rs = this.ddbb.getSentence("SELECT LAST_INSERT_ID();");
                if (!rs.next()) return false;
                int listId = rs.getInt(1);

                for (Song song : list.getSongs()) {
                    if (!this.addSongPlaylist(listId, song)) return false;
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if a playlist exists in the database
     * @param list Playlist to look for
     * @return True if it exists, false if it doesn't and null if there was an error
     */
    @Override
    public Boolean existsPlaylist(List list) {
        return this.getPlaylistId(list) != null;
    }

    /**
     * Returns the id of a playlist
     * @param list Playlist to get the id from
     * @return The internal database id from the playlist
     */
    private Integer getPlaylistId(List list) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT id FROM Lists WHERE author = (SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ?) AND name = ?;",
                    list.getCreator(), list.getName());
            if (!rs.next()) return null;
            return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Removes a playlist from the database
     * @param list Playlist to remove
     * @return True if the operation was successful
     */
    @Override
    public boolean removePlaylist(List list) {
        try {
            Integer id = this.getPlaylistId(list);
            if (id == null) return false;

            this.ddbb.runSentence("DELETE FROM ListSongs WHERE list = ?;", id);
            return (this.ddbb.runSentence("DELETE FROM Lists WHERE id = ?;", id) > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a song to a playlist in the database
     * @param list Playlist to add the song to
     * @param song Song to add
     * @return True if the operation was successful
     */
    @Override
    public boolean addSongPlaylist(List list, Song song) {
        Integer id = this.getPlaylistId(list);
        if (id == null) return false;
        return this.addSongPlaylist(id, song);
    }

    /**
     * Adds a song to a playlist in the database
     * @param id Id of the playlist to add the song to
     * @param song Song to add
     * @return True if the operation was successful
     */
    private boolean addSongPlaylist(int id, Song song) {
        try {
            return (this.ddbb.runSentence("INSERT INTO ListSongs(list, song) VALUES (?, (SELECT Songs.id FROM Songs JOIN Users ON Users.id = Songs.author WHERE Users.username = ? AND Songs.name = ? AND Songs.date = ?) );",
                    id, song.getArtist(), song.getName(), song.getDate()) > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Removes a song from a playlist in the database
     * @param list Playlist to remove a song from
     * @param song Song to remove
     * @return True if the operation was successful
     */
    @Override
    public boolean removeSongPlaylist(List list, Song song) {
        Integer id = this.getPlaylistId(list);
        if (id == null) return false;
        return this.removeSongPlaylist(id, song);
    }

    /**
     * Removes the relation between a song and it's playlists
     * @param song Song to remove the connections from
     * @return True if the operation was successful
     */
    @Override
    public boolean removeSongAllPlaylists(Song song) {
        try {
            this.ddbb.runSentence("DELETE FROM ListSongs WHERE song = (SELECT Songs.id FROM Songs JOIN Users ON Songs.author = Users.id WHERE username = ? AND name = ? AND date = ?);",
                    song.getArtist(), song.getName(), song.getDate());
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Removes one relation between Lists and one Song
     * @param id Playlist ID from the DDBB
     * @param song Song to remove from the list
     * @return True if the relation was deleted successfully
     */
    private boolean removeSongPlaylist(int id, Song song) {
        try {
            return (this.ddbb.runSentence("DELETE FROM ListSongs WHERE list = ? AND song = (SELECT Songs.id FROM Songs JOIN Users ON Songs.author = Users.id WHERE username = ? AND name = ? AND date = ?);",
                    id, song.getArtist(), song.getName(), song.getDate()) > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Returns all the playlists from a user
     * Attention: The songs inside the playlist only contain the necessary information to find them (name, author, creation date); They don't have notes
     * @param user User that owns the playlists
     * @return The user's playlists
     */
    @Override
    public ArrayList<List> getPlaylists(User user) {
        ArrayList<List> retorn = new ArrayList<>();

        try {
            ResultSet playlists = this.ddbb.getSentence("SELECT name,id FROM Lists WHERE author = (SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ?);",
                    user.getName());

            while (playlists.next()) {
                List current = new List(playlists.getString(1), user.getName());
                int listId = playlists.getInt(2);

                ResultSet songs = this.ddbb.getSentence("SELECT Songs.name, Songs.date, Users.username FROM ListSongs JOIN Songs ON ListSongs.song = Songs.id JOIN Users ON Songs.author = Users.id WHERE ListSongs.list = (?);",
                        listId);

                while (songs.next()) current.addSong(new Song(songs.getString(1), songs.getString(3), songs.getDate(2)));

                retorn.add(current);
            }

            return retorn;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
