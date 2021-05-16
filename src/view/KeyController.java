package view;

public interface KeyController {
    boolean isPressed(String id);
    boolean isNotPressed(String id);
    boolean keyBoardPressed(char nota);
    boolean keyBoardNotPressed(char nota);
}
