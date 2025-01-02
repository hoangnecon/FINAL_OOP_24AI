package Listener;

import Features.EditPatientForm;
import Features.ViewPatientForm;
import main.DatabaseConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditPatientListener implements ActionListener {
    private final JDialog parentDialog;
    private final String searchInput;
    private final String customID;
    private int doctorID;
    private final String doctorName;
    private final String specialization;

    public String getCurrent_doctorname() {
        return current_doctorname;
    }

    private String current_doctorname;
    private int curent_doctorid;


    public EditPatientListener(JDialog parentDialog, String searchInput, String customID, String doctorName, String specialization) {
        this.parentDialog = parentDialog;
        this.searchInput = searchInput;
        this.customID = customID;
        this.doctorName = doctorName;
        this.specialization = specialization;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection connection = DatabaseConnect.getConnection()) {
            // Lấy doctorID từ customID
            String queryDoctorID = "SELECT DoctorID FROM Doctors WHERE CustomID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(queryDoctorID)) {
                pstmt.setString(1, customID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        doctorID = rs.getInt("DoctorID");
                    } else {
                        JOptionPane.showMessageDialog(parentDialog, "Không tìm thấy bác sĩ với CustomID: " + customID, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }



            boolean isPatientID = searchInput.matches("\\d+");
            String query = isPatientID
                    ? "SELECT * FROM Patients WHERE PatientID = ?"
                    : "SELECT * FROM Patients WHERE FullName LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, isPatientID ? searchInput : "%" + searchInput + "%");

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        handlePatientResult(rs);
                    } else {
                        JOptionPane.showMessageDialog(parentDialog, "Không tìm thấy bệnh nhân!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentDialog, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePatientResult(ResultSet rs) throws SQLException {
        int patientID = rs.getInt("PatientID");
        String patientName = rs.getString("FullName");
        String gender = rs.getString("Gender");
        String dateOfBirth = rs.getString("DateOfBirth");
        String phoneNumber = rs.getString("PhoneNumber");
        String address = rs.getString("Address");
        String diseaseName = rs.getString("DiseaseName");
        int assignedDoctorID = rs.getInt("DoctorID");


        Connection connection = DatabaseConnect.getConnection();
        String query_get_curent_doctorid = "SELECT DoctorID from Patients WHERE PatientID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query_get_curent_doctorid)) {
            pstmt.setInt(1, patientID);
            try (ResultSet rs1 = pstmt.executeQuery()) {
                if (rs1.next()) {
                    curent_doctorid = rs1.getInt("DoctorID");
                } else {
                    JOptionPane.showMessageDialog(parentDialog, "Không tìm thấy bác sĩ với Patient: " + customID, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        String queryDoctorname = "SELECT FullName from Doctors WHERE DoctorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(queryDoctorname)) {
            pstmt.setInt(1, curent_doctorid);
            try (ResultSet rs1 = pstmt.executeQuery()) {
                if (rs1.next()) {
                    current_doctorname = rs1.getString("FullName");
                } else {
                    JOptionPane.showMessageDialog(parentDialog, "Không tìm thấy bác sĩ với CustomID: " + customID, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        if (assignedDoctorID==doctorID) {
            new EditPatientForm(parentDialog,patientName, gender, dateOfBirth, phoneNumber, address, diseaseName, patientID, doctorName, specialization);
        } else {
            JOptionPane.showMessageDialog(parentDialog, "Bạn không có quyền chỉnh sửa bệnh nhân này. Bạn chỉ có thể xem thông tin.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            new ViewPatientForm(parentDialog,patientName, gender, dateOfBirth, phoneNumber, address, diseaseName, patientID, current_doctorname, specialization);
        }
    }
}
