package view;

import entities.KeyboardConstants;
import entities.Note;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Settings extends JPanel implements ActionListener, ChangeListener, KeyChanger, DeleteUserNotifier, ConfigLoadNotifier {
    private JSlider volumePiano, volumeSong;
    private JLabel volumePianoTxt, volumeSongTxt;
    private JLabel userName, userEmail;
    private JButton deleteButton, saveButton;
    private final UpdateConfigEvent updateEvent;
    private JPasswordField passwordInput;
    private JButton confirm;
    private JDialog dialog;

    public Settings(UpdateConfigEvent updateEvent) {
        this.updateEvent = updateEvent;

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

        this.userName = new JLabel();
        this.userEmail = new JLabel();

        deleteButton = new GenericButton("Delete profile", new Font("Arial", Font.PLAIN, 15));
        deleteButton.addActionListener(this);
        deleteButton.setBackground(ColorConstants.RED_BUTTON.getColor());
        JPanel deleteB = new JPanel();
        deleteB.setBackground(ColorConstants.BACKGROUND.getColor());
        deleteB.add(deleteButton);

        information.add(this.userName, BorderLayout.WEST);
        information.add(this.userEmail, BorderLayout.EAST);
        information.add(deleteB);
        information.setBackground(ColorConstants.BACKGROUND.getColor());

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.setBackground(ColorConstants.BACKGROUND.getColor());

        JPanel pianoVolumePanel = new JPanel();
        pianoVolumePanel.setBackground(ColorConstants.BACKGROUND.getColor());
        pianoVolumePanel.setSize(new Dimension(200,40));
        volumePiano = new JSlider(0, 100, 50);
        volumePiano.addChangeListener(this);
        volumePianoTxt = new JLabel("50");

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

    private JDialog confirmPassword(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Confirm password");
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(ColorConstants.BACKGROUND.getColor());

        JPanel input = new JPanel();
        input.setBackground(ColorConstants.BACKGROUND.getColor());
        input.setVisible(true);
        input.setPreferredSize(new Dimension(180, 45));

        Label passwordTxt = new Label("Password :");
        passwordTxt.setPreferredSize(new Dimension(75, 20));

        passwordInput = new JPasswordField();
        passwordInput.setPreferredSize(new Dimension(180, 20));

        input.add(passwordTxt);
        input.add(passwordInput);

        confirm = new GenericButton("Confirm");

        // Add of all the components
        container.add(input, BorderLayout.CENTER);
        container.add(confirm, BorderLayout.CENTER);

        dialog.add(container, BorderLayout.CENTER);
        confirm.addActionListener(this);
        dialog.setVisible(true);
        return dialog;
    }

    private JPanel keysConfig() {
        JPanel keys = new JPanel(), title = new JPanel();

        keys.setLayout(new BoxLayout(keys, BoxLayout.Y_AXIS));
        keys.setBackground(ColorConstants.BACKGROUND.getColor());
        keys.add(title);

        for (byte octava = KeyboardConstants.INIT_OCTAVA; octava < KeyboardConstants.NUM_OCTAVES + KeyboardConstants.INIT_OCTAVA; octava++) {
            JPanel panel = new JPanel();
            panel.setBackground(ColorConstants.BACKGROUND.getColor());

            JLabel octave = new JLabel("[" + octava + "]");
            octave.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(octave);

            for (int i = 0; i < 12; i++) panel.add(new ChangeKeyButton(this, octava, Note.getNote(i)));

            keys.add(panel);
        }

        Label keysConfig = new Label("OCTAVES KEYS CONFIGURATION");
        keysConfig.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBackground(ColorConstants.BACKGROUND.getColor());
        title.add(keysConfig);

        return keys;
    }

    /**
     * Save the slider's current value
     */
    private void saveConfig() {
        this.updateEvent.updatePianoVolume(this.getPianoVolume());
        this.updateEvent.updateSongVolume(this.getSongVolume());
        JOptionPane.showMessageDialog(this, "Changes on configuration saved.","Configuration saved!",JOptionPane.INFORMATION_MESSAGE);
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
            dialog = confirmPassword();
        } else if(e.getSource() == confirm){
            dialog.dispose();
            this.updateEvent.deleteUser(String.valueOf(passwordInput.getPassword()));
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

    @Override
    public void setConfig(float songVolume, float pianoVolume) {
        this.volumePiano.setValue((int)(pianoVolume*100.f));
        this.volumeSong.setValue((int)(songVolume*100.f));
    }

    @Override
    public void setUserInformation(String name, String email) {
        this.userName.setText(name);
        this.userEmail.setText(email);
    }
}

