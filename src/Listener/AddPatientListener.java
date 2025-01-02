package Listener;

import Features.AddPatientPanel;
import main.DatabaseConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPatientListener implements ActionListener {
    private  AddPatientPanel patientPanel;
    private String customID;
    private String doctorName, specialization;
    private final String passwords = "123";

    public AddPatientListener(AddPatientPanel patientPanel, String customID, String doctorName, String specialization) {
        this.patientPanel = patientPanel;
        this.customID = customID;
        this.doctorName = doctorName;
        this.specialization = specialization;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String patientName = patientPanel.getPatientName();
        String gender = patientPanel.getGender();
        String dateOfBirth = patientPanel.getDateOfBirth();
        String phoneNumber = patientPanel.getPhoneNumber();
        String address = patientPanel.getAddress();
        String diseaseName = patientPanel.getDiseaseName();
        int doctorID = 0;

        // Kiểm tra dữ liệu hợp lệ
        if (patientName.isEmpty() || gender.isEmpty() || dateOfBirth.isEmpty() ||
                phoneNumber.isEmpty() || address.isEmpty() || diseaseName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Kiểm tra gender (Chỉ được nhập "Nam" hoặc "Nữ")
        if (!gender.equalsIgnoreCase("Nam") && !gender.equalsIgnoreCase("Nữ")) {
            JOptionPane.showMessageDialog(null, "Giới tính chỉ được nhập 'Nam' hoặc 'Nữ'!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Kiểm tra số điện thoại (Chỉ chứa số và độ dài 10 ký tự)
        if (!phoneNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải là chuỗi gồm 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Kiểm tra định dạng ngày tháng (YYYY-MM-DD)
        if (!dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Ngày sinh phải theo định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thử parse ngày để kiểm tra hợp lệ
        try {
            Date.valueOf(dateOfBirth);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnect.getConnection()) {
            // 1. Lấy doctorID từ customID
            String queryDoctorID = "SELECT DoctorID FROM Doctors WHERE CustomID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(queryDoctorID)) {
                pstmt.setString(1, customID);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        doctorID = resultSet.getInt("DoctorID");
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy bác sĩ với CustomID: " + customID, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // 2. Thêm bệnh nhân vào bảng Patients
            String insertPatient = "INSERT INTO Patients (FullName, Gender, DateOfBirth, PhoneNumber, Address, DiseaseName, DoctorID, Specialization, Password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertPatient)) {
                pstmt.setString(1, patientName);
                pstmt.setString(2, gender);
                pstmt.setDate(3, Date.valueOf(dateOfBirth));
                pstmt.setString(4, phoneNumber);
                pstmt.setString(5, address);
                pstmt.setString(6, diseaseName);
                pstmt.setInt(7, doctorID);
                pstmt.setString(8, specialization);
                pstmt.setString(9, passwords);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    // patientPanel.clearFields(); // Xóa form nếu cần
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
