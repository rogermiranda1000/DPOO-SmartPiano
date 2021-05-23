package persistance;

import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that manages database operations regarding registered and virtual users
 */
public class UserDDBBDAO implements UserDAO {

    /**
     * Object used to access the database
     */
    private final DDBBAccess ddbb;

    /**
     * Initiates the class, saving the DDBBAccess
     * @param ddbb Object used to access the database
     */
    public UserDDBBDAO(DDBBAccess ddbb) {
        this.ddbb = ddbb;
    }

    /**
     * Adds a registered user to the database
     * @param user User to add
     * @param password Password of the new user
     * @return True if the operation was successful
     */
    @Override
    public boolean addUser(User user, String password) {
        Boolean found = this.existsUser(user.getName());
        if (found == null || found) return false; // nom duplicat
        try {
            Integer id = this.addUserAndGetId(user.getName());
            if (id == null) return false;
            return (this.ddbb.runSentence("INSERT INTO RegisteredUsers(id, email, password, volume_piano, volume_song) VALUES (?,?,MD5(?),1,1);",
                    id, user.getEmail(), password)) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Adds a virtual user to the database
     * @param nick Username of the user
     * @return True if the operation was successful
     */
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

    /**
     * Adds a user and returns it's internal id
     * @param nick Username of the user
     * @return Internal id of the user
     */
    private synchronized Integer addUserAndGetId(String nick) {
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

    /**
     * Checks if a user (real or virtual) exists in the database
     * @param nick Username of the user to look for
     * @return True if the operation was successful
     */
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

    /**
     * Checks if a virtual user exists
     * @param nick Username if the user to look for
     * @return True if it exists and no error happened
     */
    @Override
    public Boolean existsVirtualUser(String nick) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT COUNT(*) FROM Users JOIN VirtualUsers ON Users.id = VirtualUsers.id WHERE username = ?;", nick);
            if (!rs.next()) return null; // no hi ha coincidencies
            return (rs.getInt(1) > 0);
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Deletes a user from the database if the password matches
     * @param user Username of the user
     * @param password Potential password of the user
     * @return True if the operation was successful
     */
    @Override
    public boolean deleteUser(User user, String password) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT Users.id FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE username = ? AND password=MD5(?);",
                    user.getName(), password);
            if (!rs.next()) return false; // no hi ha coincidencies
            int id = rs.getInt(1);

            if (this.ddbb.runSentence("DELETE FROM RegisteredUsers WHERE id = ?;", id) == 0) return false;
            return (this.ddbb.runSentence("DELETE FROM Users WHERE id = ?;", id) > 0);
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Returns a user in the database if the username and password matches
     * @param nick Username of the user to search for
     * @param password potential password of the user
     * @return True if the user exists and matches the password
     */
    @Override
    public User getUser(String nick, String password) {
        try {
            ResultSet rs = this.ddbb.getSentence("SELECT username,email FROM Users JOIN RegisteredUsers ON Users.id = RegisteredUsers.id WHERE (username = ? OR email = ?) AND password=MD5(?);",
                    nick, nick, password);
            if (!rs.next()) return null; // no hi ha coincidencies
            return new User(rs.getString(1), rs.getString(2));
        } catch (SQLException ex) {
            return null;
        }
    }
}
