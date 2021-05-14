package entities;

import java.util.ArrayList;
import java.util.Date;

public class List {
    private final String name;
    private final ArrayList<Song> songs;
    private final User creator;
    private final Date creationDate;

    public List(String name, User creator, Date creationDate) {
        this.name = name;
        this.creator = creator;
        this.creationDate = creationDate;
        this.songs = new ArrayList<>();
    }

    public List(String name, User creator) {
        this(name, creator, new Date());
    }

    public void addSong(Song s) {
        this.songs.add(s);
    }

    public String getName() {
        return this.name;
    }

    public Song getSongs(int index) {
        return this.songs.get(index);
    }

    public int getSongNumber() {
        return this.songs.size();
    }
}
