package entities;

public class Config {
    public static final int NUM_OCTAVES = 2;
    private float volumePiano;
    private float volumeSong;

    /**
     * Per cada Note té un caràcter asociat
     */
    private char []noteBind;

    public Config() {
        this.noteBind = null;
    }

    public Config(float volumePiano, float volumeSong) {
        this();
        this.volumePiano = volumePiano;
        this.volumeSong = volumeSong;
    }

    public float getVolumePiano() {
        return volumePiano;
    }

    public float getVolumeSong() {
        return volumeSong;
    }

    public char[] getNotesBind() {
        return noteBind;
    }

    public void setVolumePiano(float volumePiano) {
        this.volumePiano = volumePiano;
    }

    public void setVolumeSong(float volumeSong) {
        this.volumeSong = volumeSong;
    }

    public void setNoteBind(char[] valor) {
        this.noteBind = valor;
    }
}
