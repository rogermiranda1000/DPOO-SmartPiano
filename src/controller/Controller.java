package controller;

import entities.List;
import entities.Song;
import model.BusinessFacade;
import persistance.*;
import view.LogIn;
import view.Menu;

import javax.xml.stream.events.StartDocument;
import java.util.ArrayList;

public class Controller implements LoginEvent, MenuEvent, SongsEvent, PlaylistEvent, SongNotifier, SongRequest, PlaysManager {
    private Menu menu;
    private LogIn login;
    private final BusinessFacade model;
    private final SongDownloader scrapper;
    private final MusicController musicController;

    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager, ConfigDAO configManager, StatisticsDAO statisticsManager) {
        this.model = new BusinessFacade(songManager, userManager, playlistManager, configManager, statisticsManager);

        this.musicController = new MusicController(this);

        this.scrapper = new SongDownloader(this, scrappingTime);
        this.scrapper.start();

        this.login = new LogIn(this);
        this.login.setVisible(true);
    }


    @Override
    public void requestLogin(String user, String password) {
        if (this.model.login(user, password)) {
            this.login.dispose();

            this.menu = new Menu(this.musicController, this, this, this, this);
            this.menu.setVisible(true);

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
    public ArrayList<Song> getUserSongs() {
        return this.model.getSongs();
    }

    @Override
    public void exitSession() {
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
}
