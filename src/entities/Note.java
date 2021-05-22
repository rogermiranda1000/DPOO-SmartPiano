package entities;

/**
 * List of possible values a note can have within an octave
 */
public enum Note {
    Do,
    DoX,
    Re,
    ReX,
    Mi,
    Fa,
    FaX,
    Sol,
    SolX,
    La,
    LaX,
    Si;

    /**
     * Returns the corresponding note, if index > entities.Note.values().length the module is applied
     * @param index Index of the note
     * @return Note corresponding to the index
     */
    public static Note getNote(int index) {
        Note[] r = Note.values();
        index %= r.length;
        return r[index];
    }

    /**
     * Checks if the note should be black
     * @return Returns if the note ends with "X"
     */
    public boolean isBlack() {
        return this.name().matches(".+X$");
    }

    /**
     * Note's name
     * @return Note's name, but the ones that ends with "X" now ends with "#"
     */
    @Override
    public String toString() {
        return this.name().replaceAll("X$", "#");
    }
}
