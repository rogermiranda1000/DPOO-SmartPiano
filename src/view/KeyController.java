package view;

import entities.Note;

public interface KeyController {
    void isPressed(Note note, int octava);
    void isNotPressed(Note note, int octava);
}
