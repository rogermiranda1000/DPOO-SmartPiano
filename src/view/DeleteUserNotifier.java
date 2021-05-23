package view;

/**
 * Interface used for actions regarding user deletion
 */
public interface DeleteUserNotifier {

    /**
     * The user has input the correct password to delete their account
     */
    void userDeleted();

    /**
     * The user has input an incorrect password when trying to delete their account
     */
    void userNotDeleted();
}
