package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends JPanel implements SliderController, ChangeListener {

    public static final int HEIGHT = 900;
    public static final int WIDTH  = 1600;

    public Settings() {

        this.setBackground(ColorConstants.BACKGROUND.getColor());

        String[] labelsConfig = {"Octaves", "Piano", "Reproductor", "Eliminar"};
        JPanel panel = new JPanel();

        panel.setMinimumSize(new Dimension(1000, 700));
        panel.setSize(new Dimension(1000, 700));
        panel.setPreferredSize(new Dimension(1000, 700));
        panel.setBackground(new Color(128, 128, 128));
        //panel.setLayout(new BorderLayout.CENTER);

        JPanel options = new JPanel();
        ArrayList<Bars> bars = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bars.add(new Bars(this));
        }

        options.add(new Label("Volume"));
        options.add(bars.get(0));

        bars.get(0).addChangeListener(this);

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
        options.setBackground(new Color(1, 1, 1));
        information.setBackground(new Color(200, 200, 200));


        panel.add(options, BorderLayout.CENTER);
        panel.add(information, BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);



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
        System.out.println("HOLA");
    }
}
