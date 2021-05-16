package view;

import entities.Note;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyListener extends KeyAdapter {

    private KeyController k;

    public MyKeyListener(KeyController k){
        this.k = k;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        k.isPressed(Note.Do);
    }

}
