package view;

import controller.PlaylistEvent;
import entities.Song;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Initializes the panel of the "Playlists" tab and implements its functionalities
 */
public class Playlist extends JPanel implements ActionListener, ListSelectionListener, PlaylistMenuNotifier {

    /**
     * Structure that holds the names of the playlists
     */
    private final DefaultListModel<String> playlistName;

    /**
     * All the user's playlists
     */
    private final JList<String> playlistList;

    /**
     * Holds the names of the songs of the playlist
     */
    private final DefaultListModel<Song> songsName;

    /**
     * All the songs in the playlist
     */
    private final JList<Song> songsList;

    /**
     * Button to start playing the selected playlist
     */
    private JButton playPlaylistButton;

    /**
     * Button to start playing the selected playlist
     */
    private JButton removeSongButton;

    /**
     * Button to remove the selected playlist
     */
    private JButton removePlaylistButton;

    /**
     * Button to start playing the selected playlist
     */
    private JButton createPlaylistButton;

    /**
     * Object to notify for playlist events
     */
    private final PlaylistEvent event;

    /**
     * Sets up the panel with both columns and it's buttons
     * @param playlistE Object to notify for playlist events
     */
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

    /**
     * Reloads the playlists being shown in the list
     */
    public void reloadPlaylists() {
        this.playlistName.clear();
        this.playlistName.addAll(this.event.getPlaylists());
    }

    /**
     * Sets up the panel with the list of playlists
     * @return Panel with all the needed contents
     */
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

    /**
     * Sets up the panel that holds the buttons
     * @return Panel with all the buttons set up
     */
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

    /**
     * Updates the songs list for the newly selected playlist
     */
    protected void updateSongsSelectedList() {
        this.songsName.clear();

        if (this.playlistList.getSelectedValuesList().size() == 0) return;
        this.songsName.addAll(this.event.getPlaylistSongs(this.playlistList.getSelectedValuesList().get(0)));
    }

    /**
     * Called when one of the buttons is pressed, does the required action depending on which called it
     * @param e Event that called this function
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createPlaylistButton) {
            new CreatePlaylistPopUp(this.event);
        } else if (this.playlistList.getSelectedValuesList().size() == 0) {
            JOptionPane.showMessageDialog(this, "Select a playlist first.");
        } else {
            if (e.getSource() == playPlaylistButton) {
                this.event.requestPlayList(this.playlistList.getSelectedValuesList().get(0));
            } else if (e.getSource() == removePlaylistButton) {
                this.event.removePlaylist(this.playlistList.getSelectedValuesList().get(0));
            } else if (e.getSource() == removeSongButton) {
                if (this.songsList.getSelectedValuesList().size() == 0) JOptionPane.showMessageDialog(this, "Select a song first.");
                else this.event.removeSongPlaylist(this.playlistList.getSelectedValuesList().get(0), this.songsList.getSelectedValuesList().get(0));
            }
        }
    }

    /**
     * When a new playlist is selected this gets called
     * @param e Event that called this function
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        if (e.getSource() == this.playlistList) this.updateSongsSelectedList();
    }

    /**
     * The new playlist was created
     */
    @Override
    public void playlistCreated() {
        this.reloadPlaylists();
        JOptionPane.showMessageDialog(this, "Playlist created.");
    }

    /**
     * The playlist couldn't be created
     */
    @Override
    public void playlistNotCreated() {
        JOptionPane.showMessageDialog(this, "This playlist already exists!");
    }

    /**
     * The playlist was deleted
     */
    @Override
    public void playlistDeleted() {
        this.reloadPlaylists();
        this.updateSongsSelectedList();
        JOptionPane.showMessageDialog(this, "Playlist deleted.");
    }

    /**
     * The playlist couldn't be deleted
     */
    @Override
    public void playlistNotDeleted() {
        // no hauria d'arribar aquí mai
        JOptionPane.showMessageDialog(this, "Error deleting the playlist.");
    }

    /**
     * The song was deleted from the playlist
     */
    @Override
    public void songDeletedFromPlaylist() {
        this.updateSongsSelectedList();
    }

    /**
     * The song couldn't be deleted from the playlist
     */
    @Override
    public void songNotDeletedFromPlaylist() {
        // no hauria d'arribar aquí mai
        JOptionPane.showMessageDialog(this, "Error deleting the song.");
    }
}
