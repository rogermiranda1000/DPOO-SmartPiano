package persistance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.DDBBInfo;

import java.io.*;
import java.util.Scanner;

/**
 * Reads the config.json uses it's values to configure the app
 */
public class Config {

    /**
     * Variable for storing the json file information
     */
    private static final Gson gson = new Gson();

    /**
     * Interval in milliseconds describing the time between scrapings
     */
    private int scrapping;

    /**
     * File to read the configuration from
     */
    private final File configFile;

    /**
     * Sets the "configFile" variable
     * @param f Path of the file to read the configuration from
     */
    public Config(File f) {
        this.configFile = f;
    }

    /**
     * Getter for the scrapping time
     * @return the time between scrapings in milliseconds
     */
    public int getScrapping() {
        return this.scrapping;
    }

    /**
     * Sets the scrapping time
     * @param scrapping The time between scrapings in milliseconds
     */
    public void setScrapping(int scrapping) {
        this.scrapping = scrapping;
    }

    /**
     * Reads the file in "configFile" and uses it's values to configure the app
     * @return A DDBInfo object with the required information for accessing the database
     * @throws FileNotFoundException If the file doesn't exist
     */
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
