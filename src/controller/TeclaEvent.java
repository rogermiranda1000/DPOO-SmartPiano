package controller;

import entities.Note;

public interface TeclaEvent {
    void isPressed(Note note, int octava);
    void isNotPressed(Note note, int octava);
}
