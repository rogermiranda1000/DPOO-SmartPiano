package view;

import javax.swing.*;
import java.awt.*;


import controller.RankingEvent;

public class Ranking extends JPanel {
    private GraphDrawer songsGraph;
    private GraphDrawer timeGraph;
    private final RankingEvent event;


    public Ranking(RankingEvent rankingE) {

        this.event = rankingE;
        songsGraph = new GraphDrawer(this.event.getSongsStadistics(), "Number of songs listened per hour", new Color(0xFF0000));
        timeGraph = new GraphDrawer(this.event.getTimeStadistics(), "Number of seconds listened per hour", new Color(0x016618));

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(songsGraph);
        this.add(timeGraph);
        this.setBackground(ColorConstants.BACKGROUND.getColor());
        this.setVisible(true);

    }
    public void reloadGraphs(){
        this.remove(songsGraph);
        songsGraph = new GraphDrawer(this.event.getSongsStadistics(), "Listened Songs per hour", new Color(0xFF0000));
        this.add(songsGraph, BorderLayout.WEST);
        this.remove(timeGraph);
        timeGraph = new GraphDrawer(this.event.getTimeStadistics(), "Listened Time per hour", new Color(0x016618));
        this.add(timeGraph, BorderLayout.EAST);
    }
}