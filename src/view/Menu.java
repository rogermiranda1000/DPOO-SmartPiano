package view;

import controller.*;
import entities.Song;
import entities.SongNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener, SongsMenuNotifier, PlaylistMenuNotifier, DeleteUserNotifier, PianoNotifier, ConfigLoadNotifier {
    public static final int HEIGHT = 900;
    public static final int WIDTH = 1600;
    private JButton songsButton, playlistButton, pianoButton, rankingButton, settingsButton, exitButton;

    private final MenuEvent event;
    private final JPanel mainContent;
    private final CardLayout cl;

    private final Songs songs;
    private final Playlist playlist;
    private final Piano piano;
    private final Settings settings;
    private final Ranking ranking;

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

    public void focusPiano() {
        cl.show(mainContent, ("piano"));
        piano.requestFocus();

        resetButtonsColors();
        pianoButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
    }

    public JPanel topPanel() {
        JPanel panel = new JPanel();
        // TODO: Upgrade
        panel.setLayout(new GridLayout(0, 6));

        songsButton = new JButton("Songs");
        songsButton.setBackground(ColorConstants.BUTTON.getColor());
        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        songsButton.setFont(new Font("Arial", Font.BOLD, 20));

        playlistButton = new JButton("Playlists");
        playlistButton.setBackground(ColorConstants.BUTTON.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setFont(new Font("Arial", Font.BOLD, 20));

        pianoButton = new JButton("Piano");
        pianoButton.setBackground(ColorConstants.BUTTON.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setFont(new Font("Arial", Font.BOLD, 20));

        rankingButton = new JButton("Ranking");
        rankingButton.setBackground(ColorConstants.BUTTON.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setFont(new Font("Arial", Font.BOLD, 20));

        settingsButton = new JButton("Settings");
        settingsButton.setBackground(ColorConstants.BUTTON.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setFont(new Font("Arial", Font.BOLD, 20));

        exitButton = new JButton("Log out");
        exitButton.setBackground(ColorConstants.BUTTON.getColor());
        exitButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));

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


    private void resetButtonsColors() {
        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
    }

    public void reloadRankingData() {
        this.ranking.reloadGraphs();
    }

    private void exitMessage() {
        JOptionPane.showMessageDialog(this, "The user has logout.", "User logout", JOptionPane.INFORMATION_MESSAGE);
    }

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
            exitMessage();
            this.event.exitSession();
        }
    }

    public void loadConfig(char[] config) {
        this.piano.loadConfig(config);
    }

    @Override
    public void songDeleted(Song song) {
        this.songs.songDeleted(song);
    }

    @Override
    public void unableToAddSong(Song song) {
        this.songs.unableToAddSong(song);
    }

    @Override
    public void songAdded(Song song) {
        this.songs.songAdded(song);
    }

    @Override
    public void unableToDeleteSong(Song song) {
        this.songs.unableToDeleteSong(song);
    }

    @Override
    public void playlistCreated() {
        this.playlist.playlistCreated();
    }

    @Override
    public void playlistNotCreated() {
        this.playlist.playlistNotCreated();
    }

    @Override
    public void playlistDeleted() {
        this.playlist.playlistDeleted();
    }

    @Override
    public void playlistNotDeleted() {
        this.playlist.playlistNotDeleted();
    }

    @Override
    public void songDeletedFromPlaylist() {
        this.playlist.songDeletedFromPlaylist();
    }

    @Override
    public void songNotDeletedFromPlaylist() {
        this.playlist.songNotDeletedFromPlaylist();
    }

    @Override
    public void userDeleted() {
        this.settings.userDeleted();
    }

    @Override
    public void userNotDeleted() {
        this.settings.userNotDeleted();
    }

    @Override
    public void unpressAllKeys() {
        this.piano.unpressAllKeys();
    }

    /**
     * The PianoController wants to tell the view to press a button
     * @param key Note to press
     */
    @Override
    public void pressKey(SongNote key) {
        this.piano.pressKey(key);
    }

    @Override
    public void setConfig(float songVolume, float pianoVolume) {
        this.settings.setConfig(songVolume, pianoVolume);
    }

    @Override
    public void setUserInformation(String name, String email) {
        this.settings.setUserInformation(name, email);
    }
}
