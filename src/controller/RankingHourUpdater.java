package controller;

import view.NewPlayNotifier;

/**
 * Notifies the graph each SECONDS_PER_UPDATE seconds (to update the hour and the external users plays)
 */
public class RankingHourUpdater extends Thread {

    /**
     * Interval of seconds between updates
     */
    private static final int SECONDS_PER_UPDATE = 120;

    /**
     * Object to notify when an update should happen
     */
    private final NewPlayNotifier notifier;

    /**
     * Initializes the class setting the NewPlayNotifier
     * @param notifier Object to notify when an update should happen
     */
    public RankingHourUpdater(NewPlayNotifier notifier) {
        this.notifier = notifier;
    }

    /**
     * Loop each SECONDS_PER_UPDATE seconds
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
