package view;

import controller.PlaylistBarEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottomPanel extends JPanel implements ActionListener, PlayingSongNotifier {
    private JButton playButton, loopButton, nextButton, backButton, shuffleButton;
    private JLabel playingSong;
    private final PlaylistBarEvent playE;

    public BottomPanel(PlaylistBarEvent playE) {
        this.playE = playE;
        playE.setPlayingSongListner(this);
        this.setBackground(ColorConstants.MENU.getColor());
        this.setLayout(new GridLayout(0, 2));

        JPanel currentSong = new JPanel();
        currentSong.setBackground(ColorConstants.MENU.getColor());
        playingSong = new JLabel("Select a song or a playlist to play - ♬ლ(▀̿Ĺ̯▀̿ ̿ლ)♬");
        JPanel playerMenu = songPlayerMenu();

        currentSong.setSize(new Dimension(WIDTH / 2, 150));
        playerMenu.setSize(new Dimension(WIDTH / 2, 150));

        currentSong.add(playingSong, BorderLayout.CENTER);

        this.add(currentSong);
        this.add(playerMenu);
    }

    public JPanel songPlayerMenu() {
        JPanel panel = new JPanel();
        Font f = new Font(null, Font.PLAIN, 15);
        panel.setBackground(ColorConstants.MENU.getColor());

        loopButton = new GenericButton(Icon.LOOP.getIcon(), f);
        backButton = new GenericButton(Icon.BACK.getIcon(), f);
        playButton = new GenericButton(Icon.PAUSE.getIcon(), f);
        nextButton = new GenericButton(Icon.NEXT.getIcon(), f);
        shuffleButton = new GenericButton(Icon.SHUFFLE.getIcon(), f);

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
        /* BOTTOM BAR BUTTONS */
        if (e.getSource() == loopButton) {
            this.loopButton.setForeground(this.loopButton.getForeground().equals(ColorConstants.ACTIVE_BUTTON.getColor()) ? Color.LIGHT_GRAY : ColorConstants.ACTIVE_BUTTON.getColor()); // toggle button's color
            this.playE.toggleLoop();
        } else if (e.getSource() == backButton) {
            this.playE.previousSong();
        } else if (e.getSource() == playButton) {
            this.playButton.setText(this.playButton.getText().equals(Icon.PAUSE.getIcon()) ? Icon.PLAY.getIcon() : Icon.PAUSE.getIcon()); // toggle button's text
            this.playE.togglePlaying();
        } else if (e.getSource() == nextButton) {
            this.playE.nextSong();
        } else if (e.getSource() == this.shuffleButton) {
            this.shuffleButton.setForeground(this.shuffleButton.getForeground().equals(ColorConstants.ACTIVE_BUTTON.getColor()) ? Color.LIGHT_GRAY : ColorConstants.ACTIVE_BUTTON.getColor()); // toggle button's color
            this.playE.toggleRandom();
        }
    }

    @Override
    public void newSongPlaying(String songName) {
        SwingUtilities.invokeLater(()->this.playingSong.setText(songName));
    }
}
