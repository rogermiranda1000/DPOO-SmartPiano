package persistance;

import business.SongDAO;
import entities.Song;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDDBBDAO implements SongDAO {
    private final DDBBAccess ddbb;

    public SongDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addSong(Song song) {
        return false;
    }

    @Override
    public boolean deleteSong(Song song) {
        return false;
    }

    @Override
    public boolean exists(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT COUNT(*) FROM Songs WHERE name = ? AND author = ? AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());
            if (!rs.next()) return false; // no hi ha coincidencies
            return (rs.getInt(1) > 0);
        } catch (SQLException throwables) {
            return false;
        }
    }
}
