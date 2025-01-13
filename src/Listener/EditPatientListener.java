package Listener;//package Listener;

import Features.EditPatientForm;
import main.DatabaseConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class EditPatientListener implements ActionListener {
    private final JDialog parentDialog;
    private final String searchInput;
    private final String customID;
    private final String doctorName;
    private final String specialization;
    private int doctorID;

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
                    ? "SELECT * FROM Patients WHERE PatientID = ? AND DoctorID = ?"
                    : "SELECT * FROM Patients WHERE FullName LIKE ? AND DoctorID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                if (isPatientID) {
                    pstmt.setInt(1, Integer.parseInt(searchInput));
                } else {
                    pstmt.setString(1, "%" + searchInput + "%");
                }
                pstmt.setInt(2, doctorID);

                try (ResultSet rs = pstmt.executeQuery()) {
                    Vector<Vector<Object>> patientData = new Vector<>();
                    Vector<String> columnNames = new Vector<>();
                    columnNames.add("PatientID");
                    columnNames.add("Tên bệnh nhân");
                    columnNames.add("Giới tính");
                    columnNames.add("Ngày sinh");
                    columnNames.add("Tên bệnh");

                    while (rs.next()) {
                        Vector<Object> row = new Vector<>();
                        row.add(rs.getInt("PatientID"));
                        row.add(rs.getString("FullName"));
                        row.add(rs.getString("Gender"));
                        row.add(rs.getString("DateOfBirth"));
                        row.add(rs.getString("PhoneNumber"));
                        row.add(rs.getString("Address"));
                        row.add(rs.getString("DiseaseName"));
                        patientData.add(row);
                    }

                    if (patientData.isEmpty()) {
                        JOptionPane.showMessageDialog(parentDialog, "Không tìm thấy bệnh nhân!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else if (patientData.size() == 1) {
                        Vector<Object> row = patientData.get(0);
                        openEditForm(row);
                    } else {
                        showPatientTable(patientData, columnNames);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentDialog, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEditForm(Vector<Object> row) {
        int patientID = (int) row.get(0);
        String patientName = (String) row.get(1);
        String gender = (String) row.get(2);
        String dateOfBirth = (String) row.get(3);
        String phoneNumber = (String) row.get(4);
        String address = (String) row.get(5);
        String diseaseName = (String) row.get(6);

        new EditPatientForm(parentDialog, patientName, gender, dateOfBirth, phoneNumber, address, diseaseName, patientID, doctorName, specialization);
    }


    private void showPatientTable(Vector<Vector<Object>> patientData, Vector<String> columnNames) {
        JDialog tableDialog = new JDialog(parentDialog, "Chọn bệnh nhân", true);
        tableDialog.setSize(600, 400);
        tableDialog.setLocationRelativeTo(parentDialog);

        JTable patientTable = new JTable(new DefaultTableModel(patientData, columnNames));
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(patientTable);
        tableDialog.add(scrollPane, BorderLayout.CENTER);

        JButton selectButton = new JButton("Chọn");
        selectButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                Vector<Object> row = patientData.get(selectedRow);
                openEditForm(row);
                tableDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(tableDialog, "Vui lòng chọn một bệnh nhân!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tableDialog.add(selectButton, BorderLayout.SOUTH);

        tableDialog.setVisible(true);
    }
}