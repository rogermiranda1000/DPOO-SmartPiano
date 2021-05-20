package view;

import java.awt.*;

public enum ColorConstants {
    BACKGROUND(188, 205, 225),
    MENU(135, 189, 189),
    BUTTON(93, 130, 130),
    TOP_BUTTON_FONT(230, 241, 255),
    ACTIVE_BUTTON(106, 250, 188),
    RED_BUTTON(99, 20, 20),
    GREEN_BUTTON(60, 163, 117);

    private Color color;
    ColorConstants(int r, int g, int b){
        color = new Color(r,g,b);
    }

    public Color getColor() {
        return color;
    }
}
