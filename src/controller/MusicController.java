package controller;

import entities.List;
import model.NotePlayer;

public class MusicController implements SongEnder{
    private NotePlayer player;
    private boolean playing;
    private List list;
    private int songIndex;
    private float volume;
    private boolean is_random;
    private boolean is_looping;

    public MusicController() {
        this.playing = true;
        this.songIndex = 0;
        this.volume = 1;
        this.is_random = false;
        this.is_looping = false;
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

    public void setIs_random(boolean is_random) {
        this.is_random = is_random;
    }

    public void setIs_looping(boolean is_looping) {
        this.is_looping = is_looping;
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
        if (this.is_looping) {
            this.playNext();
            return;
        }
        if (this.is_random) {
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
