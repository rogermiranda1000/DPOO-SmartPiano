package controller;

import entities.List;
import entities.Song;
import model.NotePlayer;
import view.PlayingSongNotifier;

import java.util.Random;

public class MusicController implements SongEnder, PlaylistBarEvent, SongRequest, PlaylistRequest {
    // TODO syncronized?
    private NotePlayer player;
    private boolean isPlaying;
    private List list;
    private int songIndex;
    private float volume;
    private boolean isRandom;
    private boolean isLooping;
    private PlayingSongNotifier notifier;
    private PlaysManager playsManager;

    public MusicController(PlaysManager playsManager) {
        this.isPlaying = true;
        this.songIndex = 0;
        this.volume = 1;
        this.isRandom = false;
        this.isLooping = false;
        this.playsManager = playsManager;
    }

    private void setPlaying(boolean playing) {
        this.isPlaying = playing;
        if (this.player != null) this.player.setPlay(playing);
    }

    public void setList(List list) {
        this.list = list;
        this.songIndex = 0;

        this.playSong();
    }

    // TODO: Al actualitzar el volum cal cridar sempre setVolume()
    public void setVolume(float volume) {
        this.volume = volume;
        if (this.player != null) this.player.setVolume(volume);
    }

    /**
     * Gets the second of the song being played
     * @return the second that the current song is at
     */
    public int getSecond() {
        return (this.player == null)? 0 : player.getCurrentSecond();
    }

    private Song getCurrentSong() {
        return this.list.getSongs().get(this.songIndex);
    }

    private void addPlay() {
        if (this.player != null) {
            int second = this.getSecond();
            if (second > 0) playsManager.addPlay(second, this.getCurrentSong());
        }
    }

    private void playSong() {
        this.player = new NotePlayer(this.getCurrentSong(), this.volume, this);
        this.player.setPlay(this.isPlaying);
        this.player.start();

        if (this.notifier != null) this.notifier.newSongPlaying(this.getCurrentSong().toString());
    }

    private void advanceAndPlay(int value) {
        this.addPlay();
        if (this.isRandom) this.songIndex = this.getRandomNext();
        else {
            this.songIndex += value;
            if (this.isLooping) {
                if (this.songIndex < 0) this.songIndex = this.list.getSongs().size() - 1;
                else this.songIndex %= this.list.getSongs().size();
            }

            if (this.songIndex < this.list.getSongs().size() && this.songIndex >= 0) this.playSong();
        }
    }

    /**
     * Retorna el valor a incrementar (aleatori)
     * @return un enter aleatori entre 1 i list.getSongs().size() - 1
     */
    private int getRandomNext() {
        return new Random().nextInt(this.list.getSongs().size());
    }

    @Override
    public void nextSong() {
        this.advanceAndPlay(1);
    }

    @Override
    public void previousSong() {
        this.advanceAndPlay(-1);
    }

    @Override
    public void songEnded() {
        this.nextSong();
    }

    @Override
    public void toggleLoop() {
        this.isLooping = !this.isLooping;
    }

    @Override
    public void toggleRandom() {
        this.isRandom = !this.isRandom;
    }

    @Override
    public void togglePlaying() {
        this.setPlaying(!this.isPlaying);
        if (!this.isPlaying) this.addPlay();
    }

    @Override
    public void setPlayingSongListner(PlayingSongNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void requestPlaylist(List list) {
        this.setList(list);
    }

    @Override
    public void requestSong(Song song) {
        this.setList(new List().addSong(song));
    }
}
