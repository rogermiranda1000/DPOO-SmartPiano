package entities;

/**
 * List of possible values a note can have within an octave
 */
public enum Note {

    /**
     * The first note of an octave
     */
    Do,

    /**
     * The second note of an octave
     */
    DoX,

    /**
     * The third note of an octave
     */
    Re,

    /**
     * The fourth note of an octave
     */
    ReX,

    /**
     * The fifth note of an octave
     */
    Mi,

    /**
     * The sixth note of an octave
     */
    Fa,

    /**
     * The seventh note of an octave
     */
    FaX,

    /**
     * The eighth note of an octave
     */
    Sol,

    /**
     * The ninth note of an octave
     */
    SolX,

    /**
     * The tenth note of an octave
     */
    La,

    /**
     * The eleventh note of an octave
     */
    LaX,

    /**
     * The twelfth note of an octave
     */
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
