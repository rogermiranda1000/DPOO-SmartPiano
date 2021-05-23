package entities;

/**
 * Saves the information about the database access
 */
public class DDBBInfo {

    /**
     * Port used to access the database
     */
    private final String port;

    /**
     * Ip used to access the database
     */
    private final String ip;

    /**
     * The name of the database
     */
    private final String dbName;

    /**
     * Username used to access the database
     */
    private final String username;

    /**
     * Password of the user used to access the database
     */
    private final String password;

    /**
     * Creates the object and initializes it's variables
     * @param port Port used to access the database
     * @param ip Ip used to access the database
     * @param dbName The name of the database
     * @param username Username used to access the database
     * @param password Password of the user used to access the database
     */
    public DDBBInfo(String port, String ip, String dbName, String username, String password) {
        this.port = port;
        this.ip = ip;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the ip and port in a single string
     * @return "ip" + "port"
     */
    public String getHost() {
        return this.ip + ":" + this.port;
    }

    /**
     * Returns the database name
     * @return Name of the database
     */
    public String getDbName() {
        return this.dbName;
    }

    /**
     * Returns the username used to access the database
     * @return Username used for connecting to the database
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the password used to access the database
     * @return Password for the user used for connecting to the database
     */
    public String getPassword() {
        return this.password;
    }
}
