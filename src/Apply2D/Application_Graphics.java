package Apply2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import Listener.HoverPanelListener;

public class Application_Graphics{
    public static void applyGradient(Graphics g, int width, int height, Color color1, Color color2, Color color3, boolean horizontal) {
        Graphics2D g2d = (Graphics2D) g;
        Point2D start = horizontal ? new Point2D.Float(0, height / 2) : new Point2D.Float(width / 2, 0);
        Point2D end = horizontal ? new Point2D.Float(width, height / 2) : new Point2D.Float(width / 2, height);


        float[] fractions = {0.0f, 0.5f, 1.0f}; // Màu đầu, giữa, và cuối
        Color[] colors = {color1, color2, color3};


        LinearGradientPaint gradient = new LinearGradientPaint(start, end, fractions, colors);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }



    public static void drawRoundedPanel(Graphics g, JPanel panel, int cornerRadius) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(panel.getBackground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.fillRoundRect(0, 0, panel.getWidth(), panel.getHeight(), cornerRadius, cornerRadius);
    }
    public static void drawRoundedBorder(Graphics g, int x, int y, int width, int height, int arcWidth, int arcHeight, Color borderColor, int thickness) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new RoundRectangle2D.Float(x, y, width - thickness, height - thickness, arcWidth, arcHeight));
    }
    public static void applyHoverEffect(JPanel panel, Color normalColor, Color hoverColor) {
        HoverPanelListener hoverListener = new HoverPanelListener(panel, normalColor, hoverColor);
        panel.addMouseListener(hoverListener);
    }


}


