package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Class that stores information about a song and its functionalities
 */
public class Song {

    /**
     * Time [us] per tick
     */
    private double tickLength;

    /**
     * The name of the song
     */
    private final String songName;

    /**
     * The name of the author of the song
     */
    private String author;

    /**
     * Date when the song was created
     */
    private final Date creationDate;

    /**
     * True it it is public, false if it is private
     */
    private boolean isPublic;

    /**
     * List of all the notes that form the song
     */
    private final ArrayList<SongNote> notes;

    /**
     * Total duration of the song [s]
     */
    private double duration;

    /**
     * Generates a song with the given attributes
     * /!\ The song doesn't have any notes (yet)
     * @param songName The name of the song
     * @param author The name of the author of the song
     * @param creationDate Date when the song was created
     * @param tickLength Time [us] per tick
     * @param isPublic True it it is public, false if it is private
     */
    public Song(String songName, String author, Date creationDate, double tickLength, boolean isPublic) {
        this.notes = new ArrayList<>();
        this.tickLength = tickLength;
        this.songName = songName;
        this.author = author;
        this.creationDate = creationDate;
        this.isPublic = isPublic;
    }

    /**
     * Generates a song with the given attributes
     * /!\ The song doesn't have any notes (yet)
     * @param songName The name of the song
     * @param author The name of the author of the song
     * @param creationDate Date when the song was created
     * @param duration Duration of the song
     */
    public Song(String songName, String author, Date creationDate, double duration) {
        this(songName, author, creationDate, -1, false);
        this.duration = duration;
    }

    /**
     * Generates a song with the given attributes
     * /!\ The song doesn't have any notes (yet)
     * @param songName The name of the song
     * @param author The name of the author of the song
     * @param creationDate (Date) Date when the song was created
     */
    public Song(String songName, String author, Date creationDate) {
        this(songName, author, creationDate, -1, false);
    }

    /**
     * Generates a song with the given attributes
     * /!\ The song doesn't have any notes (yet)
     * @param songName The name of the song
     * @param author The name of the author of the song
     * @param creationDate (String) Date when the song was created
     */
    public Song(String songName, String author, String creationDate) {
        this(songName, author, Song.parseDate(creationDate), -1, false);
    }

    /**
     * Adds a song the user has recorded
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

    /**
     * Generates a completely empty song
     */
    public Song() {
        this(null, null, null, -1, false);
    }

    /**
     * Adds a note to the song
     * @param note Note to add
     */
    public void addNote(SongNote note) {
        this.notes.add(note);
    }

    /**
     * Sorts the notes in the list by tick
     */
    public void sort() {
        Collections.sort(this.notes);
    }

    /**
     * Returns the name of the song
     * @return Name of the song
     */
    public String getName() {
        return songName;
    }

    /**
     * Returns the name of the artist
     * @return Name of the artist of the song
     */
    public String getArtist() {
        return author;
    }

    /**
     * Returns the date
     * @return The creation date of the song in a text format
     */
    public String getDate() {
        if (this.creationDate == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd").format(this.creationDate);
    }

    /**
     * Transforms the Date input into a text format
     * @param date Date to parse
     * @return String of the resulting date in "yyyy-MM-dd" format
     */
    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Returns the notes of the song
     * @return List of notes in the song
     */
    public ArrayList<SongNote> getNotes() {
        return this.notes;
    }

    /**
     * Returns the tick length of the song
     * @return Time [us] that each tick represents
     */
    public double getTickLength() {
        return this.tickLength;
    }

    /**
     * Returns true id the song is public
     * @return True = the song is public, false = it is private
     */
    public boolean getPublic() {
        return this.isPublic;
    }

    /**
     * Sets the tick length of the song
     * @param tickLength The time [us] each tick should last
     */
    public void setTickLength(double tickLength) {
        this.tickLength = tickLength;
    }

    /**
     * Saves the public state of the song
     * @param isPublic True if it is public, false if it is private
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Sets the author of the song
     * @param author Name of the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns if the song is valid or not (if the keys are loaded)
     * @return Valid song (true), or not (false)
     */
    public boolean isValid() {
        return this.notes.size() > 0;
    }

    /**
     * Duration [s] of the song
     * @return Seconds the song lasts for
     */
    public double getDuration() {
        if (this.notes.size() == 0) return this.duration; // no hi ha canço -> retorna la durada guardada
        // Al tenir les notes ordenades per temps, la última del array és la última en sonar
        return (this.notes.get(this.notes.size()-1).getTick() * this.tickLength) / (1000 * 1000);
    }

    /**
     * Returns the Song name and Author in a single formatted string
     * @return "songName" , per "author"
     */
    @Override
    public String toString() {
        return this.songName + ", per " + this.author;
    }

    /**
     * Compares an object with this to check if they are equal
     * @param o Other object to compare with
     * @return True if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Song)) return false;
        Song s = (Song) o;
        return (this.songName.equals(s.songName) && this.author.equals(s.author) && this.getDate().equals(s.getDate()));
    }
}
