package view;

import entities.Note;

public interface KeyChanger {
    void changeKey(Note n, byte octava, char newLetter);
}
