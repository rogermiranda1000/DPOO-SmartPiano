package view;

import entities.SongNote;

public interface PianoNotifier {
    void unpressAllKeys();
    void pressKey(SongNote key);
}
