package view;

import controller.PlaylistEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pop up that asks for the name of the playlist being created
 */
public class CreatePlaylistPopUp extends JDialog implements ActionListener {

    /**
     * Name of the window
     */
    private static final String TITLE = "Create playlist";

    /**
     * Button to confirm the operation
     */
    private final JButton okay;

    /**
     * Text field of the user to input a name
     */
    private final TextField inputField;

    /**
     * Event used to communicate playlist related actions
     */
    private final PlaylistEvent event;

    /**
     * Initializes the class and configures the main panel
     * @param event Event used to communicate playlist related actions
     */
    public CreatePlaylistPopUp(PlaylistEvent event) {
        this.event = event;

        this.setTitle(CreatePlaylistPopUp.TITLE);
        this.setSize(400, 120);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(ColorConstants.BACKGROUND.getColor());

        // Username
        JPanel input = new JPanel();
        input.setBackground(ColorConstants.BACKGROUND.getColor());
        input.setVisible(true);
        input.setPreferredSize(new Dimension(180, 45));

        Label nameTxt = new Label("Name :");
        nameTxt.setPreferredSize(new Dimension(75, 20));

        inputField = new TextField();
        inputField.setPreferredSize(new Dimension(180, 20));

        input.add(nameTxt);
        input.add(inputField);

        okay = new JButton("Okay");


        // Add of all the components
        container.add(input, BorderLayout.CENTER);
        container.add(okay, BorderLayout.CENTER);

        this.add(container, BorderLayout.CENTER);
        okay.addActionListener(this);
        this.setVisible(true);
    }

    /**
     * Shows a message for when no name is written
     */
    public void emptyInput() {
        JOptionPane.showMessageDialog(this, "Empty text field.");
    }

    /**
     * The button has been clicked, either adds the playlist or shows an error message if the text was empty
     * @param e Event that triggered this function
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okay) {
            if (inputField.getText().equals("")) {
                emptyInput();
            } else {
                this.event.addPlaylist(inputField.getText());
                dispose(); // tanca
            }
        }
    }
}
