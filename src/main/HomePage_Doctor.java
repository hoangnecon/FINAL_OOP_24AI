package main;

import Apply2D.Application_Graphics;
import Apply2D.FontInput;
import Apply2D.TransparentRoundedButtonWithHighlight;
import ConnectData.getProfile;
import Features.DisplayPatients;
import Listener.ButtonClickListener;
import Listener.EditPatientListener;
import Listener.LoginListener;
import Listener.LogoutListener;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;

import static Apply2D.Application_Graphics.drawRoundedBorder;
import static Apply2D.Application_Graphics.drawRoundedPanel;

public class HomePage_Doctor extends JPanel {


    public HomePage_Doctor(SwitchCard parent) {
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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


        // Doctor button
        TransparentRoundedButtonWithHighlight Doctor = new TransparentRoundedButtonWithHighlight("Doctor", new Color(0x626262));
        Doctor.setActive(true);
        GridBagConstraints Doctorposition = new GridBagConstraints();
        Doctorposition.gridx = 2;
        Doctorposition.gridy = 0;
        Doctorposition.insets = new Insets(35, 350, 20, 0);
        Doctorposition.fill = GridBagConstraints.NONE;
        Doctorposition.weightx = 1.0;
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

        //Logout button
        TransparentRoundedButtonWithHighlight Logout = new TransparentRoundedButtonWithHighlight("Logout", new Color(0x626262));
        GridBagConstraints Logoutposition = new GridBagConstraints();
        Logoutposition.gridx = 2;
        Logoutposition.gridy = 0;
        Logoutposition.insets = new Insets(35, 1050, 20, 0);
        Logoutposition.fill = GridBagConstraints.NONE;
        Logoutposition.weightx = 1.0;
        Logout.addMouseListener(new LogoutListener());

        titlebar.add(logohos, logo_position);
        titlebar.add(Doctor, Doctorposition);
        titlebar.add(Profile, Profileposition);
        titlebar.add(Logout, Logoutposition);

        this.add(titlebar, BorderLayout.NORTH);

        JPanel choose = new JPanel(new GridBagLayout());
        choose.setPreferredSize(new Dimension(1440, 918));
        choose.setOpaque(false);
        choose.setBackground(Color.white);


        JLabel intro = new JLabel(new ImageIcon("src/imagesource/Group 9 (1).png"));
        intro.setPreferredSize(new Dimension(501, 83));
        GridBagConstraints introposition = new GridBagConstraints();
        introposition.gridx = 2;
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

                // Hiển thị dialog
                AddPatientDialog dialog = new AddPatientDialog(parent, customID, doctorName, specialization);
                dialog.setVisible(true);
            }
        });

        Application_Graphics.applyHoverEffect(p1, Color.white, Color.black);
        p1.setOpaque(false);
        p1.setPreferredSize(new Dimension(290, 273));
        p1.setBackground(Color.WHITE);
        GridBagConstraints p1oposition = new GridBagConstraints();
        p1oposition.gridx = 1;
        p1oposition.gridy = 1;
        p1oposition.weightx = 1;
        p1oposition.weighty = 1;
        p1oposition.insets = new Insets(0, 250, 250, 0);
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
                edit.setVisible(true);
            }
        });
        Application_Graphics.applyHoverEffect(p2, Color.white, Color.black);
        p2.setBackground(Color.WHITE);
        p2.setPreferredSize(new Dimension(290, 273));
        p2.setOpaque(false);

        GridBagConstraints p2position = new GridBagConstraints();
        p2position.gridx = 2;
        p2position.gridy = 1;
        p2position.weightx = 1;
        p2position.weighty = 1;
        p2position.insets = new Insets(0, 0, 250, 0);
        p2position.fill = GridBagConstraints.NONE;

        JPanel p3 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedPanel(g, this, 30);
                drawRoundedBorder(g, 0, 0, getWidth(), getHeight(), 37, 37, new Color(0xA8A8A8), 1);
            }
        };
        int doctorID = 0;
        String customID = LoginListener.getLoggedInUserId();
        String queryDoctorID = "SELECT DoctorID FROM Doctors WHERE CustomID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(queryDoctorID)) {
            pstmt.setString(1, customID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctorID = rs.getInt("DoctorID");
                }
            }
            int finalDoctorID = doctorID;
            p3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    new DisplayPatients(finalDoctorID);
                }
            });
            Application_Graphics.applyHoverEffect(p3, Color.white, Color.black);
            p3.setOpaque(false);
            p3.setPreferredSize(new Dimension(290, 273));
            p3.setBackground(Color.white);
            GridBagConstraints p3position = new GridBagConstraints();
            p3position.gridx = 3;
            p3position.gridy = 1;
            p3position.weightx = 1;
            p3position.weighty = 1;
            p3position.insets = new Insets(0, 0, 250, 250);
            p3position.fill = GridBagConstraints.NONE;

            choose.add(p1, p1oposition);
            choose.add(p2, p2position);
            choose.add(p3, p3position);


            //this is label in 3 panel

            //this is p1
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
            JLabel updatetext = new JLabel("Update Information");
            updatetext.setFont(updatefont);
            updatetext.setForeground(new Color(0xB7B7B7));
            updateposition.gridx = 0;
            updateposition.gridy = 1;
            updateposition.weightx = 1;
            updateposition.weighty = 1;
            updateposition.insets = new Insets(0, 0, 30, 0);
            p2.add(updatetext, updateposition);


            JLabel delete = new JLabel(new ImageIcon("src/imagesource/list_10056840 1.png"));
            delete.setPreferredSize(new Dimension(100, 100));
            GridBagConstraints deleteposition = new GridBagConstraints();
            deleteposition.gridx = 0;
            deleteposition.gridy = 0;
            deleteposition.weightx = 1;
            deleteposition.weighty = 1;
            deleteposition.insets = new Insets(20, 10, 0, 0);
            p3.add(delete, deleteposition);


            Font deletefont = FontInput.loadFont("src/SourceFont/Kodchasan/Kodchasan-Bold.ttf", 24f);
            JLabel deletetext = new JLabel("List Patient");
            deletetext.setFont(deletefont);
            deletetext.setForeground(new Color(0xB7B7B7));
            deleteposition.gridx = 0;
            deleteposition.gridy = 1;
            deleteposition.weightx = 1;
            deleteposition.weighty = 1;
            deleteposition.insets = new Insets(0, 0, 30, 0);
            p3.add(deletetext, deleteposition);


            add(choose, BorderLayout.SOUTH);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
