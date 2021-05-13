package model;

import entities.Song;
import entities.User;
import persistance.LoggedUserDAO;
import persistance.SongDAO;
import persistance.UserDAO;

public class BusinessFacade {
    private final SongDAO songManager;
    private final UserDAO userManager;
    private final LoggedUserDAO loggedUserManager;

    private User loggedUser = null;

    public BusinessFacade(SongDAO songManager, UserDAO userManager, LoggedUserDAO loggedUserManager) {
        this.songManager = songManager;
        this.userManager = userManager;
        this.loggedUserManager = loggedUserManager;
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
     * Afegeix una canço som autor VirtualUser
     * @param song Canço a afegir
     * @return Si s'ha afegit (true) o hi ha hagut un error (false)
     */
    public boolean addDownloadedSong(Song song) {
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
        this.loggedUser = this.loggedUserManager.getUser(nick, password);
        return this.loggedUser != null;
    }
}
