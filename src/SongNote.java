public class SongNote implements Comparable<SongNote> {
    private final long tick;
    private final boolean pressed;
    private final byte velocity;
    private final Note note;

    public SongNote(long tick, boolean pressed, byte velocity, Note note) {
        this.tick = tick;
        this.pressed = pressed;
        this.velocity = velocity;
        this.note = note;
    }

    public long getTick() {
        return tick;
    }

    public boolean isPressed() {
        return pressed;
    }

    public byte getVelocity() {
        return velocity;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Retorna <0 si tu vas abans que l'altre.
     *          =0 si sou iguals
     *          >0 si l'altre va abans
     * @param o Element a comparar
     * @return Si va abans o després de l'element a comparar
     */
    @Override
    public int compareTo(SongNote o) {
        long r = this.tick - o.tick;
        if (r < 0) return -1;
        else if (r == 0) return 0;
        else return 1;
    }
}
