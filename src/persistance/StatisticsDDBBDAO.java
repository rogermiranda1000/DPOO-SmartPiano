package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class StatisticsDDBBDAO implements StatisticsDAO {
    private final DDBBAccess ddbb;

    public StatisticsDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    public int[] getSongStatistics() {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT COUNT(l.song) AS times, DAY(l.date) AS day, HOUR(l.date) AS hour, HOUR(NOW()) AS hour_now FROM Listen AS l WHERE DATEDIFF(NOW(),l.date) <= 1 GROUP BY day, hour ORDER BY day, hour ASC;");
            ResultSet h = this.ddbb.getSentence("SELECT HOUR(NOW())");
            int[] r = new int[25];
            Arrays.fill(r, 0);
            if (!h.next()) {
                // ddbb falla?
                return null;
            }
            r[24] = h.getInt(0);    // El valor 25 dona l'hora actual
            if (!rs.next()) {
                // No hi ha reproduccions
                return r;
            }

            int i = 0;
            int hour = (rs.getInt(2)-1+24)%24;
            do {
                int target = rs.getInt(1);
                while (target != hour) {
                    hour = (hour+1)%24;
                    i++;
                }
                r[i++] = rs.getInt(0);

            } while (rs.next());

            return r;

        } catch (SQLException ex) {
            return null;
        }
    }
    public int[] getTimeStatistics() {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT SUM(l.seconds_listened) AS seconds, DAY(l.date) AS day, HOUR(l.date) AS hour, HOUR(NOW()) AS hour_now FROM Listen AS l WHERE DATEDIFF(NOW(),l.date) <= 1 GROUP BY day, hour ORDER BY day, hour ASC;");
            ResultSet h = this.ddbb.getSentence("SELECT HOUR(NOW())");
            int[] r = new int[25];
            Arrays.fill(r, 0);
            if (!h.next()) {
                // ddbb falla?
                return null;
            }
            r[24] = h.getInt(0);    // El valor 25 dona l'hora actual
            if (!rs.next()) {
                // No hi ha reproduccions
                return r;
            }

            int i = 0;
            int hour = (rs.getInt(2)-1+24)%24;
            do {
                int target = rs.getInt(1);
                while (target != hour) {
                    hour = (hour+1)%24;
                    i++;
                }
                r[i++] = rs.getInt(0);

            } while (rs.next());

            return r;

        } catch (SQLException ex) {
            return null;
        }
    }
}
