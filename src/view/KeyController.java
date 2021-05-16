package view;

public interface KeyController {
    boolean isPressed(String id);
    boolean isNotPressed(String id);
    boolean keyBoardPressed(String id);
    boolean keyBoardNotPressed(String id);
}
