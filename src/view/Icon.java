package view;

/**
 * Defines several ASCII characters that are used like icons
 */
public enum Icon {

    /**
     * The character used for the loop button
     */
    LOOP("\uD83D\uDD04"),

    /**
     * The character used for the back button
     */
    BACK("⏪"),

    /**
     * The character used for the pause button
     */
    PAUSE("⏸"),

    /**
     * The character used for the play button
     */
    PLAY("▶"),

    /**
     * The character used for the next button
     */
    NEXT("⏩"),

    /**
     * The character used for the random button
     */
    SHUFFLE("\uD83D\uDD00"),

    /**
     * The character used for going back buttons
     */
    GOBACK("←"),

    /**
     * The character used to add playlists and adding songs to playlists
     */
    ADD("+");

    /**
     * Stores the icon as a String
     */
    private String icon;

    /**
     * Generates an icon from the String
     * @param s String of the desired icon
     */
    Icon(String s){
        icon = s;
    }

    /**
     * Returns the specified icon
     * @return The icon as a String
     */
    public String getIcon(){
        return icon;
    }

}
