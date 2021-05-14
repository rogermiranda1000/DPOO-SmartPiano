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
        return getStatistics("SELECT COUNT(l.song) AS times, DAY(l.date) AS day, HOUR(l.date) AS hour, HOUR(NOW()) AS hour_now FROM Listen AS l WHERE DATE_SUB(NOW(),INTERVAL 23 HOUR) < l.date GROUP BY day, hour ORDER BY day, hour ASC;");
    }

    public int[] getTimeStatistics() {
        return getStatistics("SELECT SUM(l.seconds_listened) AS seconds, DAY(l.date) AS day, HOUR(l.date) AS hour, HOUR(NOW()) AS hour_now FROM Listen AS l WHERE DATE_SUB(NOW(),INTERVAL 23 HOUR) < l.date GROUP BY day, hour ORDER BY day, hour ASC;");
    }

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
}
