package controller;

import entities.SongNote;

/**
 * When a note must be played in NotePlayer it sends a request validation (if not null)
 */
public interface SongValidator {
    /**
     * A note must be played
     * @param sn Note
     */
    void requestNote(SongNote sn);
}
