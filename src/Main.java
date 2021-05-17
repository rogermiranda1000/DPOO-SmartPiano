import controller.Controller;
import persistance.*;

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
            new Controller(c.getScrapping(), new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb), new PlaylistDDBBDAO(ddbb));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}