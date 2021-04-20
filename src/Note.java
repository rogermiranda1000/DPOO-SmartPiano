public enum Note {
    Do1,
    DoX1,
    Re1,
    ReX1,
    Mi1,
    Fa1,
    FaX1,
    Sol1,
    SolX1,
    La1,
    LaX1,
    Si1,
    Do2,
    DoX2,
    Re2,
    ReX2,
    Mi2,
    Fa2,
    FaX2,
    Sol2,
    SolX2,
    La2,
    LaX2,
    Si2;

    public static Note getNote(int index) {
        Note[] r = Note.values();
        index %= r.length; // TODO que fem???
        return r[index];
    }
}
