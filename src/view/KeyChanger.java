package view;

import entities.Note;

public interface KeyChanger {
    void changeKey(Note n, char newLetter, int octava);
}
