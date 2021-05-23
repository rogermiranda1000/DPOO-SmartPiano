package controller;

import entities.SongNote;

/**
 * Interface tasked with requesting validations of notes to be played in NotePlayer (if not null)
 */
public interface SongValidator {

    /**
     * Requests a song to be played
     * @param sn Note to be played
     */
    void requestNote(SongNote sn);
}
