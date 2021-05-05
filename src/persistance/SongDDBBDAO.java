package persistance;

import entities.SongNote;
import model.SongDAO;
import entities.Song;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDDBBDAO implements SongDAO {
    private final UserDDBBDAO users;
    private final DDBBAccess ddbb;

    public SongDDBBDAO(DDBBAccess ddbb, UserDDBBDAO users) {
        this.ddbb = ddbb;
        this.users = users;
    }

    @Override
    public boolean addSong(Song song) {
        try {
            Integer userId = this.users.getVirtualUserId(song.getArtist());
            if (userId == null) {
                // l'usuari no existeix
                if (!this.users.addVirtualUser(song.getArtist())) return false; // afegeix
                userId = this.users.getVirtualUserId(song.getArtist()); // obtè ID
                if (userId == null) return false;
            }

            if (this.ddbb.runSentence("INSERT INTO Songs(public, name, date, author, tick_length) VALUES (?,?,?,?,?);",
                    song.getPublic(), song.getName(), song.getDate(), userId, song.getTickLength()) > 0) {
                Integer songId = this.getSongId(song);
                if (songId == null) return false;

                for (SongNote sn : song.getNotes()) {
                    // hi han cançons que començen/acaben 2 tecles idéntiques al mateix moment (?); ignorem aquestes
                    this.ddbb.runSentence("INSERT IGNORE INTO SongNotes(note, tick, pressed, song, velocity) VALUES (?,?,?,?,?);",
                            sn.getNote().toString().replaceAll("X$", "#"), sn.getTick(), sn.isPressed(), songId, sn.getVelocity());
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
        Integer id = this.getSongId(song);
        if (id == null) return false;
        try {
            this.ddbb.runSentence("DELETE FROM SongNotes WHERE song = ?;", id);
            return (this.ddbb.runSentence("DELETE FROM Songs WHERE id = ?;", id) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean existsSong(Song song) {
        return (this.getSongId(song) != null);
    }

    private Integer getSongId(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT id FROM Songs WHERE name = ? AND author IN (SELECT id FROM Users WHERE username = ?) AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return null; // no hi ha coincidencies
            return rs.getInt(1);
        } catch (SQLException ex) {
            return null;
        }
    }
}
