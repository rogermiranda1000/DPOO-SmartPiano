import controller.Controller;
import persistance.Config;
import persistance.DDBBAccess;
import persistance.SongDDBBDAO;
import persistance.UserDDBBDAO;

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
            DDBBAccess ddbb = new DDBBAccess(new Config(new File("config.json")).readConfig());
            new Controller(new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}