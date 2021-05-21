package persistance;

import entities.Note;
import entities.SongNote;
import entities.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    private boolean addSongGivenId(int id, Song song) {
        try {
            int songId;
            synchronized (this) {
                if (song.getDate() != null) {
                    // song with date:
                    if (this.ddbb.runSentence("INSERT INTO Songs(public, name, date, author, tick_length) VALUES (?,?,?,?,?);",
                            song.getPublic(), song.getName(), song.getDate(), id, song.getTickLength()) == 0) return false;
                }
                else {
                    // song without date:
                    if (this.ddbb.runSentence("INSERT INTO Songs(public, name, date, author, tick_length) VALUES (?,?,CURDATE(),?,?);",
                            song.getPublic(), song.getName(), id, song.getTickLength()) == 0) return false;
                }

                // obté l'últim ID insertat (el de Songs)
                ResultSet rs = this.ddbb.getSentence("SELECT LAST_INSERT_ID();");
                if (!rs.next()) return false;
                songId = rs.getInt(1);
            }

            // v1.0
            /*for (SongNote sn : song.getNotes()) {
                // hi han cançons que començen/acaben 2 tecles idéntiques al mateix moment (?); ignorem aquestes
                this.ddbb.runSentence("INSERT IGNORE INTO SongNotes(note, tick, pressed, song, velocity, octave) VALUES (?,?,?,?,?,?);",
                        sn.getNote().toString().replaceAll("X$", "#"), sn.getTick(), sn.isPressed(), songId, sn.getVelocity(), sn.getOctave());
            }*/

            // v2.0 aka. SQL Injection
            StringBuilder sb = new StringBuilder();
            // hi han cançons que començen/acaben 2 tecles idéntiques al mateix moment (?); ignorem aquestes
            sb.append("INSERT IGNORE INTO SongNotes(note, tick, pressed, song, velocity, octave) VALUES ");
            for (SongNote sn : song.getNotes()) {
                sb.append("('");
                sb.append(sn.getNote().toString().replaceAll("X$", "#"));
                sb.append("',");
                sb.append(sn.getTick());
                sb.append(',');
                sb.append(sn.isPressed());
                sb.append(',');
                sb.append(songId);
                sb.append(',');
                sb.append(sn.getVelocity());
                sb.append(',');
                sb.append(sn.getOctave());
                sb.append("),");
            }
            sb.setLength(sb.length()-1); // eliminem la ',' final
            sb.append(';');
            if (song.getNotes().size() > 0) this.ddbb.runSentence(sb.toString());

            return true;
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
    public boolean deleteUserSongs(String user) {
        try {
            this.ddbb.runSentence("DELETE SongNotes FROM SongNotes JOIN Songs ON Songs.id = SongNotes.song JOIN Users ON Users.id = Songs.author WHERE Users.username = ?;", user);
            this.ddbb.runSentence("DELETE Songs FROM Songs JOIN Users ON Users.id = Songs.author WHERE Users.username = ?;", user);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean isAuthor(Song song, String name) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT COUNT(*) FROM Songs JOIN Users ON Users.id = Songs.author JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE name = ? AND username = ? AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return false;

            return rs.getInt(1)>0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSong(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Songs.id,public,tick_length FROM Songs JOIN Users ON Users.id = Songs.author WHERE name = ? AND username = ? AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return false;

            song.setPublic(rs.getBoolean(2));
            song.setTickLength(rs.getLong(3));
            song.setScore(0); // TODO

            return (this.updateSongNotes(song, rs.getInt(1)));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean updateSongNotes(Song song, int id) {
        try {
            ResultSet songNotes = this.ddbb.getSentence("SELECT tick, pressed, velocity, octave, note FROM SongNotes WHERE song = ? ORDER BY tick ASC;",
                    id);

            while (songNotes.next()) {
                song.addNote(new SongNote(songNotes.getLong(1), songNotes.getBoolean(2), songNotes.getByte(3), songNotes.getByte(4), Note.valueOf(songNotes.getString(5).replaceAll("#$", "X"))));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Song> getAccessibleSongs(String loggedUser) {
        try {
            // TODO null score?
            ResultSet rs = this.ddbb.getSentence("SELECT Songs.name, Author.username, Songs.date, COALESCE(AVG(Ranking.points), 0) AS ranking, (MAX(SN.tick)*Songs.tick_length)/(1000 * 1000) AS duration FROM Songs JOIN Users Author ON Songs.author = Author.id JOIN SongNotes SN on Songs.id = SN.song LEFT JOIN Ranking ON Songs.id = Ranking.song WHERE Songs.author = (SELECT Users.id FROM Users JOIN RegisteredUsers RU ON RU.id = Users.id WHERE Users.username = ?) OR Songs.public = true GROUP BY Songs.id;",
                    loggedUser);

            ArrayList<Song> r = new ArrayList<>();
            while (rs.next()) r.add(new Song(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getFloat(4), rs.getDouble(5)));
            return r;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Integer getSongId(Song song) {
        try {
            ResultSet rs;
            if (song.getDate() != null) {
                rs = this.ddbb.getSentence("SELECT Songs.id FROM Songs JOIN Users ON Users.id = Songs.author WHERE name = ? AND username = ? AND date = ?;",
                        song.getName(), song.getArtist(), song.getDate());
            }
            else {
                rs = this.ddbb.getSentence("SELECT Songs.id FROM Songs JOIN Users ON Users.id = Songs.author WHERE name = ? AND username = ? AND date = CURDATE();",
                        song.getName(), song.getArtist());
            }

            if (!rs.next()) return null; // no hi ha coincidencies
            return rs.getInt(1);
        } catch (SQLException ex) {
            return null;
        }
    }
}
