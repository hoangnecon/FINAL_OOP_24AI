package ConnectData;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    private Connection connection;
    private String tablename;

    public UserLogin(Connection connection) {
        this.connection = connection;
    }

    public String login(String userId, String password) {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return "ERROR: Vui lòng nhập đầy đủ thông tin.";
        }

        // Kiểm tra ký tự đầu của userId
        char userType = userId.charAt(0);
        if (userType != 'P' && userType != 'D' && userType != 'M') {
            // Hiển thị lỗi dưới dạng cửa sổ
            JOptionPane.showMessageDialog(null,
                    "ERROR: Định dạng userId không hợp lệ. Phải bắt đầu bằng 'P', 'D', hoặc 'M'.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return "ERROR: Định dạng userId không hợp lệ.";
        }

        String query = null;

        // Xác định bảng dựa trên userId
        switch (userType) {
            case 'P': // Bệnh nhân
                tablename = "Patients";
                query = "SELECT FullName FROM Patients WHERE CustomID = ? AND Password = ?";
                break;
            case 'D': // Bác sĩ
                tablename = "Doctors";
                query = "SELECT FullName FROM Doctors WHERE CustomID = ? AND Password = ?";
                break;
            case 'M': // Quản lý
                tablename = "Managers";
                query = "SELECT FullName FROM Managers WHERE CustomID = ? AND Password = ?";
                break;
            default:
                // Hiển thị lỗi nếu không khớp định dạng
                JOptionPane.showMessageDialog(null, "Sai định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return "ERROR: Sai định dạng userId";
        }

        // Thực hiện truy vấn vào cơ sở dữ liệu
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("FullName");
                return "SUCCESS:" + fullName + "\n" + tablename;
            } else {
                // Thông báo lỗi khi thông tin đăng nhập sai
                JOptionPane.showMessageDialog(null, "Thông tin đăng nhập không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return "ERROR: Thông tin đăng nhập không đúng!";
            }
        } catch (SQLException e) {
            // Hiển thị lỗi cơ sở dữ liệu nếu có ngoại lệ
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return "ERROR: Lỗi hệ thống!";
        }
    }
}
