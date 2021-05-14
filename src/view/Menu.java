package view;

import controller.LoginEvent;
import controller.MenuEvent;
import controller.SongsEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    public static final int HEIGHT = 900;
    public static final int WIDTH  = 1600;
    private JButton playButton, loopButton, nextButton, backButton, shuffleButton;
    private JButton songsButton, playlistButton, pianoButton, rankingButton, settingsButton,exitButton;
    private JLabel playingSong;
    private MenuEvent event;
    private JFrame window;
    private JPanel mainContent;
    private CardLayout cl;

    private LogIn login;

    public Menu(LoginEvent loginE, MenuEvent menuE, SongsEvent songsE) {
        this.event = menuE;
        window = new JFrame("Piano TIME!");
        ImageIcon img = new ImageIcon("images\\icon.jpg");
        window.setIconImage(img.getImage());
        window.setVisible(false);

        // Login / Register view init //TODO fix
        this.login = new LogIn(loginE, window);

        // Show main view
        window.setSize(WIDTH,HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add static top & bot panels
        window.add(botPanel(), BorderLayout.SOUTH);
        window.add(topPanel(), BorderLayout.NORTH);

        // Adding layouts
        mainContent = new JPanel(new CardLayout());
        mainContent.add(new Songs(songsE), "songs");
        mainContent.add(new Playlist(), "playlists");
        mainContent.add(new Piano(), "piano");
        mainContent.add(new Ranking(), "ranking");
        mainContent.add(new Settings(), "settings");
        window.add(mainContent);
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("songs"));
        songsButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());

    }

    public void start() {
        this.login.setVisible(true);
        if (/* TODO: Controller.getUser*/ false) {
            window.dispose();
            return;
        }
        window.setVisible(true);
    }

    public JPanel topPanel(){
        JPanel panel = new JPanel();
        // TODO: Upgrade
        panel.setLayout(new GridLayout(0,6));

        songsButton = new JButton("Songs");
        songsButton.setBackground(ColorConstants.BUTTON.getColor());
        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        songsButton.setFont(new Font("Arial", Font.BOLD,20));

        playlistButton = new JButton("Playlists");
        playlistButton.setBackground(ColorConstants.BUTTON.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setFont(new Font("Arial", Font.BOLD,20));

        pianoButton = new JButton("Piano");
        pianoButton.setBackground(ColorConstants.BUTTON.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setFont(new Font("Arial", Font.BOLD,20));

        rankingButton = new JButton("Ranking");
        rankingButton.setBackground(ColorConstants.BUTTON.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setFont(new Font("Arial", Font.BOLD,20));

        settingsButton = new JButton("Settings");
        settingsButton.setBackground(ColorConstants.BUTTON.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setFont(new Font("Arial", Font.BOLD,20));

        exitButton = new JButton("Exit");
        exitButton.setBackground(ColorConstants.BUTTON.getColor());
        exitButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        exitButton.setFont(new Font("Arial", Font.BOLD,20));

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

    public JPanel botPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());
        // TODO: Upgrade
        panel.setLayout(new GridLayout(0,2));

        JPanel currentSong = new JPanel();
        currentSong.setBackground(ColorConstants.MENU.getColor());
        playingSong = new JLabel("Select a song or a playlist to play - ♬ლ(▀̿Ĺ̯▀̿ ̿ლ)♬");
        JPanel playerMenu = songPlayerMenu();

        currentSong.setSize(new Dimension(WIDTH/2, 150));
        playerMenu.setSize(new Dimension(WIDTH/2, 150));

        currentSong.add(playingSong, BorderLayout.CENTER);

        panel.add(currentSong);
        panel.add(playerMenu);

        return panel;
    }

    public JPanel songPlayerMenu(){
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

    private void resetButtonsColors(){
        songsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        playlistButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        pianoButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        rankingButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
        settingsButton.setForeground(ColorConstants.TOP_BUTTON_FONT.getColor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loopButton) {
            if(event.toggleLoop()){
                loopButton.setForeground(Color.LIGHT_GRAY);
                // TODO: player.setLoopState(false);
            } else {
                loopButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
                // TODO: player.setLoopState(true);
            }
        } else if (e.getSource() == backButton) {
            if(event.currentSongPos() > 1){
                //TODO: player.playBackSong();
                playingSong.setText("back" /*TODO : player.getPlayingSongTitle() + - + player.getPlayingSongArtist*/);
            }
        } else if (e.getSource() == playButton) {
            if(event.playing()){
                playButton.setText(Icon.PAUSE.getIcon());
                // TODO: player.pausePlay();
            } else {
                playButton.setText(Icon.PLAY.getIcon());
                //TODO player.resumePlay();
            }
        } else if (e.getSource() == nextButton) {
            if(event.currentSongPos() < 10 /* TODO: player.playingListSize()*/){
                //TODO: player.playNextSong();
                playingSong.setText("next" /*TODO : player.getPlayingSongTitle() + - + player.getPlayingSongArtist*/);
            }
        } else if (e.getSource() == songsButton) {
            cl.show(mainContent, ("songs"));

            resetButtonsColors();
            songsButton.setForeground(ColorConstants.ACTIVE_BUTTON.getColor());
        } else if (e.getSource() == playlistButton) {
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
            window.dispose();
        }
    }

    /* LOGIN/REGISTER FUNCTIONS */
    public void disposeLogin() {
        this.login.dispose();
    }

    public void wrongLogin() {
        this.login.wrongLogin();
    }

    public void userCreated() {

    }

    public void wrongCreation() {

    }
}
