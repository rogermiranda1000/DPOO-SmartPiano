package business;

import entities.User;

public interface UserDAO {
    boolean addUser(User user, String password);
    boolean deleteUser(User user, String password);
    User getUser(String nick, String password);
}
