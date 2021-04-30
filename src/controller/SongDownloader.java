package controller;

import entities.Song;
import model.WebScrapping;

public class SongDownloader extends Thread {
    private final int scrappingTime;
    private final SongNotifier notifier;
    private long lastTime;

    public SongDownloader(SongNotifier notifier, int scrappingTime) {
        this.scrappingTime = scrappingTime;
        this.lastTime = System.currentTimeMillis();
        this.notifier = notifier;
    }

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public void run() {
        try {
            while (true) {
                // obtè de la WEB
                for (Song s : WebScrapping.getSongs()) this.notifier.addSong(s);

                // espera el temps d'scrapping
                long now = System.currentTimeMillis();
                if (now - this.lastTime < this.scrappingTime) Thread.sleep(this.scrappingTime - (now - this.lastTime));
                this.lastTime = now;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
