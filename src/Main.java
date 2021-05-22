import controller.Controller;
import persistance.*;

import javax.swing.*;
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
                            "╚══╩╩═╩╩╝╚╩╩╝╚╝░╚╝╚══╩╝╚╩╩═╩═╝");

        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig(), Main.MAX_DDBB_ACCESS);
            new Controller(c.getScrapping(), new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb), new PlaylistDDBBDAO(ddbb), new ConfigDDBBDAO(ddbb), new StatisticsDDBBDAO(ddbb));

            /* FILE EXCEPTIONS */
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "El fitxer config.json no existeix!", "ERROR!", JOptionPane.ERROR_MESSAGE);

            /* DDBB EXCEPTIONS */
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "No tens la dependencia del driver de MariaDB!", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (SQLInvalidAuthorizationSpecException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Les credencials especificades en config.json (usuari/password) son incorrectes!", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (SQLNonTransientConnectionException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Les dades especificades en config.json (ip/port) son incorrectes!", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (SQLSyntaxErrorException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "El nom de la base de dades especificat al config.json és incorrecte!", "ERROR!", JOptionPane.ERROR_MESSAGE);

            /* OTHER EXCEPTIONS */
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Hi hagut un error de tipus SQLException", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}