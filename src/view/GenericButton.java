package view;

import javax.swing.*;
import java.awt.*;

public class GenericButton extends JButton {
    private static final Font f = new Font(null, Font.BOLD, 20);

    public GenericButton(String text, Font customFont) {
        super(text);

        this.setBackground(ColorConstants.BUTTON.getColor());
        this.setForeground(Color.LIGHT_GRAY);
        this.setVisible(true);
        this.setFont(customFont);
        this.setFocusable(false);
    }

    public GenericButton(String text) {
        this(text, GenericButton.f);
    }
}
