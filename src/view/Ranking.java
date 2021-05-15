package view;

import javax.swing.*;
import java.awt.*;

public class Ranking extends JPanel {
    int listenedSongs[];
    int listenedTime[];
    //TODO Importar la llista de valors per als ranqings

    public Ranking(){
        this.setLayout(new BorderLayout(10, 10));
        GraphDrawer songsGraph = new GraphDrawer(listenedSongs, "Listened Songs per hour", new Color(0xFF0000));
        GraphDrawer timeGraph = new GraphDrawer(listenedTime, "Listened Time per hour", new Color(0x016618));
        this.add(songsGraph, BorderLayout.WEST);
        this.add(timeGraph, BorderLayout.EAST);
        this.setBackground(ColorConstants.BACKGROUND.getColor());
        this.setVisible(true);
    }


}
