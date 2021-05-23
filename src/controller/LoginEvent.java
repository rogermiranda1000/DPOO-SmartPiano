package controller;

/**
 * Interface tasked with login and register events
 */
public interface LoginEvent {

    /**
     * Requests a user's login with the given (username/email - password) combination
     * @param user Username or email input by the user
     * @param password Password input by the user
     */
    void requestLogin(String user, String password);

    /**
     * Requests an account creation with the given username, email and password
     * @param user Username input by the user
     * @param email Email input by the user
     * @param password Password input by the user
     */
    void requestRegister(String user, String email, String password);
}
