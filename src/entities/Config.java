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

    public float getVolumePiano() {
        return volumePiano;
    }

    public float getVolumeSong() {
        return volumeSong;
    }

    public char getNoteBind(Note note) {
        return noteBind[note.ordinal()];
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
