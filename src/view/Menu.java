package view;

import controller.*;
import entities.Song;
import entities.SongNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JFrame that contains everything in the app once the user has logged in
 */
public class Menu extends JFrame implements ActionListener, SongsMenuNotifier, PlaylistMenuNotifier, DeleteUserNotifier, PianoNotifier, ConfigLoadNotifier, NewPlayNotifier {

    /**
     * Height of the window
     */
    public static final int HEIGHT = 900;

    /**
     * Width of the window
     */
    public static final int WIDTH = 1600;

    /**
     * Button used to access the "Songs" tab
     */
    private JButton songsButton;

    /**
     * Button used to access the "Playlists" tab
     */
    private JButton playlistButton;

    /**
     * Button used to access the "Piano" tab
     */
    private JButton pianoButton;

    /**
     * Button used to access the "Ranking" tab
     */
    private JButton rankingButton;

    /**
     * Button used to access the "Settings" tab
     */
    private JButton settingsButton;

    /**
     * Button used to log out from the app
     */
    private JButton exitButton;

    /**
     * Object to notify when the user wants to exit the app
     */
    private final MenuEvent event;

    /**
     * Panel that displays the content of the chosen tab in the center
     */
    private final JPanel mainContent;

    /**
     * Used to switch the contents of the view according to the selected tab
     */
    private final CardLayout cl;

    /**
     * Panel with the contents of the "Songs" tab
     */
    private final Songs songs;

    /**
     * Panel with the contents of the "Playlists" tab
     */
    private final Playlist playlist;

    /**
     * Panel with the contents of the "Piano" tab
     */
    private final Piano piano;

    /**
     * Panel with the contents of the "Settings" tab
     */
    private final Settings settings;

    /**
     * Panel with the contents of the "Ranking" tab
     */
    private final Ranking ranking;

    /**
     * Sets up all the content in the panel and initiates every tab and the bottom player
     * @param playE Manages events from the Music player
     * @param songRequestE Manages new songs to be played on the music player
     * @param menuE Manages the exiting of the session
     * @param songsE Manages events regarding songs and playlists
     * @param playlistE Manages events related to playlists, removing, adding, getting...
     * @param rankingE Manages events regarding statistics information
     * @param keyE Manages events regarding key presses
     * @param configE Manages events regarding the update of the database from config changes
     * @param recordE Manages events relating to the recording of songs by the user
     * @param sRP Manages the requests of playing a song in the piano
     */
    public Menu(PlaylistBarEvent playE, SongRequest songRequestE, MenuEvent menuE, SongsEvent songsE, PlaylistEvent playlistE, RankingEvent rankingE, TeclaEvent keyE, UpdateConfigEvent configE, RecordingEvent recordE, SongRequestPiano sRP) {
        this.event = menuE;

        this.setTitle("Piano TIME!");
        ImageIcon img = new ImageIcon("images\\icon.jpg");
        this.setIconImage(img.getImage());
        this.setVisible(false);

        // Show main view
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add static top & bot panels
        this.add(new BottomPanel(playE), BorderLayout.SOUTH);
        this.add(topPanel(), BorderLayout.NORTH);

        // Adding layouts
        this.playlist = new Playlist(playlistE);
        this.songs = new Songs(songsE, songRequestE, sRP);
        mainContent = new JPanel(new CardLayout());
        mainContent.add(this.songs, "songs");
        mainContent.add(this.playlist, "playlists");

        piano = new Piano(keyE, recordE);
        mainContent.add(piano, "piano");
        ranking = new Ranking(rankingE);
        mainContent.add(ranking, "ranking");
        this.settings = new Settings(configE);
        mainContent.add(this.settings, "settings");
        this.add(mainContent);
        cl = (CardLayout) (mainContent.getLayout());

        // default view
        cl.show(mainContent, ("ranking"));
        rankingButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
    }

    /**
     * Puts the focus on piano to enable the key listeners to work
     */
    public void focusPiano() {
        SwingUtilities.invokeLater(()-> {
            cl.show(mainContent, ("piano"));
            piano.requestFocus();

            resetButtonsColors();
            pianoButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        });
    }

