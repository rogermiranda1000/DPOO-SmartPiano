import controller.Controller;
import persistance.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;

public class Main {
    private static final int MAX_DDBB_ACCESS = 2;

    public static void main (String[] args) {
        System.out.println( "╔══╦═╦═╦══╦═╦══╗╔═╦══╦══╦═╦╦═╗\n" +
                            "║══╣║║║║╔╗║╬╠╗╔╝║╬╠║║╣╔╗║║║║║║\n" +
                            "╠══║║║║║╠╣║╗╣║║░║╔╬║║╣╠╣║║║║║║\n" +
                            "╚══╩╩═╩╩╝╚╩╩╝╚╝░╚╝╚══╩╝╚╩╩═╩═╝"); //Roger, no borris.


        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig(), Main.MAX_DDBB_ACCESS);
            new Controller(c.getScrapping(), new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb), new PlaylistDDBBDAO(ddbb), new ConfigDDBBDAO(ddbb));

            /* FILE EXCEPTIONS */
        } catch (FileNotFoundException ex) {
            System.err.println("El fitxer config.json no existeix!");

            /* DDBB EXCEPTIONS */
        } catch (ClassNotFoundException ex) {
            System.err.println("No tens la dependencia del driver de MariaDB!");
        } catch (SQLInvalidAuthorizationSpecException ex) {
            System.err.println("Les credencials especificades en config.json (usuari/password) son incorrectes!");
        } catch (SQLNonTransientConnectionException ex) {
            System.err.println("Les dades especificades en config.json (ip/port) son incorrectes!");
        } catch (SQLSyntaxErrorException ex) {
            System.err.println("El nom de la base de dades especificat al config.json és incorrecte!");

            /* OTHER EXCEPTIONS */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}