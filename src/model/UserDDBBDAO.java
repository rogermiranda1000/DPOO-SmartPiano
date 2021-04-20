package model;

import business.UserDAO;
import ddbb.DDBBAccess;
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
            return ddbb.runSentence("INSERT INTO Users(username, email, password, octave_mode, volume_piano, volume_song) VALUES (?,?,MD5(?),'Single',1,1);",
                    user.getName(), user.getEmail(), password) > 0;
        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user, String password) {
        return false;
    }

    @Override
    public User getUser(String match, String password) {
        try {
            ResultSet rs = ddbb.getSentence("SELECT username,email FROM Users WHERE (username = ? OR email = ?) AND password=MD5(?);",
                    match, match, password);
            if (!rs.next()) return null; // no hi ha coincidencies
            return new User(rs.getString(1), rs.getString(2));
        } catch (SQLException throwables) {
            return null;
        }
    }
}
