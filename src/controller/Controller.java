package controller;

import entities.Song;
import model.BusinessFacade;
import model.SongDAO;
import model.UserDAO;
import view.Menu;

public class Controller implements LoginEvent, MenuEvent, SongsEvent,SongNotifier {
    private final Menu view;
    private final BusinessFacade model;
    private final SongDownloader scrapper;

    public Controller(int scrappingTime, SongDAO songManager, UserDAO userManager) {
        this.model = new BusinessFacade(songManager, userManager);

        this.scrapper = new SongDownloader(this, scrappingTime);
        this.scrapper.start();

        this.view = new Menu(this, this, this);
    }


    @Override
    public boolean requestLogin(String user, String password) {
        return this.model.login(user, password);
    }

    @Override
    public boolean requestRegister(String user, String email, String password) {
        // TODO: Check no empty fields [view]
        return this.model.addUser(user, email, password);
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
        if (!this.model.existsSong(song)) this.model.addSong(song);
    }
}
