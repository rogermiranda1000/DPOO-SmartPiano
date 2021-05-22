package view;

import controller.SongRequest;
import controller.SongRequestPiano;
import controller.SongsEvent;
import entities.Song;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Initializes the "Songs" tab, and implements its functionalities
 */
public class Songs extends JPanel implements ActionListener, SongsMenuNotifier {

    /**
     * Model the song table will have
     */
    private final DefaultTableModel songs;

    /**
     * Table that holds the songs
     */
    private final JTable songsTable;

    /**
     * Model the playlist table will have (for when inserting a song into a playlist)
     */
    private final DefaultTableModel playlists;

    /**
     * Table that holds the available playlists (for when inserting a song into a playlist)
     */
    private final JTable playlistsTable;

    /**
     * Object to notify song related actions to
     */
    private final SongsEvent event;

    /**
     * Object to request songs to be played in the music player
     */
    private final SongRequest requestEvent;

    /**
     * Object to request songs to be played in the piano
     */
    private final SongRequestPiano playSongPiano;

    /**
     * Button used to play the song in the music player
     */
    private JButton playSongButton;

    /**
     * Button used to play the song in the piano
     */
    private JButton playSongButtonInPiano;

    /**
     * Button used to add songs into a playlist
     */
    private JButton addSongButton;

    /**
     * Button used to delete songs
     */
    private JButton removeSongButton;

    /**
     * Button used to return to the main songs view when adding to a playlist
     */
    private JButton backToSongsButton;

    /**
     * Button used to add songs into a playlist
     */
    private JButton addToPlaylistButton;

    /**
     * Panel that stores the contents of this tab
     */
    private final JPanel mainContent;

    /**
     * Used to switch between the main "songs" view, and the add to "playlist" view
     */
    private final CardLayout cl;

    /**
     * Initializes the variables and sets up the panels
     * @param songE Object to notify song related actions to
     * @param requestE Object to request songs to be played in the music player
     * @param playSongP Object to request songs to be played in the piano
     */
    public Songs(SongsEvent songE, SongRequest requestE, SongRequestPiano playSongP) {
        this.songs = new UneditableTable();
        this.songsTable = new JTable(this.songs);

        this.playlists = new UneditableTable();
        this.playlistsTable = new JTable(this.playlists);

        this.event = songE;
        this.requestEvent = requestE;
        this.playSongPiano = playSongP;

        mainContent = new JPanel(new CardLayout());
        mainContent.add(songsList(), "songs");
        mainContent.add(addToPlaylist(), "playlists");
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("songs"));

