package model;

import business.UserDAO;
import ddbb.DDBBAccess;
import entities.User;

public class UserDDBBDAO implements UserDAO {
    private final DDBBAccess ddbb;

    public UserDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    @Override
    public boolean addUser(User user, String password) {
        return false;
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
