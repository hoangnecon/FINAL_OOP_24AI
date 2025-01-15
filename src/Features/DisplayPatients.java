package Features;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import Apply2D.FontInput;
import Apply2D.RoundedButtonPanel;
import Apply2D.RoundedTextFieldPanel;
import ConnectData.getProfile;
import Listener.EditPatientListener;
import Listener.LoginListener;
import main.DatabaseConnect;

public class DisplayPatients extends JFrame {
        private int PatientID;
        private String FullName;
        private  String Gender;
        private  String DOB;
        private String DiseaseName;
        private EditPatientListener editPatientListener;

        private int doctoridallpatient;
        private String doctornameallpatient;
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
        allPatientsTable.setPreferredScrollableViewportSize(new Dimension(700,200));
        JScrollPane allPatientsScrollPane = new JScrollPane(allPatientsTable);
        allPatientsScrollPane.setPreferredSize(new Dimension(700, 200));
        DefaultTableModel allPatientsModel = new DefaultTableModel();
        allPatientsModel.setColumnIdentifiers(new String[]{"Mã bệnh nhân", "Họ tên", "Giới tính", "Ngày sinh", "Tên bệnh","ID.BS.Phụ trách","BS.phụ trách"});
        allPatientsTable.setModel(allPatientsModel);
        loadAllPatientsInDepartment(doctorID, allPatientsModel);
        GridBagConstraints all = new GridBagConstraints();
        all.gridx=0;
        all.gridy=1;
        all.weightx=1;
        all.weighty=1;
        all.fill = GridBagConstraints.BOTH;
        main.add(allPatientsScrollPane, all);
        JLabel allpatient = new JLabel("Tất cả bệnh nhân trong khoa: "+profile.getSpecialization());
        all.gridx=0;
        all.gridy=0;
        all.weightx=0;
        all.weighty=0;
        main.add(allpatient, all);

        JTable assignedPatientsTable = new JTable();
        assignedPatientsTable.setPreferredScrollableViewportSize(new Dimension(700,200));
        JScrollPane assignedPatientsScrollPane = new JScrollPane(assignedPatientsTable);
        allPatientsScrollPane.setPreferredSize(new Dimension(700,200));
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
        JLabel assignpatient = new JLabel("Bệnh nhân được bác sĩ " +profile.getFullName()+ " phụ trách ");
        assign.gridx=1;
        assign.gridy=0;
        assign.weightx=0;
        assign.weighty=0;
        main.add(assignpatient,assign);


        //panel search all
        JPanel search_p1 = new JPanel(new GridBagLayout());
        search_p1.setPreferredSize(new Dimension(700, 200));
        search_p1.setVisible(true);
        all.gridx=0;
        all.gridy=2;
        all.weightx=0;
        all.weighty=0;
        main.add(search_p1, all);



        Font fieldfont = FontInput.loadFont("src/SourceFont/JetBrainsMono-Bold.ttf", 20);
        JTextField fieldsearch1 = new JTextField();
        fieldsearch1.setOpaque(false);
        fieldsearch1.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel fieldsearchpanel1 = new RoundedTextFieldPanel(30);
        fieldsearch1.setBackground(Color.WHITE);
        fieldsearch1.setFont(fieldfont);
        fieldsearchpanel1.setForeground(Color.BLACK);
        fieldsearch1.setPreferredSize(new Dimension(300,50));
        all.gridx=0;
        all.gridy=0;
        all.weightx=0;
        all.weighty=0;
        all.insets= new Insets(0,0,0,0);
        fieldsearchpanel1.setPreferredSize(new Dimension(300, 50));
        fieldsearchpanel1.add(fieldsearch1);
        search_p1.add(fieldsearchpanel1,all);


