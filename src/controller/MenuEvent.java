package controller;

/**
 * Interface tasked with session exiting events
 */
public interface MenuEvent {

    /**
     * Makes the user log out and closes the session
     */
    void exitSession();
}
