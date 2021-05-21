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
        songsGraph = new GraphDrawer(new int[]{1461, 1135, 258, 457, 1273, 840, 1414, 2168, 2575, 1115, 2919, 2226, 945, 1179, 2410, 3446, 3144, 389, 2935, 3192, 3321, 1138, 3600, 3523}, "Number of songs listened per hour", new Color(0xFF0000));
        timeGraph = new GraphDrawer(new int[]{176, 404, 402, 249, 653, 374, 945, 581, 30, 886, 465, 45, 824, 626, 302, 12, 849, 844, 914, 851, 222, 123, 518, 186}, "Number of seconds listened per hour", new Color(0x016618));
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
        songsGraph = new GraphDrawer(this.event.getSongsStadistics(), "Listened Songs per hour", new Color(0xFF0000));
        this.add(songsGraph, BorderLayout.WEST);
        this.remove(timeGraph);
        timeGraph = new GraphDrawer(this.event.getTimeStadistics(), "Listened Time per hour", new Color(0x016618));
        this.add(timeGraph, BorderLayout.EAST);
    }
}