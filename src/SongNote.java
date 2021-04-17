public class SongNote {
    private long tick;
    private boolean pressed;
    private byte velocity;
    private Note note;

    public SongNote(long tick, boolean pressed, byte velocity, Note note) {
        this.tick = tick;
        this.pressed = pressed;
        this.velocity = velocity;
        this.note = note;
    }
}
