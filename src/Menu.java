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

    JFrame window;

    Menu(LoginEvent loginE, MenuEvent menuE ){
        this.event = menuE;
        window = new JFrame("Piano TIME!");
        window.setVisible(false);

        LogIn l = new LogIn(loginE, window);
        if (/* TODO: Controller.getUser*/ false) {
            window.dispose();
            return;
        }

        window.setSize(WIDTH,HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.add(botPanel(), BorderLayout.SOUTH);
        window.add(topPanel(), BorderLayout.NORTH);
    }

    public JPanel topPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,6));

        songsButton = new JButton("Songs");
        songsButton.setBackground(Constants.COLOR.BUTTON);
        songsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        songsButton.setFont(new Font("Arial", Font.BOLD,20));

        playlistButton = new JButton("Playlist");
        playlistButton.setBackground(Constants.COLOR.BUTTON);
        playlistButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        playlistButton.setFont(new Font("Arial", Font.BOLD,20));

        pianoButton = new JButton("Piano");
        pianoButton.setBackground(Constants.COLOR.BUTTON);
        pianoButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        pianoButton.setFont(new Font("Arial", Font.BOLD,20));

        rankingButton = new JButton("Ranking");
        rankingButton.setBackground(Constants.COLOR.BUTTON);
        rankingButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        rankingButton.setFont(new Font("Arial", Font.BOLD,20));

        settingsButton = new JButton("Settings");
        settingsButton.setBackground(Constants.COLOR.BUTTON);
        settingsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        settingsButton.setFont(new Font("Arial", Font.BOLD,20));

        exitButton = new JButton("Exit");
        exitButton.setBackground(Constants.COLOR.BUTTON);
        exitButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        exitButton.setFont(new Font("Arial", Font.BOLD,20));

        songsButton.addActionListener(this);
        playlistButton.addActionListener(this);
        pianoButton.addActionListener(this);
        rankingButton.addActionListener(this);
        settingsButton.addActionListener(this);
        exitButton.addActionListener(this);

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
        panel.setBackground(Constants.COLOR.MENU);
        panel.setLayout(new GridLayout(0,2));

        JPanel currentSong = new JPanel();
        currentSong.setBackground(Constants.COLOR.MENU);
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
        panel.setBackground(Constants.COLOR.MENU);

        loopButton = new JButton(Constants.ICON.LOOP);
        loopButton.setBackground(Constants.COLOR.BUTTON);
        loopButton.setForeground(Color.LIGHT_GRAY);
        loopButton.setVisible(true);

        backButton = new JButton(Constants.ICON.BACK);
        backButton.setBackground(Constants.COLOR.BUTTON);
        backButton.setForeground(Color.LIGHT_GRAY);
        backButton.setVisible(true);

        playButton = new JButton(Constants.ICON.PAUSE);
        playButton.setBackground(Constants.COLOR.BUTTON);
        playButton.setForeground(Color.LIGHT_GRAY);
        playButton.setVisible(true);

        nextButton = new JButton(Constants.ICON.NEXT);
        nextButton.setBackground(Constants.COLOR.BUTTON);
        nextButton.setForeground(Color.LIGHT_GRAY);
        nextButton.setVisible(true);

        shuffleButton = new JButton(Constants.ICON.SHUFFLE);
        shuffleButton.setBackground(Constants.COLOR.BUTTON);
        shuffleButton.setForeground(Color.LIGHT_GRAY);
        shuffleButton.setVisible(true);

        loopButton.addActionListener(this);
        backButton.addActionListener(this);
        playButton.addActionListener(this);
        nextButton.addActionListener(this);
        shuffleButton.addActionListener(this);

        panel.add(loopButton);
        panel.add(backButton);
        panel.add(playButton);
        panel.add(nextButton);
        panel.add(shuffleButton);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loopButton) {
            if(event.requestLoopButton()){
                loopButton.setForeground(Color.LIGHT_GRAY);
                // TODO: player.setLoopState(false);
            } else {
                loopButton.setForeground(Constants.COLOR.ACTIVE_BUTTON);
                // TODO: player.setLoopState(true);
            }
        } else if (e.getSource() == backButton) {
            if(event.currentSongPos() > 1){
                //TODO: player.playBackSong();
                playingSong.setText("back" /*TODO : player.getPlayingSongTitle() + - + player.getPlayingSongArtist*/);
            }
        } else if (e.getSource() == playButton) {
            if(event.playing()){
                playButton.setText(Constants.ICON.PAUSE);
                // TODO: player.pausePlay();
            } else {
                playButton.setText(Constants.ICON.PLAY);
                //TODO player.resumePlay();
            }
        } else if (e.getSource() == nextButton) {
            if(event.currentSongPos() < 10 /* TODO: player.playingListSize()*/){
                //TODO: player.playNextSong();
                playingSong.setText("next" /*TODO : player.getPlayingSongTitle() + - + player.getPlayingSongArtist*/);
            }
        } else if (e.getSource() == songsButton) {
            //TODO: showSongList

            songsButton.setForeground(Constants.COLOR.ACTIVE_BUTTON);
            playlistButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            pianoButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            rankingButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            settingsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        } else if (e.getSource() == playlistButton) {
            //TODO: showPlaylistList

            songsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            playlistButton.setForeground(Constants.COLOR.ACTIVE_BUTTON);
            pianoButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            rankingButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            settingsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        } else if (e.getSource() == pianoButton) {
            //TODO: showPiano

            songsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            playlistButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            pianoButton.setForeground(Constants.COLOR.ACTIVE_BUTTON);
            rankingButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            settingsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        } else if (e.getSource() == rankingButton) {
            //TODO: showRanking

            songsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            playlistButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            pianoButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            rankingButton.setForeground(Constants.COLOR.ACTIVE_BUTTON);
            settingsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
        } else if (e.getSource() == settingsButton) {
            //TODO: showSettings

            songsButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            playlistButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            pianoButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            rankingButton.setForeground(Constants.COLOR.TOP_BUTTON_FONT);
            settingsButton.setForeground(Constants.COLOR.ACTIVE_BUTTON);
        } else if (e.getSource() == exitButton) {
            window.dispose();
        }

    }
}
