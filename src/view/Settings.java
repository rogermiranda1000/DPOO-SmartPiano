package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Settings extends JPanel {

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

        options.add(new Label("Octaves"));


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
        /*
        try {
            BufferedImage img = ImageIO.read(new File(LOGO_PATH));
            ImageIcon icon = new ImageIcon(img);
            Image a = icon.getImage().getScaledInstance(28, 28,Image.SCALE_DEFAULT);

            JLabel logoLabel = new JLabel(icon);
            information.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //information.add(logoSubPanel);

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

        //options.setPreferredSize(new Dimension(100, 100));
        //information.setPreferredSize(new Dimension(100, 100));
        options.setBackground(new Color(1, 1, 1));
        information.setBackground(new Color(200, 200, 200));


        panel.add(options, BorderLayout.CENTER);
        panel.add(information, BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);


        this.setVisible(true);
    }

    public JPanel options(){
        return null;
    }
}
