package Features;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectData.getProfile;
import Listener.LoginListener;
import main.DatabaseConnect;

public class DisplayPatients extends JFrame {

    public DisplayPatients(int doctorID) {
        Connection connection;
        try {
            connection = DatabaseConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getProfile profile = new getProfile(connection, LoginListener.getLoggedInUserId());
        setTitle("Danh sách bệnh nhân");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel background = new JPanel(new BorderLayout());
        background.setPreferredSize(new Dimension(1440, 1024));
        background.setBackground(Color.WHITE);

        JPanel titlebar = new JPanel(new GridBagLayout());
        titlebar.setPreferredSize(new Dimension(1440, 150));
        titlebar.setBackground(new Color(0x38C1CE));

        // Logo
        JLabel logohos = new JLabel(new ImageIcon("src/imagesource/Group 1.png"));
        logohos.setPreferredSize(new Dimension(353, 70));
        GridBagConstraints logo_position = new GridBagConstraints();
        logo_position.gridx = 0;
        logo_position.gridy = 0;
        logo_position.weightx = 0.1;
        logo_position.insets = new Insets(0, 75, 20, 10);
        logo_position.fill = GridBagConstraints.NONE;
        titlebar.add(logohos, logo_position);





        JPanel main = new JPanel(new GridBagLayout());
        main.setPreferredSize(new Dimension(1440,878));


        JTable allPatientsTable = new JTable();
        allPatientsTable.setPreferredScrollableViewportSize(new Dimension(200,200));
        JScrollPane allPatientsScrollPane = new JScrollPane(allPatientsTable);
        allPatientsScrollPane.setPreferredSize(new Dimension(200, 200));
        DefaultTableModel allPatientsModel = new DefaultTableModel();
        allPatientsModel.setColumnIdentifiers(new String[]{"Mã bệnh nhân", "Họ tên", "Giới tính", "Ngày sinh", "Tên bệnh"});
        allPatientsTable.setModel(allPatientsModel);
        loadAllPatientsInDepartment(doctorID, allPatientsModel);
        GridBagConstraints all = new GridBagConstraints();
        all.gridx=0;
        all.gridy=1;
        all.weightx=1;
        all.weighty=1;
        all.fill = GridBagConstraints.BOTH;
        main.add(allPatientsScrollPane, all);
        JLabel allpatient = new JLabel("Tất cả bệnh nhân trong khoa "+ profile.getSpecialization());
        all.gridx=0;
        all.gridy=0;
        all.weightx=0;
        all.weighty=0;
        main.add(allpatient, all);



        JTable assignedPatientsTable = new JTable();
        assignedPatientsTable.setPreferredScrollableViewportSize(new Dimension(200,200));
        JScrollPane assignedPatientsScrollPane = new JScrollPane(assignedPatientsTable);
        allPatientsScrollPane.setPreferredSize(new Dimension(200,200));
        DefaultTableModel assignedPatientsModel = new DefaultTableModel();
        assignedPatientsModel.setColumnIdentifiers(new String[]{"Mã bệnh nhân", "Họ tên", "Giới tính", "Ngày sinh", "Tên bệnh"});
        assignedPatientsTable.setModel(assignedPatientsModel);
        loadAssignedPatients(doctorID, assignedPatientsModel);
        GridBagConstraints assign = new GridBagConstraints();
        assign.gridx=1;
        assign.gridy=1;
        assign.weightx=1;
        assign.weighty=1;
        assign.fill = GridBagConstraints.BOTH;
        main.add(assignedPatientsScrollPane, assign);
        JLabel assignpatient = new JLabel("Bệnh nhân được bác sĩ phụ trách "+ profile.getFullName());
        assign.gridx=1;
        assign.gridy=0;
        assign.weightx=0;
        assign.weighty=0;
        main.add(assignpatient,assign);





        this.add(background, BorderLayout.CENTER);
        background.add(main, BorderLayout.SOUTH);
        background.add(titlebar, BorderLayout.NORTH);
        this.setVisible(true);
    }
    private void loadAllPatientsInDepartment(int doctorID, DefaultTableModel tableModel) {
        String query = "SELECT p.PatientID, p.FullName, p.Gender, p.DateOfBirth, p.DiseaseName \n" +
                "FROM Patients p \n" +
                "JOIN Doctors d ON p.Specialization = d.Specialization \n" +
                "WHERE d.DoctorID = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, doctorID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("PatientID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("DiseaseName")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

//    // Tải danh sách tất cả bệnh nhân được bác sĩ phụ trách
    private void loadAssignedPatients(int doctorID, DefaultTableModel tableModel) {
        String query = "SELECT PatientID, FullName, Gender, DateOfBirth, DiseaseName \n" +
                "FROM Patients \n" +
                "WHERE DoctorID = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, doctorID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("PatientID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("DiseaseName")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args){
        new DisplayPatients(7);
    }
    }
