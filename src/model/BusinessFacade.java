package model;

import entities.List;
import entities.Song;
import entities.User;
import persistance.PlaylistDAO;
import persistance.SongDAO;
import persistance.UserDAO;

import java.util.ArrayList;

public class BusinessFacade {
    private final SongDAO songManager;
    private final UserDAO userManager;
    private final PlaylistDAO playlistManager;

    private User loggedUser = null;

    public BusinessFacade(SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager) {
        this.songManager = songManager;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
    }

    public ArrayList<List> getPlaylists() {
        if (this.loggedUser == null) return new ArrayList<>(); // empty array

        ArrayList<List> playlists = this.playlistManager.getPlaylists(this.loggedUser);
        for (List p: playlists) {
            for (Song s: p.getSongs()) this.songManager.updateSong(s);
        }
        return playlists;
    }

    /**
     * Afegeix una canço som autor RegisteredUser
     * @param song Canço a afegir
     * @return Si s'ha afegit (true) o hi ha hagut un error (false)
     */
    public boolean addSong(Song song) {
        //if (!this.userManager.existsUser(song.getArtist())) return false; // no hauria de pasar mai
        return this.songManager.addSong(song);
    }

    /**
     * Afegeix una canço som autor VirtualUser.
     * Accessible des del fil principal i des de SongDownloader (ha de ser syncronized)
     * @param song Canço a afegir
     * @return Si s'ha afegit (true) o hi ha hagut un error (false)
     */
    public synchronized boolean addDownloadedSong(Song song) {
        if (!this.userManager.existsVirtualUser(song.getArtist())) {
            if (!this.userManager.addVirtualUser(song.getArtist())) return false;
        }
        return this.songManager.addVirtualSong(song);
    }

    public boolean deleteSong(Song song) {
        return this.songManager.deleteSong(song);
    }

    public boolean existsSong(Song song) {
        return this.songManager.existsSong(song);
    }

    public boolean addUser(String nick, String email, String password) {
        return this.userManager.addUser(new User(nick, email), password);
    }

    public boolean deleteUser(String nick, String email, String password) {
        return this.userManager.deleteUser(new User(nick, email), password);
    }

    /**
     * Intenta obtenir l'usuari especificat i el guarda (si existeix)
     * @param nick Nick o email del usuari
     * @param password Contrasenya
     * @return Si s'ha obtingut (true) o no (false) l'usuari
     */
    public boolean login(String nick, String password) {
        this.loggedUser = this.userManager.getUser(nick, password);
        if (this.loggedUser == null) return false;

        //this.loggedUser.addPlaylist();
        return true;
    }
}
