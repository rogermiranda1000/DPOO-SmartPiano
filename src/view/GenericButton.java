package view;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button used widely on the app
 */
public class GenericButton extends JButton {

    /**
     * The font used for the button's text
     */
    private static final Font f = new Font(null, Font.BOLD, 20);

    /**
     * Constructor to be used if a specific font is desired
     * @param text Text to be shown on the button
     * @param customFont Desired font for the button
     */
    public GenericButton(String text, Font customFont) {
        super(text);

        this.setBackground(ColorConstants.BUTTON.getColor());
        this.setForeground(Color.LIGHT_GRAY);
        this.setVisible(true);
        this.setFont(customFont);
        this.setFocusable(false);
    }

    /**
     * Constructor to be used if the default font is desired
     * @param text Text to be shown on the button
     */
    public GenericButton(String text) {
        this(text, GenericButton.f);
    }
}
