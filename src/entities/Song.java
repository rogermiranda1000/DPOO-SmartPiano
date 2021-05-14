package entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Song {
    /**
     * Temps [us] per tick
     */
    private double tickLength;
    private final String songName;
    private final String author;
    private final Date creationDate;
    private boolean isPublic;
    private final ArrayList<SongNote> notes;

    public Song(String songName, String author, Date creationDate, double tickLength, boolean isPublic) {
        this.notes = new ArrayList<>();
        this.tickLength = tickLength;
        this.songName = songName;
        this.author = author;
        this.creationDate = creationDate;
        this.isPublic = isPublic;
    }

    public Song(String songName, String author, Date creationDate) {
        this(songName, author, creationDate, -1, false);
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

    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.creationDate);
    }

    public ArrayList<SongNote> getNotes() {
        return this.notes;
    }

    public double getTickLength() {
        return this.tickLength;
    }

    public boolean getPublic() {
        return this.isPublic;
    }

    public void setTickLength(double tickLength) {
        this.tickLength = tickLength;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
