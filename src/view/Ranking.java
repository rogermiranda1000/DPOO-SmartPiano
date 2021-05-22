package view;

import javax.swing.*;
import java.awt.*;
import controller.RankingEvent;

/**
 * Initializes the "Ranking" tab, sets up the graphs, table, and implements the functionalities
 */
public class Ranking extends JPanel implements NewPlayNotifier {

    /**
     * Object that will draw the plays graph
     */
    private GraphDrawer songsGraph;

    /**
     * Object that will draw the time graph
     */
    private GraphDrawer timeGraph;

    /**
     * Object that will draw the "top 5 songs" table
     */
    private Top5Drawer top5Table;

    /**
     * Object that will draw the songs graph
     */
    private final RankingEvent event;

    /**
     * Panel that holds both graphs
     */
    private final JPanel graphPanel;

    /**
     * Panel that holds the "top 5 songs" table
     */
    private final JPanel tablePanel;

    /**
     * Initializes the panel and inserts the table and graphs
     * @param rankingE Object that will draw the songs graph
     */
    public Ranking(RankingEvent rankingE) {
        this.event = rankingE;

        this.graphPanel = new JPanel();
        this.graphPanel.setLayout(new BoxLayout(this.graphPanel, BoxLayout.LINE_AXIS));
        this.tablePanel = new JPanel();
        this.tablePanel.setLayout(new BoxLayout(this.tablePanel, BoxLayout.LINE_AXIS));

        this.addStatistics();

        this.setLayout(new BorderLayout());
        this.add(this.graphPanel, BorderLayout.CENTER);
        this.add(this.tablePanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    /**
     * Adds the graphical elements into both panels
     */
    private void addStatistics() {
        this.songsGraph = new GraphDrawer(this.event.getSongsStatistics(), "Songs listened per hour", Color.RED);
        this.graphPanel.add(this.songsGraph);

        this.timeGraph = new GraphDrawer(this.event.getTimeStatistics(), "Seconds listened per hour", new Color(0x016618));
        this.graphPanel.add(this.timeGraph);

        this.top5Table = new Top5Drawer(this.event);
        this.tablePanel.add(this.top5Table);
    }

    /**
     * Updates the information on the table and graphs and repaints
     */
    @Override
    public void reloadGraphs() {
        // eliminar anteriors elements
        this.graphPanel.remove(this.songsGraph);
        this.graphPanel.remove(this.timeGraph);
        this.tablePanel.remove(this.top5Table);

        // afegir-los de nou
        this.addStatistics();

        // re-pintar
        this.revalidate();
    }
}