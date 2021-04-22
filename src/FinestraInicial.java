import javax.swing.*;
import java.awt.*;

public class FinestraInicial {

    public static final int Alcada_finestra = 600;
    public static final int Amplada_finestra  = 1000;

    JFrame finestra;

    FinestraInicial(){
        finestra = new JFrame("Piano TIME!");

        finestra.setVisible(true);
        finestra.setSize(Amplada_finestra,Alcada_finestra);
        finestra.setLocationRelativeTo(null);
        finestra.setResizable(false);
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        finestra.add(barraInferior(), BorderLayout.SOUTH);

    }

    public JPanel barraInferior(){

        JPanel panell = new JPanel();
        JPanel barraPlay = barraPlay();

        JLabel nomCanço = new JLabel("Himne del Carbassot");

        panell.setBackground(new Color(135, 135, 135));

        panell.add(nomCanço, BorderLayout.WEST);
        panell.add(barraPlay, BorderLayout.CENTER);

        return panell;
    }

    public JPanel barraPlay(){
        JPanel panell = new JPanel();

        JPanel enrrere = new JPanel();
        enrrere.setVisible(true);
        enrrere.setSize(150,150);
        enrrere.setBackground(new Color(253, 47, 47));

        JPanel endevant = new JPanel();
        endevant.setVisible(true);
        endevant.setSize(150,150);
        endevant.setBackground(new Color(39, 123, 255));

        JPanel play = new JPanel();
        play.setVisible(true);
        play.setSize(150,150);
        play.setBackground(new Color(79, 255, 39));

        panell.add(enrrere);
        panell.add(endevant);
        panell.add(play);

        return panell;
    }

}
