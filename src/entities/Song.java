package entities;

import java.util.ArrayList;
import java.util.Collections;

public class Song {
    /**
     * Indica si l'array està ordenat o no
     */
    private boolean isSorted;

    private final ArrayList<SongNote> notes;

    /**
     * Temps [us] per tick
     */
    private final double tickLenght;

    public Song(double tickLenght) {
        this.notes = new ArrayList<>();
        this.tickLenght = tickLenght;
    }

    /**
     * Afegeix una nota al arraylist
     * @param note Nota a afegir
     */
    public void addNote(SongNote note) {
        this.notes.add(note);
        this.isSorted = false;
    }

    private void sort() {
        Collections.sort(this.notes);
        this.isSorted = true;
    }
}
