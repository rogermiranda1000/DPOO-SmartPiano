package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddSongOptionPane extends JDialog implements ActionListener {
    private JButton b1;
    private JCheckBox ck;
    private JTextField tF;

    public AddSongOptionPane() {
        super();
        this.setSize(new Dimension(200, 200));
        this.add(this.getPanel());
        this.setLocationRelativeTo(null);

        this.getPanel();
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        b1 = new JButton("OK");
        ck = new JCheckBox("Private");
        tF = new JTextField();

        tF.setSize(new Dimension(10, 30));

        b1.addActionListener(this);

        panel.add(tF);
        panel.add(ck);
        panel.add(b1);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            // TODO close & return
            System.out.println(tF.getText() + " - " + this.ck.isSelected());
        }
    }
}
