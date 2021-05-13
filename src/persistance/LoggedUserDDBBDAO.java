package persistance;

import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggedUserDDBBDAO implements LoggedUserDAO {
    private final DDBBAccess ddbb;

    public LoggedUserDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
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


}
