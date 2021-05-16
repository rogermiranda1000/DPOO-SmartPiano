package controller;

import entities.List;
import entities.Song;
import model.BusinessFacade;
import persistance.PlaylistDAO;
import persistance.SongDAO;
import persistance.UserDAO;
import view.Menu;

import java.util.ArrayList;

public class Controller implements LoginEvent, SongsEvent, PlaylistEvent, SongNotifier {
    private final Menu view;
    private final BusinessFacade model;
    private final SongDownloader scrapper;
    private final MusicController musicController;

    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager) {
        this.model = new BusinessFacade(songManager, userManager, playlistManager);

        this.musicController = new MusicController();

        this.scrapper = new SongDownloader(this, scrappingTime);
        this.scrapper.start();

        this.view = new Menu(this, this.musicController, this, this);
        this.view.start();
    }


    @Override
    public void requestLogin(String user, String password) {
        if (this.model.login(user, password)) this.view.disposeLogin();
        else this.view.wrongLogin();
    }

    @Override
    public void requestRegister(String user, String email, String password) {
        if (this.model.addUser(user, email, password)) this.view.userCreated();
        else this.view.wrongCreation();
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
}
