package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddSongOptionPane extends JPanel implements ActionListener, ItemListener {

    private final JButton b1;
    private final JCheckBox ck;
    private final JTextField tF;

    private boolean state = true;

    public AddSongOptionPane(){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        b1 = new JButton("OK");
        ck = new JCheckBox("Private");
        tF = new JTextField();

        tF.setSize(new Dimension(10, 30));

        b1.addActionListener(this);
        ck.addItemListener(this);

        this.add(tF);
        this.add(ck);
        this.add(b1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            System.out.println(tF.getText());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("Private: " + state);
        state = !state;
    }
}
