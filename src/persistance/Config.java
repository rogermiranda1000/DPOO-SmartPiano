package persistance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.DDBBInfo;

import java.io.*;
import java.util.Scanner;

public class Config {
    private static final Gson gson = new Gson();

    private int scrapping;
    private final File configFile;

    public Config(File f) {
        this.configFile = f;
    }

    public int getScrapping() {
        return this.scrapping;
    }

    public void setScrapping(int scrapping) {
        this.scrapping = scrapping;
    }

    public DDBBInfo readConfig() throws FileNotFoundException {
        if (!this.configFile.exists()) throw new FileNotFoundException("The file don't exists");

        // llegim el fitxer; la seva informació queda guardada a 'sb'
        String fileResult;
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(this.configFile);
        while (scanner.hasNextLine()) sb.append(scanner.nextLine());
        scanner.close();
        fileResult = sb.toString();

        this.setScrapping(Config.gson.fromJson(fileResult, JsonObject.class).get("scrapping").getAsInt());
        return Config.gson.fromJson(fileResult, DDBBInfo.class);
    }
}
