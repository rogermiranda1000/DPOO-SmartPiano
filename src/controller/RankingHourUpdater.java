package controller;

import model.WebScrapping;
import view.NewPlayNotifier;

/**
 * Notifies the graph when an hour changes
 */
public class RankingHourUpdater extends Thread {
    /**
     * Connection with the view
     */
    private NewPlayNotifier notifier;

    public RankingHourUpdater(NewPlayNotifier notifier) {
        this.notifier = notifier;
    }

    /**
     * Loop each 1h
     */
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    @Override
    public void run() {
        try {
            while (true) {
                notifier.reloadGraphs();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
