package view;

/**
 * Interfaced used to notify about the results of log in and register
 */
public interface LogInNotifier {

    /**
     * The credentials specified don't match up with a user in the database
     */
    void wrongLogin();

    /**
     * The user could be created successfully
     */
    void userCreated();

    /**
     * The user could not be registered
     */
    void wrongCreation();
}
