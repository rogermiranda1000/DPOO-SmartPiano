package entities;

/**
 * Stores information about a user
 */
public class User {

    /**
     * Name of the user
     */
    private final String name;

    /**
     * Email of the user
     */
    private final String email;

    /**
     * Generates a user with the give name and email
     * @param name Name of the user
     * @param email Email of the user
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the name of the user
     * @return Name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the email of the user
     * @return Email of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * The data of the user in a single String
     * @return String with all the data from the user
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
