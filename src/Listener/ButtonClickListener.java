package Listener;

import Apply2D.TransparentRoundedButtonWithHighlight;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonClickListener extends MouseAdapter {
    private final TransparentRoundedButtonWithHighlight activeButton;
    private final TransparentRoundedButtonWithHighlight b1,b2,b3;
    public ButtonClickListener(TransparentRoundedButtonWithHighlight activeButton, TransparentRoundedButtonWithHighlight b1,TransparentRoundedButtonWithHighlight b2, TransparentRoundedButtonWithHighlight b3) {
        this.activeButton = activeButton;
        this.b1 = b1;
        this.b2=b2;
        this.b3=b3;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        SwingUtilities.invokeLater(()->setActiveTab(activeButton,b1, b2, b3));
    }

    private void setActiveTab(TransparentRoundedButtonWithHighlight active, TransparentRoundedButtonWithHighlight b1, TransparentRoundedButtonWithHighlight b2, TransparentRoundedButtonWithHighlight b3) {
        active.setActive(true);
        b1.setActive(false);
        b2.setActive(false);
        b3.setActive(false);
    }
}
