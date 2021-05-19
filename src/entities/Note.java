package entities;

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
     * Retorna la nota corresponent. Si index > entities.Note.values().length es fa el mòdul.
     * @param index Index de la nota
     * @return Nota segons l'index
     */
    public static Note getNote(int index) {
        Note[] r = Note.values();
        index %= r.length;
        return r[index];
    }
}
