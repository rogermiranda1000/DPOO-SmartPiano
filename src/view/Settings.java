package view;

import entities.Config;
import entities.Note;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends JPanel implements ActionListener, ChangeListener, KeyChanger, DeleteUserNotifier {
    /**
     * First key octave
     */
    private static final int INIT_OCTAVA = 3;
    /**
     * Num of octaves shown
     */
    private static final int NUM_OCTAVA = 2;
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1200;

    private JSlider volumePiano, volumeSong;
    private JLabel volumePianoTxt, volumeSongTxt;
    private JButton deleteButton, saveButton;
    private final UpdateConfigEvent updateEvent;
    private final ArrayList<ChangeKeyButton> keyBinders;

    public Settings(UpdateConfigEvent updateEvent) {
        this.updateEvent = updateEvent;
        this.keyBinders = new ArrayList<>(Settings.NUM_OCTAVA*12);

        this.setBackground(ColorConstants.BACKGROUND.getColor());

        this.add(settingsView());
        this.setVisible(true);
    }

    private JPanel settingsView() {
        JPanel content = new JPanel();
        content.setBackground(ColorConstants.BACKGROUND.getColor());
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();

        panel.setMinimumSize(new Dimension(800, 300));
        panel.setSize(new Dimension(800, 300));
        panel.setPreferredSize(new Dimension(800, 300));
        panel.setBackground(ColorConstants.BACKGROUND.getColor());

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.setBackground(ColorConstants.BACKGROUND.getColor());

        //Barra de volum
        JPanel pianoVolumePanel = new JPanel();
        pianoVolumePanel.setBackground(ColorConstants.BACKGROUND.getColor());
        pianoVolumePanel.setSize(new Dimension(200,40));
        volumePiano = new JSlider(0, 100, 50);
        volumePiano.addChangeListener(this);
        volumePianoTxt = new JLabel("50");

        //Barra de velocitat
        JPanel songVolumePanel = new JPanel();
        songVolumePanel.setBackground(ColorConstants.BACKGROUND.getColor());
        songVolumePanel.setSize(new Dimension(200,40));
        volumeSong = new JSlider(0, 100, 50);
        volumeSong.addChangeListener(this);
        volumeSongTxt = new JLabel("50");

        pianoVolumePanel.add(new Label("Piano volume"));
        pianoVolumePanel.add(volumePiano);
        pianoVolumePanel.add(volumePianoTxt);
        songVolumePanel.add(new Label("Song volume"));
        songVolumePanel.add(volumeSong);
        songVolumePanel.add(volumeSongTxt);

        options.add(pianoVolumePanel);
        options.add(songVolumePanel);
        options.setBackground(ColorConstants.BACKGROUND.getColor());

        JPanel information = new JPanel();
        information.setSize(new Dimension(300, 300));
        information.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));

        String LOGO_PATH = "images\\default-user.png";
        try {
            BufferedImage img = ImageIO.read(new File(LOGO_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel logoLabel = new JLabel(icon);
            JPanel logoPanel = new JPanel();
            logoPanel.add(logoLabel);
            logoPanel.setBackground(ColorConstants.BACKGROUND.getColor());
            information.add(logoPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: Connectar amb BBDD per aconseguir el nom, mail...
        String[] labelsInfo = {"[USER NAME]", "[EMAIL]", "[DATE]", "[POINTS]"};

        for (int i = 0; i < labelsInfo.length; i++) {
            JPanel temp = new JPanel();
            temp.setBackground(ColorConstants.BACKGROUND.getColor());
            temp.add(new JLabel(labelsInfo[i]), BorderLayout.CENTER);
            information.add(temp, BorderLayout.CENTER);
        }

        deleteButton = new GenericButton("Delete profile", new Font("Arial", Font.PLAIN, 15));
        deleteButton.addActionListener(this);
        deleteButton.setBackground(ColorConstants.RED_BUTTON.getColor());

        JPanel deleteP = new JPanel();
        deleteP.setBackground(ColorConstants.BACKGROUND.getColor());
        deleteP.add(deleteButton);

        information.add(deleteP);
        information.setBackground(ColorConstants.BACKGROUND.getColor());

        panel.add(information, BorderLayout.CENTER);
        panel.add(options, BorderLayout.CENTER);
        content.add(panel, BorderLayout.CENTER);

        content.add(keysConfig(), BorderLayout.CENTER);

        JPanel saveB = new JPanel();
        saveButton = new GenericButton("Save changes");
        saveButton.setBackground(ColorConstants.GREEN_BUTTON.getColor());
        saveButton.setForeground(Color.white);
        saveButton.addActionListener(this);
        saveB.setBackground(ColorConstants.BACKGROUND.getColor());
        saveB.add(saveButton);

        content.add(saveB, BorderLayout.CENTER);

        content.setVisible(true);
        return content;
    }

    private JPanel keysConfig() {
        JPanel keys = new JPanel(), title = new JPanel();

        keys.setLayout(new BoxLayout(keys, BoxLayout.Y_AXIS));
        keys.setBackground(ColorConstants.BACKGROUND.getColor());

        for (byte octava = Settings.INIT_OCTAVA; octava < Settings.NUM_OCTAVA + Settings.INIT_OCTAVA; octava++) {
            JPanel panel = new JPanel();
            panel.setBackground(ColorConstants.BACKGROUND.getColor());

            JLabel octave = new JLabel("[" + octava + "]");
            octave.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(octave);

            for (int i = 0; i < 12; i++) {
                ChangeKeyButton current = new ChangeKeyButton(this, octava, Note.getNote(i));
                this.keyBinders.add(current);
                panel.add(current);
            }

            keys.add(panel);
        }

        Label keysConfig = new Label("OCTAVES KEYS CONFIGURATION");
        keysConfig.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBackground(ColorConstants.BACKGROUND.getColor());
        title.add(keysConfig);
        keys.add(title);

        return keys;
    }

    /**
     * Save the slider's current value
     */
    private void saveConfig() {
        this.updateEvent.updatePianoVolume(this.getPianoVolume());
        this.updateEvent.updateSongVolume(this.getSongVolume());
        //JOptionPane.showMessageDialog(this, "Changes on configuration saved.","Configuration saved!",JOptionPane.INFORMATION_MESSAGE);
    }

    private String deleteUser() {
        return JOptionPane.showInputDialog(this, "All data from this user will be removed from LSPiano." +
                "\nAre you sure you want to delete this account?\nConfirm your password:", "Delete confirmation",JOptionPane.WARNING_MESSAGE);
    }

    private float getPianoVolume() {
        return this.volumePiano.getValue()/100.f;
    }

    private float getSongVolume() {
        return this.volumeSong.getValue()/100.f;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == volumePiano) volumePianoTxt.setText(Integer.toString(volumePiano.getValue()));
        else if (e.getSource() == volumeSong) volumeSongTxt.setText(Integer.toString(volumeSong.getValue()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveConfig();
        } else if (e.getSource() == deleteButton) {
            this.updateEvent.deleteUser(this.deleteUser());
        }
    }

    @Override
    public void changeKey(Note n, byte octava, char newLetter) {
        this.updateEvent.updateKeyBinder(n, octava, newLetter);
    }

    @Override
    public void userDeleted() {
        JOptionPane.showMessageDialog(this, "Bye! :'(","Did I do something wrong? owo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void userNotDeleted() {
        JOptionPane.showMessageDialog(this, "This password it's not the current user's one!","Wrong password!", JOptionPane.ERROR_MESSAGE);
    }
}

