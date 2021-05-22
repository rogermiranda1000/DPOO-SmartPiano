package persistance;

import entities.Note;
import entities.SongNote;
import entities.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that manages database operations regarding songs
 */
public class SongDDBBDAO implements SongDAO {

    /**
     * Object used to access the database
     */
    private final DDBBAccess ddbb;

    /**
     * Initiates the class, saving the DDBBAccess
     * @param ddbb Object used to access the database
     */
    public SongDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    /**
     * Adds a song with a virtual creator to the database
     * @param song Song to be added
     * @return True if the operation was successful
     */
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

    /**
     * Adds a song to the database
     * @param song Song to add
     * @return True if the operation was successful
     */
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

    /**
     * Adds a song to the database given an internal id of the author
     * @param id Database id of the author
     * @param song Song to add
     * @return True if the operation was successful
     */
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

    /**
     * Deletes a song from the database
     * @param song Song to be deleted
     * @return True if the operation was successful
     */
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

    /**
     * Checks if the specified song exists in the database
     * @param song Song to look for
     * @return True if the song exists
     */
    @Override
    public boolean existsSong(Song song) {
        return (this.getSongId(song) != null);
    }

    /**
     * Deletes all the songs created by the specified user
     * @param user User to delete the songs from
     * @return True if the operation was successful
     */
    @Override
    public boolean deleteUserSongs(String user) {
        try {
            this.ddbb.runSentence("DELETE SongNotes FROM SongNotes JOIN Songs ON Songs.id = SongNotes.song JOIN Users ON Users.id = Songs.author JOIN RegisteredUsers ON RegisteredUsers.id = Users.id WHERE Users.username = ?;", user);
            this.ddbb.runSentence("DELETE Songs FROM Songs JOIN Users ON Users.id = Songs.author JOIN RegisteredUsers ON RegisteredUsers.id = Users.id WHERE Users.username = ?;", user);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Checks if the registered user is the author of the song
     * @param song Song to check
     * @param name Registered user to check
     * @return If it's the author (true), or not (false); null if something went wrong
     */
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

    /**
     * Given a song with basic attributes (name, date, author), obtains the rest of the information
     * @param song Song to fill
     * @return True if the operation was successful
     */
    @Override
    public boolean updateSong(Song song) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Songs.id,public,tick_length FROM Songs JOIN Users ON Users.id = Songs.author WHERE name = ? AND username = ? AND date = ?;",
                    song.getName(), song.getArtist(), song.getDate());

            if (!rs.next()) return false;

            song.setPublic(rs.getBoolean(2));
            song.setTickLength(rs.getLong(3));

            return (this.updateSongNotes(song, rs.getInt(1)));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Fills a song with its notes
     * @param song Song to fill
     * @param id Internal id of the song
     * @return True if the operation was successful
     */
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

    /**
     * Obtains all the songs from the user and the public ones,
     * /!\ Only the basic information of every song is loaded (there are no notes)
     * @param loggedUser User logged in the app
     * @return The songs the user should be able to see
     */
    @Override
    public ArrayList<Song> getAccessibleSongs(String loggedUser) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Songs.name, Author.username, Songs.date, (MAX(SN.tick)*Songs.tick_length)/(1000 * 1000) AS duration " +
                            "FROM Songs JOIN Users AS Author ON Songs.author = Author.id" +
                            "    JOIN SongNotes SN ON Songs.id = SN.song" +
                            "    LEFT JOIN RegisteredUsers AS RegisteredAuthor ON RegisteredAuthor.id = Author.id" +
                            "    LEFT JOIN Listen ON Songs.id = Listen.song " +
                            "WHERE (RegisteredAuthor.id IS NOT NULL AND Author.username = ?) OR Songs.public = true " +
                            "GROUP BY Songs.id " +
                            "ORDER BY COUNT(DISTINCT Listen.date) DESC, MAX(Author.username) ASC, MAX(Songs.name) ASC;",
                    loggedUser);

            ArrayList<Song> r = new ArrayList<>();
            while (rs.next()) r.add(new Song(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getDouble(4)));
            return r;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the internal Id of the specified song
     * @param song Song to get the id from
     * @return Internal database id of the song
     */
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