    /**
     * Sets up a Panel with the buttons for switching tabs
     * @return A panel with every button set up and an action listener associated
     */
    public JPanel topPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 6));
        Font f = new Font("Arial", Font.BOLD, 20);

        songsButton = new GenericButton("Songs",f);
        playlistButton = new GenericButton("Playlist",f);
        pianoButton = new GenericButton("Piano",f);
        rankingButton = new GenericButton("Ranking",f);
        settingsButton = new GenericButton("Settings",f);
        exitButton = new GenericButton("Log out",f);

        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        exitButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());

        songsButton.addActionListener(this);
        playlistButton.addActionListener(this);
        pianoButton.addActionListener(this);
        rankingButton.addActionListener(this);
        settingsButton.addActionListener(this);
        exitButton.addActionListener(this);

        songsButton.setFocusable(false);
        playlistButton.setFocusable(false);
        pianoButton.setFocusable(false);
        rankingButton.setFocusable(false);
        settingsButton.setFocusable(false);
        exitButton.setFocusable(false);

        panel.add(songsButton);
        panel.add(playlistButton);
        panel.add(pianoButton);
        panel.add(rankingButton);
        panel.add(settingsButton);
        panel.add(exitButton);

        return panel;
    }

    /**
     * Sets every tab's button color to default
     */
    private void resetButtonsColors() {
        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
    }

    /**
     * Asks for confirmation for when the user logs out
     * @return The user's answer
     */
    private int exitMessage() {
        return JOptionPane.showConfirmDialog(null, "Do you want to logout?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Called when a button was pressed. Switches between tabs
     * @param e Event that triggered this function
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /* TOP BAR BUTTONS */
        if (e.getSource() == songsButton) {
            // s'entrarà a songs -> recargar
            this.songs.reloadSongs();
            this.songs.reloadPlaylists();
            cl.show(mainContent, ("songs"));

            resetButtonsColors();
            songsButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == playlistButton) {
            this.playlist.reloadPlaylists(); // s'entrarà a playlists -> recargar
            cl.show(mainContent, ("playlists"));

            resetButtonsColors();
            playlistButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == pianoButton) {
            this.focusPiano();
        } else if (e.getSource() == rankingButton) {
            cl.show(mainContent, ("ranking"));

            resetButtonsColors();
            rankingButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == settingsButton) {
            cl.show(mainContent, ("settings"));

            resetButtonsColors();
            settingsButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == exitButton) {
            if (exitMessage() == JOptionPane.YES_OPTION) {
                this.event.exitSession();
            }


        }
    }

    /**
     * Loads a key configuration into the piano
     * @param config Characters ordered from low to high
     */
    public void loadConfig(char[] config) {
        this.piano.loadConfig(config);
    }

    /**
     * Notifies the "Songs" tab that a song was deleted
     * @param song The song that was deleted
     */
    @Override
    public void songDeleted(Song song) {
        this.songs.songDeleted(song);
    }

    /**
     * Notifies the "Songs" tab that a song couldn't be added successfully
     * @param song The song that couldn't be added
     */
    @Override
    public void unableToAddSong(Song song) {
        this.songs.unableToAddSong(song);
    }

    /**
     * Notifies the "Songs" tab that a song was added successfully
     * @param song The song that was added
     */
    @Override
    public void songAdded(Song song) {
        this.songs.songAdded(song);
    }

    /**
     * Notifies the "Songs" tab that a song couldn't be deleted successfully
     * @param song The song that couldn't be deleted
     */
    @Override
    public void unableToDeleteSong(Song song) {
        this.songs.unableToDeleteSong(song);
    }

    /**
     * Notifies the "Playlist" tab that a playlist was created successfully
     */
    @Override
    public void playlistCreated() {
        this.playlist.playlistCreated();
    }

    /**
     * Notifies the "Playlist" tab that a playlist couldn't be created successfully
     */
    @Override
    public void playlistNotCreated() {
        this.playlist.playlistNotCreated();
    }

    /**
     * Notifies the "Playlist" tab that a playlist was deleted successfully
     */
    @Override
    public void playlistDeleted() {
        this.playlist.playlistDeleted();
    }

    /**
     * Notifies the "Playlist" tab that a playlist couldn't be deleted successfully
     */
    @Override
    public void playlistNotDeleted() {
        this.playlist.playlistNotDeleted();
    }

    /**
     * Notifies the "Playlist" tab that a song was deleted from a playlist successfully
     */
    @Override
    public void songDeletedFromPlaylist() {
        this.playlist.songDeletedFromPlaylist();
    }

    /**
     * Notifies the "Playlist" tab that a song couldn't be deleted from a playlist successfully
     */
    @Override
    public void songNotDeletedFromPlaylist() {
        this.playlist.songNotDeletedFromPlaylist();
    }

    /**
     * Notifies the "Settings" tab that the user was deleted successfully
     */
    @Override
    public void userDeleted() {
        this.settings.userDeleted();
    }

    /**
     * Notifies the "Settings" tab that the user couldn't be deleted successfully
     */
    @Override
    public void userNotDeleted() {
        this.settings.userNotDeleted();
    }

    /**
     * Makes the piano tab un-press every key to reset them
     */
    @Override
    public void unpressAllKeys() {
        SwingUtilities.invokeLater(()->this.piano.unpressAllKeys());
    }

    /**
     * The PianoController wants to tell the view to press a button
     * @param key Note being played
     */
    @Override
    public void pressKey(SongNote key) {
        SwingUtilities.invokeLater(()->this.piano.pressKey(key));
    }

    /**
     * Asks the user for information about their recorded song
     */
    @Override
    public void requestSongInformation() {
        this.piano.requestSongInformation();
    }

    /**
     * Loads the volume values on the sliders of the "Settings" tab
     * @param songVolume Volume of the music player (0 = silent, 1 = max volume)
     * @param pianoVolume Volume of the piano player (0 = silent, 1 = max volume)
     */
    @Override
    public void setConfig(float songVolume, float pianoVolume) {
        SwingUtilities.invokeLater(()->this.settings.setConfig(songVolume, pianoVolume));
    }

    /**
     * Loads the name and email of the user on the "Settings" tab
     * @param name Name of the user
     * @param email Email of the user
     */
    @Override
    public void setUserInformation(String name, String email) {
        SwingUtilities.invokeLater(()->this.settings.setUserInformation(name, email));
    }

    /**
     * There's a new play -> the graphs needs to be updated
     */
    @Override
    public void reloadGraphs() {
        SwingUtilities.invokeLater(()->this.ranking.reloadGraphs());
    }
}
