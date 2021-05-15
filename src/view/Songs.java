package view;

import controller.SongsEvent;
import entities.Song;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Songs extends JPanel implements ActionListener {
    private final DefaultTableModel songs;
    private final DefaultTableModel playlists;
    private final SongsEvent event;
    private final JTable songsTable;
    private final JTable playlistsTable;
    private JButton playSongButton, addSongButton, removeSongButton, backToSongsButton, addToPlaylistButton;
    private final JPanel mainContent;
    private final CardLayout cl;

    public Songs(SongsEvent songE) {
        this.songs = new DefaultTableModel();
        this.playlists = new DefaultTableModel();
        this.songsTable = new JTable(this.songs);
        this.playlistsTable = new JTable(this.playlists);

        this.event = songE;

        mainContent = new JPanel(new CardLayout());
        mainContent.add(songsList(), "songs");
        mainContent.add(addToPlaylist(), "playlists");
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("songs"));

        this.add(mainContent);
        this.setVisible(true);
    }

    public JPanel songsList(){
        JPanel songsList = new JPanel();
        Font f = new Font(null, Font.PLAIN, 16);
        //TODO: Listers de la taula: https://www.javatpoint.com/java-jtable
        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // Create columns
        songs.addColumn("Title");
        songs.addColumn("Artist");
        songs.addColumn("Duration");
        songs.addColumn("Punctuation");

        songsTable.setBounds(30, 40, 80, 90);
        songsTable.setFont(f);
        Dimension dim = new Dimension(20, 2);
        songsTable.setRowHeight(songsTable.getRowHeight() + 10);
        songsTable.setIntercellSpacing(new Dimension(dim));

        JScrollPane scrollPane = new JScrollPane(songsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1000, 700));

        songsList.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        songsList.setLayout(new BoxLayout(songsList, BoxLayout.Y_AXIS));
        songsList.add(scrollPane);
        songsList.add(songsBotPanel());
        songsList.setBackground(ColorConstants.BACKGROUND.getColor());
        return songsList;
    }

    public void reloadSongs() {
        for (Song s : this.event.getUserSongs()) addSongToTable(s);
    }

    public JPanel addToPlaylist(){
        JPanel playlistsList = new JPanel();
        Font f = new Font(null, Font.PLAIN, 16);
        //TODO: Listers de la taula: https://www.javatpoint.com/java-jtable
        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // Create columns
        playlists.addColumn("Playlist");

        //TODO: add Playlists from database
        for (int i = 0; i < 100; i++) addPlaylistToTable(String.valueOf(i));

        playlistsTable.setBounds(30, 40, 80, 90);
        playlistsTable.setFont(f);
        Dimension dim = new Dimension(20, 2);
        playlistsTable.setRowHeight(playlistsTable.getRowHeight() + 10);
        playlistsTable.setIntercellSpacing(new Dimension(dim));

        JScrollPane scrollPane = new JScrollPane(playlistsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1000, 700));

        playlistsList.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        playlistsList.setLayout(new BoxLayout(playlistsList, BoxLayout.Y_AXIS));
        playlistsList.add(scrollPane);
        playlistsList.add(playlistsBotPanel());
        playlistsList.setBackground(ColorConstants.BACKGROUND.getColor());

        return playlistsList;
    }

    public JPanel playlistsBotPanel() {
        Font f = new Font(null, Font.BOLD, 20);
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        backToSongsButton = new GenericButton(view.Icon.GOBACK.getIcon());
        addToPlaylistButton = new GenericButton("+");

        backToSongsButton.addActionListener(this);
        addToPlaylistButton.addActionListener(this);

        panel.add(backToSongsButton);
        panel.add(addToPlaylistButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    public JPanel songsBotPanel() {
        Font f = new Font(null, Font.BOLD, 20);
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        playSongButton = new GenericButton(view.Icon.PLAY.getIcon());
        addSongButton = new GenericButton("+");
        removeSongButton = new GenericButton("x");

        playSongButton.addActionListener(this);
        addSongButton.addActionListener(this);
        removeSongButton.addActionListener(this);

        panel.add(playSongButton);
        panel.add(addSongButton);
        panel.add(removeSongButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    private void addSongToTable(Song song) {
        songs.addRow(new Object[]{song.getName(), song.getArtist(), song.getDuration(), song.getScore()});
    }

    private void clearSongs() {
        int count;

        count = this.songs.getRowCount();
        while (count > 0){
            this.songs.removeRow(count-1);

            count = this.songs.getRowCount();
        }
    }

    private void addPlaylistToTable(String title) {
        playlists.addRow(new Object[]{title});
    }

    private void removeSongFromTable(int rowNum) {
        songs.removeRow(rowNum);
    }

    private void popUpNoSongSelected() {
        JOptionPane.showMessageDialog(this, "You haven't selected a song. ლ(ಠ益ಠლ)");
    }

    private void popUpNoPlaylistSelected() {
        JOptionPane.showMessageDialog(this, "You haven't selected a playlist. ლ(ಠ益ಠლ)");
    }

    private void popUpSongsDeleted(ArrayList<String> titles) {
        String msg = "";
        for(int i = 0; titles.size() > i ; i++ ){
            msg = msg + titles.get(i);
            if(i == 5){
                msg = msg + " and " + String.valueOf((titles.size() - 4)) + " more songs";
                break;
            } else {
                msg = msg + ", ";
            }
        }
        if(titles.size() == 1){
            msg = msg + " was";
            msg = msg.replace(",", "");
        } else {
            msg = msg + " were";
        }
        msg = msg + " deleted.";
        JOptionPane.showMessageDialog(this, msg);
    }

    public void popUpSongsAdded() {
        JOptionPane.showMessageDialog(this, "Songs added to playlist. (◕‿◕✿)");
    }

    private String getSongTitle(int pos){
        return String.valueOf(songsTable.getModel().getValueAt(pos, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<String> titles = new ArrayList<>();
        int[] selectedSongs = songsTable.getSelectedRows();
        int[] selectedPlaylists = playlistsTable.getSelectedRows();

        if (e.getSource() == playSongButton) {

        } else if (e.getSource() == addSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                cl.show(mainContent, ("playlists"));
            }
        } else if (e.getSource() == removeSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                //TODO: remove from database & lists
                for (int i = selectedSongs.length - 1; i >= 0; i--) {
                    titles.add(getSongTitle(selectedSongs[i]));
                    removeSongFromTable(selectedSongs[i]);
                }
                popUpSongsDeleted(titles);


            }
        } else if (e.getSource() == backToSongsButton) {
            cl.show(mainContent, ("songs"));
        } else if (e.getSource() == addToPlaylistButton) {
            if (selectedPlaylists.length == 0) this.popUpNoPlaylistSelected();
            else {
                for (int i = selectedPlaylists.length - 1; i >= 0; i--) {
                    // playlist
                    for (int j = selectedSongs.length - 1; j >= 0; j--) {
                        // song
                    }
                }
                this.popUpSongsAdded();
                cl.show(mainContent,("songs"));

                //TODO: update database & list
            }
        }
    }
}
