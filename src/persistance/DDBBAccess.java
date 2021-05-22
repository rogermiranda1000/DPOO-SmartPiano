package persistance;

import entities.DDBBInfo;

import java.sql.*;

/**
 * Establishes a connection with the database specified in connect()
 */
public class DDBBAccess {

    /**
     * The driver used to access the database
     */
    private static final String DRIVER = "org.mariadb.jdbc.Driver";

    /**
     * The protocol used to access the database
     */
    private static final String PROTOCOL = "jdbc:mariadb:";

    /**
     * Connection queue for the database
     */
    private final Connection[] ddbb;

    /**
     * Array that idicates which connections are being used
     */
    private final boolean[] using;

    /**
     * Connects to the database using the parameters
     * @throws SQLException Error when connecting to the database
     * @throws ClassNotFoundException The driver is missing
     * @throws SQLInvalidAuthorizationSpecException Invalid credentials
     * @throws SQLNonTransientConnectionException The database IP address is invalid
     * @throws SQLSyntaxErrorException The database name is invalid
     */
    public DDBBAccess(DDBBInfo info, int maxConnections) throws SQLException, ClassNotFoundException, SQLInvalidAuthorizationSpecException, SQLNonTransientConnectionException, SQLSyntaxErrorException {
        DDBBAccess.loadDriver();

        this.ddbb = new Connection[maxConnections];
        this.using = new boolean[maxConnections];
        for (int x = 0; x < this.ddbb.length; x++) {
            this.ddbb[x] = DriverManager.getConnection(String.format("%s//%s/%s", PROTOCOL, info.getHost(), info.getDbName()),
                                info.getUsername(), info.getPassword());
            this.using[x] = false;
        }
    }

    /**
     * Charges the database driver
     * If you don't have it you have to add the dependency. You can download it here https://mariadb.com/kb/en/about-mariadb-connector-j/
     * @throws ClassNotFoundException The driver is not installed
     */
    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }

    /**
     * Obtains the first available connection
     * @return ID of the connection
     * @throws OutOfConnectionsException There have been more connections requested than the maximum
     */
    private synchronized int getConnection() throws OutOfConnectionsException {
        for (int x = 0; x < this.using.length; x++) {
            if (!this.using[x]) {
                this.using[x] = true;
                return x;
            }
        }
        throw new OutOfConnectionsException();
    }

    /**
     * Closes the connection for the specified handler
     * @param handler Handler of the connection to close
     */
    private synchronized void closeConnection(int handler) {
        this.using[handler] = false;
    }

    /**
     * Executes an SQL query and returns the resulting objects
     * @param sql Query to execute
     * @param params Query parameters
     * @return ResultSet of the query
     * @throws SQLException The query couldn't be executed
     */
    public ResultSet getSentence(String sql, Object... params) throws SQLException {
        int connectionId = this.getConnection();
        try {
            PreparedStatement stmt = this.ddbb[connectionId].prepareStatement(sql);
            int x;
            for (x = 0; x < params.length; x++) stmt.setObject(x + 1, params[x]);

            ResultSet rs = stmt.executeQuery();

            stmt.close();
            this.closeConnection(connectionId);
            return rs;
        } catch (SQLException ex) {
            this.closeConnection(connectionId);
            throw ex;
        }
    }

    /**
     * Executes an SQL query without return values
     * @param sql SQL query
     * @param params Query parameters
     * @return Nº of rows affected by the query
     * @throws SQLException The query couldn't be executed
     */
    public int runSentence(String sql, Object... params) throws SQLException {
        int connectionId = this.getConnection();
        try {
            PreparedStatement stmt = this.ddbb[connectionId].prepareStatement(sql);
            int x;
            for (x = 0; x < params.length; x++) stmt.setObject(x+1, params[x]);

            x = stmt.executeUpdate();
            stmt.close();
            this.closeConnection(connectionId);

            return x;
        } catch (SQLException ex) {
            this.closeConnection(connectionId);
            throw ex;
        }
    }
}
