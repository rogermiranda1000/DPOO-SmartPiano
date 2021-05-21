package view;

import javax.swing.*;
import java.awt.*;
import controller.RankingEvent;

public class Ranking extends JPanel {
    private GraphDrawer songsGraph;
    private GraphDrawer timeGraph;
    private Top5Drawer top5Table;
    private final RankingEvent event;


    public Ranking(RankingEvent rankingE) {

        this.event = rankingE;

        //TODO Acabar les events de RankingEvent.java per retornar els valors per a les gràfiques
        //songsGraph = new GraphDrawer(this.event.getSongsStadistics(), "Number of songs listened per hour", new Color(0xFF0000));
        //timeGraph = new GraphDrawer(this.event.getTimeStadistics(), "Number of seconds listened per hour", new Color(0x016618));

        //Test lines to check the graph output until the controller is ready to provide the data.
        songsGraph = new GraphDrawer(this.event.getSongsStatistics(), "Number of songs listened per hour", new Color(0xFF0000));
        timeGraph = new GraphDrawer(this.event.getTimeStatistics(), "Number of seconds listened per hour", new Color(0x016618));
        top5Table = new Top5Drawer(this.event);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(songsGraph);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        songsGraph.setAlignmentY(BOTTOM_ALIGNMENT);
        this.add(timeGraph);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        timeGraph.setAlignmentY(BOTTOM_ALIGNMENT);

        this.add(top5Table);

        this.setBackground(ColorConstants.BACKGROUND.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setVisible(true);

    }
    private void reloadGraphs(){
        this.remove(songsGraph);
        songsGraph = new GraphDrawer(this.event.getSongsStatistics(), "Listened Songs per hour", new Color(0xFF0000));
        this.add(songsGraph, BorderLayout.WEST);
        this.remove(timeGraph);
        timeGraph = new GraphDrawer(this.event.getTimeStatistics(), "Listened Time per hour", new Color(0x016618));
        this.add(timeGraph, BorderLayout.EAST);
    }
}