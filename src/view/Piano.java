package view;

import entities.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Piano extends JPanel implements KeyController, ActionListener {
    public static final boolean IS_BLACK = true;
    public static final boolean IS_WHITE = false;

    private final ArrayList<JPanel> jP = new ArrayList<>();
    private final JPanel jP2 = new JPanel();
    private final ArrayList<Tecla> keys = new ArrayList<>();

    private JButton record;

    public Piano() {
        record = new JButton("Record");
        record.addActionListener(this);
        record.setFocusable(false);
        this.add(record, BorderLayout.NORTH);
        drawPiano();
        this.setBackground(ColorConstants.BACKGROUND.getColor());
    }

    private void drawPiano(){

        for (int i = 0; i < 24; i++) {
            int octava = (i/12) + 1;
            String nota = Note.getNote(i % 12).toString();

            Tecla temp = new Tecla(this, Note.getNote(i % 12), (nota.charAt(nota.length() - 1) == 'X') ? IS_BLACK : IS_WHITE, octava);
            keys.add(temp);
            this.add(temp, BorderLayout.SOUTH);
        }
    }

    //TODO: Revisat, crec que bé
    /**
     * Funció bucle que actualitza les notes/tecles
     * @param n
     * @param letter
     * @param octava
     */
    public void changeKey(Note n, char letter, int octava){
        for(int i = 0; i < 24; i++){
            if(n.equals(keys.get(i).getNote()) && keys.get(i).getOctava() == octava){
                keys.get(i).setKeyAssocieted(letter);
                break;
            }
        }
    }

    public void playNote(char key){
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i).getKey() == key) keys.get(i).playNote();
        }
    }

    public void stopNote(char key){
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i).getKey() == key) keys.get(i).stopNote();
        }
    }

    @Override
    public boolean isPressed(Note note, int octava) {
        System.out.println("PRESSED " + note.toString() + " - " + octava);
        return true;
    }

    @Override
    public boolean isNotPressed(Note note, int octava) {
        System.out.println("UNPRESSED " + note.toString() + " - " + octava);
        return true;
    }

    @Override
    public boolean keyBoardPressed(char nota) {
        System.out.println("TIPED " + nota);
        return true;
    }

    @Override
    public boolean keyBoardNotPressed(char nota) {
        System.out.println("UNTIPED " + nota);
        return false;
    }

    //TODO: connectar amb controller
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == record) System.out.println("Connectar amb controller amb PianoRecorder");
    }
}
