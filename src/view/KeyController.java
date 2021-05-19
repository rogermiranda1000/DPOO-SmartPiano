package view;

import entities.Note;

public interface KeyController {
    boolean isPressed(Note note, int octava);
    boolean isNotPressed(Note note, int octava);
    boolean keyBoardPressed(char nota);
    boolean keyBoardNotPressed(char nota);
}
