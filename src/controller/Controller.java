package controller;

import entities.List;
import entities.Note;
import entities.Song;
import entities.SongNote;
import model.BusinessFacade;
import persistance.*;
import view.LogIn;
import view.Menu;
import view.Tecla;
import view.UpdateKeys;

import java.util.ArrayList;

public class Controller implements LoginEvent, MenuEvent, SongsEvent, PlaylistEvent, SongNotifier, SongRequest, RankingEvent, PlaysManager, UpdateKeys, TeclaEvent {
    private Menu menu;
    private LogIn login;
    private final BusinessFacade model;
    private final SongDownloader scrapper;
    private final MusicController musicController;
    private final PianoController pianoController;

    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager, ConfigDAO configManager, StatisticsDAO statisticsManager) {
        this.model = new BusinessFacade(songManager, userManager, playlistManager, configManager, statisticsManager);

        this.musicController = new MusicController(this);
        this.pianoController = new PianoController();

        this.scrapper = new SongDownloader(this, scrappingTime);
        this.scrapper.start();

        this.login = new LogIn(this);
        this.login.setVisible(true);
    }


    @Override
    public void requestLogin(String user, String password) {
        if (this.model.login(user, password)) {
            this.login.dispose();

            this.menu = new Menu(this.musicController, this, this, this, this, this, this);
            this.menu.setVisible(true);
            this.menu.loadConfig(this.model.getBinds());

            this.musicController.setVolume(this.model.getSongVolume());
        } else this.login.wrongLogin();
    }

    @Override
    public void requestRegister(String user, String email, String password) {
        if (this.model.addUser(user, email, password)) this.login.userCreated();
        else this.login.wrongCreation();
    }

    @Override
    public void addSong(Song song) {
        if (!this.model.existsSong(song)) this.model.addDownloadedSong(song);
    }

    @Override
    public ArrayList<String> getPlaylists() {
        ArrayList<List> l = this.model.getPlaylists();
        ArrayList<String> r = new ArrayList<>();
        for (List list: l) r.add(list.getName());
        return r;
    }

    @Override
    public ArrayList<Song> getPlaylistSongs(String playlist) {
        ArrayList<List> l = this.model.getPlaylists();
        for (List list: l) {
            if (list.getName().equals(playlist)) return list.getSongs();
        }
        return null;
    }

    @Override
    public void addPlaylist(String playlist) {
        if (this.model.addPlaylist(playlist)) this.menu.playlistCreated();
        else this.menu.playlistNotCreated();
    }

    @Override
    public void removePlaylist(String playlist) {
        if (this.model.removePlaylist(playlist)) this.menu.playlistDeleted();
        else this.menu.playlistNotDeleted();
    }

    @Override
    public void removeSongPlaylist(String playlist, Song song) {
        if (this.model.removeSongPlaylist(playlist, song)) this.menu.songDeletedFromPlaylist();
        else this.menu.songNotDeletedFromPlaylist();
    }

    @Override
    public void requestPlayList(String playlist) {
        List request = this.model.getPlaylist(playlist);
        if (request == null) return;

        this.musicController.requestPlaylist(request);
    }

    @Override
    public ArrayList<Song> getUserSongs() {
        return this.model.getSongs();
    }

    @Override
    public void deleteSong(Song song) {
        if (this.model.deleteSong(song)) this.menu.songDeleted(song);
        else this.menu.unableToDeleteSong(song);
    }

    @Override
    public void addSongPlaylist(Song song, String list) {
        Boolean exists = this.model.existsSongInPlaylist(song, list);
        if (exists == null || exists) this.menu.unableToAddSong(song); // ja existeix
        else {
            this.model.addSongPlaylist(song, list);
            this.menu.songAdded(song);
        }
    }

    @Override
    public void exitSession() {
        this.model.logout();

        this.musicController.reset();

        this.menu.dispose();
        this.login = new LogIn(this);
        this.login.setVisible(true);
    }

    @Override
    public void requestSong(Song song) {
        this.musicController.requestSong(this.model.getSong(song));
    }

    @Override
    public void addPlay(int secondsPlayed, Song song) {
        this.model.addPlay(secondsPlayed, song);
    }

    @Override
    public int[] getSongsStadistics() {
        //TODO Return the number of songs listened the las 24h and splited between hours.
        return new int[0];
    }

    @Override
    public int[] getTimeStadistics() {
        //TODO Return the number of seconds listened the las 24h and splited between hours.
        return new int[0];
    }

    @Override
    public Song[] getTop5(int[] plays) {
        return new Song[0];
    }


    // TODO
    @Override
    public void isPressed(Note note, int octava) {
        this.pianoController.addNote(new SongNote(0,true,(byte)127,(byte)octava,note));
    }

    @Override
    public void isNotPressed(Note note, int octava) {
        this.pianoController.addNote(new SongNote(0,false,(byte)127,(byte)octava,note));
    }

    // TODO review
    @Override
    public char[] keysToArray(ArrayList<Tecla> keys) {

        char[] aux = new char[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            //aux[i] = keys.get(i).getKey();
        }

        return aux;

    }
}
