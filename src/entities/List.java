package entities;

import java.util.ArrayList;

/**
 * Stores a group of songs as a playlist with a name and a creator
 */
public class List {

    /**
     * Name of the playlist
     */
    private final String name;

    /**
     * List of all the songs in the playlist
     */
    private final ArrayList<Song> songs;

    /**
     * Name of the creator of the playlist
     */
    private final String creator;

    /**
     * Creates a playlist with the given name and creator
     * @param name Name of the playlist
     * @param creator Name of the creator
     */
    public List(String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.songs = new ArrayList<>();
    }

    /**
     * Creates an empty list with no creator and no name
     */
    public List() {
        this("", "");
    }

    /**
     * Adds a song to the playlist
     * @param s Song to added
     * @return Itself
     */
    public List addSong(Song s) {
        this.songs.add(s);
        return this;
    }

    /**
     * Removes a song from the playlist
     * @param s Song to remove
     */
    public void removeSong(Song s) {
        this.songs.remove(s);
    }

    /**
     * Returns the name of the song
     * @return The name of the playlist
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the songs the playlist has
     * @return List with all the songs in the playlist
     */
    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    /**
     * Returns the name of the creator of the playlist
     * @return The name of the creator of the playlist
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * Checks if an object is equal to this one
     * @param o Other object to compare with
     * @return True if the the objects are the same
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof List)) return false;
        List l = (List)o;
        return (l.name.equals(this.name) && l.creator.equals(this.creator));
    }
}
