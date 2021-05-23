package view;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel with text and an input
 */
public class GenericField extends JPanel {
    /**
     * Width of the window
     */
    private static final int WIDTH = 400;

    /**
     * Height of the JPanel
     */
    private static final int ELEMENT_HEIGHT = 45;

    /**
     * Preferred width of labels
     */
    private static final int TXT_WIDTH = 120;

    /**
     * Preferred height of labels
     */
    private static final int TXT_HEIGHT = 20;

    /**
     * JPanel's input
     */
    private final JTextField input;

    /**
     * It specifies a text and if the input should be hidden
     * @param text Text to show
     * @param secure If it's secure (true -> hidden), or not (false -> plain text)
     */
    public GenericField(String text, boolean secure) {
        JLabel textLabel = new JLabel(text);
        textLabel.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        if (secure) this.input = new JPasswordField();
        else this.input = new JTextField();
        this.input.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));

        this.add(textLabel);
        this.add(this.input);

        this.setBackground(ColorConstants.BACKGROUND.getColor());
        this.setMaximumSize(new Dimension(GenericField.WIDTH, GenericField.ELEMENT_HEIGHT));
    }

    /**
     * It specifies text; the input will be seen
     * @param text Text to show
     */
    public GenericField(String text) {
        this(text, false);
    }

    public String getInput() {
        if (this.input instanceof JPasswordField) return String.valueOf(((JPasswordField)this.input).getPassword());
        else return this.input.getText();
    }
}
