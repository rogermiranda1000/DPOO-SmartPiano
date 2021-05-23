package model;

import entities.*;
import entities.Config;
import persistance.*;

import java.util.ArrayList;

/**
 * Class tasked with managing the access to the business logic, also manages DAO access
 */
public class BusinessFacade {

    /**
     * DAO for song related operations
     */
    private final SongDAO songManager;

    /**
     * DAO for user related operations
     */
    private final UserDAO userManager;

    /**
     * DAO for playlist related operations
     */
    private final PlaylistDAO playlistManager;

    /**
     * DAO for configuration related operations
     */
    private final ConfigDAO configManager;

    /**
     * DAO for generating statistics
     */
    private final StatisticsDAO statisticsManager;

    /**
     * User that's using the app
     */
    private User loggedUser;

    /**
     * Configuration of the logged user
     */
    private Config loggedUserConfig;

    /**
     * Playlists of the logged user
     */
    private ArrayList<List> loggedUserPlaylists;

    /**
     * Initiates the class and the DAOs
     * @param songManager DAO for song related operations
     * @param userManager DAO for user related operations
     * @param playlistManager DAO for playlist related operations
     * @param configManager DAO for configuration related operations
     * @param statisticsManager DAO for generating statistics
     */
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

    /**
     * Updates the saved playlists for the current logged user
     * @return True if the operation was successful
     */
    private boolean updatePlaylists() {
        if (this.loggedUser == null) return false;

        this.loggedUserPlaylists = this.playlistManager.getPlaylists(this.loggedUser);
        return true;
    }

    /**
     * Updates the saved configuration for the current logged user
     * @return True if the operation was successful
     */
    private boolean updateConfig() {
        if (this.loggedUser == null) return false;

        this.loggedUserConfig = this.configManager.getConfig(this.loggedUser.getName());
        return (this.loggedUserConfig != null);
    }

    /**
     * Returns the saved configuration
     * @return Saved configuration of the logged user
     */
    private Config getConfig() {
        if (this.loggedUserConfig == null) {
            if (!updateConfig()) return null;
        }

        return this.loggedUserConfig;
    }

    /**
     * Fills the given song with notes
     * @param s Song to fill
     * @return The input song filled with notes, if it was valid
     */
    public Song getSong(Song s) {
        if (!s.isValid()) this.songManager.updateSong(s); // m'aprofito de la referència per Java; al cambiar la canço aquí tambè la estic cambiant on es crida i pot arribar al cas que ja estigui actualitzada
        return s;
    }

    /**
     * Returns the playlists of the logged user
     * @return The saved playlists, it tries to update them if they are null
     */
    public ArrayList<List> getPlaylists() {
        if (this.loggedUserPlaylists == null) {
            if (!this.updatePlaylists()) return new ArrayList<>();
        }
        return this.loggedUserPlaylists;
    }

    /**
     * Given a name of a playlist returns the matching playlist of the user,
     * /!\ the songs in the playlist are filled with notes
     * @param list Name of the playlist
     * @return List of songs (filled with notes)
     */
    public List getPlaylist(String list) {
        List l = this.getBasicPlaylist(list);
        if (l != null) {
            for (Song s: l.getSongs()) this.songManager.updateSong(s);
        }
        return l;
    }

    /**
     * Given a name of a playlist returns the matching playlist of the user, without notes
     * @param list Name of the list
     * @return List of songs (without notes)
     */
    public List getBasicPlaylist(String list) {
        if (this.loggedUser == null) return null;

        List search = new List(list, this.loggedUser.getName());
        for (List l : this.getPlaylists()) {
            if (l.equals(search)) return l;
        }
        return null; // not found
    }

    /**
     * Returns the logged user
     * @return The user that logged it
     */
    public User getLoggedUser() {
        return this.loggedUser;
    }


    /**
     * Obtains every song the user should be able to see,
     * /!\ The songs don't have notes in them
     * @return Visible notes for the logged user
     */
    public ArrayList<Song> getSongs() {
        if (this.loggedUser == null) return new ArrayList<>(); // empty array

        return this.songManager.getAccessibleSongs(this.loggedUser.getName());
    }

