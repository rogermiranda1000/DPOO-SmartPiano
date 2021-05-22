package view;

import javax.swing.*;
import java.awt.*;
import controller.RankingEvent;

public class Ranking extends JPanel implements NewPlayNotifier {
    private GraphDrawer songsGraph;
    private GraphDrawer timeGraph;
    private Top5Drawer top5Table;
    private final RankingEvent event;
    private final JPanel graphPanel;
    private final JPanel tablePanel;



    public Ranking(RankingEvent rankingE) {
        this.event = rankingE;

        this.graphPanel = new JPanel();
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.LINE_AXIS));
        this.tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));

        this.addStatistics();

        this.setLayout(new BorderLayout());
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void addStatistics() {
        songsGraph = new GraphDrawer(this.event.getSongsStatistics(), "Songs listened per hour", Color.RED);
        graphPanel.add(songsGraph);

        timeGraph = new GraphDrawer(this.event.getTimeStatistics(), "Seconds listened per hour", new Color(0x016618));
        graphPanel.add(timeGraph);

        top5Table = new Top5Drawer(this.event);
        tablePanel.add(top5Table);
    }

    @Override
    public void reloadGraphs() {
        // eliminar anteriors elements
        this.graphPanel.remove(this.songsGraph);
        this.graphPanel.remove(this.timeGraph);
        this.tablePanel.remove(this.top5Table);

        // afegir-los de nou
        this.addStatistics();

        // repintar
        this.setVisible(true);
        //this.repaint();
    }
}