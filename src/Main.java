public class Main {
    public static void main (String[] args) {
        System.out.println( "╔══╦═╦═╦══╦═╦══╗╔═╦══╦══╦═╦╦═╗\n" +
                            "║══╣║║║║╔╗║╬╠╗╔╝║╬╠║║╣╔╗║║║║║║\n" +
                            "╠══║║║║║╠╣║╗╣║║░║╔╬║║╣╠╣║║║║║║\n" +
                            "╚══╩╩═╩╩╝╚╩╩╝╚╝░╚╝╚══╩╝╚╩╩═╩═╝"); //Roger, no borris.


        LoginEvent e = new LoginEvent() {
            @Override
            public boolean requestLogin(String user, String password) {
                // TODO: Check in ddbb if user exists.
                return user.contains("admin") && password.contains("admin");
            }
        };

        MenuEvent m = new MenuEvent() {
            @Override
            public boolean toggleLoop() {
                // TODO: Check in player if loop mode on.
                return false;
            }

            @Override
            public int currentSongPos() {
                // TODO: check current song position on playing list
                return 0;
            }

            @Override
            public boolean playing() {
                // TODO: Check if a song is playing
                return false;
            }
        };

        Menu f = new Menu(e,m);
    }
}