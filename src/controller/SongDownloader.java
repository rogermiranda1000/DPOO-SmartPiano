package controller;

import entities.Song;
import model.WebScrapping;

public class SongDownloader extends Thread implements SongNotifier {
    private final int scrappingTime;
    private final SongNotifier notifier;
    private long lastTime;

    public SongDownloader(SongNotifier notifier, int scrappingTime) {
        this.scrappingTime = scrappingTime;
        this.lastTime = System.currentTimeMillis();
        this.notifier = notifier;
    }

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


    @Override
    public void addSong(Song song) {
        this.notifier.addSong(song);
    }
}
