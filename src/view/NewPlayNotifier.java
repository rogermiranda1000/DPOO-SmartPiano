package view;

/**
 * It connects the Controller with Ranking
 */
public interface NewPlayNotifier {
    /**
     * There's a new play -> the graphs needs to be updated
     */
    void reloadGraphs();
}
