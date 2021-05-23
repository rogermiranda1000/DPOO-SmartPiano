package controller;

import entities.Song;
import model.WebScrapping;

/**
 * Class that notifies every time a new scrapping should happen
 */
public class SongDownloader extends Thread implements SongNotifier {

    /**
     * Time in milliseconds between every scrapping
     */
    private final int scrappingTime;

    /**
     * Object to notify to add a new song to the database
     */
    private final SongNotifier notifier;

    /**
     * Last time that the scrapping happened
     */
    private long lastTime;

    /**
     * Initializes the object and sets the variables to the input
     * @param notifier Object to notify to add a new song to the database
     * @param scrappingTime Time in milliseconds between every scrapping
     */
    public SongDownloader(SongNotifier notifier, int scrappingTime) {
        this.scrappingTime = scrappingTime;
        this.lastTime = System.currentTimeMillis();
        this.notifier = notifier;
    }

    /**
     * Obtains the scrapped songs and sleeps for the next interval
     */
    @Override
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public void run() {
        try {
            while (true) {
                // obtè de la WEB
                WebScrapping.getSongs(this);

                // espera el temps d'scrapping
                long now = System.currentTimeMillis();
                if (now - this.lastTime < this.scrappingTime) Thread.sleep(this.scrappingTime - (now - this.lastTime));
                this.lastTime = now;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new song to the database
     * @param song Song to add
     */
    @Override
    public void addSong(Song song) {
        this.notifier.addSong(song);
    }
}
