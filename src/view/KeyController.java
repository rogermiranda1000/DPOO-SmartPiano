package view;

import entities.Note;

public interface KeyController {
    boolean isPressed(Note note);
    boolean isNotPressed(Note note);
    boolean keyBoardPressed(char nota);
    boolean keyBoardNotPressed(char nota);
}
