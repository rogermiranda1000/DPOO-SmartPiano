import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    private static int scrapping;

    public static int getScrapping() {
        return scrapping;
    }

    public static void setScrapping(int scrapping) {
        Config.scrapping = scrapping;
    }

    public DDBB readConfig(String path){
        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            return null;
        }

        String content = "";

        try {
            content = new String ( Files.readAllBytes( Paths.get(path) ) );
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(content).getAsJsonObject();

        String port = rootObj.get("port").getAsString();
        String ip = rootObj.get("ip").getAsString();
        String dbName = rootObj.get("dbName").getAsString();
        String username = rootObj.get("username").getAsString();
        String password = rootObj.get("password").getAsString();
        setScrapping(rootObj.get("scrapping").getAsInt());
        DDBB r = new DDBB(port, ip, dbName, username, password);

        return r;
    }
}
