package view;

/**
 * Used to charge configuration values on the view
 */
public interface ConfigLoadNotifier {

    /**
     * Loads the volume values on the sliders
     * @param songVolume Volume of the music player (0 = silent, 1 = max volume)
     * @param pianoVolume Volume of the piano player (0 = silent, 1 = max volume)
     */
    void setConfig(float songVolume, float pianoVolume);

    /**
     * Loads the name and email of the user on the view
     * @param name Name of the user
     * @param email Email of the user
     */
    void setUserInformation(String name, String email);
}
