package persistance;

import model.UserDAO;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDDBBDAO implements UserDAO {
    private final DDBBAccess ddbb;

    public UserDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addUser(User user, String password) {
        Boolean found = this.existsUser(user.getName());
        if (found == null || found) return false; // nom duplicat
        try {
            Integer id = this.addUserAndGetId(user.getName());
            if (id == null) return false;
            return (this.ddbb.runSentence("INSERT INTO RegisteredUsers(id, email, password, octave_mode, volume_piano, volume_song) VALUES (?,?,MD5(?),'Single',1,1);",
                    id, user.getEmail(), password)) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean addVirtualUser(String nick) {
        try {
            Integer id = this.addUserAndGetId(nick);
            if (id == null) return false;
            return (this.ddbb.runSentence("INSERT INTO VirtualUsers(id) VALUES (?);", id)) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    private Integer addUserAndGetId(String nick) {
        try {
            if (this.ddbb.runSentence("INSERT INTO Users(username) VALUES (?);", nick) > 0) {
                ResultSet rs = this.ddbb.getSentence("SELECT MAX(id) FROM Users WHERE username = ?;",
                        nick);

                if (!rs.next()) return null; // no hi ha coincidencies
                return rs.getInt(1);
            }
        } catch (SQLException ignored) { }
        return null;
    }

    @Override
    public Boolean existsUser(String nick) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT COUNT(*) FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ?;",
                    nick);
            if (!rs.next()) return null; // no hi ha coincidencies
            return (rs.getInt(1) > 0);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean deleteUser(User user, String password) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ? AND password=MD5(?);",
                    user.getName(), password);
            if (!rs.next()) return false; // no hi ha coincidencies
            int id = rs.getInt(1);

            if (this.ddbb.runSentence("DELETE FROM Users WHERE id = ?;", id) == 0) return false;
            return (this.ddbb.runSentence("DELETE FROM RegisteredUsers WHERE id = ?;", id) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User getUser(String match, String password) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT username,email FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE (username = ? OR email = ?) AND password=MD5(?);",
                    match, match, password);
            if (!rs.next()) return null; // no hi ha coincidencies
            return new User(rs.getString(1), rs.getString(2));
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Integer getVirtualUserId(String nick) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Users.id FROM Users JOIN VirtualUsers ON Users.id = VirtualUsers.id WHERE username = ?;", nick);
            if (!rs.next()) return null; // no hi ha coincidencies
            return rs.getInt(1);
        } catch (SQLException ex) {
            return null;
        }
    }
}
