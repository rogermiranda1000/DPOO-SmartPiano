package entities;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private final ArrayList<List> playlists;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.playlists = new ArrayList<>();
    }

    public void addPlaylist(List playlist) {
        this.playlists.add(playlist);
    }

    public void addAllPlaylist(ArrayList<List> playlists) {
        this.playlists.addAll(playlists);
    }

    public int getPlaylistLenght() {
        return this.playlists.size();
    }

    public List getPlaylist(int index) {
        return this.playlists.get(index);
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
