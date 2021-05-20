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

public class Settings extends JPanel implements ActionListener, ChangeListener, KeyController {
    private JSlider volumePiano, volumeSong;
    private JLabel volumePianoTxt, volumeSongTxt;
    private JButton deleteButton, saveButton;
    private JButton Do, DoX, Re, ReX, Mi, Fa, FaX, Sol, SolX, La, LaX, Si, Do2, DoX2, Re2, ReX2, Mi2, Fa2, FaX2, Sol2, SolX2, La2, LaX2, Si2;

    private final KeyChanger kC;

    public static final int HEIGHT = 900;
    public static final int WIDTH = 1600;

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

        panel.setMinimumSize(new Dimension(1000, 500));
        panel.setSize(new Dimension(1000, 500));
        panel.setPreferredSize(new Dimension(1000, 500));
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

        Do = new GenericButton("Do");
        DoX = new GenericButton("DoX");
        Re = new GenericButton("Re");
        ReX = new GenericButton("ReX");
        Mi = new GenericButton("Mi");
        Fa = new GenericButton("Fa");
        FaX = new GenericButton("FaX");
        Sol = new GenericButton("Sol");
        SolX = new GenericButton("SolX");
        La = new GenericButton("La");
        LaX = new GenericButton("LaX");
        Si = new GenericButton("Si");

        JLabel firstOctave = new JLabel("[1]");
        firstOctave.setFont(new Font("Arial", Font.BOLD, 15));
        first.add(firstOctave);
        first.add(Do);
        first.add(DoX);
        first.add(Re);
        first.add(ReX);
        first.add(Mi);
        first.add(Fa);
        first.add(FaX);
        first.add(Sol);
        first.add(SolX);
        first.add(La);
        first.add(LaX);
        first.add(Si);

        Do.addActionListener(this);
        DoX.addActionListener(this);
        Re.addActionListener(this);
        ReX.addActionListener(this);
        Mi.addActionListener(this);
        Fa.addActionListener(this);
        FaX.addActionListener(this);
        Sol.addActionListener(this);
        SolX.addActionListener(this);
        La.addActionListener(this);
        LaX.addActionListener(this);
        Si.addActionListener(this);

        first.setBackground(ColorConstants.BACKGROUND.getColor());

        Do2 = new GenericButton("Do");
        DoX2 = new GenericButton("DoX");
        Re2 = new GenericButton("Re");
        ReX2 = new GenericButton("ReX");
        Mi2 = new GenericButton("Mi");
        Fa2 = new GenericButton("Fa");
        FaX2 = new GenericButton("FaX");
        Sol2 = new GenericButton("Sol");
        SolX2 = new GenericButton("SolX");
        La2 = new GenericButton("La");
        LaX2 = new GenericButton("LaX");
        Si2 = new GenericButton("Si");

        JLabel secondOctave = new JLabel("[2]");
        secondOctave.setFont(new Font("Arial", Font.BOLD, 15));
        second.add(secondOctave);
        second.add(Do2);
        second.add(DoX2);
        second.add(Re2);
        second.add(ReX2);
        second.add(Mi2);
        second.add(Fa2);
        second.add(FaX2);
        second.add(Sol2);
        second.add(SolX2);
        second.add(La2);
        second.add(LaX2);
        second.add(Si2);

        Do2.addActionListener(this);
        DoX2.addActionListener(this);
        Re2.addActionListener(this);
        ReX2.addActionListener(this);
        Mi2.addActionListener(this);
        Fa2.addActionListener(this);
        FaX2.addActionListener(this);
        Sol2.addActionListener(this);
        SolX2.addActionListener(this);
        La2.addActionListener(this);
        LaX2.addActionListener(this);
        Si2.addActionListener(this);

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

    private void changeLetter(Note note, char letter, int octava) {
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
        } else if (e.getSource() == Do) {
            pressKey("Do");
        } else if (e.getSource() == DoX) {
            pressKey("DoX");
        } else if (e.getSource() == Re) {
            pressKey("Re");
        } else if (e.getSource() == ReX) {
            pressKey("ReX");
        } else if (e.getSource() == Mi) {
            pressKey("Mi");
        } else if (e.getSource() == Fa) {
            pressKey("Fa");
        } else if (e.getSource() == FaX) {
            pressKey("FaX");
        } else if (e.getSource() == Sol) {
            pressKey("Sol");
        } else if (e.getSource() == SolX) {
            pressKey("SolX");
        } else if (e.getSource() == La) {
            pressKey("La");
        } else if (e.getSource() == LaX) {
            pressKey("LaX");
        } else if (e.getSource() == Si) {
            pressKey("Si");
        } else if (e.getSource() == Do2) {
            pressKey("Do2");
        } else if (e.getSource() == DoX2) {
            pressKey("DoX2");
        } else if (e.getSource() == Re2) {
            pressKey("Re2");
        } else if (e.getSource() == ReX2) {
            pressKey("ReX2");
        } else if (e.getSource() == Mi2) {
            pressKey("Mi2");
        } else if (e.getSource() == Fa2) {
            pressKey("Fa2");
        } else if (e.getSource() == FaX2) {
            pressKey("FaX2");
        } else if (e.getSource() == Sol2) {
            pressKey("Sol2");
        } else if (e.getSource() == SolX2) {
            pressKey("SolX2");
        } else if (e.getSource() == La2) {
            pressKey("La2");
        } else if (e.getSource() == LaX2) {
            pressKey("LaX2");
        } else if (e.getSource() == Si2) {
            pressKey("Si2");
        }
    }

    @Override
    public boolean isPressed(Note note) {
        return false;
    }

    @Override
    public boolean isNotPressed(Note note) {
        return false;
    }

    @Override
    public boolean keyBoardPressed(char nota) {
        return false;
    }

    @Override
    public boolean keyBoardNotPressed(char nota) {
        return false;
    }
}

