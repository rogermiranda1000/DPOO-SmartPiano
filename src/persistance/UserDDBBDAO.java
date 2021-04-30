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
        try {
            return (this.ddbb.runSentence("INSERT INTO Users(username, email, password, octave_mode, volume_piano, volume_song) VALUES (?,?,MD5(?),'Single',1,1);",
                    user.getName(), user.getEmail(), password)) > 0;
        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user, String password) {
        try {
            return (this.ddbb.runSentence("DELETE FROM Users WHERE username = ? AND password=MD5(?);",
                    user.getName(), password)) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User getUser(String match, String password) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT username,email FROM Users WHERE (username = ? OR email = ?) AND password=MD5(?);",
                    match, match, password);
            if (!rs.next()) return null; // no hi ha coincidencies
            return new User(rs.getString(1), rs.getString(2));
        } catch (SQLException throwables) {
            return null;
        }
    }
}
