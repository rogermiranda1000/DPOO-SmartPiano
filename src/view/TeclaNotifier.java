package view;

import entities.Note;

public interface TeclaNotifier {
    void playNote(Note note, int octava);
    void stopNote(Note note, int octava);
}
