package Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverPanelListener extends MouseAdapter {
    private final JPanel panel;
    private final Color normalColor;
    private final Color hoverColor;

    public HoverPanelListener(JPanel panel, Color normalColor, Color hoverColor) {
        this.panel = panel;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        panel.setBackground(hoverColor);
        panel.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        panel.setBackground(normalColor);
        panel.repaint();
    }

}
