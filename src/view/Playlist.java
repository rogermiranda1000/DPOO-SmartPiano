package view;

import controller.SongsEvent;

import javax.swing.*;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Playlist extends JPanel {
    private SongsEvent event;
    private DefaultListModel playlistName = new DefaultListModel();
    private JList playlistList = new JList(playlistName);
    private DefaultListModel songsName = new DefaultListModel();
    private JList songsList = new JList(songsName);
    private JButton playPlaylistButton, removeSongButton, removePlaylistButton;


    public JPanel playlistList() {
        JPanel r = new JPanel();

        playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlistList.setSelectedIndex(0);
        playlistList.setVisibleRowCount(25);
        playlistList.setFixedCellWidth(200);
        playlistList.setFixedCellHeight(25);
        JScrollPane fruitListScrollPane = new JScrollPane(playlistList);


        songsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        songsList.setSelectedIndex(0);
        songsList.setVisibleRowCount(25);
        songsList.setFixedCellWidth(200);
        songsList.setFixedCellHeight(25);
        JScrollPane vegListScrollPane = new JScrollPane(songsList);

        r.add(fruitListScrollPane);
        r.add(vegListScrollPane);

        r.setVisible(true);
        return r;
    }


    public JPanel playlistBotPanel() {
        Font f = new Font(null, Font.BOLD, 20);
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        playPlaylistButton = new JButton(view.Icon.PLAY.getIcon());
        playPlaylistButton.setBackground(ColorConstants.BUTTON.getColor());
        playPlaylistButton.setForeground(Color.LIGHT_GRAY);
        playPlaylistButton.setVisible(true);
        playPlaylistButton.setFont(f);
        playPlaylistButton.setFocusable(false);

        removePlaylistButton = new JButton("X");
        removePlaylistButton.setBackground(ColorConstants.BUTTON.getColor());
        removePlaylistButton.setForeground(Color.LIGHT_GRAY);
        removePlaylistButton.setVisible(true);
        removePlaylistButton.setFont(f);
        removePlaylistButton.setFocusable(false);

        removeSongButton = new JButton("X");
        removeSongButton.setBackground(ColorConstants.BUTTON.getColor());
        removeSongButton.setForeground(Color.LIGHT_GRAY);
        removeSongButton.setVisible(true);
        removeSongButton.setFont(new Font(null, Font.PLAIN, 20));
        removeSongButton.setFocusable(false);
        removeSongButton.setVisible(false);

        /*
        playPlaylistButton.addActionListener(this);
        removePlaylistButton.addActionListener(this);
        removeSongButton.addActionListener(this);
        */

        panel.add(playPlaylistButton);
        panel.add(removePlaylistButton);
        panel.add(removeSongButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }


    Playlist() {
        this.setBackground(ColorConstants.BACKGROUND.getColor());
        JPanel playlistView = new JPanel();

        playlistView.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        playlistView.setBackground(ColorConstants.BACKGROUND.getColor());
        playlistView.setLayout(new BoxLayout(playlistView, BoxLayout.Y_AXIS));
        playlistView.add(playlistList());
        playlistView.add(playlistBotPanel());

        for(int i = 0; i < 50; i++) {
            playlistName.addElement(i);
            songsName.addElement(i);
        }

        this.add(playlistView);
    }
}
