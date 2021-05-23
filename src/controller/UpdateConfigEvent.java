package controller;

import entities.Note;

/**
 * Events of configuration updates
 */
public interface UpdateConfigEvent {

    /**
     * The song volume should be updated
     * @param volume new song volume
     */
    void updateSongVolume(float volume);

    /**
     * The piano volume should be updated
     * @param volume new pianovolume
     */
    void updatePianoVolume(float volume);

    /**
     * There's a new piano bind
     * @param key Note who's bind changed
     * @param octave Octave of the note
     * @param newBind Character associated to the note
     */
    void updateKeyBinder(Note key, byte octave, char newBind);

    /**
     * The user should be deleted
     * @param password Password input by the user
     */
    void deleteUser(String password);
}