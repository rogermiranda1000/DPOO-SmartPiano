import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LogIn extends JDialog implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private JButton loginButton, registerButton;
    private TextField usernameInput;
    private JPasswordField passwordInput;
    private LoginEvent event;

    public LogIn(LoginEvent event, Frame frame){
        super(frame, true);
        this.event = event;

        this.setTitle("Sign In");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add components to the window
        addComponents();
        this.setVisible(true);
    }

    public void addComponents(){
        JPanel container = new JPanel(new BorderLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Logo
        JPanel logo = new JPanel();
        logo.setBackground(ColorConstants.BACKGROUND.getColor());
        logo.setVisible(true);
        logo.setMaximumSize(new Dimension(WIDTH,200));
        String LOGO_PATH = "images\\smartpiano.png";

        try {
            BufferedImage img = ImageIO.read(new File(LOGO_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel logoLabel = new JLabel(icon);
            logo.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Username
        JPanel username = new JPanel();
        username.setBackground(ColorConstants.BACKGROUND.getColor());
        username.setVisible(true);
        username.setMaximumSize(new Dimension(WIDTH,45));

        Label usernameTxt = new Label("Username :");
        usernameTxt.setPreferredSize(new Dimension(75,20));

        usernameInput = new TextField();
        usernameInput.setPreferredSize(new Dimension(180, 20));

        username.add(usernameTxt);
        username.add(usernameInput);

        // Password
        JPanel password = new JPanel();
        password.setBackground(ColorConstants.BACKGROUND.getColor());
        password.setVisible(true);
        password.setMaximumSize(new Dimension(WIDTH,45));

        Label passwordTxt = new Label("Password :");
        passwordTxt.setPreferredSize(new Dimension(75,20));

        passwordInput=new JPasswordField();
        passwordInput.setPreferredSize(new Dimension(180, 20));

        password.add(passwordTxt);
        password.add(passwordInput);

        // Buttons
        JPanel buttons = new JPanel();
        buttons.setBackground(ColorConstants.BACKGROUND.getColor());
        buttons.setVisible(true);
        buttons.setMaximumSize(new Dimension(WIDTH,100));

        loginButton = new JButton("Sign In");
        registerButton = new JButton("Register");

        buttons.add(loginButton, BorderLayout.CENTER);
        buttons.add(registerButton, BorderLayout.CENTER);

        // Add of all the components
        container.add(logo, BorderLayout.CENTER);
        container.add(username, BorderLayout.CENTER);
        container.add(password, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.CENTER);

        this.add(container, BorderLayout.CENTER);

        // Login click
        loginButton.addActionListener(this);

        // Register click
        registerButton.addActionListener(this);
    }

    private void wrongPass(){
        JOptionPane.showMessageDialog(this, "Wrong combination. Try it again ( ͡❛ ︹ ͡❛)");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            if (event.requestLogin(usernameInput.getText(), String.valueOf(passwordInput.getPassword()))) {
                this.dispose();
            } else {
                wrongPass();
                usernameInput.setText("");
                passwordInput.setText("");
            }

        } else if (e.getSource() == registerButton) {
                //TODO: Swap register view
        }
    }
}