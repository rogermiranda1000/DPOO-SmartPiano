package persistance;

import entities.DDBBInfo;

import java.sql.*;

/**
 * Estableix la conexió amb la base de dades especificada en connect()
 */
public class DDBBAccess {
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String PROTOCOL = "jdbc:mariadb:";

    private final Connection[] ddbb;
    private final boolean[] using;

    /**
     * Es conecta a la base de dades segons els parà
     * @throws SQLException Error al conectar
     * @throws ClassNotFoundException No es té el driver de MariaDB
     * @throws SQLInvalidAuthorizationSpecException Credencials invàl·lides
     * @throws SQLNonTransientConnectionException IP de la base de dades invàl·lida
     * @throws SQLSyntaxErrorException DB nom invàl·lid
     */
    public DDBBAccess(DDBBInfo info, int maxConnections) throws SQLException, ClassNotFoundException, SQLInvalidAuthorizationSpecException {
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
     * Carga el driver de MariaDB
     * Si no el tens, l'has d'afegir com dependència. El pots descargar en https://mariadb.com/kb/en/about-mariadb-connector-j/
     * @throws ClassNotFoundException No s'ha instalat el driver
     */
    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }

    /**
     * Obtè la primera conexió lliure de la llista
     * @return ID de la conexió
     * @throws OutOfConnectionsException S'han demanat més conexions de les disponibles
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

    private synchronized void closeConnection(int handler) {
        this.using[handler] = false;
    }

    /**
     * Executa una sentència SQL i retorna els objectes obtinguts
     * @param sql Sentència a executar
     * @param params Paràmetres de la sentència
     * @return ResultSet de la consulta
     * @throws SQLException No s'ha pogut executar la sentència
     */
    public ResultSet getSentence(String sql, Object... params) throws SQLException {
        int connectionId = this.getConnection();
        PreparedStatement stmt = this.ddbb[connectionId].prepareStatement(sql);
        int x;
        for (x = 0; x < params.length; x++) stmt.setObject(x+1, params[x]);

        ResultSet rs = stmt.executeQuery();

        stmt.close();
        this.closeConnection(connectionId);
        return rs;
    }

    /**
     * Executa una sentencia SQL sense retorn (UPDATE, DELETE, INSERT)
     * @param sql Sentència SQL
     * @param params Paràmetres de la sentència
     * @return Nº files afectades
     * @throws SQLException No s'ha pogut executar la sentència
     */
    public int runSentence(String sql, Object... params) throws SQLException {
        int connectionId = this.getConnection();
        PreparedStatement stmt = this.ddbb[connectionId].prepareStatement(sql);
        int x;
        for (x = 0; x < params.length; x++) stmt.setObject(x+1, params[x]);

        x = stmt.executeUpdate();
        stmt.close();
        this.closeConnection(connectionId);

        return x;
    }
}
