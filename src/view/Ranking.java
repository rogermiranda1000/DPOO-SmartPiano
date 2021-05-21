package view;

import javax.swing.*;
import java.awt.*;
import controller.RankingEvent;

public class Ranking extends JPanel{
    private GraphDrawer songsGraph;
    private GraphDrawer timeGraph;
    private Top5Drawer top5Table;
    private final RankingEvent event;
    private final JPanel graphPanel = new JPanel();
    private final JPanel tablePanel = new JPanel();



    public Ranking(RankingEvent rankingE) {

        this.event = rankingE;
        songsGraph = new GraphDrawer(this.event.getSongsStatistics(), "Songs listened per hour", new Color(0xFF0000));
        timeGraph = new GraphDrawer(this.event.getTimeStatistics(), "Seconds listened per hour", new Color(0x016618));
        top5Table = new Top5Drawer(this.event);

        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.LINE_AXIS));
        this.setLayout(new BorderLayout());
        graphPanel.add(songsGraph);
        graphPanel.add(timeGraph);

        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));
        tablePanel.add(top5Table);

        this.add(graphPanel, BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    //TODO Utilitzar a quest metode per actualiztar les estadistiques amb un event.
    private void reloadGraphs(){
        graphPanel.remove(songsGraph);
        songsGraph = new GraphDrawer(this.event.getSongsStatistics(), "Songs listened per hour", new Color(0xFF0000));
        graphPanel.add(songsGraph);

        graphPanel.remove(timeGraph);
        timeGraph = new GraphDrawer(this.event.getTimeStatistics(), "Seconds listened per hour", new Color(0x016618));
        graphPanel.add(timeGraph);

        tablePanel.remove(top5Table);
        top5Table = new Top5Drawer(this.event);
        top5Table.add(top5Table);
    }
}