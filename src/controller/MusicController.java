package controller;

import entities.List;
import entities.Song;
import model.NotePlayer;
import view.PlayingSongNotifier;

import java.util.Random;

/**
 * Class that manages the songs played in the music player
 */
public class MusicController implements SongEnder, PlaylistBarEvent, SongRequest, PlaylistRequest {

    /**
     * Object used to play the songs
     */
    // TODO syncronized?
    private NotePlayer player;

    /**
     * True if the music is not pause, false if it is
     */
    private boolean isPlaying;

    /**
     * Holds the playlist that should play
     */
    private List list;

    /**
     * Song in the playlist that is playing
     */
    private int songIndex;

    /**
     * Volume of the songs playing (0 = silent, 1 = max volume)
     */
    private float volume;

    /**
     * True if the next song in the playlist should be randomized
     */
    private boolean isRandom;

    /**
     * True if the playlist should loop when it reaches the end
     */
    private boolean isLooping;

    /**
     * Manages when a new song is playing
     */
    private PlayingSongNotifier notifier;

    /**
     * Object tasked with adding plays data to the database
     */
    private PlaysManager playsManager;

    /**
     * Initializes a new instance of MusicController
     * @param playsManager Object tasked with adding plays data to the database
     */
    public MusicController(PlaysManager playsManager) {
        this.reset();
        this.volume = 1; // teoricament es sobreescriu
        this.playsManager = playsManager;
    }

    /**
     * Resets the variables
     */
    public void reset() {
        this.setPlaying(false); // per si estava sonant

        this.list = null;
        this.isPlaying = true;
        this.songIndex = 0;
        this.isRandom = false;
        this.isLooping = false;
    }

    /**
     * Sets the playing state to the input, either playing or paused
     * @param playing True = playing, false = paused
     */
    private void setPlaying(boolean playing) {
        this.isPlaying = playing;
        if (this.player != null) this.player.setPlay(playing);
    }

    /**
     * Sets a new list of songs the player should play
     * @param list List of songs to play (a list with one song can be added)
     */
    public void setList(List list) {
        this.list = list;
        this.songIndex = 0;

        if (this.list.getSongs().size() > 0) this.playSong();
    }

    /**
     * Changes the volume of the songs being played
     * @param volume Volume of the songs playing (0 = silent, 1 = max volume)
     */
    // TODO: Al actualitzar el volum cal cridar sempre setVolume()
    public void setVolume(float volume) {
        this.volume = volume;
        if (this.player != null) this.player.setVolume(volume);
    }

    /**
     * Returns the number of seconds that elapsed since this function was last called (or since the song started)
     * @return Seconds elapsed since this function was last called
     */
    public int getSecond() {
        return (this.player == null)? 0 : player.getCurrentSecond();
    }

    /**
     * Returns the song in the list that is currently playing
     * @return Song that is plying
     */
    private Song getCurrentSong() {
        return this.list.getSongs().get(this.songIndex);
    }

    /**
     * Adds a play to the database for the current song
     */
    private void addPlay() {
        if (this.player != null) {
            int second = this.getSecond();
            if (second > 0) playsManager.addPlay(second, this.getCurrentSong());
        }
    }

    /**
     * Starts playing the next song
     */
    private void playSong() {
        if (this.player != null) {
            this.addPlay();
            this.player.closePlayer();
        }
        this.player = new NotePlayer(this.getCurrentSong(), this.volume, this);
        this.player.setPlay(this.isPlaying);
        this.player.start();

        if (this.notifier != null) this.notifier.newSongPlaying(this.getCurrentSong().toString());
    }

    /**
     * Advances the index in function of the state of the player (isPlaying, isRandom) and plays the next song
     * @param value 1 to advance forward, -1 to go back
     */
    private void advanceAndPlay(int value) {
        if (this.list == null) return;

        this.addPlay();
        if (this.isRandom) {
            this.songIndex = this.getRandomNext();
            this.playSong(); // getRandomNext() sempre retorna un numero dins de la llista
        }
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
     * Returns a random value of the next song to play
     * @return A random integer between 0 and the size of the list
     */
    private int getRandomNext() {
        return new Random().nextInt(this.list.getSongs().size());
    }

    /**
     * Plays the next song in the list in function of the state of the player (isPlaying, isRandom)
     */
    @Override
    public void nextSong() {
        this.advanceAndPlay(1);
    }

    /**
     * Plays the previous song in the list in function of the state of the player (isPlaying, isRandom)
     */
    @Override
    public void previousSong() {
        this.advanceAndPlay(-1);
    }

    /**
     * A song ended, we should advance to the next song
     */
    @Override
    public void songEnded() {
        this.nextSong();
    }

    /**
     * Switch the state of isLooping
     */
    @Override
    public void toggleLoop() {
        this.isLooping = !this.isLooping;
    }

    /**
     * Switch the state of isRandom
     */
    @Override
    public void toggleRandom() {
        this.isRandom = !this.isRandom;
    }

    /**
     * Switch the state of isPlaying
     */
    @Override
    public void togglePlaying() {
        this.setPlaying(!this.isPlaying);
        if (!this.isPlaying) this.addPlay();
    }

    /**
     * Saves the object to notify when a new song is payig
     * @param notifier Object that will be notified
     */
    @Override
    public void setPlayingSongListner(PlayingSongNotifier notifier) {
        this.notifier = notifier;
    }

    /**
     * Requests a playlist to play
     * @param list Playlist to play
     */
    @Override
    public void requestPlaylist(List list) {
        this.setList(list);
    }

    /**
     * Requests a song to play
     * @param song Song to play
     */
    @Override
    public void requestSong(Song song) {
        this.setList(new List().addSong(song));
    }
}
