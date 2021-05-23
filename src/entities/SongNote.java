package entities;

/**
 * Event representing a note press / release in a song
 */
public class SongNote implements Comparable<SongNote> {

    /**
     * Moment in the song in which the note happened
     */
    private final long tick;

    /**
     * True if this event represents a key press, false if it represents a release
     */
    private final boolean pressed;

    /**
     * Force in which the note was pressed (127 = maximum force, 0 = no force)
     */
    private final byte velocity;

    /**
     * Octave this note belongs in
     */
    private final byte octave;

    /**
     * Note in the octave this event represents
     */
    private final Note note;

    /**
     * Generates a song note with the given attributes
     * @param tick Tick in the song in which the note happened
     * @param pressed True if this event represents a key press, false if it represents a release
     * @param velocity Force in which the note was pressed (127 = maximum force, 0 = no force)
     * @param octave Octave this note belongs in
     * @param note Note in the octave this event represents
     */
    public SongNote(long tick, boolean pressed, byte velocity, byte octave, Note note) {
        this.tick = tick;
        this.pressed = pressed;
        this.velocity = velocity;
        this.octave = octave;
        this.note = note;
    }

    /**
     * Returns the tick of the note
     * @return Tick in which the note happened
     */
    public long getTick() {
        return tick;
    }

    /**
     * Returns if the note represents a key press
     * @return True if this event represents a key press, false if it represents a release
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Returns the velocity of the note
     * @return Force in which the note was pressed (127 = maximum force, 0 = no force)
     */
    public byte getVelocity() {
        return velocity;
    }

    /**
     * Returns the octave where the note belongs
     * @return Octave of the note
     */
    public byte getOctave() {
        return octave;
    }

    /**
     * Returns the note this event represents
     * @return The note of the event
     */
    public Note getNote() {
        return note;
    }

    /**
     * Returns the id of the note
     * @return Id of the note, combining the note and octave
     */
    public int getId() {
        return (this.getOctave() + 1) * 12 + this.getNote().ordinal();
    }

    /**
     * Returns less than 0 if this goes before other,
     *          0 if you are equal,
     *          greater than 0 if other goes before.
     * @param o Object to compare to
     * @return If this goes before the note being compared with
     */
    @Override
    public int compareTo(SongNote o) {
        long r = this.tick - o.tick;
        if (r < 0) return -1;
        else if (r == 0) return 0;
        else return 1;
    }
}
