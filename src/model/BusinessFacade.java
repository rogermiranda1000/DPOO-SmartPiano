package model;

import entities.*;
import entities.Config;
import persistance.*;

import java.util.ArrayList;

public class BusinessFacade {
    private final SongDAO songManager;
    private final UserDAO userManager;
    private final PlaylistDAO playlistManager;
    private final ConfigDAO configManager;
    private final StatisticsDAO statisticsManager;

    private User loggedUser;
    private Config loggedUserConfig;
    private ArrayList<List> loggedUserPlaylists;

    public BusinessFacade(SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager, ConfigDAO configManager, StatisticsDAO statisticsManager) {
        this.songManager = songManager;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
        this.configManager = configManager;
        this.statisticsManager = statisticsManager;

        this.loggedUser = null;
        this.loggedUserConfig = null;
        this.loggedUserPlaylists = null;
    }

    private boolean updatePlaylists() {
        if (this.loggedUser == null) return false;

        this.loggedUserPlaylists = this.playlistManager.getPlaylists(this.loggedUser);
        return true;
    }

    private boolean updateConfig() {
        if (this.loggedUser == null) return false;

        this.loggedUserConfig = this.configManager.getConfig(this.loggedUser.getName());
        return (this.loggedUserConfig != null);
    }

    private Config getConfig() {
        if (this.loggedUserConfig == null) {
            if (!updateConfig()) return null;
        }

        return this.loggedUserConfig;
    }

    public Song getSong(Song s) {
        this.songManager.updateSong(s);
        return s;
    }

    public ArrayList<List> getPlaylists() {
        if (this.loggedUserPlaylists == null) {
            if (!this.updatePlaylists()) return new ArrayList<>();
        }
        return this.loggedUserPlaylists;
    }

    /**
     * Donat un nom obtè la playlist del usuari amb coincidencia.
     * Important: les cançons de la llista son vàl·lides (tenen notes)
     * @param list Nom de la llista
     * @return LLista amb cançons
     */
    public List getPlaylist(String list) {
        if (this.loggedUser == null) return null;

        List search = new List(list, this.loggedUser.getName());
        for (List l : this.getPlaylists()) {
            if (l.equals(search)) {
                for (Song s: l.getSongs()) this.songManager.updateSong(s);
                return l;
            }
        }
        return null; // not found
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }


    /**
     * Obtè les cançons del usuari loguejat i les públiques
     * /!\ La informació de les cançons és la bàsica (no hi ha tecles)
     * @return Cançons visibles per l'usuari loguejat
     */
    public ArrayList<Song> getSongs() {
        if (this.loggedUser == null) return new ArrayList<>(); // empty array

        return this.songManager.getAccessibleSongs(this.loggedUser.getName());
    }

    /**
     * Adds a RegisteredUser's song
     * @param song Song to add
     * @return If the song was added (true), or not (false)
     */
    public boolean addSong(Song song) {
        if (this.loggedUser == null) return false;

        //if (!this.userManager.existsUser(song.getArtist())) return false; // no hauria de pasar mai
        song.setAuthor(this.loggedUser.getName());
        if (this.existsSong(song)) return false;
        return this.songManager.addSong(song);
    }

    /**
     * Afegeix una canço som autor VirtualUser.
     * Accessible des del fil principal i des de SongDownloader (ha de ser syncronized)
     * @param song Canço a afegir
     * @return Si s'ha afegit (true) o hi ha hagut un error (false)
     */
    public boolean addDownloadedSong(Song song) {
        if (!this.userManager.existsVirtualUser(song.getArtist())) {
            if (!this.userManager.addVirtualUser(song.getArtist())) return false;
        }
        return this.songManager.addVirtualSong(song);
    }

    /**
     * Elimina una canço si l'autor és el propi usuari
     * @param song Canço a eliminar
     * @return Si s'ha pogut eliminar (true) o no (false)
     */
    public boolean deleteSong(Song song) {
        if (this.loggedUser == null) return false; // si no hi ha usuari loguejat, no pot eliminar una canço
        Boolean isAuthor = this.songManager.isAuthor(song, this.loggedUser.getName());
        if (isAuthor == null || !isAuthor) return false;

        // elimina les cançons
        if (!this.playlistManager.removeSongAllPlaylists(song)) return false;
        if (!this.statisticsManager.deleteStatistics(song)) return false;
        return this.songManager.deleteSong(song);
    }

    public synchronized boolean existsSong(Song song) {
        return this.songManager.existsSong(song);
    }

    public boolean addUser(String nick, String email, String password) {
        return this.userManager.addUser(new User(nick, email), password);
    }