    /**
     * Adds a song created by the logged user
     * @param song Song to add
     * @return True if the operation was successful
     */
    public boolean addSong(Song song) {
        if (this.loggedUser == null) return false;

        //if (!this.userManager.existsUser(song.getArtist())) return false; // no hauria de pasar mai
        song.setAuthor(this.loggedUser.getName());
        if (this.existsSong(song)) return false;
        return this.songManager.addSong(song);
    }

    /**
     * Adds a song with a virtual user
     * Accessible through the main thread and through SongDownloader (must be synchronized)
     * @param song Song to add
     * @return True if the operation was successful
     */
    public boolean addDownloadedSong(Song song) {
        if (!this.userManager.existsVirtualUser(song.getArtist())) {
            if (!this.userManager.addVirtualUser(song.getArtist())) return false;
        }
        return this.songManager.addVirtualSong(song);
    }

    /**
     * Deletes a song if it's author is the logged user
     * @param song Song to eliminate
     * @return True if the operation was successful
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

    /**
     * Checks if a song exists in the database
     * @param song Song to look for
     * @return True if the song exists
     */
    public synchronized boolean existsSong(Song song) {
        return this.songManager.existsSong(song);
    }

    /**
     * Adds a new user to the database
     * @param nick Name of the user
     * @param email Email of the user
     * @param password Password of the user
     * @return True if the user could be created
     */
    public boolean addUser(String nick, String email, String password) {
        return this.userManager.addUser(new User(nick, email), password);
    }

    /**
     * Tries to delete the logged user with the given password
     * @param password Password the user input
     * @return True if the password was correct and the user could be deleted
     */
    public boolean deleteLoggedUser(String password) {
        if (this.loggedUser == null) return false;

        if (this.userManager.getUser(this.loggedUser.getName(), password) == null) return false; // password incorrecte

        if (!this.configManager.deleteUserConfig(this.loggedUser.getName())) return false;
        if (!this.statisticsManager.deletePlayerStatistics(this.loggedUser.getName())) return false;
        for (List l : this.getPlaylists()) {
            if (!this.playlistManager.removePlaylist(l)) return false;
        }
        if (!this.songManager.deleteUserSongs(this.loggedUser.getName())) return false;
        return this.userManager.deleteUser(this.loggedUser, password);
    }

    /**
     * Tries to obtain the specified user and saves it (if it exists)
     * @param nick Username or email of the user
     * @param password Password the user tried to log in with
     * @return True if the (name/email - password) combination matched a user
     */
    public boolean login(String nick, String password) {
        this.loggedUser = this.userManager.getUser(nick, password);
        return (this.loggedUser != null);
    }

    /**
     * Deletes the user's information from RAM memory
     */
    public void logout() {
        this.loggedUser = null;
        this.loggedUserPlaylists = null;
        this.loggedUserConfig = null;

        System.gc(); // l'enunciat especifica que no poden quedar dades de l'usuari en RAM; cap problema! ;)
    }

    /**
     * Returns the song volume of the logged user
     * @return Song volume of the logged user
     */
    public float getSongVolume() {
        Config c = this.getConfig();
        if (c == null) return 1.f;
        return c.getVolumeSong();
    }

    /**
     * Returns the piano volume of the logged user
     * @return Piano volume of the logged user
     */
    public float getPianoVolume() {
        Config c = this.getConfig();
        if (c == null) return 1.f;
        return c.getVolumePiano();
    }

    /**
     * Creates a playlist for the logged user in the database
     * @param list Playlist to create
     * @return True if the operation was successful
     */
    public boolean addPlaylist(String list) {
        if (this.loggedUser == null) return false;
        List add = new List(list, this.loggedUser.getName());
        if (this.playlistManager.existsPlaylist(add)) return false;

        if (!this.playlistManager.createPlaylist(add)) return false;
        this.loggedUserPlaylists.add(add);
        return true;
    }

    /**
     * Deletes a playlist for the logged user
     * @param list Playlist to delete
     * @return True if the operation was successful
     */
    public boolean removePlaylist(String list) {
        if (this.loggedUser == null) return false;
        List remove = new List(list, this.loggedUser.getName());
        if (!this.playlistManager.existsPlaylist(remove)) return false;

        if (!this.playlistManager.removePlaylist(remove)) return false;
        this.loggedUserPlaylists.remove(remove);
        return true;
    }

