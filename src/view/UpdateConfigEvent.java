package view;

import entities.Note;

public interface UpdateConfigEvent {
    void updateSongVolume(float volume);
    void updatePianoVolume(float volume);
    void updateKeyBinder(Note key, byte octava, char newBind);
}