import business.BusinessFacade;
import ddbb.DDBBAccess;
import model.SongDDBBDAO;
import model.UserDDBBDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) {
        /*try {
            DDBBAccess.loadDriver();
            ResultSet r = new DDBBAccess(new Config(new File("config.json")).readConfig()).getSentence("SELECT id, public, name, date, author FROM Songs;");
            while (r.next()) {
                System.out.println(r.getObject(1, Integer.class));
                System.out.println(r.getObject(2, Boolean.class));
                System.out.println(r.getObject(3, String.class));
                System.out.println(r.getObject(4, String.class)); // TODO Timestamp?
                System.out.println(r.getObject(5, String.class));
                System.out.println();
            }
        } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }*/

        try {
            DDBBAccess ddbb = new DDBBAccess(new Config(new File("config.json")).readConfig());
            new BusinessFacade(new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}