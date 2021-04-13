public class DDBB {
    private final String port;
    private final String ip;
    private final String dbName;
    private final String username;
    private final String password;

    public DDBB(String port, String ip, String dbName, String username, String password) {
        this.port = port;
        this.ip = ip;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

}
