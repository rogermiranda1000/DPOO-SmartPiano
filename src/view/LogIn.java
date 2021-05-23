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
import java.util.regex.Pattern;

/**
 * Window used for user Log in and Registration, the user can switch between both screens
 */
public class LogIn extends JFrame implements ActionListener, LogInNotifier {

    /**
     * Width of the window
     */
    private static final int WIDTH = 400;

    /**
     * Height of the window
     */
    private static final int HEIGHT = 400;

    /**
     * Button to confirm to log in
     */
    private JButton loginButton;

    /**
     * Button to switch to the register screen
     */
    private JButton registerButton;

    /**
     * Button to go back from register to log in
     */
    private JButton backButton;

    /**
     * Button to confirm registration
     */
    private JButton pushRegisterButton;

    /**
     * Field where the username for logging in is typed
     */
    private GenericField usernameInput;

    /**
     * Field where the email is typed
     */
    private GenericField emailRegister;

    /**
     * Field where the username for registering is typed
     */
    private GenericField usernameRegister;

    /**
     * Field where the password is input when logging in
     */
    private GenericField passwordInput;

    /**
     * Field where the password is input when registering
     */
    private GenericField passwordRegister;

    /**
     * Field where the password is input for the second time when registering
     */
    private GenericField confirmPasswordRegister;

    /**
     * Event to request log in and registers
     */
    private final LoginEvent event;

    /**
     * Panel containing both register and log in views
     */
    private final JPanel mainContent;

    /**
     * Card layout for switching between both register and log in views
     */
    private final CardLayout cl;

    /**
     * Sets up every component in the frame
     * @param event Object to notify log in and register events to
     */
    public LogIn(LoginEvent event) {
        this.event = event;

        this.setTitle("Sign In");
        ImageIcon img = new ImageIcon("images\\icon.jpg");
        this.setIconImage(img.getImage());
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainContent = new JPanel(new CardLayout());
        mainContent.add(buildLogin(), "login");
        mainContent.add(buildRegister(), "register");
        this.add(mainContent);
        cl = (CardLayout) (mainContent.getLayout());
        cl.show(mainContent, ("login"));
    }

    /**
     * Builds everything in the log in screen
     * @return Panel with every component needed set up inside
     */
    public JPanel buildLogin() {
        JPanel container = new JPanel();
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
        this.usernameInput = new GenericField("Username:");

        // Password
        this.passwordInput = new GenericField("Password:", true);

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
        container.add(logo, BorderLayout.NORTH);
        container.add(this.usernameInput, BorderLayout.CENTER);
        container.add(this.passwordInput, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);

        this.add(container, BorderLayout.CENTER);

        // Login click
        loginButton.addActionListener(this);

        // Register click
        registerButton.addActionListener(this);

        return container;
    }

    /**
     * Builds everything in the register screen
     * @return Panel with every component needed set up inside
     */
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
        this.usernameRegister = new GenericField("Username:");

        // Password
        this.passwordRegister = new GenericField("Password:", true);

        // Confirm password
        this.confirmPasswordRegister = new GenericField("Confirm Password:", true);

        // Email
        this.emailRegister = new GenericField("Email:");

        // Buttons
        JPanel buttons = new JPanel();
        buttons.setBackground(ColorConstants.BACKGROUND.getColor());
        buttons.setVisible(true);
        buttons.setMaximumSize(new Dimension(WIDTH, 100));

        backButton = new JButton("Back to view LogIn");
        pushRegisterButton = new JButton("Register");

        buttons.add(backButton, BorderLayout.CENTER);
        buttons.add(pushRegisterButton, BorderLayout.CENTER);

        // Add of all the components
        container.add(header, BorderLayout.CENTER);
        container.add(this.usernameRegister, BorderLayout.CENTER);
        container.add(this.passwordRegister, BorderLayout.CENTER);
        container.add(this.confirmPasswordRegister, BorderLayout.CENTER);
        container.add(this.emailRegister, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.CENTER);

