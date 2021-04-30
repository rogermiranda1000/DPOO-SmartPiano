package entities;

public class DDBBInfo {
    private final String port;
    private final String ip;
    private final String dbName;
    private final String username;
    private final String password;

    public DDBBInfo(String port, String ip, String dbName, String username, String password) {
        this.port = port;
        this.ip = ip;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return this.ip + ":" + this.port;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
