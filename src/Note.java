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

    public static Note getNote(int index) {
        Note[] r = Note.values();
        index %= r.length;
        return r[index];
    }
}