    public boolean deleteLoggedUser(String password) {
        if (this.loggedUser == null) return false;

        if (!this.configManager.deleteUserConfig(this.loggedUser.getName())) return false;
        if (!this.statisticsManager.deletePlayerStatistics(this.loggedUser.getName())) return false;
        for (List l : this.getPlaylists()) {
            if (!this.playlistManager.removePlaylist(l)) return false;
        }
        if (!this.songManager.deleteUserSongs(this.loggedUser.getName())) return false;
        return this.userManager.deleteUser(this.loggedUser, password);
    }

    /**
     * Intenta obtenir l'usuari especificat i el guarda (si existeix)
     * @param nick Nick o email del usuari
     * @param password Contrasenya
     * @return Si s'ha obtingut (true) o no (false) l'usuari
     */
    public boolean login(String nick, String password) {
        this.loggedUser = this.userManager.getUser(nick, password);
        return (this.loggedUser != null);
    }

    public void logout() {
        this.loggedUser = null;
        this.loggedUserPlaylists = null;
        this.loggedUserConfig = null;
    }

    public float getSongVolume() {
        Config c = this.getConfig();
        if (c == null) return 1.f;
        return c.getVolumeSong();
    }

    public float getPianoVolume() {
        Config c = this.getConfig();
        if (c == null) return 1.f;
        return c.getVolumePiano();
    }

    public boolean addPlaylist(String list) {
        if (this.loggedUser == null) return false;
        List add = new List(list, this.loggedUser.getName());
        if (this.playlistManager.existsPlaylist(add)) return false;

        if (!this.playlistManager.createPlaylist(add)) return false;
        this.loggedUserPlaylists.add(add);
        return true;
    }

    public boolean removePlaylist(String list) {
        if (this.loggedUser == null) return false;
        List remove = new List(list, this.loggedUser.getName());
        if (!this.playlistManager.existsPlaylist(remove)) return false;

        if (!this.playlistManager.removePlaylist(remove)) return false;
        this.loggedUserPlaylists.remove(remove);
        return true;
    }

    public boolean addSongPlaylist(Song song, String playlist) {
        List add = this.getPlaylist(playlist);
        if (add == null || this.loggedUser == null) return false;

        if (!this.playlistManager.addSongPlaylist(new List(playlist, this.loggedUser.getName()), song)) return false;
        add.addSong(song);
        return true;
    }

    public Boolean existsSongInPlaylist(Song song, String playlist) {
        List search = this.getPlaylist(playlist);
        if (search == null) return null;

        for (Song s : search.getSongs()) {
            if (s.equals(song)) return true;
        }
        return false;
    }

    public boolean addPlay(int secondsPlayed, Song song) {
        if (this.loggedUser == null) return false;
        return this.statisticsManager.addListen(this.loggedUser.getName(), song, secondsPlayed);
    }

    public boolean removeSongPlaylist(String playlist, Song song) {
        if (this.loggedUser == null) return false;
        if (!this.playlistManager.removeSongPlaylist(new List(playlist, this.loggedUser.getName()), song)) return false;

        this.getPlaylist(playlist).removeSong(song);
        return true;
    }

    public char[] getBinds() {
        Config c = this.getConfig();
        if (c == null) return null;
        return c.getNotesBind();
    }

    public boolean setKeyBinder(Note key, byte octava, char newBind) {
        if (this.loggedUser == null) return false;

        char[] bind = this.loggedUserConfig.getNotesBind();
        bind[(octava-KeyboardConstants.INIT_OCTAVA)*12 + key.ordinal()] = newBind;
        this.loggedUserConfig.setNoteBind(bind); // tecnicament va per referencia; no faria falta
        this.configManager.setConfig(this.loggedUser.getName(), bind);
        return true;
    }

    public boolean setVolumePiano(float volume) {
        if (this.loggedUser == null) return false;
        if (!this.configManager.setVolumePiano(this.loggedUser.getName(), volume)) return false;
        this.loggedUserConfig.setVolumePiano(volume);
        return true;
    }

    public boolean setVolumeSong(float volume) {
        if (this.loggedUser == null) return false;
        if (!this.configManager.setVolumeSong(this.loggedUser.getName(), volume)) return false;
        this.loggedUserConfig.setVolumeSong(volume);
        return true;
    }

    public int[] getSongStatistics() {
        return this.statisticsManager.getSongStatistics();
    }

    public int[] getTimeStatistics() {
        return this.statisticsManager.getTimeStatistics();
    }

    public Song[] getTop5(int[] plays) {
        return this.statisticsManager.getTop5(plays);
    }
}
