package Apply2D;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontInput {
    // Phương thức tĩnh để tải font
    public static Font loadFont(String fontPath, float size) {
        Font customFont;
        try {
            // Tạo font từ file
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            return customFont.deriveFont(size); // Đặt kích thước font
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Trả về font mặc định nếu xảy ra lỗi
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }
}
