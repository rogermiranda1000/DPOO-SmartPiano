package controller;

import entities.List;
import model.NotePlayer;

public class MusicController implements SongEnder{
    private NotePlayer player;
    private boolean playing;
    private List list;
    private int songIndex;
    private float volume;
    private boolean isRandom;
    private boolean isLooping;

    public MusicController() {
        this.playing = true;
        this.songIndex = 0;
        this.volume = 1;
        this.isRandom = false;
        this.isLooping = false;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
        this.player.setPlay(playing);
    }

    public void setList(List list) {
        this.list = list;
        this.songIndex = 0;
    }

    // TODO: Al carregar/canviar usuari cal cridar sempre setVolume()
    public void setVolume(float volume) {
        this.volume = volume;
        this.player.setVolume(volume);
    }

    public void setIsRandom(boolean is_random) {
        this.isRandom = is_random;
    }

    public void setIsLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    public List getList() {
        return this.list;
    }

    private void playNext() {
        this.player = new NotePlayer(this.list.getSongs().get(this.songIndex), volume, this);
        this.player.setPlay(this.playing);
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
}
