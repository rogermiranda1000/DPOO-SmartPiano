package entities;

import java.text.ParseException;
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
    private String author;
    private final Date creationDate;
    private boolean isPublic;
    private final ArrayList<SongNote> notes;
    private double duration;

    public Song(String songName, String author, Date creationDate, double tickLength, boolean isPublic) {
        this.notes = new ArrayList<>();
        this.tickLength = tickLength;
        this.songName = songName;
        this.author = author;
        this.creationDate = creationDate;
        this.isPublic = isPublic;
    }

    public Song(String songName, String author, Date creationDate, double duration) {
        this(songName, author, creationDate, -1, false);
        this.duration = duration;
    }

    public Song(String songName, String author, Date creationDate) {
        this(songName, author, creationDate, -1, false);
    }

    public Song(String songName, String author, String creationDate) {
        this(songName, author, Song.parseDate(creationDate), -1, false);
    }

    /**
     * User song to add
     * Important: it doesn't have creator not date (yet)
     * @param songName Name of the song
     * @param tickLength Tick length
     * @param isPublic Public song, or private
     * @param notes Notes that builds the song
     */
    public Song(String songName, double tickLength, boolean isPublic, ArrayList<SongNote> notes) {
        this(songName, "", null, tickLength, isPublic);
        this.notes.addAll(notes);
    }

    public Song() {
        this(null, null, null, -1, false);
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
        if (this.creationDate == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd").format(this.creationDate);
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
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

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Song)) return false;
        Song s = (Song) o;
        return (this.songName.equals(s.songName) && this.author.equals(s.author) && this.getDate().equals(s.getDate()));
    }
}
