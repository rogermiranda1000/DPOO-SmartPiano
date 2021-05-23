package view;

/**
 * Interface tasked with reloading the graph's information when the user generates a new play
 */
public interface NewPlayNotifier {

    /**
     * The user generated a new play, the graph's information should be updated
     */
    void reloadGraphs();
}