        this.add(mainContent);
        this.setVisible(true);
    }


    /**
     * Creates the song list table and stores it in a panel
     * @return A Panel containing the song list
     */
    public JPanel songsList(){
        JPanel songsList = new JPanel();
        Font f = new Font(null, Font.PLAIN, 16);

        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // Create columns
        songs.addColumn("Title");
        songs.addColumn("Artist");
        songs.addColumn("Date");
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

    /**
     * Updates the songs being displayed on the song list
     */
    public void reloadSongs() {
        this.clearSongsTable();
        for (Song s : this.event.getUserSongs()) this.addSongToTable(s);
    }

    /**
     * Updates the playlists on the playlist list
     */
    public void reloadPlaylists() {
        this.clearPlaylistTable();
        for (String l : this.event.getPlaylists()) this.addPlaylistToTable(l);
    }

    /**
     * Sets up a panel for choosing what playlist to add songs to
     * @return Panel with a list of playlists
     */
    public JPanel addToPlaylist(){
        JPanel playlistsList = new JPanel();
        Font f = new Font(null, Font.PLAIN, 16);

        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // Create columns
        playlists.addColumn("Playlist");

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

    /**
     * Generates the bottom panel of the playlist view
     * @return A panel with the buttons used to navigate the playlist selection for adding a song
     */
    public JPanel playlistsBotPanel() {
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

    /**
     * Generates the bottom panel of the songs view
     * @return A panel with buttons to add, play (in piano and player) and delete songs
     */
    public JPanel songsBotPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        playSongButton = new GenericButton(view.Icon.PLAY.getIcon());
        playSongButtonInPiano = new GenericButton("Play in piano");
        addSongButton = new GenericButton("+");
        removeSongButton = new GenericButton("x");

        playSongButton.addActionListener(this);
        playSongButtonInPiano.addActionListener(this);
        addSongButton.addActionListener(this);
        removeSongButton.addActionListener(this);

        panel.add(playSongButtonInPiano);
        panel.add(playSongButton);
        panel.add(addSongButton);
        panel.add(removeSongButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    /**
     * Adds a song to the songs table
     * @param song Song to add to the table
     */
    private void addSongToTable(Song song) {
        songs.addRow(new Object[]{song.getName(), song.getArtist(), song.getDate(), (int)Math.floor(song.getDuration()), String.format("%.1f", song.getScore())});
    }

    /**
     * Returns the song at a specific row of the table
     * @param row Row to search the song on
     * @return The song in the selected row of the table
     */
    private Song getSongFromTable(int row) {
        // cada fila té Object[]{song, song.getArtist(), song.getDate(), song.getDuration(), song.getScore()}
        return new Song((String) this.songs.getValueAt(row, 0), (String) this.songs.getValueAt(row, 1), (String) this.songs.getValueAt(row, 2));
    }

    /**
     * Removes every song from the song table
     */
    private void clearSongsTable() {
        int count = this.songs.getRowCount();
        while (count > 0){
            this.songs.removeRow(count-1);
            count = this.songs.getRowCount();
        }
    }

    /**
     * Adds a playlist to the playlist table
     * @param title Title of the list to add
     */
    private void addPlaylistToTable(String title) {
        playlists.addRow(new Object[]{title});
    }

    /**
     * Removes every playlist from the playlist table
     */
    private void clearPlaylistTable() {
        int rowCount = this.playlists.getRowCount();
        while (rowCount > 0) {
            this.playlists.removeRow(rowCount - 1);
            rowCount = this.playlists.getRowCount();
        }
    }

    /**
     * Generates a popup for when a user tries to do an action with no song selected
     */
    private void popUpNoSongSelected() {
        JOptionPane.showMessageDialog(this, "You haven't selected a song. ლ(ಠ益ಠლ)");
    }

    /**
     * Generates a popup for when a user tries to do an action with no playlist selected
     */
    private void popUpNoPlaylistSelected() {
        JOptionPane.showMessageDialog(this, "You haven't selected a playlist. ლ(ಠ益ಠლ)");
    }

    /**
     * Generates a popup for when a song was added to a playlist
     */
    public void popUpSongsAdded() {
        JOptionPane.showMessageDialog(this, "Finished adding to playlist. (◕‿◕✿)");
    }

    /**
     * Donada una fila de la taula retorna la canço
     * @param pos Fila
     * @return Canço indicada a la fila
     */
    private Song getSong(int pos){
        return new Song((String) songsTable.getModel().getValueAt(pos, 0), (String) songsTable.getModel().getValueAt(pos, 1), (String) songsTable.getModel().getValueAt(pos, 2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedSongs = songsTable.getSelectedRows();
        int[] selectedPlaylists = playlistsTable.getSelectedRows();

        if (e.getSource() == playSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                this.requestEvent.requestSong(this.getSongFromTable(selectedSongs[0])); // TODO multiple
            }
        } else if(e.getSource() == playSongButtonInPiano){
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                this.playSongPiano.requestSongInPiano(this.getSongFromTable(selectedSongs[0]));
            }
        }else if (e.getSource() == addSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                cl.show(mainContent, ("playlists"));
            }
        } else if (e.getSource() == removeSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                for (int i = 0; i < selectedSongs.length; i++) {
                    this.event.deleteSong(this.getSong(selectedSongs[i]));
                }
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
                        this.event.addSongPlaylist(this.getSong(selectedSongs[j]), (String) playlistsTable.getModel().getValueAt(selectedPlaylists[i], 0));
                    }
                }
                this.popUpSongsAdded();
                cl.show(mainContent,("songs"));
            }
        }
    }

    /**
     * The song couldn't be deleted
     * @param song Song which we tried to delete
     */
    @Override
    public void unableToDeleteSong(Song song) {
        JOptionPane.showMessageDialog(this, song.getName() + " can't be deleted!");
    }

    /**
     * The song was deleted
     * @param song Song which we tried to delete
     */
    @Override
    public void songDeleted(Song song) {
        this.reloadSongs();
        JOptionPane.showMessageDialog(this, song.getName() + " was deleted.");
    }

    /**
     * The song couldn't be added
     * @param song Song which we tried to add
     */
    @Override
    public void unableToAddSong(Song song) {
        JOptionPane.showMessageDialog(this, "Error adding the song " + song.toString() + ".");
    }

    /**
     * The song was added
     * @param song Song which we tried to add
     */
    @Override
    public void songAdded(Song song) {

    }
}
