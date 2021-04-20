package model;

import business.UserDAO;
import ddbb.DDBBAccess;
import entities.User;

import java.sql.SQLException;

public class UserDDBBDAO implements UserDAO {
    private final DDBBAccess ddbb;

    public UserDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addUser(User user, String password) {
        try {
            return ddbb.runSentence("INSERT INTO Users(name, email, password, octave_mode, volume_piano, volume_song) VALUES (?,?,MD5(?),'Single',1,1);",
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
    public User getUser(String nick, String password) {
        return null;
    }
}
