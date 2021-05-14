package controller;

import entities.Song;
import model.BusinessFacade;
import persistance.PlaylistDAO;
import persistance.SongDAO;
import persistance.UserDAO;
import view.Menu;

public class Controller implements LoginEvent, MenuEvent, SongsEvent,SongNotifier {
    private final Menu view;
    private final BusinessFacade model;
    private final SongDownloader scrapper;

    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager, PlaylistDAO playlistManager) {
        this.model = new BusinessFacade(songManager, userManager, playlistManager);

        this.scrapper = new SongDownloader(this, scrappingTime);
        this.scrapper.start();

        this.view = new Menu(this, this, this);
    }


    @Override
    public void requestLogin(String user, String password) {
        /*if (this.model.login(user, password)) this.view.dispose();
        else this.view.wrongLogin();*/ // TODO
    }

    @Override
    public void requestRegister(String user, String email, String password) {
        /*if (this.model.addUser(user, email, password)) this.view.userCreated();
        else this.view.wrongPass();*/ // TODO
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
}