    /**
     * Adds a song to a playlist created by the logged user
     * @param song Song to add to the playlist
     * @param playlist Playlist to add the song to
     * @return True if the operation was successful
     */
    public boolean addSongPlaylist(Song song, String playlist) {
        List add = this.getBasicPlaylist(playlist);
        if (add == null) return false;

        if (!this.playlistManager.addSongPlaylist(new List(playlist, this.loggedUser.getName()), song)) return false;
        add.addSong(song);
        return true;
    }

    /**
     * Checks if a song exists in a playlist
     * @param song Song to look for
     * @param playlist Playlist the song should be in
     * @return True if the song exists in the specified playlist
     */
    public Boolean existsSongInPlaylist(Song song, String playlist) {
        List search = this.getBasicPlaylist(playlist);
        if (search == null) return null;

        for (Song s : search.getSongs()) {
            if (s.equals(song)) return true;
        }
        return false;
    }

    /**
     * Adds a play from the logged user to the specified song with the specified length
     * @param secondsPlayed Seconds the sog was played for
     * @param song Song the user played
     * @return True if the operation was successful
     */
    public boolean addPlay(int secondsPlayed, Song song) {
        if (this.loggedUser == null) return false;
        return this.statisticsManager.addListen(this.loggedUser.getName(), song, secondsPlayed);
    }

    /**
     * Removes a song from a playlist created by the logged user
     * @param playlist Playlist to remove the song from
     * @param song Song to remove from the playlist
     * @return True if the operation was successful
     */
    public boolean removeSongPlaylist(String playlist, Song song) {
        List list = this.getBasicPlaylist(playlist);
        if (list == null) return false;

        if (!this.playlistManager.removeSongPlaylist(new List(playlist, this.loggedUser.getName()), song)) return false;
        list.removeSong(song);
        return true;
    }

    /**
     * Returns the note binds configuration for the logged user
     * @return A character array of the bindings of the notes, from lowest to highest
     */
    public char[] getBinds() {
        Config c = this.getConfig();
        if (c == null) return null;
        return c.getNotesBind();
    }

    /**
     * Saves a new note bind configuration for the logged user in the database
     * @param key Note to change the bind of
     * @param octava Octave of the note
     * @param newBind New character the note should be associated with
     * @return True if the operation was successful
     */
    public boolean setKeyBinder(Note key, byte octava, char newBind) {
        if (this.loggedUser == null) return false;

        char[] bind = this.loggedUserConfig.getNotesBind();
        bind[(octava-KeyboardConstants.INIT_OCTAVE)*12 + key.ordinal()] = newBind;
        this.loggedUserConfig.setNoteBind(bind); // tecnicament va per referencia; no faria falta
        this.configManager.setConfig(this.loggedUser.getName(), bind);
        return true;
    }

    /**
     * Saves a new piano volume for the logged user
     * @param volume New piano volume (0 = silent, 1 = max volume)
     * @return True if the operation was successful
     */
    public boolean setVolumePiano(float volume) {
        if (this.loggedUser == null) return false;
        if (!this.configManager.setVolumePiano(this.loggedUser.getName(), volume)) return false;
        this.loggedUserConfig.setVolumePiano(volume);
        return true;
    }

    /**
     * Saves a new song player volume for the logged user
     * @param volume New song player volume (0 = silent, 1 = max volume)
     * @return True if the operation was successful
     */
    public boolean setVolumeSong(float volume) {
        if (this.loggedUser == null) return false;
        if (!this.configManager.setVolumeSong(this.loggedUser.getName(), volume)) return false;
        this.loggedUserConfig.setVolumeSong(volume);
        return true;
    }

    /**
     * Returns data necessary to create "plays" statistics
     * @return The data of how many song plays per hour exist in the last 24 hours, the last position is the current hour
     */
    public int[] getSongStatistics() {
        return this.statisticsManager.getSongStatistics();
    }

    /**
     * Returns data necessary to create "seconds listened" statistics
     * @return The data of how many song plays per hour exist in the last 24 hours, the last position is the current hour
     */
    public int[] getTimeStatistics() {
        return this.statisticsManager.getTimeStatistics();
    }

    /**
     * Returns data necessary to create the "top 5 songs" table
     * @param plays Array that will get filled up with the plays each song has, from most popular to least
     * @return Top 5 most listened to songs in the database, ordered from most popular to least
     */
    public Song[] getTop5(int[] plays) {
        return this.statisticsManager.getTop5(plays);
    }
}
