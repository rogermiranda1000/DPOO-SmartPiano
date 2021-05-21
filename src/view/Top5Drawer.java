package view;

import entities.Song;
import controller.RankingEvent;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class Top5Drawer extends JPanel {
    private static final int POSITION_WIDTH = 100;
    private static final int NAME_WIDTH = 450;
    private static final int ARTIST_WIDTH = 250;
    private static final int REPRODUCTIONS_WIDTH = 180;
    private static final int ROW_HEIGHT = 30;
    private final Song[] topSongs;

    private final int[] numRep = new int[5];
    private final String[][] tableData = new String[6][4];


    public Top5Drawer (RankingEvent rankingE) {


        topSongs = rankingE.getTop5(numRep);
        if (topSongs.length == 0) {
            JLabel emptyMsg = new JLabel("No songs reproduced yet");
            this.add(emptyMsg);
        } else {
            this.dataFormat();
            String[] columnID = {"POSITION", "SONG NAME", "ARTIST", "REPRODUCTIONS"};

            DefaultTableModel tableModel = new DefaultTableModel(tableData, columnID) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            };
            JTable top5 = new JTable();

            top5.setModel(tableModel);
            //Defining the width and height of the table
            top5.getColumnModel().getColumn(0).setPreferredWidth(POSITION_WIDTH);
            top5.getColumnModel().getColumn(1).setPreferredWidth(NAME_WIDTH);
            top5.getColumnModel().getColumn(2).setPreferredWidth(ARTIST_WIDTH);
            top5.getColumnModel().getColumn(3).setPreferredWidth(REPRODUCTIONS_WIDTH);
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

    private void dataFormat(){
        tableData[0][0] = "POSITION";
        tableData[0][1] = "SONG NAME";
        tableData[0][2] = "ARTIST";
        tableData[0][3] = "REPRODUCTIONS";
        for(int y = 0; y < topSongs.length && topSongs[y] != null; y++){
            tableData[y+1][0] = "# " + (y + 1);
            tableData[y+1][1] = topSongs[y].getName();
            tableData[y+1][2] = topSongs[y].getArtist();
            tableData[y+1][3] = String.valueOf(numRep[y]);
        }

    }



}
