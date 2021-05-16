package view;

import controller.MenuEvent;
import controller.PlaylistEvent;
import controller.SongsEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener, PlayingSongNotifier {
    public static final int HEIGHT = 900;
    public static final int WIDTH = 1600;
    private JButton playButton, loopButton, nextButton, backButton, shuffleButton;
    private JButton songsButton, playlistButton, pianoButton, rankingButton, settingsButton, exitButton;
    private JLabel playingSong;
    private MenuEvent event;
    private PlaylistBarEvent event;
    private JPanel mainContent;
    private CardLayout cl;

    private final Playlist playlist;
    private final Songs songs;

    public Menu(PlaylistBarEvent playE, MenuEvent menuE, SongsEvent songsE, PlaylistEvent playlistE) {
        this.event = menuE;
        this.event.setPlayingSongListner(this);

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
        this.add(botPanel(), BorderLayout.SOUTH);
        this.add(topPanel(), BorderLayout.NORTH);

        // Adding layouts
        this.playlist = new Playlist(playlistE);
        this.songs = new Songs(songsE);
        mainContent = new JPanel(new CardLayout());
        mainContent.add(this.songs, "songs");
        mainContent.add(this.playlist, "playlists");
        mainContent.add(new Piano(), "piano");
        mainContent.add(new Ranking(), "ranking");
        mainContent.add(new Settings(), "settings");
        this.add(mainContent);
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("songs"));
        songsButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());

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

    public JPanel botPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());
        // TODO: Upgrade
        panel.setLayout(new GridLayout(0, 2));

        JPanel currentSong = new JPanel();
        currentSong.setBackground(ColorConstants.MENU.getColor());
        playingSong = new JLabel("Select a song or a playlist to play - ♬ლ(▀̿Ĺ̯▀̿ ̿ლ)♬");
        JPanel playerMenu = songPlayerMenu();

        currentSong.setSize(new Dimension(WIDTH / 2, 150));
        playerMenu.setSize(new Dimension(WIDTH / 2, 150));

        currentSong.add(playingSong, BorderLayout.CENTER);

        panel.add(currentSong);
        panel.add(playerMenu);

        return panel;
    }

    public JPanel songPlayerMenu() {
        JPanel panel = new JPanel();
        Font f = new Font(null, Font.PLAIN, 15);
        panel.setBackground(ColorConstants.MENU.getColor());

        loopButton = new JButton(Icon.LOOP.getIcon());
        loopButton.setBackground(ColorConstants.BUTTON.getColor());
        loopButton.setForeground(Color.LIGHT_GRAY);
        loopButton.setVisible(true);
        loopButton.setFont(f);

        backButton = new JButton(Icon.BACK.getIcon());
        backButton.setBackground(ColorConstants.BUTTON.getColor());
        backButton.setForeground(Color.LIGHT_GRAY);
        backButton.setVisible(true);
        backButton.setFont(f);

        playButton = new JButton(Icon.PAUSE.getIcon());
        playButton.setBackground(ColorConstants.BUTTON.getColor());
        playButton.setForeground(Color.LIGHT_GRAY);
        playButton.setVisible(true);
        playButton.setFont(f);

        nextButton = new JButton(Icon.NEXT.getIcon());
        nextButton.setBackground(ColorConstants.BUTTON.getColor());
        nextButton.setForeground(Color.LIGHT_GRAY);
        nextButton.setVisible(true);
        nextButton.setFont(f);

        shuffleButton = new JButton(Icon.SHUFFLE.getIcon());
        shuffleButton.setBackground(ColorConstants.BUTTON.getColor());
        shuffleButton.setForeground(Color.LIGHT_GRAY);
        shuffleButton.setVisible(true);
        shuffleButton.setFont(f);

        loopButton.addActionListener(this);
        backButton.addActionListener(this);
        playButton.addActionListener(this);
        nextButton.addActionListener(this);
        shuffleButton.addActionListener(this);

        loopButton.setFocusable(false);
        backButton.setFocusable(false);
        playButton.setFocusable(false);
        nextButton.setFocusable(false);
        shuffleButton.setFocusable(false);

        panel.add(loopButton);
        panel.add(backButton);
        panel.add(playButton);
        panel.add(nextButton);
        panel.add(shuffleButton);

        return panel;
    }

    private void resetButtonsColors() {
        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* BOTTOM BAR BUTTONS */
        if(e.getSource() == loopButton) {
            this.loopButton.setForeground( this.loopButton.getForeground().equals(ColorConstants.ACTIVE_BUTTON.getColor()) ? Color.LIGHT_GRAY : ColorConstants.ACTIVE_BUTTON.getColor() ); // toggle button's color
            this.event.toggleLoop();
        } else if (e.getSource() == backButton) {
            /*if(event.currentSongPos() > 1){
                //TODO: player.playBackSong();
                playingSong.setText("back"); // TODO : player.getPlayingSongTitle() + - + player.getPlayingSongArtist
            }*/
        } else if (e.getSource() == playButton) {
            this.playButton.setText( this.playButton.getText().equals(Icon.PAUSE.getIcon()) ? Icon.PLAY.getIcon() : Icon.PAUSE.getIcon() ); // toggle button's text
            this.event.togglePlaying();
        } else if (e.getSource() == nextButton) {
            /*if(event.currentSongPos() < 10){ // TODO: player.playingListSize()
                //TODO: player.playNextSong();
                playingSong.setText("next" ); // TODO : player.getPlayingSongTitle() + - + player.getPlayingSongArtist
            }*/
        } else if (e.getSource() == this.shuffleButton) {
            this.shuffleButton.setForeground( this.shuffleButton.getForeground().equals(ColorConstants.ACTIVE_BUTTON.getColor()) ? Color.LIGHT_GRAY : ColorConstants.ACTIVE_BUTTON.getColor() ); // toggle button's color
            this.event.toggleRandom();
        }
        /* TOP BAR BUTTONS */
        else if (e.getSource() == songsButton) {
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
            cl.show(mainContent, ("piano"));

            resetButtonsColors();
            pianoButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == rankingButton) {
            cl.show(mainContent, ("ranking"));

            resetButtonsColors();
            rankingButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == settingsButton) {
            cl.show(mainContent, ("settings"));

            resetButtonsColors();
            settingsButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == exitButton) {
            event.exitSession();
        }
    }

    @Override
    public void newSongPlaying(String songName) {
        this.playingSong.setText(songName);
    }
}
