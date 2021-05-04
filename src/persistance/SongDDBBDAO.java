package persistance;

import entities.SongNote;
import model.SongDAO;
import entities.Song;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDDBBDAO implements SongDAO {
    private final DDBBAccess ddbb;

    public SongDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addSong(Song song) {
        try {
            if (this.ddbb.runSentence("INSERT INTO Songs(public, name, tick_length) VALUES (?,?,?);",
                    song.getPublic(), song.getName(), /*song.getArtist(),*/ song.getTickLength()) > 0) { // TODO artist referencia a user?
                this.getSongId(song);
                for (SongNote sn : song.getNotes()) {
                    if (this.ddbb.runSentence("INSERT INTO SongNote(note, tick, pressed, song, velocity) VALUES (?,?,?,?,?);",
                            sn.getNote().toString(), sn.getTick(), sn.isPressed(), song.getId(), sn.getVelocity()) == 0) return false;
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSong(Song song) {
        return false;
    }

    @Override
    public boolean existsSong(Song song) {
        try {
            ResultSet rs;
            if (song.getId() == null) rs = this.ddbb.getSentence("SELECT COUNT(*) FROM Songs WHERE name = ? AND author = (SELECT id FROM Users WHERE username = ?) AND date = ?;", song.getName(), song.getArtist(), song.getDate());
            else rs = this.ddbb.getSentence("SELECT COUNT(*) FROM Songs WHERE id = ?;", song.getId());

            if (!rs.next()) return false; // no hi ha coincidencies
            return (rs.getInt(1) > 0);
        } catch (SQLException throwables) {
            return false;
        }
    }

    private void getSongId(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT id FROM Songs WHERE name = ? AND author = (SELECT id FROM Users WHERE username = ?) AND date = ?;", song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return; // no hi ha coincidencies
            song.setId(rs.getInt(1));
        } catch (SQLException throwables) { }
    }
}
