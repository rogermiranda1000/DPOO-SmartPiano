package controller;

import model.WebScrapping;
import view.NewPlayNotifier;

/**
 * Notifies the graph each SECONDS_PER_UPDATE seconds (to update the hour and the external users reproductions)
 */
public class RankingHourUpdater extends Thread {
    /**
     * Time to notify the view that update is required
     */
    private static final int SECONDS_PER_UPDATE = 120;

    /**
     * Connection with the view
     */
    private final NewPlayNotifier notifier;

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
                Thread.sleep( RankingHourUpdater.SECONDS_PER_UPDATE * 1000);
                notifier.reloadGraphs();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
