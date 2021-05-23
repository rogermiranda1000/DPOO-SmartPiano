package view;

import entities.Song;
import controller.RankingEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Draws and fills the "top 5 songs" table
 */
public class Top5Drawer extends JPanel {

    /**
     * The width of the "POSITION" column
     */
    private static final int POSITION_WIDTH = 100;

    /**
     * The width of the "NAME" column
     */
    private static final int NAME_WIDTH = 450;

    /**
     * The width of the "ARTIST" column
     */
    private static final int ARTIST_WIDTH = 250;

    /**
     * The width of the "TOTAL PLAYS" column
     */
    private static final int PLAYS_WIDTH = 180;

    /**
     * The height of each row
     */
    private static final int ROW_HEIGHT = 30;

    /**
     * The number of songs to be displayed
     */
    private static final int NUM_SONGS = 5;

    /**
     * Array of songs to display, from most popular to least
     */
    private final Song[] topSongs;

    /**
     * Number of reproductions per song, from most popular to least
     */
    private int[] numRep = new int[NUM_SONGS];

    /**
     * The data to show on the table
     */
    private final String[][] tableData = new String[NUM_SONGS+1][4];


    /**
     * Asks for the required data and builds the table
     * @param rankingE Object to notify when data is needed
     */
    public Top5Drawer (RankingEvent rankingE) {
        topSongs = rankingE.getTop5(numRep);
        if (topSongs.length == 0) {
            JLabel emptyMsg = new JLabel("No songs reproduced yet");
            this.add(emptyMsg);
        } else {
            this.dataFormat();
            String[] columnID = {"POSITION", "SONG NAME", "ARTIST", "TOTAL PLAYS"};

            DefaultTableModel tableModel = new UneditableTable(tableData, columnID);
            JTable top5 = new JTable();

            top5.setModel(tableModel);
            //Defining the width and height of the table
            top5.getColumnModel().getColumn(0).setPreferredWidth(POSITION_WIDTH);
            top5.getColumnModel().getColumn(1).setPreferredWidth(NAME_WIDTH);
            top5.getColumnModel().getColumn(2).setPreferredWidth(ARTIST_WIDTH);
            top5.getColumnModel().getColumn(3).setPreferredWidth(PLAYS_WIDTH);
            top5.setRowHeight(ROW_HEIGHT);

            //Alignment of the text in all comuns
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            top5.setDefaultRenderer(top5.getColumnClass(0), centerRenderer);


            top5.setFont(new Font("Arial", Font.BOLD, 20));
            top5.setRowHeight(30);

            this.add(top5);
        }
    }

    /**
     * Fills the table with the stored information
     */
    private void dataFormat(){
        tableData[0][0] = "POSITION";
        tableData[0][1] = "SONG NAME";
        tableData[0][2] = "ARTIST";
        tableData[0][3] = "REPRODUCTIONS";

        for(int y = 0; y < NUM_SONGS && topSongs[y] != null; y++){
            if (topSongs[y] == null) break;
            tableData[y+1][0] = '#' + String.valueOf(y + 1);
            tableData[y+1][1] = topSongs[y].getName();
            tableData[y+1][2] = topSongs[y].getArtist();
            tableData[y+1][3] = String.valueOf(numRep[y]);
        }
    }
}
