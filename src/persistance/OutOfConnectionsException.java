package persistance;

import java.sql.SQLException;

public class OutOfConnectionsException extends SQLException {
    public OutOfConnectionsException() {
        super();
    }

    public OutOfConnectionsException(String msg) {
        super(msg);
    }
}
