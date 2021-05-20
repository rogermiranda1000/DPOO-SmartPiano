package view;

import entities.Song;
import controller.RankingEvent;
import javax.swing.*;



public class Top5Drawer extends JPanel {

    private final String[] columnID= {"#","NAME","ARTIST", "# REPRODUCTIONS"};
    private final Song[] topSongs;
    public int[] numRep = new int[5];
    private final String[][] tableData = new String[5][4];


    public Top5Drawer (RankingEvent rankingE) {
        topSongs = rankingE.getTop5(numRep);
        /*if (topSongs.length == 0) {
            JLabel emptyMsg = new JLabel("No songs reproduced yet");
            this.add(emptyMsg);
        } else {
        */

            this.dataFormat();
            JTable top5 = new JTable(tableData, columnID);
            this.add(top5);

    }

    private void dataFormat(){
        for(int y = 0; y < 5/*topSongs.length*/; y++){
            tableData[y][0] = '#' + String.valueOf(y + 1);
            tableData[y][1] = "Voy contigo Pipo"; //topSongs[y].getName();
            tableData[y][2] = "Alexelcapo"; //topSongs[y].getArtist();
            tableData[y][3] = "10000000";//String.valueOf(numRep[y]);
        }

    }
}
