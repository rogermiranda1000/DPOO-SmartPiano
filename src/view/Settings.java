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

public class Settings extends JPanel implements ActionListener, ChangeListener{ //}, SliderController, ChangeListener {
    private final CardLayout cl;
    private final JPanel mainContent;
    private JButton keysConfigButton, saveKeysConfigButton;
    private JSlider volum, velocitat;
    private JLabel volumTxt, velocitatTxt;
    private JButton oneOctava, twoOctaves;
    private JButton deleteButton, editButton, saveButton;

    private static ConfigNotifier cN;
    private static KeyChanger kC;

    //TODO: LOGOUT, delete account

    public static final int HEIGHT = 900;
    public static final int WIDTH  = 1600;

    public Settings(KeyChanger kC, ConfigNotifier cN) {

        this.kC = kC;
        this.cN = cN;

        mainContent = new JPanel(new CardLayout());
        mainContent.add(settingsView(), "settings");
        mainContent.add(keysView(), "keysConfig");
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("settings"));
        this.add(mainContent);
        this.setVisible(true);

    }

    private JPanel settingsView(){
        JPanel content = new JPanel();
        content.setBackground(ColorConstants.BACKGROUND.getColor());

        String[] labelsConfig = {"Octaves", "Piano", "Reproductor", "Eliminar"};
        JPanel panel = new JPanel();

        panel.setMinimumSize(new Dimension(1000, 700));
        panel.setSize(new Dimension(1000, 700));
        panel.setPreferredSize(new Dimension(1000, 700));
        panel.setBackground(new Color(128, 128, 128));
        //panel.setLayout(new BorderLayout.CENTER);

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        /*ArrayList<Bars> bars = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bars.add(new Bars(this));
        }
        bars.get(0).addChangeListener(this);*/

        //Barra de volum
        JPanel volumePanel = new JPanel();
        volum = new JSlider(0,100,50);
        volum.addChangeListener(this);
        volumTxt = new JLabel("50");

        //Barra de velocitat
        JPanel speedPanel = new JPanel();
        velocitat = new JSlider(0,100,50);
        velocitat.addChangeListener(this);
        velocitatTxt = new JLabel("50");

        //Botons octaves
        JPanel octavesPanel = new JPanel();
        oneOctava = new JButton("[1]");
        twoOctaves = new JButton("[2]");
        oneOctava.addActionListener(this);
        twoOctaves.addActionListener(this);

        JPanel blankSpace = new JPanel(); //TODO: buscar millor maner de fer espai en blanc.
        blankSpace.setPreferredSize(new Dimension(100,30));

        //Edit profile
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());
        deleteButton = new JButton("Delete profile");
        editButton = new JButton("Edit profile");
        editPanel.add(new JLabel("Do you want to delete/edit your profile?"), BorderLayout.NORTH);
        editPanel.add(deleteButton, BorderLayout.WEST);
        editPanel.add(editButton, BorderLayout.EAST);


        //Save button
        saveButton = new JButton("[SAVE CURRENT CONFIGURATION]");

        //Afegim els elements als seus JPanel
        volumePanel.add(new Label("Volume"));
        volumePanel.add(volum);
        volumePanel.add(volumTxt);
        speedPanel.add(new Label("Speed"));
        speedPanel.add(velocitat);
        speedPanel.add(velocitatTxt);
        octavesPanel.add(new Label("Octaves: "));
        octavesPanel.add(oneOctava);
        octavesPanel.add(twoOctaves);

        //Afegim els JPanels que contenen els elements a la pantalla (JPanel que està dins de la pantalla).
        options.add(volumePanel);
        options.add(speedPanel);
        options.add(octavesPanel);
        options.add(blankSpace);
        options.add(editPanel);
        options.add(saveButton);

        JPanel information = new JPanel();
        information.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));

        JPanel logoSubPanel = new JPanel();
        JPanel infoSubPanel = new JPanel();

        //infoSubPanel.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));

        String LOGO_PATH = "images\\default-user.png";

        try {
            BufferedImage img = ImageIO.read(new File(LOGO_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel logoLabel = new JLabel(icon);
            information.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: Connectar amb BBDD per aconseguir el nom, mail...
        String[] labelsInfo = {"[USER NAME]","[EMAIL]","[DATE]","[POINTS]"};

        for (int i = 0; i < labelsInfo.length; i++) {
            JPanel temp = new JPanel();
            temp.add(new JLabel(labelsInfo[i]), BorderLayout.CENTER);
            information.add(temp, BorderLayout.CENTER);
        }
        /*infoSubPanel.add(new JLabel("[USER NAME]"), BorderLayout.CENTER);
        infoSubPanel.add(new JLabel("[EMAIL]"), BorderLayout.CENTER);
        infoSubPanel.add(new JLabel("[DATE]"), BorderLayout.CENTER);
        infoSubPanel.add(new JLabel("[POINTS]"), BorderLayout.CENTER);*/

        //information.add(infoSubPanel);

        //OPTIONS
        JPanel temp = new JPanel();


        //options.setPreferredSize(new Dimension(100, 100));
        //information.setPreferredSize(new Dimension(100, 100));
        options.setBackground(new Color(205, 134, 134));
        information.setBackground(new Color(200, 200, 200));

        keysConfigButton = new GenericButton("Change Keys Config", null);
        keysConfigButton.addActionListener(this);

        panel.add(options, BorderLayout.CENTER);
        panel.add(information, BorderLayout.CENTER);
        panel.add(keysConfigButton, BorderLayout.CENTER);
        content.add(panel, BorderLayout.CENTER);


        content.setVisible(true);
        return content;
    }

    private JPanel keysView(){
        JPanel content = new JPanel();
        saveKeysConfigButton = new GenericButton("Save changes!", null);
        saveKeysConfigButton.addActionListener(this);
        content.add(saveKeysConfigButton, BorderLayout.CENTER);
    return content;
    }

    private void changeLetter(Note note, char letter, int octava){
        kC.changeKey(note, letter, octava);
    }

    private void addChangeListener(Settings settings) {
        System.out.println("CANVI " + settings.getName());
    }

    public JPanel options(){
        return null;
    }

    /*@Override
    public void changeValue(int value) {
        System.out.println("CANVI " + value);
    }*/


    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == volum) volumTxt.setText(Integer.toString(volum.getValue()));
        else if(e.getSource() == velocitat) velocitatTxt.setText(Integer.toString(velocitat.getValue()));

        //cN.saveVolumes(); //TODO: actualitxar valors de settings cap a menu

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == keysConfigButton) {
            cl.show(mainContent, ("keysConfig"));
        } else if(e.getSource() == saveKeysConfigButton){
            cl.show(mainContent, ("settings"));
        }

        //TODO: connectar amb BBDD.
        if(e.getSource() == saveButton) System.out.println("SAVE!");

        //TODO: preguntar com fer sense getSouce (es per una cosa que va dir el Pol de que es podia fer millor) - De David
        if(e.getSource() == oneOctava) System.out.println("Primera octava");
        else if(e.getSource() == twoOctaves) System.out.println("Segona octava");

        if(e.getSource() == deleteButton) System.out.println("Delete");
        else if(e.getSource() == editButton) System.out.println("Edit");
    }
}

