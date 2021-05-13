import controller.Controller;
import controller.LoginEvent;
import controller.MenuEvent;
import controller.SongsEvent;
import persistance.Config;
import persistance.DDBBAccess;
import persistance.SongDDBBDAO;
import persistance.UserDDBBDAO;
import view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) {
        System.out.println( "╔══╦═╦═╦══╦═╦══╗╔═╦══╦══╦═╦╦═╗\n" +
                            "║══╣║║║║╔╗║╬╠╗╔╝║╬╠║║╣╔╗║║║║║║\n" +
                            "╠══║║║║║╠╣║╗╣║║░║╔╬║║╣╠╣║║║║║║\n" +
                            "╚══╩╩═╩╩╝╚╩╩╝╚╝░╚╝╚══╩╝╚╩╩═╩═╝"); //Roger, no borris.


        try {
            Config c = new Config(new File("config.json"));
            DDBBAccess ddbb = new DDBBAccess(c.readConfig());
            new Controller(c.getScrapping(), new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}