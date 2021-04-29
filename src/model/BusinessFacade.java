package model;

import entities.Song;
import entities.User;

public class BusinessFacade {
    private final SongDAO songManager;
    private final UserDAO userManager;

    private User loggedUser = null;

    public BusinessFacade(SongDAO songManager, UserDAO userManager) {
        this.songManager = songManager;
        this.userManager = userManager;
    }

    public boolean addSong(Song song) {
        return this.songManager.addSong(song);
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
        return this.loggedUser != null;
    }
}
