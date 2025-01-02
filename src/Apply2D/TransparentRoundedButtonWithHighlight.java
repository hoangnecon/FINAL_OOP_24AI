package Apply2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TransparentRoundedButtonWithHighlight extends JPanel{
    private Color highlightColor;
    private String text = "";
    private boolean isActive = false;
    private Runnable onClick;

    public TransparentRoundedButtonWithHighlight(String text, Color highlightColor) {
        this.text = text;
        this.highlightColor = highlightColor;

        setPreferredSize(new Dimension(150, 50));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                isActive = true;
                repaint();
                if (onClick != null) {
                    onClick.run(); // Gọi hành động khi nút được click
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isActive) {
            g2d.setColor(highlightColor);
        }else {
            g2d.setColor(new Color(0, 0, 0, 0));
        }

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
        Font buttonFont = FontInput.loadFont("src/SourceFont/JetBrainsMono-Bold.ttf", 29f);
        g2d.setColor(Color.WHITE);
        g2d.setFont(buttonFont);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g2d.drawString(text, x, y);
    }
    public void setActive(boolean active) {
        this.isActive = active;
        repaint();
    }

}
