package Apply2D;

import javax.swing.*;
import java.awt.*;

public class CustomCheckbox extends JCheckBox {
    private Color borderColor = new Color(120, 120, 120);
    private Color tickColor = new Color(50, 205, 50);
    private int borderRadius = 8;

    public CustomCheckbox(String text) {
        super(text);
        setFont(new Font("Arial", Font.PLAIN, 18));
        setFocusPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = 20, x = 0, y = (getHeight() - size) / 2;
        g2.setColor(borderColor);
        g2.fillRoundRect(x, y, size, size, borderRadius, borderRadius);

        if (isSelected()) {
            g2.setColor(tickColor);
            g2.setStroke(new BasicStroke(2));
            g2.fillRoundRect(x + 4, y + 4, size - 8, size - 8, borderRadius, borderRadius);
        }

        g2.setColor(borderColor.darker());
        g2.drawRoundRect(x, y, size - 1, size - 1, borderRadius, borderRadius);

        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(getText(), x + size + 10, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);

        g2.dispose();
    }

    public static JCheckBox createCustomCheckbox(String text, Color borderColor, Color tickColor, int borderRadius) {
        CustomCheckbox checkbox = new CustomCheckbox(text);
        checkbox.borderColor = borderColor;
        checkbox.tickColor = tickColor;
        checkbox.borderRadius = borderRadius;
        return checkbox;
    }
}
