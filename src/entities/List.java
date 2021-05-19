package entities;

import java.util.ArrayList;

public class List {
    private final String name;
    private final ArrayList<Song> songs;
    private final String creator;

    public List(String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.songs = new ArrayList<>();
    }

    public List() {
        this("", "");
    }

    public List addSong(Song s) {
        this.songs.add(s);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    public String getCreator() {
        return this.creator;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof List)) return false;
        List l = (List)o;
        return (l.name.equals(this.name) && l.creator.equals(this.creator));
    }
}
