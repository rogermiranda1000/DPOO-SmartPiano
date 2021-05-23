package controller;

import entities.Note;

/**
 * Interface tasked with events of note presses and releases in the piano
 */
public interface TeclaEvent {

    /**
     * A note was pressed in the piano
     * @param note Note that was played
     * @param octave Octave of the note
     */
    void isPressed(Note note, int octave);

    /**
     * A note was released in the piano
     * @param note Note that was released
     * @param octave Octave of the note
     */
    void isNotPressed(Note note, int octave);
}
