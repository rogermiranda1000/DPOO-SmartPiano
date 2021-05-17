package view;

import entities.Note;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends JPanel implements ActionListener, SliderController, ChangeListener {
    private final CardLayout cl;
    private final JPanel mainContent;
    private JButton keysConfigButton, saveKeysConfigButton;
    private JSlider volum, velocitat;
    private JLabel volumTxt, velocitatTxt;
    private JButton oneOctava, twoOctaves;
    private JButton deleteButton, editButton;

    private static KeyChanger kC;

    //TODO: LOGOUT, delete account

    public static final int HEIGHT = 900;
    public static final int WIDTH  = 1600;

    public Settings(KeyChanger kC) {

        this.kC = kC;

        mainContent = new JPanel(new CardLayout());
        mainContent.add(settingsView(), "settings");
        mainContent.add(keysView(), "keysConfig");
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("settings"));
        this.add(mainContent);
        this.setVisible(true);
    }

    private void addChangeListener(Settings settings) {
        System.out.println("CANVI " + settings.getName());
    }

    public JPanel options(){
        return null;
    }

    @Override
    public void changeValue(int value) {
        System.out.println("CANVI " + value);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == volum) volumTxt.setText(Integer.toString(volum.getValue()));
        else if(e.getSource() == velocitat) velocitatTxt.setText(Integer.toString(velocitat.getValue()));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == keysConfigButton) {
            cl.show(mainContent, ("keysConfig"));
        } else if(e.getSource() == saveKeysConfigButton){
            cl.show(mainContent, ("settings"));
        }

        //TODO: preguntar com fer sense getSouce (es per una cosa que va dir el Pol de que es podia fer millor) - De David
        if(e.getSource() == oneOctava) System.out.println("Primera octava");
        else if(e.getSource() == twoOctaves) System.out.println("Segona octava");

        if(e.getSource() == deleteButton) System.out.println("Delete");
        else if(e.getSource() == editButton) System.out.println("Edit");
    }
}
