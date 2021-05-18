package controller;

import entities.List;
import entities.Song;
import model.BusinessFacade;
import persistance.PlaylistDAO;
import persistance.SongDAO;
import persistance.UserDAO;
import view.Menu;

import java.util.ArrayList;

public class Controller implements LoginEvent, MenuEvent, SongsEvent, PlaylistEvent, SongNotifier {
    private final Menu view;
    private final BusinessFacade model;
    private final SongDownloader scrapper;

    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager) {
        this.model = new BusinessFacade(songManager, userManager, playlistManager);

        this.scrapper = new SongDownloader(this, scrappingTime);
        this.scrapper.start();

        this.view = new Menu(this, this, this, this);
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
    public boolean toggleLoop() {
        // TODO: Check in player if loop mode on.
        return false;
    }

    @Override
    public int currentSongPos() {
        // TODO: check current song position on playing list
        return 0;
    }

    @Override
    public boolean playing() {
        // TODO: Check if a song is playing
        return false;
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
    public ArrayList<Song> getSongs(String playlist) {
        ArrayList<List> l = this.model.getPlaylists();
        ArrayList<String> r = new ArrayList<>();
        for (List list: l) {
            if (list.getName().equals(playlist)) return list.getSongs();
        }
        return null;
    }

    @Override
    public boolean saveKeyNotes(char[] chars) {
        return false;
    }

    @Override
    public boolean saveVolumes(float pianoVolume, float songVolume) {
        return false;
    }
}
