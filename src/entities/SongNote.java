package entities;

public class SongNote implements Comparable<SongNote> {
    private final long tick;
    private final boolean pressed;
    private final byte velocity;
    private final byte octave;
    private final Note note;

    public SongNote(long tick, boolean pressed, byte velocity, byte octave, Note note) {
        this.tick = tick;
        this.pressed = pressed;
        this.velocity = velocity;
        this.octave = octave;
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

    public byte getOctave() {
        return octave;
    }

    public Note getNote() {
        return note;
    }

    public int getId() {
        return (this.getOctave() + 1) * 12 + this.getNote().ordinal();
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

    /**
     * Check if it's the same note (independent from the tick and pressed)
     * @param o Note to compare
     * @return If it's the same velocity, ocatve and note (true), or not (false)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SongNote)) return false;
        SongNote note = (SongNote) o;
        return (note.velocity == this.velocity && note.octave == this.octave && note.note == this.note);
    }
}
