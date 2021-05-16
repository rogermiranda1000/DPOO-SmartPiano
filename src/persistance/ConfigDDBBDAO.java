package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDDBBDAO implements ConfigDAO{
    private final DDBBAccess ddbb;

    public ConfigDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean setVolumePiano(String nick, float value) {
        try {
            return (this.ddbb.runSentence("UPDATE RegisteredUsers SET volume_piano = ? WHERE(id = (SELECT Users.id FROM Users WHERE Users.username = ?));", value, nick) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Float getVolumePiano(String nick) {
        entities.Config config = this.getConfig(nick);
        if (config == null) return null;
        return config.getVolumePiano();
    }

    @Override
    public boolean setVolumeSong(String nick, float value) {
        try {
            return (this.ddbb.runSentence("UPDATE RegisteredUsers SET volume_song = ? WHERE(id = (SELECT Users.id FROM Users WHERE Users.username = ?));", value, nick) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Float getVolumeSong(String nick) {
        entities.Config config = this.getConfig(nick);
        if (config == null) return null;
        return config.getVolumeSong();
    }

    private entities.Config getConfig(String nick) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT ru.volume_piano, ru.volume_song FROM RegisteredUsers AS ru JOIN Users AS u ON u.id = ru.id WHERE u.username = ?;", nick);
            if (!rs.next()) {
                return null;
            }
            return new entities.Config(rs.getFloat(1), rs.getFloat(2));
        } catch (SQLException ex) {
            return null;
        }
    }


}
