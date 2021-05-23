package view;

import java.awt.*;

/**
 * Defines color constants commonly used
 */
public enum ColorConstants {

    /**
     * Color used for the background of the app
     */
    BACKGROUND(188, 205, 225),

    /**
     * Clor used for background menus
     */
    MENU(135, 189, 189),

    /**
     * Color used for some buttons
     */
    BUTTON(93, 130, 130),

    /**
     * Color used for the font of the top buttons when they aren't selected
     */
    TOP_BUTTON_FONT(230, 241, 255),

    /**
     * Color used for the font of the top buttons when they are selected
     */
    ACTIVE_BUTTON(106, 250, 188),

    /**
     * Color used for the "delete account" button
     */
    RED_BUTTON(99, 20, 20),

    /**
     * Color used for the "confirm configuration" button
     */
    GREEN_BUTTON(60, 163, 117);

    /**
     * Stores a color of the enum
     */
    private final Color color;

    /**
     * Creates a Color with the given values
     * @param r Red value (255 = max, 0 = min)
     * @param g Green value (255 = max, 0 = min)
     * @param b Blue value (255 = max, 0 = min)
     */
    ColorConstants(int r, int g, int b){
        this.color = new Color(r,g,b);
    }

    /**
     * Returns a specified color
     * @return The color as a Color object
     */
    public Color getColor() {
        return this.color;
    }
}
