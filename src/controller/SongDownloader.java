package controller;

import entities.Song;
import model.WebScrapping;

public class SongDownloader extends Thread {
    private final int scrappingTime;
    private long lastTime;

    public SongDownloader(int scrappingTime) {
        this.scrappingTime = scrappingTime;
        this.lastTime = System.currentTimeMillis();
    }

    public void run() {
        try {
            while (true) {
                // obtè de la WEB
                Song[] result = WebScrapping.getSongs();
                // TODO send result to data persistance

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
