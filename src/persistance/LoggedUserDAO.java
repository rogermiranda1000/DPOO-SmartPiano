package persistance;

import entities.User;

public interface LoggedUserDAO {
    User getUser(String nick, String password);
}
