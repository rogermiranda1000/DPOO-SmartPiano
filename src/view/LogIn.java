package view;

import controller.LoginEvent;

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
    private static final int TXT_WIDTH = 120;
    private static final int TXT_HEIGHT = 20;

    private JButton loginButton, registerButton, backButton, pushResgisterButton;
    private TextField usernameInput, emailRegister, usernameRegister;
    private JPasswordField passwordInput, passwordRegister, confirmPasswordRegister;
    private final LoginEvent event;
    private final JPanel mainContent;
    private final CardLayout cl;

    public LogIn(LoginEvent event, Frame frame) {
        super(frame, true);
        this.event = event;

        this.setTitle("Sign In");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainContent = new JPanel(new CardLayout());
        mainContent.add(buildLogin(), "login");
        mainContent.add(buildRegister(), "register");
        this.add(mainContent);
        cl = (CardLayout) (mainContent.getLayout());
        cl.show(mainContent, ("login"));

        this.setVisible(true);
    }

    public JPanel buildLogin() {
        JPanel container = new JPanel(new BorderLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Logo
        JPanel logo = new JPanel();
        logo.setBackground(ColorConstants.BACKGROUND.getColor());
        logo.setVisible(true);
        logo.setMaximumSize(new Dimension(WIDTH, 200));
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
        username.setMaximumSize(new Dimension(WIDTH, 45));

        Label usernameTxt = new Label("Username :");
        usernameTxt.setPreferredSize(new Dimension(75, TXT_HEIGHT));

        usernameInput = new TextField();
        usernameInput.setPreferredSize(new Dimension(180, TXT_HEIGHT));

        username.add(usernameTxt);
        username.add(usernameInput);

        // Password
        JPanel password = new JPanel();
        password.setBackground(ColorConstants.BACKGROUND.getColor());
        password.setVisible(true);
        password.setMaximumSize(new Dimension(WIDTH, 45));

        Label passwordTxt = new Label("Password :");
        passwordTxt.setPreferredSize(new Dimension(75, TXT_HEIGHT));

        passwordInput = new JPasswordField();
        passwordInput.setPreferredSize(new Dimension(180, TXT_HEIGHT));

        password.add(passwordTxt);
        password.add(passwordInput);

        // Buttons
        JPanel buttons = new JPanel();
        buttons.setBackground(ColorConstants.BACKGROUND.getColor());
        buttons.setVisible(true);
        buttons.setMaximumSize(new Dimension(WIDTH, 100));

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

        return container;
    }

    public JPanel buildRegister() {
        JPanel container = new JPanel(new BorderLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Header
        JPanel header = new JPanel();
        header.setBackground(ColorConstants.BACKGROUND.getColor());
        header.setVisible(true);
        header.setMaximumSize(new Dimension(WIDTH, 200));
        String IMG_PATH = "images\\register.png";

        try {
            BufferedImage img = ImageIO.read(new File(IMG_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel logoLabel = new JLabel(icon);
            header.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Username
        JPanel username = new JPanel();
        username.setBackground(ColorConstants.BACKGROUND.getColor());
        username.setVisible(true);
        username.setMaximumSize(new Dimension(WIDTH, 45));

        Label usernameTxt = new Label("Username :");
        usernameTxt.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        usernameRegister = new TextField();
        usernameRegister.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        username.add(usernameTxt);
        username.add(usernameRegister);

        // Email
        JPanel email = new JPanel();
        email.setBackground(ColorConstants.BACKGROUND.getColor());
        email.setVisible(true);
        email.setMaximumSize(new Dimension(WIDTH, 45));

        Label emailTxt = new Label("Email :");
        emailTxt.setPreferredSize(new Dimension(TXT_WIDTH, 20));

        emailRegister = new TextField();
        emailRegister.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        email.add(emailTxt);
        email.add(emailRegister);

        // Password
        JPanel password = new JPanel();
        password.setBackground(ColorConstants.BACKGROUND.getColor());
        password.setVisible(true);
        password.setMaximumSize(new Dimension(WIDTH, 45));

        Label passwordTxt = new Label("Password :");
        passwordTxt.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        passwordRegister = new JPasswordField();
        passwordRegister.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        password.add(passwordTxt);
        password.add(passwordRegister);

        // Confirm password
        JPanel confirmPassword = new JPanel();
        confirmPassword.setBackground(ColorConstants.BACKGROUND.getColor());
        confirmPassword.setVisible(true);
        confirmPassword.setMaximumSize(new Dimension(WIDTH, 45));

        Label confirmPasswordTxt = new Label("Confirm Password :");
        confirmPasswordTxt.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));
        confirmPasswordRegister = new JPasswordField();
        confirmPasswordRegister.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        confirmPassword.add(confirmPasswordTxt);
        confirmPassword.add(confirmPasswordRegister);

        // Buttons
        JPanel buttons = new JPanel();
        buttons.setBackground(ColorConstants.BACKGROUND.getColor());
        buttons.setVisible(true);
        buttons.setMaximumSize(new Dimension(WIDTH, 100));

        backButton = new JButton("Back to view.LogIn");
        pushResgisterButton = new JButton("Register");

        buttons.add(backButton, BorderLayout.CENTER);
        buttons.add(pushResgisterButton, BorderLayout.CENTER);

        // Add of all the components
        container.add(header, BorderLayout.CENTER);
        container.add(username, BorderLayout.CENTER);
        container.add(password, BorderLayout.CENTER);
        container.add(confirmPassword, BorderLayout.CENTER);
        container.add(email, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.CENTER);

        this.add(container, BorderLayout.CENTER);

        // Back Login click
        backButton.addActionListener(this);

        // Register push click
        pushResgisterButton.addActionListener(this);

        return container;
    }

    private boolean checkPass() {
        return String.valueOf(passwordRegister.getPassword()).equals(String.valueOf(confirmPasswordRegister.getPassword()));
    }

    private void wrongPass() {
        JOptionPane.showMessageDialog(this, "Wrong password. Try it again ( ͡❛ ︹ ͡❛)");
    }

    public void wrongLogin() {
        JOptionPane.showMessageDialog(this, "Wrong LogIn. Try it again ( ͡❛ ︹ ͡❛)");
    }

    public void userCreated() {
        JOptionPane.showMessageDialog(this, "User created successfully ¯\\_( ͡❛ ‿‿ ͡❛)_/¯");
    }

    //TODO: que es pugui accedir tant amb el nom d'usuari o el mail.
    //TODO: dir exactament quin camp està malament si no s'accepta password.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            event.requestLogin(usernameInput.getText(), String.valueOf(passwordInput.getPassword()));
        } else if (e.getSource() == registerButton) {
            cl.show(mainContent, ("register"));
        } else if (e.getSource() == backButton) {
            cl.show(mainContent, ("login"));
        } else if (e.getSource() == pushResgisterButton) {
            if (this.checkPass()) this.event.requestRegister(usernameRegister.getText(), emailRegister.getText(), String.valueOf(passwordRegister.getPassword()));
            else this.wrongPass();
        }
    }
}