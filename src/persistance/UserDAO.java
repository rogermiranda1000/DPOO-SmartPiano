package persistance;

import entities.User;

/**
 * Interface used for accessing the users
 */
public interface UserDAO {
    /* REGISTERED USERS */

    /**
     * Adds a registered user to the database
     * @param user User to add
     * @param password Password of the new user
     * @return True if the operation was successful
     */
    boolean addUser(User user, String password);

    /**
     * Deletes a user from the database if the password matches
     * @param user Username of the user
     * @param password Potential password of the user
     * @return True if the operation was successful
     */
    boolean deleteUser(User user, String password);

    /**
     * Checks if a user (real or virtual) exists in the database
     * @param nick Username of the user to look for
     * @return True if the operation was successful
     */
    Boolean existsUser(String nick);

    /* LOGGED USERS */

    /**
     * Returns a user in the database if the username and password matches
     * @param nick Username of the user to search for
     * @param password potential password of the user
     * @return True if the user exists and matches the password
     */
    User getUser(String nick, String password);

    /* VIRTUAL USERS */

    /**
     * Adds a virtual user to the database
     * @param nick Username of the user
     * @return True if the operation was successful
     */
    boolean addVirtualUser(String nick);

    /**
     * Checks if a virtual user exists
     * @param nick Username if the user to look for
     * @return True if it exists and no error happened
     */
    Boolean existsVirtualUser(String nick);
}
