package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Bars extends JSlider implements ChangeListener {

    JSlider jS;
    SliderController sC;

    Bars(SliderController slidCont){
        sC = slidCont;
        jS = new JSlider(0,100,50);
        jS.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        sC.changeValue(3);
    }
}
