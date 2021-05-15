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
    private Float score;
    private final ArrayList<SongNote> notes;
    private double duration;

    public Song(String songName, String author, Date creationDate, double tickLength, boolean isPublic, Float score) {
        this.notes = new ArrayList<>();
        this.tickLength = tickLength;
        this.songName = songName;
        this.author = author;
        this.creationDate = creationDate;
        this.isPublic = isPublic;
        this.score = score;
    }

    public Song(String songName, String author, Date creationDate, Float score, double duration) {
        this(songName, author, creationDate, -1, false, score);
        this.duration = duration;
    }

    public Song(String songName, String author, Date creationDate) {
        this(songName, author, creationDate, -1, false, null);
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

    public void setScore(float score) {
        this.score = score;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Obté la puntuació de la canço
     * @return Mitjana de les puntuacions [@Nullable]
     */
    public Float getScore() {
        return this.score;
    }

    /**
     * Duració [s] de la canço
     * @return Segons que dura la canço
     */
    public double getDuration() {
        if (this.notes.size() == 0) return this.duration; // no hi ha canço -> retorna la durada guardada
        // Al tenir les notes ordenades per temps, la última del array és la última en sonar
        return (this.notes.get(this.notes.size()-1).getTick() * this.tickLength) / (1000 * 1000);
    }

    @Override
    public String toString() {
        return this.songName + ", per " + this.author;
    }
}
