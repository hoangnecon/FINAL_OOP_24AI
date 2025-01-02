package Apply2D;

import javax.swing.*;
import java.awt.*;

public class RoundedTextFieldPanel extends JPanel {
    private int cornerRadius;

    public RoundedTextFieldPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Đảm bảo nền không bị ghi đè
        setLayout(new GridBagLayout()); // Dùng để căn chỉnh thành phần bên trong
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}
