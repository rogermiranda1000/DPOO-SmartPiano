package persistance;

import entities.Song;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Class that manages database operations regarding song plays and statistics creation
 */
public class StatisticsDDBBDAO implements StatisticsDAO {

    /**
     * Object used to access the database
     */
    private final DDBBAccess ddbb;

    /**
     * Initiates the class, saving the DDBBAccess
     * @param ddbb Object used to access the database
     */
    public StatisticsDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    /**
     * Returns the evolution of the plays per hour
     * @return An array of values representing the number of total plays for each hour in the last 24 hours,
     * the last value is the current hour
     */
    @Override
    public int[] getSongStatistics() {
        return getStatistics("SELECT COUNT(l.song) AS times, DAY(l.date) AS day, HOUR(l.date) AS hour, HOUR(NOW()) AS hour_now FROM Listen AS l WHERE DATE_SUB(NOW(),INTERVAL 23 HOUR) < l.date GROUP BY day, hour ORDER BY day, hour ASC;");
    }

    /**
     * Returns the evolution of the time listened per hour
     * @return An array of values representing the number of seconds listened for each hour in the last 24 hours,
     * the last value is the current hour
     */
    @Override
    public int[] getTimeStatistics() {
        return getStatistics("SELECT SUM(l.seconds_listened) AS seconds, DAY(l.date) AS day, HOUR(l.date) AS hour, HOUR(NOW()) AS hour_now FROM Listen AS l WHERE DATE_SUB(NOW(),INTERVAL 23 HOUR) < l.date GROUP BY day, hour ORDER BY day, hour ASC;");
    }

    /**
     * Returns the values regarding a certain query (statistics)
     * @param query Query to get the statistics from
     * @return Values of the query
     */
    private int[] getStatistics(String query) {
        try {
            ResultSet rs = this.ddbb.getSentence(query);
            ResultSet h = this.ddbb.getSentence("SELECT HOUR(NOW())");
            int[] r = new int[25];
            Arrays.fill(r, 0);
            if (!h.next()) {
                // ddbb falla?
                return null;
            }
            int now = h.getInt(1);
            r[24] = now;    // El valor 25 dona l'hora actual
            if (!rs.next()) {
                // No hi ha reproduccions
                return r;
            }

            int i = 0;
            int target;
            int hour = (r[24]+1+24)%24;
            do {
                target = rs.getInt(3);
                while (hour != target) {
                    hour = (hour+1)%24;
                    i++;
                }
                hour = (hour+1)%24;
                r[i++] = rs.getInt(1);

            } while (rs.next() & i < 24);

            return r;

        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Adds a play of a certain length to the specified song
     * @param nick Username of the logged user
     * @param song Song to add the play to
     * @param seconds Seconds this play lasted for
     * @return True if the operation was successful
     */
    @Override
    public boolean addListen(String nick, Song song, int seconds) {
        try {
            return this.ddbb.runSentence("INSERT INTO Listen (user, song, seconds_listened) VALUES ( " +
                            "(SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ?)," +
                            "(SELECT Songs.id FROM Songs WHERE Songs.name = ? AND Songs.date = ? AND (SELECT Users.id FROM Users WHERE Users.username = ?))," +
                            "?);",
                    nick, song.getName(), song.getDate(), song.getArtist(), seconds)
                    > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Returns the top 5 most popular songs
     * @param plays Array which will be filled with the plays of each song, from most popular to least
     * @return Array of songs, from most popular to least
     */
    @Override
    public Song[] getTop5(int[] plays) {
        try {
            if (plays == null || plays.length != 5) {
                throw new InvalidParameterException();
            }
            Arrays.fill(plays, -1);
            Song[] top = new Song[5];
            ResultSet rs = this.ddbb.getSentence("SELECT COUNT(*) AS plays, s.name, (SELECT Users.username FROM Users WHERE Users.id = s.author), s.date FROM Songs AS s JOIN Listen AS l ON s.id = l.song GROUP BY s.id ORDER BY plays DESC LIMIT 5;");
            for (int i = 0; i < 5 && rs.next(); i++) {
                plays[i] = rs.getInt(1);
                top[i] = new Song(rs.getString(2), rs.getString(3), rs.getDate(4));
            }

            return top;
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Delete the user's statistics
     * @param s User name
     * @return If the statistics from the player was deleted (true) or something happened (false)
     */
    @Override
    public boolean deletePlayerStatistics(String s) {
        try {
            this.ddbb.runSentence("DELETE Listen FROM Listen JOIN Users ON Users.id = Listen.user WHERE Users.username = ?;",
                    s);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Deletes the plays from a specified song
     * @param s Song to delete the plays from
     * @return True if the operation was successful
     */
    @Override
    public boolean deleteStatistics(Song s) {
        try {
            this.ddbb.runSentence("DELETE Listen FROM Listen JOIN Songs ON Listen.song = Songs.id JOIN Users ON Users.id = Songs.author WHERE Users.username = ? AND Songs.name = ? AND Songs.date = ?;",
                    s.getArtist(), s.getName(), s.getDate());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
