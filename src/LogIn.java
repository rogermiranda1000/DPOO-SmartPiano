import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LogIn {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private JDialog screen;

    public LogIn(){
        screen = new JDialog();

        screen.setTitle("LogIn");
        screen.setSize(WIDTH, HEIGHT);
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
        screen.setResizable(false);

        //Inserim components a dins de la finestra
        addComponents();

    }

        public void addComponents(){
            JPanel container = new JPanel(new BorderLayout());
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            //Logo
            /*try{
                File input = new File("images\\logo.jpeg");
                Image img = ImageIO.read(input);
            }
            catch(IOException ie)
            {
                System.out.println(ie.getMessage());
            }

            prinImage(graphic);*/

            JLabel label = new JLabel();

            JPanel logo = new JPanel();
            logo.setBackground(new Color(114, 114, 114));
            logo.setVisible(true);
            logo.setMaximumSize(new Dimension(WIDTH,200));

            /*try {
                BufferedImage img = ImageIO.read(new File("images\\logo.jpeg"));
                ImageIcon icon = new ImageIcon(img);
                label = new JLabel(icon);
                //JOptionPane.showMessageDialog(null, label);
                logo.add(label);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            String[] s = {"╔══╦═╦═╦══╦═╦══╗╔═╦══╦══╦═╦╦═╗",
                          "║══╣║║║║╔╗║╬╠╗╔╝║╬╠║║╣╔╗║║║║║║",
                          "╠══║║║║║╠╣║╗╣║║░║╔╬║║╣╠╣║║║║║║",
                          "╚══╩╩═╩╩╝╚╩╩╝╚╝░╚╝╚══╩╝╚╩╩═╩═╝"};
            for (int i = 0; i < 4; i++) {
                logo.add(new JLabel(s[i]));
            }
            //JLabel labelLogo = new JLabel( ); //Roger, no borris.



            //Username
            JPanel username = new JPanel();
            username.setBackground(new Color(243, 0, 0));
            username.setVisible(true);
            username.setMinimumSize(new Dimension(WIDTH,20));
            username.setMaximumSize(new Dimension(WIDTH,30));

            TextField txtUsername = new TextField();
            txtUsername.setPreferredSize(new Dimension(200, 18));

            username.add(new Label("Username: "));
            username.add(txtUsername);

            //Password
            JPanel password = new JPanel();
            password.setBackground(new Color(67, 180, 17));
            password.setVisible(true);
            password.setMinimumSize(new Dimension(WIDTH,20));
            password.setMaximumSize(new Dimension(WIDTH,30));

            TextField txtPassword = new TextField();
            txtPassword.setPreferredSize(new Dimension(200, 18));

            password.add(new Label("Password: "));
            password.add(txtPassword);

            //Buttons
            JPanel buttons = new JPanel();
            buttons.setBackground(new Color(17, 142, 180));
            buttons.setVisible(true);
            buttons.setMaximumSize(new Dimension(WIDTH,100));

            buttons.add(new Button("LogIn"), BorderLayout.CENTER);
            buttons.add(new Button("Register"), BorderLayout.CENTER);

            //Add of all the components
            container.add(logo, BorderLayout.CENTER);
            container.add(username, BorderLayout.CENTER);
            container.add(password, BorderLayout.CENTER);
            container.add(buttons, BorderLayout.CENTER);

            screen.add(container, BorderLayout.CENTER);

        }
}
