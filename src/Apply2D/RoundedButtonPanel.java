package Apply2D;

import Listener.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButtonPanel extends JPanel {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private String text = "";

    public RoundedButtonPanel(String text, int arc, Color BackgroundColor, Color hoverColor, Color pressedColor) {
        this.text = text;
        this.backgroundColor = BackgroundColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        setPreferredSize(new Dimension(150, 50));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        if (isPressed) {
            g2d.setColor(pressedColor);
        } else if (isHovered) {
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(backgroundColor);
        }


        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
        Font loginbuttonFont = FontInput.loadFont("src/SourceFont/JetBrainsMono-Bold.ttf", 32f);
        g2d.setColor(Color.WHITE);
        g2d.setFont(loginbuttonFont);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g2d.drawString(text, x, y);
    }

}
