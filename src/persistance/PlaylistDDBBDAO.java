package persistance;

import entities.List;
import entities.Song;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDDBBDAO implements PlaylistDAO {
    private final DDBBAccess ddbb;

    public PlaylistDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

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

    @Override
    public Boolean existsPlaylist(List list) {
        return this.getPlaylistId(list) != null;
    }

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

    @Override
    public boolean addSongPlaylist(List list, Song song) {
        Integer id = this.getPlaylistId(list);
        if (id == null) return false;
        return this.addSongPlaylist(id, song);
    }

    private boolean addSongPlaylist(int id, Song song) {
        try {
            return (this.ddbb.runSentence("INSERT INTO ListSongs(list, song) VALUES (?, (SELECT Songs.id FROM Songs JOIN Users ON Users.id = Songs.author WHERE Users.username = ? AND Songs.name = ? AND Songs.date = ?) );",
                    id, song.getArtist(), song.getName(), song.getDate()) > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeSongPlaylist(List list, Song song) {
        Integer id = this.getPlaylistId(list);
        if (id == null) return false;
        return this.removeSongPlaylist(id, song);
    }

    /**
     * Remove the relations between Lists and one Songs
     * Important: call before removing a Song
     * @param song Song to remove from the list (if exists)
     * @return If everythins is OK (true), or something happened (false)
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
     * Remove one relation between Lists and one Songs
     * @param id List ID from the DDBB
     * @param song Song to remove from the list
     * @return If the song was deleted (true), or something happened (false)
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
