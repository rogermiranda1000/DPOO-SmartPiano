package view;

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
import java.util.ArrayList;

public class Settings extends JPanel implements ActionListener, ChangeListener {
    private JSlider volumePiano, volumeSong;
    private JLabel volumePianoTxt, volumeSongTxt;
    private JButton deleteButton, saveButton;
    private final ArrayList<JButton> notes = new ArrayList<>();


    private final KeyChanger kC;

    public static final int HEIGHT = 600;
    public static final int WIDTH = 1200;

    public Settings(KeyChanger kC) {
        this.kC = kC;
        this.setBackground(ColorConstants.BACKGROUND.getColor());

        this.add(settingsView());
        this.setVisible(true);

    }

    private JPanel settingsView() {
        JPanel content = new JPanel();
        content.setBackground(ColorConstants.BACKGROUND.getColor());
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();

        //ABANS ERA 1000,500
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
        JPanel keys = new JPanel(), first = new JPanel(), second = new JPanel(), title = new JPanel();

        keys.setLayout(new BoxLayout(keys, BoxLayout.Y_AXIS));
        keys.setBackground(ColorConstants.BACKGROUND.getColor());

        String[] noteNames = {"Do", "DoX", "Re", "ReX", "Mi", "Fa", "FaX", "Sol", "SolX", "La", "LaX", "Si"};

        boolean labelsDone = true;

        JLabel firstOctave = new JLabel("[1]");
        firstOctave.setFont(new Font("Arial", Font.BOLD, 15));
        first.add(firstOctave);

        for (int i = 0; i < noteNames.length * 2; i++) {
            notes.add(new ChangeKeyButton(kC, 1 + (i / 12), Note.getNote(i)));

            if(1 + (i / 12) == 1) {
                first.add(notes.get(i));
            }
            if(1 + (i / 12) == 2){
                if(labelsDone){
                    JLabel secondOctave = new JLabel("[2]");
                    secondOctave.setFont(new Font("Arial", Font.BOLD, 15));
                    second.add(secondOctave);

                    labelsDone = false;
                }
                second.add(notes.get(i));
            }
        }

        first.setBackground(ColorConstants.BACKGROUND.getColor());
        second.setBackground(ColorConstants.BACKGROUND.getColor());

        Label keysConfig = new Label("OCTAVES KEYS CONFIGURATION");
        keysConfig.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBackground(ColorConstants.BACKGROUND.getColor());
        title.add(keysConfig);
        keys.add(title);
        keys.add(first);
        keys.add(second);

        return keys;
    }

    private void pressKey(String note) {
        JOptionPane.showMessageDialog(this, "Press a key to set up " + note + "." +
                "\nOr press OK to cancel.", "Key recorder", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveConfig() {
        JOptionPane.showMessageDialog(this, "Changes on configuration saved.","Configuration saved!",JOptionPane.INFORMATION_MESSAGE);
        //uK.
    }

    private String deleteUser() {
        return JOptionPane.showInputDialog(this, "All data from this user will be removed from LSPiano." +
                "\nAre you sure you want to delete this account?\nConfirm your password:", "Delete confirmation",JOptionPane.WARNING_MESSAGE);
    }

    private int getPianoVolume() {
        return this.volumePiano.getValue();
    }

    private int getSongVolume() {
        return this.volumeSong.getValue();
    }

    private JPanel keysView(){
        JPanel content = new JPanel();
        saveButton = new GenericButton("Save changes!", null);
        saveButton.addActionListener(this);
        content.add(saveButton, BorderLayout.CENTER);
        return content;
    }

    private void changeLetter(Note note, char letter, int octava){
        kC.changeKey(note, letter, octava);
    }

    private void addChangeListener(Settings settings) {
        System.out.println("CANVI " + settings.getName());
    }

    public JPanel options() {
        return null;
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
            String password = deleteUser();
            //passar la pass al controller i eliminar
        }
    }
}

