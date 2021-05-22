package entities;

/**
 * Stores the configuration of a specific user: the piano volume, song volume and note binds
 */
public class Config {

    /**
     * Volume of the piano (0 = silent, 1 = max volume)
     */
    private float volumePiano;

    /**
     * Volume of the music player (0 = silent, 1 = max volume)
     */
    private float volumeSong;

    /**
     * Per cada Note té un caràcter asociat
     */
    private char []noteBind;

    /**
     * Configures the the volumes of the user
     * @param volumePiano Volume of the piano (0 = silent, 1 = max volume)
     * @param volumeSong Volume of the music player (0 = silent, 1 = max volume)
     */
    public Config(float volumePiano, float volumeSong) {
        this.volumePiano = volumePiano;
        this.volumeSong = volumeSong;
    }

    /**
     * Returns the configured value of the piano
     * @return The piano volume (0 = silent, 1 = max volume)
     */
    public float getVolumePiano() {
        return volumePiano;
    }

    /**
     * Returns the configured value of the music player
     * @return The music player volume (0 = silent, 1 = max volume)
     */
    public float getVolumeSong() {
        return volumeSong;
    }

    /**
     * Returns the values of the note binds
     * @return The configuration of the note binds, from lowest note to highest
     */
    public char[] getNotesBind() {
        return noteBind;
    }

    /**
     * Saves the volume of the piano
     * @param volumePiano The desired piano volume (0 = silent, 1 = max volume)
     */
    public void setVolumePiano(float volumePiano) {
        this.volumePiano = volumePiano;
    }

    /**
     * Saves the volume of the music player
     * @param volumeSong The desired music player volume (0 = silent, 1 = max volume)
     */
    public void setVolumeSong(float volumeSong) {
        this.volumeSong = volumeSong;
    }

    /**
     * Saves new note binds
     * @param valor Array of characters, from lowest note to highest
     */
    public void setNoteBind(char[] valor) {
        this.noteBind = valor;
    }
}
