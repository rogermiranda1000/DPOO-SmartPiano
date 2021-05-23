package controller;

import entities.List;
import entities.Note;
import entities.Song;
import entities.SongNote;
import model.BusinessFacade;
import persistance.*;
import view.LogIn;
import view.Menu;
import view.NewPlayNotifier;

import java.util.ArrayList;

/**
 * Class that controls the flow of the program
 */
public class Controller implements LoginEvent, MenuEvent, SongsEvent, PlaylistEvent, SongNotifier, SongRequest, RankingEvent, PlaysManager, TeclaEvent, UpdateConfigEvent, RecordingEvent, SongRequestPiano, NewPlayNotifier {

    /**
     * The main view of the app, with every element the user can interact with
     */
    private Menu menu;

    /**
     * Window the user will use to log in or register
     */
    private LogIn login;

    /**
     * Object used to access the business logic and the database
     */
    private final BusinessFacade model;

    /**
     * Controls the music that sound in the music player
     */
    private final MusicController musicController;

    /**
     * Controls the actions the user can so in the piano, like playing songs and recording
     */
    private final PianoController pianoController;

    /**
     * Creates the controller that will manage the flow of the program
     * @param scrappingTime Interval in milliseconds describing the time between scrapings
     * @param songManager DAO used do access the information in the database regarding songs
     * @param userManager DAO used do access the information in the database regarding users
     * @param playlistManager DAO used do access the information in the database regarding plays
     * @param configManager DAO used do access the information in the database regarding the user's configuration
     * @param statisticsManager DAO used do access the information in the database to create statistics
     */
    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager, ConfigDAO configManager, StatisticsDAO statisticsManager) {
        this.model = new BusinessFacade(songManager, userManager, playlistManager, configManager, statisticsManager);

        this.musicController = new MusicController(this);
        this.pianoController = new PianoController();

        new SongDownloader(this, scrappingTime).start();
        new RankingHourUpdater(this).start();

        this.login = new LogIn(this);
        this.login.setVisible(true);
    }

    /**
     * Requests a user's login with the given (username/email - password) combination
     * @param user Username or email input by the user
     * @param password Password input by the user
     */
    @Override
    public void requestLogin(String user, String password) {
        if (this.model.login(user, password)) {
            this.login.dispose();
            this.login = null;

            this.menu = new Menu(this.musicController, this, this, this, this, this, this, this, this, this);
            this.menu.setVisible(true);
            this.menu.loadConfig(this.model.getBinds());
            this.pianoController.addEventListener(this.menu);

            this.musicController.setVolume(this.model.getSongVolume());
            this.pianoController.setVolume(this.model.getPianoVolume());
            this.menu.setConfig(this.model.getSongVolume(), this.model.getPianoVolume());
            this.menu.setUserInformation(this.model.getLoggedUser().getName(), this.model.getLoggedUser().getEmail());
        } else this.login.wrongLogin();
    }

    /**
     * Requests an account creation with the given username, email and password
     * @param user Username input by the user
     * @param email Email input by the user
     * @param password Password input by the user
     */
    @Override
    public void requestRegister(String user, String email, String password) {
        if (this.model.addUser(user, email, password)) this.login.userCreated();
        else this.login.wrongCreation();
    }

    /**
     * Adds a song to the database
     * @param song Song to add
     */
    @Override
    public void addSong(Song song) {
        if (!this.model.existsSong(song)) this.model.addDownloadedSong(song);
    }

    /**
     * Returns the playlists of the logged user
     * @return The names of all the playlists
     */
    @Override
    public ArrayList<String> getPlaylists() {
        ArrayList<List> l = this.model.getPlaylists();
        ArrayList<String> r = new ArrayList<>();
        for (List list: l) r.add(list.getName());
        return r;
    }

    /**
     * Returns all the songs in a playlist
     * @param playlist Name of the playlist to look inside
     * @return Cançons formant playlist
     */
    @Override
    public ArrayList<Song> getPlaylistSongs(String playlist) {
        ArrayList<List> l = this.model.getPlaylists();
        for (List list: l) {
            if (list.getName().equals(playlist)) return list.getSongs();
        }
        return null;
    }

    /**
     * Adds a playlist to the database
     * @param playlist Name of the playlist
     */
    @Override
    public void addPlaylist(String playlist) {
        if (this.model.addPlaylist(playlist)) this.menu.playlistCreated();
        else this.menu.playlistNotCreated();
    }

    /**
     * Deletes a playlist from the logged user
     * @param playlist Name of the playlist
     */
    @Override
    public void removePlaylist(String playlist) {
        if (this.model.removePlaylist(playlist)) this.menu.playlistDeleted();
        else this.menu.playlistNotDeleted();
    }

    /**
     * Deletes a song from a specifiedplaylist
     * @param playlist Name of the playlist where the song is in
     * @param song Song to delete
     */
    @Override
    public void removeSongPlaylist(String playlist, Song song) {
        if (this.model.removeSongPlaylist(playlist, song)) this.menu.songDeletedFromPlaylist();
        else this.menu.songNotDeletedFromPlaylist();
    }

    /**
     * Plays a playlist
     * @param playlist Name of the playlist to play
     */
    @Override
    public void requestPlayList(String playlist) {
        List request = this.model.getPlaylist(playlist);
        if (request == null) return;

        this.musicController.requestPlaylist(request);
    }

    /**
     * Returns all the songs the logged user should be able to see
     * @return Private songs the user created and all the public songs
     */
    @Override
    public ArrayList<Song> getUserSongs() {
        return this.model.getSongs();
    }

    /**
     * Deletes the specified song from the database
     * @param song Song to delete
     */
    @Override
    public void deleteSong(Song song) {
        if (this.model.deleteSong(song)) this.menu.songDeleted(song);
        else this.menu.unableToDeleteSong(song);
    }

    /**
     * Adds a song to a playlist
     * @param song Song to add
     * @param list Playlist to add the song in
     */
    @Override
    public void addSongPlaylist(Song song, String list) {
        Boolean exists = this.model.existsSongInPlaylist(song, list);
        if (exists == null || exists) this.menu.unableToAddSong(song); // ja existeix
        else {
            this.model.addSongPlaylist(song, list);
            this.menu.songAdded(song);
        }
    }

    /**
     * Makes the user log out and closes the session
     */
    @Override
    public void exitSession() {
        this.model.logout();

        this.musicController.reset();

        this.pianoController.stopRecording();
        this.pianoController.closeCurrentSong();

        this.menu.dispose();
        this.menu = null;

        this.login = new LogIn(this);
        this.login.setVisible(true);
        // TODO es lia si s'estava gravant?
    }

    /**
     * Requests a song to be played
     * @param song Song to play
     */
    @Override
    public void requestSong(Song song) {
        this.musicController.requestSong(this.model.getSong(song));
    }

    /**
     * Requests a specified song to play on the piano
     * @param song Song to play
     */
    @Override
    public void requestSongInPiano(Song song) {
        this.model.getSong(song);
        this.menu.focusPiano();
        this.pianoController.playSong(song);
    }

    /**
     * Adds a play of a certain duration to a song from the logged user
     * @param secondsPlayed Seconds the play lasted for
     * @param song Song the user was listening to
     */
    @Override
    public void addPlay(int secondsPlayed, Song song) {
        this.model.addPlay(secondsPlayed, song);
        this.reloadGraphs();
    }

    /**
     * Returns the evolution of the plays per hour
     * @return An array of values representing the number of total plays for each hour in the last 24 hours,
     * the last value is the current hour
     */
    @Override
    public int[] getSongsStatistics() {
        return this.model.getSongStatistics();
    }

    /**
     * Returns the evolution of the time listened per hour
     * @return An array of values representing the number of seconds listened for each hour in the last 24 hours,
     * the last value is the current hour
     */
    @Override
    public int[] getTimeStatistics() {
        return this.model.getTimeStatistics();
    }

    /**
     * Returns the top 5 most popular songs
     * @param plays Array which will be filled with the plays of each song, from most popular to least
     * @return Array of songs, from most popular to least
     */
    @Override
    public Song[] getTop5(int[] plays) {
        return this.model.getTop5(plays);
    }

    /**
     * A note was pressed in the piano
     * @param note Note that was played
     * @param octave Octave of the note
     */
    @Override
    public void isPressed(Note note, int octave) {
        this.pianoController.addNote(new SongNote(0,true,(byte)127,(byte)octave,note));
    }

    /**
     * A note was released in the piano
     * @param note Note that was released
     * @param octave Octave of the note
     */
    @Override
    public void isNotPressed(Note note, int octave) {
        this.pianoController.addNote(new SongNote(0,false,(byte)127,(byte)octave,note));
    }

    /**
     * Notifies the controller that the user started recording
     * @param recording True if the user started recording, false if the user stopped
     */
    @Override
    public void startRecording(boolean recording) {
        if (recording) this.pianoController.startRecording();
        else this.pianoController.stopRecording();
    }

    /**
     * Notifies the controller that the recorded song must be saved
     * @param name Song's name
     * @param isPublic Song's visibility (true = public, false = private)
     */
    @Override
    public void saveRecordedSong(String name, boolean isPublic) {
        if (!this.model.addSong(new Song(name, PianoController.TICK_LENGTH, isPublic, this.pianoController.getSongNotes()))); // no hauria de succeir mai
    }

    /**
     * Mute the octave INIT_OCTAVE to (INIT_OCTAVE + NUM_OCTAVES)
     */
    @Override
    public void muteSong() {
        this.pianoController.mute();
    }

    /**
     * Unmute the octave INIT_OCTAVE to (INIT_OCTAVE + NUM_OCTAVES)
     */
    @Override
    public void unmuteSong() {
        this.pianoController.unMute();
    }

    /**
     * Tells the Controller the user has stopped recording the piano song,
     * if the song is valid the Controller should notify the view thorough 'requestSongInformation()'.
     */
    @Override
    public void validateRecording() {
        // only request if the player has pressed any note
        if (this.pianoController.getSongNotes().size() > 0) this.menu.requestSongInformation();
    }

    /**
     * The song volume should be updated
     * @param volume new song volume
     */
    @Override
    public void updateSongVolume(float volume) {
        if (!this.model.setVolumeSong(volume)) return;
        this.musicController.setVolume(volume);
    }

    /**
     * The piano volume should be updated
     * @param volume new pianovolume
     */
    @Override
    public void updatePianoVolume(float volume) {
        if (!this.model.setVolumePiano(volume)) return;
        this.pianoController.setVolume(volume);
    }

    /**
     * There's a new piano bind
     * @param key Note who's bind changed
     * @param octave Octave of the note
     * @param newBind Character associated to the note
     */
    @Override
    public void updateKeyBinder(Note key, byte octave, char newBind) {
        if (!this.model.setKeyBinder(key, octave, newBind)) return;
        this.menu.loadConfig(this.model.getBinds());
    }

    /**
     * The user should be deleted
     * @param password Password input by the user
     */
    @Override
    public void deleteUser(String password) {
        if (this.model.deleteLoggedUser(password)) {
            this.menu.userDeleted();
            this.exitSession();
        }
        else this.menu.userNotDeleted();
    }

    /**
     * The user generated a new play, the graph's information should be updated
     */
    @Override
    public void reloadGraphs() {
        if (this.menu != null) this.menu.reloadGraphs();
    }
}
