package Apply2D;

import javax.swing.*;
import java.awt.*;

public class ImageCheckbox extends JCheckBox {
    private ImageIcon uncheckedIcon;
    private ImageIcon checkedIcon;

    public ImageCheckbox(String text, ImageIcon uncheckedIcon, ImageIcon checkedIcon) {
        super(text);
        this.uncheckedIcon = uncheckedIcon;
        this.checkedIcon = checkedIcon;
        setFont(new Font("Arial", Font.PLAIN, 18));
        setFocusPainted(false);
        setOpaque(false);

        // Đặt icon mặc định
        setIcon(uncheckedIcon);
        setSelectedIcon(checkedIcon);
    }


    public static JCheckBox createCustomCheckbox_byImage(String text, ImageIcon uncheckedIcon, ImageIcon checkedIcon) {
        return new ImageCheckbox(text, uncheckedIcon, checkedIcon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
