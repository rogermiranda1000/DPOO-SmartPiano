import controller.Controller;
import persistance.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

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
            new Controller(c.getScrapping(), new SongDDBBDAO(ddbb), new UserDDBBDAO(ddbb), new PlaylistDDBBDAO(ddbb), new ConfigDDBBDAO(ddbb), new StatisticsDDBBDAO(ddbb));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}