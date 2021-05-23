import controller.Controller;
import persistance.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;

/**
 * Entry point of the program
 */
public class Main {

    /**
     * Maximum number of simultaneous connections on the database
     */
    private static final int MAX_DDBB_ACCESS = 2;

    /**
     * Entry point of the program
     * @param args Arguments of the program (none used)
     */
    public static void main (String[] args) {
        System.out.println( "╔══╦═╦═╦══╦═╦══╗╔═╦══╦══╦═╦╦═╗\n" +
                            "║══╣║║║║╔╗║╬╠╗╔╝║╬╠║║╣╔╗║║║║║║\n" +
                            "╠══║║║║║╠╣║╗╣║║░║╔╬║║╣╠╣║║║║║║\n" +
                            "╚══╩╩═╩╩╝╚╩╩╝╚╝░╚╝╚══╩╝╚╩╩═╩═╝");

        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig(), Main.MAX_DDBB_ACCESS);
            new Controller(c.getScrapping(), new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb), new PlaylistDDBBDAO(ddbb), new ConfigDDBBDAO(ddbb), new StatisticsDDBBDAO(ddbb));

            /* FILE EXCEPTIONS */
        } catch (FileNotFoundException ex) {
            Main.showErrorAndClose("El fitxer config.json no existeix!");

            /* DDBB EXCEPTIONS */
        } catch (ClassNotFoundException ex) {
            Main.showErrorAndClose("No tens la dependencia del driver de MariaDB!");
        } catch (SQLInvalidAuthorizationSpecException ex) {
            Main.showErrorAndClose("Les credencials especificades en config.json (usuari/password) son incorrectes!");
        } catch (SQLNonTransientConnectionException ex) {
            Main.showErrorAndClose("Les dades especificades en config.json (ip/port) son incorrectes!");
        } catch (SQLSyntaxErrorException ex) {
            Main.showErrorAndClose("El nom de la base de dades especificat al config.json és incorrecte!");

            /* OTHER EXCEPTIONS */
        } catch (SQLException ex) {
            Main.showErrorAndClose("Error d'SQL desconegut! (" + ex.getMessage() + ")");
        }
    }

    /**
     * Shows an error screen with the specified message
     * @param error Message to show on the screen
     */
    private static void showErrorAndClose(String error) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, error, "ERROR!", JOptionPane.ERROR_MESSAGE);
        frame.dispose();
    }
}