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
     * Preferred width of labels
     */
    private static final int TXT_WIDTH = 120;

    /**
     * Preferred height of labels
     */
    private static final int TXT_HEIGHT = 20;

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
    private TextField usernameInput;

    /**
     * Field where the email is typed
     */
    private TextField emailRegister;

    /**
     * Field where the username for registering is typed
     */
    private TextField usernameRegister;

    /**
     * Field where the password is input when logging in
     */
    private JPasswordField passwordInput;

    /**
     * Field where the password is input when registering
     */
    private GenericField passwordRegister;

    /**
     * Field where the password is input for the second time when registering
     */
    private JPasswordField confirmPasswordRegister;

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
        this.passwordRegister = new GenericField("Password:", true);

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

        backButton = new JButton("Back to view LogIn");
        pushRegisterButton = new JButton("Register");

        buttons.add(backButton, BorderLayout.CENTER);
        buttons.add(pushRegisterButton, BorderLayout.CENTER);

        // Add of all the components
        container.add(header, BorderLayout.CENTER);
        container.add(username, BorderLayout.CENTER);
        container.add(this.passwordRegister, BorderLayout.CENTER);
        container.add(confirmPassword, BorderLayout.CENTER);
        container.add(email, BorderLayout.CENTER);
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
        return String.valueOf(passwordRegister.getInput()).equals(String.valueOf(confirmPasswordRegister.getPassword()));
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
        if (emailRegister.getText() == null)
            return false;
        return pat.matcher(emailRegister.getText()).matches();
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
            event.requestLogin(usernameInput.getText(), String.valueOf(passwordInput.getPassword()));
        } else if (e.getSource() == registerButton) {
            cl.show(mainContent, ("register"));
        } else if (e.getSource() == backButton) {
            cl.show(mainContent, ("login"));
        } else if (e.getSource() == pushRegisterButton) {
            if (this.checkMail()) {
                if (this.checkPassword()) {
                    if (this.checkPasswords()) {
                        this.event.requestRegister(usernameRegister.getText(), emailRegister.getText(), String.valueOf(passwordRegister.getInput()));
                    } else this.wrongPasswords();
                } else this.wrongPassword();
            } else this.wrongMail();

        }
    }
}