package persistance;

import entities.DDBBInfo;

import java.sql.*;
import java.util.ArrayList;

/**
 * Estableix la conexió amb la base de dades especificada en connect()
 */
public class DDBBAccess {
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String PROTOCOL = "jdbc:mariadb:";

    private final Connection ddbb;

    /**
     * Es conecta a la base de dades segons els parà
     * @throws SQLException Error al conectar
     */
    public DDBBAccess(DDBBInfo info) throws SQLException {
        this.ddbb = DriverManager.getConnection(String.format("%s//%s/%s", PROTOCOL, info.getHost(), info.getDbName()),
                info.getUsername(), info.getPassword());
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
     * Executa una sentència SQL i retorna els objectes obtinguts
     * @param sql Sentència a executar
     * @param params Paràmetres de la sentència
     * @return ResultSet de la consulta
     * @throws SQLException No s'ha pogut executar la sentència
     */
    public synchronized ResultSet getSentence(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = this.ddbb.prepareStatement(sql);
        int x;
        for (x = 0; x < params.length; x++) stmt.setObject(x+1, params[x]);

        ResultSet rs = stmt.executeQuery();

        stmt.close();
        return rs;
    }

    /**
     * Executa una sentencia SQL sense retorn (UPDATE, DELETE, INSERT)
     * @param sql Sentència SQL
     * @param params Paràmetres de la sentència
     * @return Nº files afectades
     * @throws SQLException No s'ha pogut executar la sentència
     */
    public synchronized int runSentence(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = this.ddbb.prepareStatement(sql);
        int x;
        for (x = 0; x < params.length; x++) stmt.setObject(x+1, params[x]);

        x = stmt.executeUpdate();
        stmt.close();

        return x;
    }
}
