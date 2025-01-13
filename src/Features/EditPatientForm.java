package Features;

import main.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditPatientForm extends JDialog {
    public EditPatientForm(JDialog parentDialog, String patientName, String gender, String dateOfBirth, String phoneNumber, String address, String diseaseName, int patientID, String doctorName, String specialization) {
        super(parentDialog, "Chỉnh sửa thông tin bệnh nhân", true);
        setLayout(new GridLayout(7, 2));
        setSize(600, 400);

        add(new JLabel("Tên bệnh nhân:"));
        JTextField nameField = new JTextField(patientName);
        add(nameField);

        add(new JLabel("Giới tính:"));
        JTextField genderField = new JTextField(gender);
        add(genderField);

        add(new JLabel("Ngày sinh:"));
        JTextField dobField = new JTextField(dateOfBirth);
        add(dobField);

        add(new JLabel("Số điện thoại:"));
        JTextField phoneField = new JTextField(phoneNumber);
        add(phoneField);

        add(new JLabel("Địa chỉ:"));
        JTextField addressField = new JTextField(address);
        add(addressField);

        add(new JLabel("Tên bệnh:"));
        JTextField diseaseField = new JTextField(diseaseName);
        add(diseaseField);

        JButton saveButton = new JButton("Lưu thông tin");
        saveButton.addActionListener(e -> {
            if (updatePatientInfo(patientID, nameField.getText(), genderField.getText(), dobField.getText(), phoneField.getText(), addressField.getText(), diseaseField.getText())) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(saveButton);
        setLocationRelativeTo(parentDialog);
        setVisible(true);
    }

    private boolean updatePatientInfo(int patientID, String name, String gender, String dob, String phone, String address, String disease) {
        String query = "UPDATE Patients SET FullName = ?, Gender = ?, DateOfBirth = ?, PhoneNumber = ?, Address = ?, DiseaseName = ? WHERE PatientID = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setString(3, dob);
            pstmt.setString(4, phone);
            pstmt.setString(5, address);
            pstmt.setString(6, disease);
            pstmt.setInt(7, patientID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
