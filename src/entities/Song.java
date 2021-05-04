package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Song {
    private final ArrayList<SongNote> notes;

    /**
     * Temps [us] per tick
     */
    private final double tickLength;
    private final String songName;
    private final String author;
    private final Date creationDate;

    public Song(String songName, String author, Date creationDate, double tickLength) {
        this.notes = new ArrayList<>();
        this.tickLength = tickLength;
        this.songName = songName;
        this.author = author;
        this.creationDate = creationDate;
    }

    /**
     * Afegeix una nota al arraylist
     * @param note Nota a afegir
     */
    public void addNote(SongNote note) {
        this.notes.add(note);
    }

    public void sort() {
        Collections.sort(this.notes);
    }

    public String getName() {
        return songName;
    }

    public String getArtist() {
        return author;
    }

    public Date getDate() {
        return creationDate;
    }

    public ArrayList<SongNote> getNotes() {
        return this.notes;
    }

    public double getTickLength() {
        return this.tickLength;
    }
}
