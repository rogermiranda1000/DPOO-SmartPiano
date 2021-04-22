import business.BusinessFacade;
import entities.User;
import persistance.DDBBAccess;
import persistance.SongDDBBDAO;
import persistance.UserDDBBDAO;

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
            System.out.println(new UserDDBBDAO(ddbb).addUser(new User("Carbassot", "carbassot@salle.url.edu"), "fa_el_que_pot"));
            User u = new UserDDBBDAO(ddbb).getUser("carbassot@salle.url.edu", "fa_el_que_pot");
            System.out.println(u);
            System.out.println(new UserDDBBDAO(ddbb).deleteUser(u, "fa_el_que_pot"));
            System.out.println(new UserDDBBDAO(ddbb).getUser("carbassot@salle.url.edu", "fa_el_que_pot"));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}