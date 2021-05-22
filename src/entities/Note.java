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
}
