package view;

import controller.SongsEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends JPanel implements ActionListener, ListSelectionListener {
    private SongsEvent event;
    private final DefaultListModel<String> playlistName;
    private final JList<String> playlistList;
    private final DefaultListModel<String> songsName;
    private final JList<String> songsList;
    private JButton playPlaylistButton;
    private JButton removeSongButton;
    private JButton removePlaylistButton;
    private List<String> selectedValuesList;

    public Playlist() {
        this.playlistName = new DefaultListModel<>();
        this.playlistList = new JList<>(playlistName);
        this.songsName = new DefaultListModel<>();
        this.songsList = new JList<>(songsName);

        this.setBackground(ColorConstants.BACKGROUND.getColor());
        JPanel playlistView = new JPanel();

        playlistView.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        playlistView.setBackground(ColorConstants.BACKGROUND.getColor());
        playlistView.setLayout(new BoxLayout(playlistView, BoxLayout.Y_AXIS));
        playlistView.add(playlistList());
        playlistView.add(playlistBotPanel());

        //TODO: ADD PLAYLISTS
        for (int i = 0; i < 50; i++) {
            playlistName.addElement(String.valueOf(i));
        }

        this.add(playlistView);
    }

    public JPanel playlistList() {
        JPanel r = new JPanel();

        playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlistList.setSelectedIndex(0);
        playlistList.setVisibleRowCount(25);
        playlistList.setFixedCellWidth(200);
        playlistList.setFixedCellHeight(25);
        JScrollPane playlistScrollPane = new JScrollPane(playlistList);

        playlistList.addListSelectionListener(this);

        songsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        songsList.setSelectedIndex(0);
        songsList.setVisibleRowCount(25);
        songsList.setFixedCellWidth(200);
        songsList.setFixedCellHeight(25);
        JScrollPane songsListScrollPane = new JScrollPane(songsList);

        r.add(playlistScrollPane);
        r.add(songsListScrollPane);

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

        playPlaylistButton.addActionListener(this);
        removePlaylistButton.addActionListener(this);
        removeSongButton.addActionListener(this);

        panel.add(playPlaylistButton);
        panel.add(removePlaylistButton);
        panel.add(removeSongButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    protected void updateSongsList(String playlist) {
        ArrayList<String> songs = new ArrayList<>();
        this.songsName.clear();
        //TODO: agafar el llistat de cançons de la llista de la base de dades songs = cotroller.getSongsPlaylist(playlist)
        songs.add(this.selectedValuesList.get(0));
        for (String song : songs) {
            this.songsName.addElement(song);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playPlaylistButton) {
            // TODO: controller.playPlaylist(String.valueOf(selectedValuesList.get(0));
        } else if (e.getSource() == removePlaylistButton) {
            // TODO: controller.deletePlaylist(String.valueOf(selectedValuesList.get(0));
        } else if (e.getSource() == removeSongButton) {
            // TODO: controller.deleteSongsFromPlaylist(String.valueOf(selectedValuesList.get(0), selectedSongs);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            this.selectedValuesList = this.playlistList.getSelectedValuesList();
            updateSongsList(String.valueOf(this.selectedValuesList.get(0)));
            this.removeSongButton.setVisible(true);
        }
    }
}
