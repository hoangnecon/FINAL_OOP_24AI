package main;

import Apply2D.Application_Graphics;
import Apply2D.FontInput;
import Apply2D.TransparentRoundedButtonWithHighlight;
import ConnectData.getProfile;
import Features.DisplayDoctor;
import Features.DisplayPatients;
import Listener.ButtonClickListener;
import Listener.LoginListener;
import Listener.LogoutListener;
import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Apply2D.Application_Graphics.drawRoundedBorder;
import static Apply2D.Application_Graphics.drawRoundedPanel;

public class Homepage_Manage extends JPanel {

    public Homepage_Manage(SwitchCard parent) {
        setSize(1440, 1024);
        setLayout(new BorderLayout());
        // Background panel
        JPanel background = new JPanel(new BorderLayout());
        background.setPreferredSize(new Dimension(1440, 1024));
        background.setBackground(Color.WHITE);
        this.add(background, BorderLayout.CENTER);
        Connection connection;
        try {
            connection = DatabaseConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getProfile profile = new getProfile(connection, LoginListener.getLoggedInUserId());

        // Title bar panel with GridBagLayout
        JPanel titlebar = new JPanel(new GridBagLayout());
        titlebar.setPreferredSize(new Dimension(1440, 107));
        titlebar.setBackground(new Color(0x38C1CE));

        // Logo
        JLabel logohos = new JLabel(new ImageIcon("src/imagesource/Group 1.png"));
        logohos.setPreferredSize(new Dimension(353, 70));
        GridBagConstraints logo_position = new GridBagConstraints();
        logo_position.gridx = 0;
        logo_position.gridy = 0;
        logo_position.weightx = 0.1;
        logo_position.insets = new Insets(10, 75, 10, 10);
        logo_position.fill = GridBagConstraints.NONE;

        // Manage button
        TransparentRoundedButtonWithHighlight Manage = new TransparentRoundedButtonWithHighlight("Manage", new Color(0x626262));
        Manage.setActive(true);
        GridBagConstraints Manageposition = new GridBagConstraints();
        Manageposition.gridx = 2;
        Manageposition.gridy = 0;
        Manageposition.insets = new Insets(35, 350, 20, 0);
        Manageposition.fill = GridBagConstraints.NONE;
        Manageposition.weightx = 1.0;

        // Profile button
        TransparentRoundedButtonWithHighlight Profile = new TransparentRoundedButtonWithHighlight("Profile", new Color(0x626262));
        Profile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Homepage_Profile profile = new Homepage_Profile(parent);
                parent.showPanel(profile);
            }
        });
        GridBagConstraints Profileposition = new GridBagConstraints();
        Profileposition.gridx = 2;
        Profileposition.gridy = 0;
        Profileposition.insets = new Insets(35, 700, 20, 0);
        Profileposition.fill = GridBagConstraints.NONE;
        Profileposition.weightx = 1.0;

        // Logout button
        TransparentRoundedButtonWithHighlight Logout = new TransparentRoundedButtonWithHighlight("Logout", new Color(0x626262));
        GridBagConstraints Logoutposition = new GridBagConstraints();
        Logoutposition.gridx = 2;
        Logoutposition.gridy = 0;
        Logoutposition.insets = new Insets(35, 1050, 20, 0);
        Logoutposition.fill = GridBagConstraints.NONE;
        Logoutposition.weightx = 1.0;
        Logout.addMouseListener(new LogoutListener());

        titlebar.add(logohos, logo_position);
        titlebar.add(Manage, Manageposition);
        titlebar.add(Profile, Profileposition);
        titlebar.add(Logout, Logoutposition);

        this.add(titlebar, BorderLayout.NORTH);

        JPanel choose = new JPanel(new GridBagLayout());
        choose.setPreferredSize(new Dimension(1440, 950));
        choose.setOpaque(false);
        choose.setBackground(Color.white);

        JLabel intro = new JLabel(new ImageIcon("src/imagesource/Group 9 (1).png"));
        intro.setPreferredSize(new Dimension(501, 83));
        GridBagConstraints introposition = new GridBagConstraints();
        introposition.gridx = 3;
        introposition.gridy = 0;
        introposition.weightx = 1;
        introposition.weighty = 0;
        introposition.insets = new Insets(100, 0, 0, 0);
        introposition.fill = GridBagConstraints.NONE;
        intro.setOpaque(false);
        choose.add(intro, introposition);

        JPanel p1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedPanel(g, this, 30);
                drawRoundedBorder(g, 0, 0, getWidth(), getHeight(), 37, 37, new Color(0xA8A8A8), 1);
            }
        };
        p1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String customID = LoginListener.getLoggedInUserId();
                String doctorName = profile.getFullName();
                String specialization = profile.getSpecialization();
                UpdatePatientDialog edit = new UpdatePatientDialog(parent, customID, doctorName, specialization);
            }
        });
        Application_Graphics.applyHoverEffect(p1, Color.white, Color.black);
        p1.setOpaque(false);
        p1.setPreferredSize(new Dimension(227, 273));
        p1.setBackground(Color.WHITE);
        GridBagConstraints p1oposition = new GridBagConstraints();
        p1oposition.gridx = 1;
        p1oposition.gridy = 1;
        p1oposition.weightx = 1;
        p1oposition.weighty = 1;
        p1oposition.insets = new Insets(0, 0, 100, 0);
        p1oposition.fill = GridBagConstraints.NONE;

        JPanel p2 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedPanel(g, this, 30);
                drawRoundedBorder(g, 0, 0, getWidth(), getHeight(), 37, 37, new Color(0xA8A8A8), 1);
            }
        };
        p2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String customID = LoginListener.getLoggedInUserId();
                String doctorName = profile.getFullName();
                String specialization = profile.getSpecialization();
                UpdatePatientDialog edit = new UpdatePatientDialog(parent, customID, doctorName, specialization);
            }
        });
        Application_Graphics.applyHoverEffect(p2, Color.white, Color.black);
        p2.setBackground(Color.WHITE);
        p2.setPreferredSize(new Dimension(227, 273));
        p2.setOpaque(false);

        GridBagConstraints p2position = new GridBagConstraints();
        p2position.gridx = 2;
        p2position.gridy = 1;
        p2position.weightx = 1;
        p2position.weighty = 1;
        p2position.insets = new Insets(0, 0, 100, 0);
        p2position.fill = GridBagConstraints.NONE;

        JPanel pm = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedPanel(g, this, 30);
                drawRoundedBorder(g, 0, 0, getWidth(), getHeight(), 37, 37, new Color(0xA8A8A8), 1);
            }
        };
        Application_Graphics.applyHoverEffect(pm, Color.white, Color.black);
        pm.setBackground(Color.WHITE);
        pm.setPreferredSize(new Dimension(400, 400));
        pm.setOpaque(false);

        GridBagConstraints pmposition = new GridBagConstraints();
        pmposition.gridx = 3;
        pmposition.gridy = 1;
        pmposition.weightx = 1;
        pmposition.weighty = 1;
        pmposition.insets = new Insets(0, 0, 100, 0);
        pmposition.fill = GridBagConstraints.NONE;

        JPanel p3 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedPanel(g, this, 30);
                drawRoundedBorder(g, 0, 0, getWidth(), getHeight(), 37, 37, new Color(0xA8A8A8), 1);
            }
        };
        Application_Graphics.applyHoverEffect(p3, Color.white, Color.black);
        p3.setOpaque(false);
        p3.setPreferredSize(new Dimension(227, 273));
        p3.setBackground(Color.white);
        GridBagConstraints p3position = new GridBagConstraints();
        p3position.gridx = 4;
        p3position.gridy = 1;
        p3position.weightx = 1;
        p3position.weighty = 1;
        p3position.insets = new Insets(0, 0, 100, 50);
        p3position.fill = GridBagConstraints.NONE;
        JPanel p4 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedPanel(g, this, 30);
                drawRoundedBorder(g, 0, 0, getWidth(), getHeight(), 37, 37, new Color(0xA8A8A8), 1);
            }
        };
        int hospitalID = 1;
        String queryDoctorID = "SELECT HospitalID FROM Doctors";
        try (PreparedStatement pstmt = connection.prepareStatement(queryDoctorID)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hospitalID = rs.getInt("HospitalID");
                }
            }
            int finalDoctorID = hospitalID;
            p4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    new DisplayDoctor(finalDoctorID);
                }
            });
            Application_Graphics.applyHoverEffect(p4, Color.white, Color.black);
            p4.setOpaque(false);
            p4.setPreferredSize(new Dimension(227, 273));
            p4.setBackground(Color.white);
            GridBagConstraints p4position = new GridBagConstraints();
            p4position.gridx = 5;
            p4position.gridy = 1;
            p4position.weightx = 1;
            p4position.weighty = 1;
            p4position.insets = new Insets(0, 0, 100, 0);
            p4position.fill = GridBagConstraints.NONE;

            choose.add(p1, p1oposition);
            choose.add(p2, p2position);
            choose.add(p3, p3position);
            choose.add(p4, p4position);
            choose.add(pm, pmposition);

            // this is label in 3 panel
            JLabel add = new JLabel(new ImageIcon("src/imagesource/blue-linear-outline-add-person-icon-user-vector-29004179_1-removebg-preview.png"));
            add.setPreferredSize(new Dimension(100, 100));
            GridBagConstraints addposition = new GridBagConstraints();
            addposition.gridx = 0;
            addposition.gridy = 0;
            addposition.weightx = 1;
            addposition.weighty = 1;
            addposition.insets = new Insets(20, 0, 0, 0);
            p1.add(add, addposition);

            Font addfont = FontInput.loadFont("src/SourceFont/Kodchasan/Kodchasan-Bold.ttf", 24f);
            JLabel addtext = new JLabel("Add");
            addtext.setFont(addfont);
            addtext.setForeground(new Color(0xB7B7B7));
            addposition.gridx = 0;
            addposition.gridy = 1;
            addposition.weightx = 1;
            addposition.weighty = 1;
            addposition.insets = new Insets(0, 0, 30, 0);
            p1.add(addtext, addposition);

            JLabel update = new JLabel(new ImageIcon("src/imagesource/repeat-pixel-perfect-gradient-linear-ui-icon-vector-43353369_1-removebg-preview.png"));
            update.setPreferredSize(new Dimension(100, 100));
            GridBagConstraints updateposition = new GridBagConstraints();
            updateposition.gridx = 0;
            updateposition.gridy = 0;
            updateposition.weightx = 1;
            updateposition.weighty = 1;
            updateposition.insets = new Insets(20, 0, 0, 0);
            p2.add(update, updateposition);
            Font updatefont = FontInput.loadFont("src/SourceFont/Kodchasan/Kodchasan-Bold.ttf", 24f);
            String textUpdate = "<html><body style='text-align: center; font-family: \"Kodchasan\"; font-size: 24px; color: #B7B7B7;'>" + "Update<br>Information</body></html>";
            JLabel updatetext = new JLabel(textUpdate);
            updatetext.setFont(updatefont);
            updatetext.setForeground(new Color(0xB7B7B7));
            updateposition.gridx = 0;
            updateposition.gridy = 1;
            updateposition.weightx = 1;
            updateposition.weighty = 2;
            updateposition.insets = new Insets(0, 0, 30, 0);
            p2.add(updatetext, updateposition);

            JLabel listpatient = new JLabel(new ImageIcon("src/imagesource/list_10056840 1.png"));
            listpatient.setPreferredSize(new Dimension(100, 100));
            GridBagConstraints listpatientposition = new GridBagConstraints();
            listpatientposition.gridx = 0;
            listpatientposition.gridy = 0;
            listpatientposition.weightx = 1;
            listpatientposition.weighty = 1;
            listpatientposition.insets = new Insets(20, 10, 0, 0);
            p3.add(listpatient, listpatientposition);
            Font deletefont = FontInput.loadFont("src/SourceFont/Kodchasan/Kodchasan-Bold.ttf", 24f);
            JLabel listPatienttext = new JLabel("List Patient");
            listPatienttext.setFont(deletefont);
            listPatienttext.setForeground(new Color(0xB7B7B7));
            listpatientposition.gridx = 0;
            listpatientposition.gridy = 1;
            listpatientposition.weightx = 1;
            listpatientposition.weighty = 1;
            listpatientposition.insets = new Insets(0, 0, 30, 0);
            p3.add(listPatienttext, listpatientposition);

            JLabel listdoctor = new JLabel(new ImageIcon("src/imagesource/list_10056840 1.png"));
            listdoctor.setPreferredSize(new Dimension(100, 100));
            GridBagConstraints listdoctorposition = new GridBagConstraints();
            listdoctorposition.gridx = 0;
            listdoctorposition.gridy = 0;
            listdoctorposition.weightx = 1;
            listdoctorposition.weighty = 1;
            listdoctorposition.insets = new Insets(20, 10, 0, 0);
            p4.add(listdoctor, listdoctorposition);
            JLabel listdoctortext = new JLabel("List Doctor");
            listdoctortext.setFont(deletefont);
            listdoctortext.setForeground(new Color(0xB7B7B7));
            listdoctorposition.gridx = 0;
            listdoctorposition.gridy = 1;
            listdoctorposition.weightx = 1;
            listdoctorposition.weighty = 1;
            listdoctorposition.insets = new Insets(0, 0, 30, 0);
            p4.add(listdoctortext, listdoctorposition);

            JLabel picmain = new JLabel(new ImageIcon("src/imagesource/rb_136743 1.png"));
            picmain.setPreferredSize(new Dimension(300, 300));
            GridBagConstraints picmainposition = new GridBagConstraints();
            picmainposition.gridx = 0;
            picmainposition.gridy = 0;
            picmainposition.weightx = 1;
            picmainposition.weighty = 1;
            picmainposition.insets = new Insets(20, 10, 0, 0);
            pm.add(picmain, picmainposition);

            add(choose, BorderLayout.SOUTH);
//            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