        this.add(container, BorderLayout.CENTER);

        // Back Login click
        backButton.addActionListener(this);

        // Register push click
        pushRegisterButton.addActionListener(this);

        return container;
    }

    /**
     * Checks if both passwords are equal
     * @return True if they are equal
     */
    private boolean checkPasswords() {
        return passwordRegister.getInput().equals(this.confirmPasswordRegister.getInput());
    }

    /**
     * Checks if the email input by the user complies with the requirements
     * @return True if the email complies with the format specifications
     */
    private boolean checkMail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (this.emailRegister.getInput() == null)
            return false;
        return pat.matcher(this.emailRegister.getInput()).matches();
    }

    /**
     * Checks if the password is valid
     * @return True if the password complies with MIT password policies
     */
    private boolean checkPassword() {
        String passwordRegex = "(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$)";

        Pattern pat = Pattern.compile(passwordRegex);
        if (String.valueOf(passwordRegister.getInput()).equals(""))
            return false;
        return pat.matcher(String.valueOf(passwordRegister.getInput())).matches();
    }

    /**
     * Shows an error message explaining the requirements of a valid password
     */
    private void wrongPassword() {
        JOptionPane.showMessageDialog(this, "Wrong password. Password need have: " +
                        "\n- More than 8 characters. " +
                        "\n- At least one lower case and upper case." +
                        "\n- At least one numeric character." +
                        "\n- No whitespaces." +
                        "\nTry it again ( ͡❛ ︹ ͡❛)",
                "Wrong password",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an error message for when an incorrect password is input when registering
     */
    private void wrongPasswords() {
        JOptionPane.showMessageDialog(this, "Wrong password. Try it again ( ͡❛ ︹ ͡❛)", "Wrong password", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an error message for when an incorrect email is input when registering
     */
    private void wrongMail() {
        JOptionPane.showMessageDialog(this, "Wrong mail format. Try it again ( ͡❛ ︹ ͡❛)", "Wrong mail", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an error message for when an incorrect user is input when registering
     */
    private void wrongUser() {
        JOptionPane.showMessageDialog(this, "Wrong user format. Try it again ( ͡❛ ︹ ͡❛)", "Wrong mail", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an error message for a user-password combination doesn't exist at logging in
     */
    @Override
    public void wrongLogin() {
        JOptionPane.showMessageDialog(this, "Wrong LogIn. Try it again ( ͡❛ ︹ ͡❛)", "Wrong Login", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an message for when an account is created successfully
     */
    @Override
    public void userCreated() {
        JOptionPane.showMessageDialog(this, "User created successfully ¯\\_( ͡❛ ‿‿ ͡❛)_/¯", "UserCreated", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error message for when the user already exists in the database
     */
    @Override
    public void wrongCreation() {
        JOptionPane.showMessageDialog(this, "This user is already taken. Try it again ( ͡❛ ︹ ͡❛)", "Wrong user", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Called when a button is pressed, does the action required depending on which button it was
     * @param e Event that triggered this function
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            event.requestLogin(this.usernameInput.getInput(), this.passwordInput.getInput());
        } else if (e.getSource() == registerButton) {
            cl.show(mainContent, ("register"));
        } else if (e.getSource() == backButton) {
            cl.show(mainContent, ("login"));
        } else if (e.getSource() == pushRegisterButton) {
            if (!this.usernameRegister.getInput().equals("")) {
                if (this.checkMail()) {
                    if (this.checkPassword()) {
                        if (this.checkPasswords()) {
                            this.event.requestRegister(this.usernameRegister.getInput(), this.emailRegister.getInput(), this.passwordRegister.getInput());
                        } else this.wrongPasswords();
                    } else this.wrongPassword();
                } else this.wrongMail();
            } else this.wrongUser();
        }
    }
}