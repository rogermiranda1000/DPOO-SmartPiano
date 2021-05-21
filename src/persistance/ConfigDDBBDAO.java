package persistance;

import entities.KeyboardConstants;
import entities.Note;

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
    public boolean setVolumeSong(String nick, float value) {
        try {
            return (this.ddbb.runSentence("UPDATE RegisteredUsers SET volume_song = ? WHERE(id = (SELECT Users.id FROM Users WHERE Users.username = ?));", value, nick) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public entities.Config getConfig(String nick) {
        try {
            ResultSet rs1 = this.ddbb.getSentence("SELECT ru.volume_piano, ru.volume_song FROM RegisteredUsers AS ru JOIN Users AS u ON u.id = ru.id WHERE u.username = ?;", nick);
            if (!rs1.next()) return null;

            entities.Config config = new entities.Config(rs1.getFloat(1), rs1.getFloat(2));
            ResultSet rs2 = this.ddbb.getSentence("SELECT pk.keyboard FROM PianoKeys AS pk JOIN RegisteredUsers ru ON pk.user = ru.id JOIN Users u on ru.id = u.id WHERE u.username = ? ORDER BY pk.octave ASC, pk.note ASC;", nick);

            char[] characters = new char[12 * KeyboardConstants.NUM_OCTAVES];
            for (int i = 0; i < characters.length & rs2.next(); i++) {
                characters[i] = rs2.getString(1).charAt(0);
            }
            config.setNoteBind(characters);
            return config;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean setConfig(String nick, char[] characters) {
        try {
            for (int i = 0; i < 12 * KeyboardConstants.NUM_OCTAVES; i++) {
                if (this.ddbb.runSentence("UPDATE PianoKeys SET keyboard = ? WHERE (user = (SELECT u.id FROM Users AS u JOIN RegisteredUsers AS ru ON u.id = ru.id WHERE u.username = ?) AND octave = ? AND note = ?);",
                        String.valueOf(characters[i]), nick, i/12 + 1, Note.getNote(i).name().replaceAll("X$", "#")) == 0) return false;
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteUserConfig(String nick) {
        try {
            return (this.ddbb.runSentence("DELETE PianoKeys FROM PianoKeys JOIN Users ON Users.id = PianoKeys.user WHERE Users.username = ?;", nick) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }
}
