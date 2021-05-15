package persistance;

import entities.Note;
import entities.SongNote;
import entities.Song;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDDBBDAO implements SongDAO {
    private final DDBBAccess ddbb;

    public SongDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addVirtualSong(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Users.id FROM Users JOIN VirtualUsers ON Users.id = VirtualUsers.id WHERE username = ?;",
                    song.getArtist());

            if (!rs.next()) return false; // no hi ha coincidencies
            return addSongGivenId(rs.getInt(1), song);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean addSong(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ?;",
                    song.getArtist());

            if (!rs.next()) return false; // no hi ha coincidencies
            return addSongGivenId(rs.getInt(1), song);
        } catch (SQLException ex) {
            return false;
        }
    }

    private synchronized boolean addSongGivenId(int id, Song song) {
        try {
            if (this.ddbb.runSentence("INSERT INTO Songs(public, name, date, author, tick_length) VALUES (?,?,?,?,?);",
                    song.getPublic(), song.getName(), song.getDate() == null ? "CURRENT_DATE()" : song.getDate(), id, song.getTickLength()) > 0) {
                // obté l'últim ID insertat (el de Songs)
                ResultSet rs = this.ddbb.getSentence("SELECT LAST_INSERT_ID();");
                if (!rs.next()) return false;
                int songId = rs.getInt(1);

                for (SongNote sn : song.getNotes()) {
                    // hi han cançons que començen/acaben 2 tecles idéntiques al mateix moment (?); ignorem aquestes
                    this.ddbb.runSentence("INSERT IGNORE INTO SongNotes(note, tick, pressed, song, velocity, octave) VALUES (?,?,?,?,?,?);",
                            sn.getNote().toString().replaceAll("X$", "#"), sn.getTick(), sn.isPressed(), songId, sn.getVelocity(), sn.getOctave());
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

    @Override
    public boolean updateSong(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Songs.id,public,tick_length FROM Songs JOIN Users ON Users.id = Songs.author WHERE name = ? AND username = ? AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return false;

            int songId = rs.getInt(1);
            song.setPublic(rs.getBoolean(2));
            song.setTickLength(rs.getLong(3));

            ResultSet songNotes = this.ddbb.getSentence("SELECT tick, pressed, velocity, octave, note FROM SongNotes WHERE song = ?;",
                    songId);

            while (songNotes.next()) {
                song.addNote(new SongNote(songNotes.getLong(1), songNotes.getBoolean(2), songNotes.getByte(3), songNotes.getByte(4), Note.valueOf(songNotes.getString(5).replaceAll("#$", "X"))));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Integer getSongId(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Songs.id FROM Songs JOIN Users ON Users.id = Songs.author WHERE name = ? AND username = ? AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return null; // no hi ha coincidencies
            return rs.getInt(1);
        } catch (SQLException ex) {
            return null;
        }
    }
}
