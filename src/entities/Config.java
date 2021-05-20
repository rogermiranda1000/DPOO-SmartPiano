package entities;

public class Config {
    private float volumePiano;
    private float volumeSong;

    /**
     * Per cada Note té un caràcter asociat
     */
    private final char []noteBind;

    public Config() {
        this.noteBind = new char[Note.values().length];
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

    public void setNoteBind(Note nota, char valor) {
        this.noteBind[nota.ordinal()] = valor;
    }
}
