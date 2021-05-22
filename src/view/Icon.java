package view;

/**
 * Defines several ASCII characters that are used like icons
 */
public enum Icon {

    LOOP("\uD83D\uDD04"),
    BACK("⏪"),
    PAUSE("⏸"),
    PLAY("▶"),
    NEXT("⏩"),
    SHUFFLE("\uD83D\uDD00"),
    GOBACK("←"),
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
