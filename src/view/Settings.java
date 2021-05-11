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

    Settings() {

        this.setBackground(ColorConstants.BACKGROUND.getColor());

        String[] labels = {"Octaves", "Piano", "Reproductor", "Eliminar"};
        JPanel panel = new JPanel();

        panel.setMinimumSize(new Dimension(1000, 700));
        panel.setSize(new Dimension(1000, 700));
        panel.setPreferredSize(new Dimension(1000, 700));
        panel.setBackground(new Color(128, 128, 128));
        //panel.setLayout(new BorderLayout.CENTER);

        JPanel options = new JPanel();

        options.add(new Label("Octaves"));


        JPanel information = new JPanel();

        String LOGO_PATH = "images\\default-user.png";

        try {
            BufferedImage img = ImageIO.read(new File(LOGO_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel logoLabel = new JLabel(icon);
            information.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        information.add(new Label("[USER NAME]"));
        information.add(new Label("[EMAIL]"));
        information.add(new Label("[DATE]"));
        information.add(new Label("[POINTS]"));

        //options.setPreferredSize(new Dimension(100, 100));
        //information.setPreferredSize(new Dimension(100, 100));
        information.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));
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
