package Features;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

import Apply2D.FontInput;
import Apply2D.RoundedButtonPanel;
import Apply2D.RoundedTextFieldPanel;
import ConnectData.getProfile;
import Listener.LoginListener;
import main.DatabaseConnect;

public class DisplayDoctor extends JFrame {
    private int PatientID;
    private String FullName;
    private  String Gender;
    private  String DOB;
    private String DiseaseName;

    private int doctoridallpatient;
    private String doctornameallpatient;
    public DisplayDoctor(int hospitalID) {
        Connection connection;
        try {
            connection = DatabaseConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        getProfile profile = new getProfile(connection, LoginListener.getLoggedInUserId());
        setTitle("Danh sách bác sĩ");
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
        allPatientsModel.setColumnIdentifiers(new String[]{"DoctorID", "Họ tên", "Giới tính", "Ngày sinh", "SDT","Dia Chi","Khoa"});
        allPatientsTable.setModel(allPatientsModel);
        loadAllPatientsInDepartment(hospitalID, allPatientsModel);
        GridBagConstraints all = new GridBagConstraints();
        all.gridx=0;
        all.gridy=1;
        all.weightx=1;
        all.weighty=1;
        all.fill = GridBagConstraints.BOTH;
        main.add(allPatientsScrollPane, all);


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
        all.gridx=1;
        all.gridy=0;
        all.weightx=0;
        all.weighty=0;
        all.insets = new Insets(0,50,0,0);
        search_p1.add(searchbut1,all);


        this.add(background, BorderLayout.CENTER);
        background.add(main, BorderLayout.SOUTH);
        background.add(titlebar, BorderLayout.NORTH);
        this.setResizable(false);
        this.setVisible(true);
    }


    private void loadAllPatientsInDepartment(int hospitalID, DefaultTableModel tableModel) {
        String query = "SELECT p.DoctorID, p.FullName, p.Gender, p.DateOfBirth, " +
                "p.PhoneNumber, p.Address, p.Specialization " +
                "FROM Doctors p " +
                "JOIN Hospitals d ON p.HospitalID = d.HospitalID " +
                "WHERE p.HospitalID = ?";  // Thêm điều kiện WHERE

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Gán giá trị cho tham số
            pstmt.setInt(1, hospitalID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("DoctorID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        rs.getString("Specialization")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
