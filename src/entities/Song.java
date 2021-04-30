package entities;

import java.sql.Date;

public class Song {
    private boolean isPublic;
    private String name;
    private Date date;
    private String artist;
    // TODO SongNote


    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }
}