        Font searchbutfont = FontInput.loadFont("src/SourceFont/Kodchasan/Kodchasan-Bold.ttf", 18f);
        RoundedButtonPanel searchbut1 = new RoundedButtonPanel("Search", 3, Color.BLACK, Color.BLACK, Color.BLACK);
        searchbut1.setFont(searchbutfont);
        searchbut1.setForeground(Color.WHITE);
        searchbut1.setPreferredSize(new Dimension(100,30));
        // Tạo hoạt động tìm kiếm trong bảng bệnh nhân trong khoa
        searchbut1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String searchText = fieldsearch1.getText().trim();
                if (searchText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Xóa dữ liệu cũ trong bảng
                allPatientsModel.setRowCount(0);

                String query = "SELECT p.PatientID, p.FullName AS PatientName, p.Gender, p.DateOfBirth, " +
                        "p.DiseaseName, p.DoctorID AS AssignedDoctorID, d.FullName AS AssignedDoctorName " +
                        "FROM Patients p " +
                        "JOIN Doctors d ON p.DoctorID = d.DoctorID " +
                        "WHERE p.Specialization = (SELECT Specialization FROM Doctors WHERE DoctorID = ?) " +
                        "AND (p.PatientID = ? OR p.FullName LIKE ?)";

                try (Connection connection = DatabaseConnect.getConnection();
                     PreparedStatement pstmt = connection.prepareStatement(query)) {

                    pstmt.setInt(1, doctorID);
                    pstmt.setString(2, searchText.matches("\\d+") ? searchText : "0");
                    pstmt.setString(3, "%" + searchText + "%");

                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        allPatientsModel.addRow(new Object[]{
                                rs.getInt("PatientID"),
                                rs.getString("PatientName"),
                                rs.getString("Gender"),
                                rs.getDate("DateOfBirth"),
                                rs.getString("DiseaseName"),
                                rs.getInt("AssignedDoctorID"),
                                rs.getString("AssignedDoctorName")
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        all.gridx=1;
        all.gridy=0;
        all.weightx=0;
        all.weighty=0;
        all.insets = new Insets(0,50,0,0);
        search_p1.add(searchbut1,all);



        RoundedButtonPanel returnbut1 = new RoundedButtonPanel("Return", 3, Color.BLACK, Color.BLACK, Color.BLACK);
        returnbut1.setFont(searchbutfont);
        returnbut1.setForeground(Color.WHITE);
        returnbut1.setPreferredSize(new Dimension(100,30));
        returnbut1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa dữ liệu cũ trong bảng
                allPatientsModel.setRowCount(0);

                // Truy vấn toàn bộ bệnh nhân
                String query = "SELECT p.PatientID, p.FullName AS PatientName, p.Gender, p.DateOfBirth, " +
                        "p.DiseaseName, p.DoctorID AS AssignedDoctorID, d.FullName AS AssignedDoctorName " +
                        "FROM Patients p " +
                        "JOIN Doctors d ON p.DoctorID = d.DoctorID " +
                        "WHERE p.Specialization = (SELECT Specialization FROM Doctors WHERE DoctorID = ?)";

                try (Connection connection = DatabaseConnect.getConnection();
                     PreparedStatement pstmt = connection.prepareStatement(query)) {

                    pstmt.setInt(1, doctorID); // Sử dụng doctorID để lấy bệnh nhân trong khoa
                    ResultSet rs = pstmt.executeQuery();

                    // Lặp qua từng kết quả và thêm vào bảng
                    while (rs.next()) {
                        allPatientsModel.addRow(new Object[]{
                                rs.getInt("PatientID"),
                                rs.getString("PatientName"),
                                rs.getString("Gender"),
                                rs.getDate("DateOfBirth"),
                                rs.getString("DiseaseName"),
                                rs.getInt("AssignedDoctorID"),
                                rs.getString("AssignedDoctorName")
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        all.gridx=1;
        all.gridy=1;
        all.weightx=0;
        all.weighty=0;
        all.insets = new Insets(50,50,0,0);
        search_p1.add(returnbut1, all);

        //panel search assign
        JPanel search_p2 = new JPanel(new GridBagLayout());
        search_p2.setPreferredSize(new Dimension(700, 200));
        assign.gridx=1;
        assign.gridy=2;
        assign.weightx=0;
        assign.weighty=0;
        main.add(search_p2, assign);

        JTextField fieldsearch2 = new JTextField();
        fieldsearch2.setOpaque(false);
        fieldsearch2.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel fieldsearchpanel2 = new RoundedTextFieldPanel(30);
        fieldsearch2.setBackground(Color.WHITE);
        fieldsearch2.setFont(fieldfont);
        fieldsearchpanel2.setForeground(Color.BLACK);
        fieldsearch2.setPreferredSize(new Dimension(300,50));
        assign.gridx=0;
        assign.gridy=0;
        assign.weightx=0;
        assign.weighty=0;
        assign.insets= new Insets(0,0,0,0);
        fieldsearchpanel2.setPreferredSize(new Dimension(300, 50));
        fieldsearchpanel2.add(fieldsearch2);
        search_p2.add(fieldsearchpanel2,assign);

        RoundedButtonPanel searchbut2 = new RoundedButtonPanel("Search", 3, Color.BLACK, Color.BLACK, Color.BLACK);
        searchbut2.setFont(searchbutfont);
        searchbut2.setForeground(Color.WHITE);
        searchbut2.setPreferredSize(new Dimension(100,30));
        // Tạo hoạt động cho bảng bệnh nhân phụ trách
        searchbut2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String searchText = fieldsearch2.getText().trim();
                if (searchText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Xóa dữ liệu cũ trong bảng
                assignedPatientsModel.setRowCount(0);

                String query = "SELECT PatientID, FullName, Gender, DateOfBirth, DiseaseName " +
                        "FROM Patients " +
                        "WHERE DoctorID = ? AND (PatientID = ? OR FullName LIKE ?)";

                try (Connection connection = DatabaseConnect.getConnection();
                     PreparedStatement pstmt = connection.prepareStatement(query)) {

                    pstmt.setInt(1, doctorID);
                    pstmt.setString(2, searchText.matches("\\d+") ? searchText : "0");
                    pstmt.setString(3, "%" + searchText + "%");

                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        assignedPatientsModel.addRow(new Object[]{
                                rs.getInt("PatientID"),
                                rs.getString("FullName"),
                                rs.getString("Gender"),
                                rs.getDate("DateOfBirth"),
                                rs.getString("DiseaseName")
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        assign.gridx=1;
        assign.gridy=0;
        assign.weightx=0;
        assign.weighty=0;
        assign.insets = new Insets(0,50,0,0);
        search_p2.add(searchbut2,assign);

        RoundedButtonPanel returnbut2 = new RoundedButtonPanel("Return", 3, Color.BLACK, Color.BLACK, Color.BLACK);
        returnbut2.setFont(searchbutfont);
        returnbut2.setForeground(Color.WHITE);
        returnbut2.setPreferredSize(new Dimension(100,30));
        // Nút Return2 - Quay lại danh sách bệnh nhân phụ trách
        returnbut2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa dữ liệu cũ trong bảng
                assignedPatientsModel.setRowCount(0);

                // Truy vấn toàn bộ bệnh nhân mà bác sĩ phụ trách
                String query = "SELECT PatientID, FullName, Gender, DateOfBirth, DiseaseName " +
                        "FROM Patients " +
                        "WHERE DoctorID = ?";

                try (Connection connection = DatabaseConnect.getConnection();
                     PreparedStatement pstmt = connection.prepareStatement(query)) {

                    pstmt.setInt(1, doctorID); // Sử dụng doctorID để lấy bệnh nhân phụ trách
                    ResultSet rs = pstmt.executeQuery();

                    // Lặp qua từng kết quả và thêm vào bảng
                    while (rs.next()) {
                        assignedPatientsModel.addRow(new Object[]{
                                rs.getInt("PatientID"),
                                rs.getString("FullName"),
                                rs.getString("Gender"),
                                rs.getDate("DateOfBirth"),
                                rs.getString("DiseaseName")
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        assign.gridx=1;
        assign.gridy=1;
        assign.weightx=0;
        assign.weighty=0;
        assign.insets = new Insets(50,50,0,0);
        search_p2.add(returnbut2, assign);




        this.add(background, BorderLayout.CENTER);
        background.add(main, BorderLayout.SOUTH);
        background.add(titlebar, BorderLayout.NORTH);
        this.setResizable(false);
        this.setVisible(true);
    }


private void loadAllPatientsInDepartment(int doctorID, DefaultTableModel tableModel) {
    String query = "SELECT p.PatientID, p.FullName AS PatientName, p.Gender, p.DateOfBirth, " +
            "p.DiseaseName, p.DoctorID AS AssignedDoctorID, d.FullName AS AssignedDoctorName " +
            "FROM Patients p " +
            "JOIN Doctors d ON p.DoctorID = d.DoctorID " +
            "WHERE p.Specialization = (SELECT Specialization FROM Doctors WHERE DoctorID = ?)";

    try (Connection connection = DatabaseConnect.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(query)) {

        pstmt.setInt(1, doctorID);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            tableModel.addRow(new Object[]{
                    rs.getInt("PatientID"),
                    rs.getString("PatientName"),
                    rs.getString("Gender"),
                    rs.getDate("DateOfBirth"),
                    rs.getString("DiseaseName"),
                    rs.getInt("AssignedDoctorID"),
                    rs.getString("AssignedDoctorName")
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
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
    }
