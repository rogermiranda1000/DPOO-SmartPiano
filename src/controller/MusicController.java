package controller;

import entities.List;
import entities.Song;
import model.NotePlayer;

public class MusicController implements SongEnder, MenuEvent {
    // TODO syncronized?
    private NotePlayer player;
    private boolean isPlaying;
    private List list;
    private int songIndex;
    private float volume;
    private boolean isRandom;
    private boolean isLooping;

    public MusicController() {
        this.isPlaying = true;
        this.songIndex = 0;
        this.volume = 1;
        this.isRandom = false;
        this.isLooping = false;
    }

    private void setPlaying(boolean playing) {
        this.isPlaying = playing;
        if (this.player != null) this.player.setPlay(playing);
    }

    public void setList(List list) {
        this.list = list;
        this.songIndex = 0;
    }

    // TODO: Al carregar/canviar usuari cal cridar sempre setVolume()
    public void setVolume(float volume) {
        this.volume = volume;
        if (this.player != null) this.player.setVolume(volume);
    }

    private Song getCurrentSong() {
        return this.list.getSongs().get(this.songIndex);
    }

    private void playNext() {
        this.player = new NotePlayer(this.getCurrentSong(), volume, this);
        this.player.setPlay(this.isPlaying);
    }

    @Override
    public void songEnded() {
        if (this.isLooping) {
            this.playNext();
            return;
        }
        if (this.isRandom) {
            this.songIndex = ((this.songIndex + this.getRandomNext()))%this.list.getSongs().size();
        } else {
            this.songIndex = (this.songIndex + 1)%this.list.getSongs().size();
        }

        this.playNext();
    }

    /**
     * Retorna el valor a incrementar (aleatori)
     * @return un enter aleatori entre 1 i list.getSongs().size() - 1
     */
    private int getRandomNext() {
        return (int)Math.round(Math.random() * (this.list.getSongs().size()-2))+1;
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
    }

    @Override
    public String getActualSong() {
        return this.getCurrentSong().toString();
    }

}
