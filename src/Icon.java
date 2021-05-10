public enum Icon {

    LOOP("\uD83D\uDD04"),
    BACK("⏪"),
    PAUSE("⏸"),
    PLAY("▶"),
    NEXT("⏩"),
    SHUFFLE("\uD83D\uDD00"),
    GOBACK("←");

    private String icon;

    Icon(String s){
        icon = s;
    }

    public String getIcon(){
        return icon;
    }

}
