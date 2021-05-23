package view;

import entities.Note;

/**
 * Interface used for when a note is assigned a new character
 */
public interface KeyChanger {

    /**
     * Notifies of a configuration change in note associations
     * @param n Note that changed
     * @param octava Octave of the note
     * @param newLetter New letter the note will be assigned to
     */
    void changeKey(Note n, byte octava, char newLetter);
}
