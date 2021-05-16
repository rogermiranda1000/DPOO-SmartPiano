package view;

import controller.PlaylistEvent;
import entities.Song;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Playlist extends JPanel implements ActionListener, ListSelectionListener {
    private final DefaultListModel<String> playlistName;
    private final JList<String> playlistList;
    private final DefaultListModel<Song> songsName;
    private final JList<Song> songsList;
    private JButton playPlaylistButton;
    private JButton removeSongButton;
    private JButton removePlaylistButton;
    private JButton createPlaylistButton;
    private List<String> selectedValuesList;

    private final PlaylistEvent event;

    public Playlist(PlaylistEvent playlistE) {
        this.event = playlistE;

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

        this.add(playlistView);
    }

    public void reloadPlaylists() {
        this.playlistName.clear();
        this.playlistName.addAll(this.event.getPlaylists());
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
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        createPlaylistButton = new GenericButton(view.Icon.ADD.getIcon());
        playPlaylistButton = new GenericButton(view.Icon.PLAY.getIcon());
        removePlaylistButton = new GenericButton("X");
        removeSongButton = new GenericButton("X", new Font(null, Font.PLAIN, 20));

        playPlaylistButton.addActionListener(this);
        removePlaylistButton.addActionListener(this);
        removeSongButton.addActionListener(this);
        createPlaylistButton.addActionListener(this);

        panel.add(createPlaylistButton);
        panel.add(playPlaylistButton);
        panel.add(removePlaylistButton);
        panel.add(removeSongButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    protected void updateSongsList(String playlist) {
        this.songsName.clear();
        this.songsName.addAll(this.event.getSongs(playlist));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playPlaylistButton) {
            // TODO: controller.playPlaylist(String.valueOf(selectedValuesList.get(0));
        } else if (e.getSource() == removePlaylistButton) {
            // TODO: controller.deletePlaylist(String.valueOf(selectedValuesList.get(0));
        } else if (e.getSource() == removeSongButton) {
            // TODO: controller.deleteSongsFromPlaylist(String.valueOf(selectedValuesList.get(0), selectedSongs);
        } else if (e.getSource() == createPlaylistButton) {
            new CreatePlaylistPopUp("Create playlist");

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            this.selectedValuesList = this.playlistList.getSelectedValuesList();
            this.updateSongsList(this.selectedValuesList.get(0));
            this.removeSongButton.setVisible(true);
        }
    }
}
