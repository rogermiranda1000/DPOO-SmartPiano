package persistance;

import java.sql.SQLException;

/**
 * Thrown when a connection is requested and no more are available
 */
public class OutOfConnectionsException extends SQLException {

    /**
     * Thrown when a connection is requested and no more are available
     */
    public OutOfConnectionsException() {
        super();
    }

    /**
     * Thrown when a connection is requested and no more are available, includes a message
     * @param msg Message of the exception
     */
    public OutOfConnectionsException(String msg) {
        super(msg);
    }
}
