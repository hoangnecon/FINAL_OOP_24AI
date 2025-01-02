package main;

import Apply2D.FontInput;
import Apply2D.TransparentRoundedButtonWithHighlight;
import ConnectData.getProfile;
import Listener.ButtonClickListener;
import Listener.LoginListener;
import Listener.LogoutListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class Homepage_Profile extends JPanel {
    public Homepage_Profile(SwitchCard parent){
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1440, 1024);
        this.setLayout(new BorderLayout());
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getProfile profile = new getProfile(connection, LoginListener.getLoggedInUserId());

        // Background panel
        JPanel background = new JPanel(new BorderLayout());
        background.setPreferredSize(new Dimension(1440, 1024));
        background.setBackground(Color.WHITE);
        this.add(background, BorderLayout.CENTER);

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
        Manage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Homepage_Manage manage = new Homepage_Manage(parent);
                parent.showPanel(manage);
            }
        });
        GridBagConstraints Manageposition = new GridBagConstraints();
        Manageposition.gridx = 2;
        Manageposition.gridy = 0;
        Manageposition.insets = new Insets(35, 0, 20, 0);
        Manageposition.fill = GridBagConstraints.NONE;
        Manageposition.weightx = 1.0;

        // Doctor button
        TransparentRoundedButtonWithHighlight Doctor = new TransparentRoundedButtonWithHighlight("Doctor", new Color(0x626262));
        Doctor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                HomePage_Doctor doctor = new HomePage_Doctor(parent);
                parent.showPanel(doctor);
            }
        });
        GridBagConstraints Doctorposition = new GridBagConstraints();
        Doctorposition.gridx = 2;
        Doctorposition.gridy = 0;
        Doctorposition.insets = new Insets(35, 350, 20, 0);
        Doctorposition.fill = GridBagConstraints.NONE;
        Doctorposition.weightx = 1.0;
        // Profile button
        TransparentRoundedButtonWithHighlight Profile = new TransparentRoundedButtonWithHighlight("Profile", new Color(0x626262));
        Profile.setActive(true);
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

        String userId = LoginListener.getLoggedInUserId();
        char userType = userId.charAt(0);
        switch (userType){
            case 'P':
                titlebar.add(logohos, logo_position);
                break;
            case 'D':
                titlebar.add(logohos, logo_position);
                titlebar.add(Doctor, Doctorposition);
                break;
            case 'M':
                titlebar.add(logohos, logo_position);
                titlebar.add(Manage, Manageposition);
                Manageposition.gridx = 2;
                Manageposition.gridy = 0;
                Manageposition.insets = new Insets(35, 350, 20, 0);
                Manageposition.fill = GridBagConstraints.NONE;
                Manageposition.weightx = 1.0;
                titlebar.add(Manage, Manageposition);
                break;
        }
        titlebar.add(Profile,Profileposition);
        titlebar.add(Logout, Logoutposition);
        ButtonClickListener ManageClickListener = new ButtonClickListener(Manage, Doctor, Profile, Logout);
        ButtonClickListener DoctorClickListener = new ButtonClickListener(Doctor, Manage, Profile, Logout);
        Manage.addMouseListener(ManageClickListener);
        Doctor.addMouseListener(DoctorClickListener);
        this.add(titlebar, BorderLayout.NORTH);

        JPanel choose = new JPanel(new GridBagLayout());
        choose.setPreferredSize(new Dimension(1440,918));
        choose.setOpaque(false);
        choose.setBackground(Color.white);


        JLabel textintro = new JLabel(new ImageIcon("src/imagesource/THÔNG TIN CÁ NHÂN NGƯỜI DÙNG.png"));
        GridBagConstraints textintroposittion = new GridBagConstraints();
        textintroposittion.gridx=1;
        textintroposittion.gridy= 0;
        textintroposittion.insets = new Insets(0,0,40,500);
        textintroposittion.anchor = GridBagConstraints.NORTH;
        choose.add(textintro,textintroposittion);
        JLabel userimage = new JLabel(new ImageIcon("src/imagesource/biguser.png"));
        userimage.setPreferredSize(new Dimension(380,400));
        GridBagConstraints userimageposittion = new GridBagConstraints();
        userimageposittion.gridx=0;
        userimageposittion.gridy=1;
        userimageposittion.insets= new Insets(0,0,0,50);
        choose.add(userimage, userimageposittion);

        JPanel information = new JPanel(new GridBagLayout());
        information.setPreferredSize(new Dimension(900,600));
        information.setOpaque(true);
        GridBagConstraints inforposition = new GridBagConstraints();
        inforposition.gridx=1;
        inforposition.gridy=1;
        inforposition.insets = new Insets(0,0,0,0);


        GridBagConstraints textposition = new GridBagConstraints();
        Font infortextFont = FontInput.loadFont("src/SourceFont/KoHo/KoHo-Light.ttf",29f);
        Font inforfieldFont = FontInput.loadFont("src/SourceFont/KoHo/KoHo-Bold.ttf",29f);
        JPanel leftin4 = new JPanel(new GridBagLayout());
        JPanel rightpanel = new JPanel(new GridBagLayout());
        rightpanel.setPreferredSize(new Dimension(450,600));
        leftin4.setPreferredSize(new Dimension(400, 600));


        JLabel fullnametext = new JLabel("FULL NAME: ");
        fullnametext.setFont(infortextFont);
        fullnametext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=0;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(fullnametext, textposition);

        JLabel namefield = new JLabel(profile.getFullName());
        namefield.setFont(inforfieldFont);
        namefield.setPreferredSize(new Dimension(600,50));
        textposition.insets = new Insets(0,0,0,0) ;
        rightpanel.add(namefield, textposition);

        JLabel gendertext = new JLabel("GENDER: ");
        gendertext.setFont(infortextFont);
        gendertext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=1;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(gendertext,textposition);
        JLabel genderfield = new JLabel(profile.getGender());
        genderfield.setFont(inforfieldFont);
        genderfield.setPreferredSize(new Dimension(600,70));
        textposition.insets = new Insets(0,150,0,150) ;
        rightpanel.add(genderfield,textposition);

        JLabel datetext = new JLabel("DATE OF BIRTH: ");
        datetext.setFont(infortextFont);
        datetext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=2;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(datetext,textposition);
        JLabel datefield = new JLabel(profile.getDateofBirth());
        datefield.setFont(inforfieldFont);
        datefield.setPreferredSize(new Dimension(600,70));
        textposition.insets = new Insets(0,150,0,150) ;
        rightpanel.add(datefield, textposition);

        JLabel addresstext = new JLabel("ADDRESS: ");
        addresstext.setFont(infortextFont);
        addresstext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=3;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(addresstext, textposition);
        JLabel addressfield = new JLabel(profile.getAddress());
        addressfield.setFont(inforfieldFont);
        addressfield.setPreferredSize(new Dimension(600,70));
        textposition.insets = new Insets(0,150,0,150) ;
        rightpanel.add(addressfield, textposition);

        JLabel pathologytext = new JLabel("PATHOLOGY: ");
        pathologytext.setFont(infortextFont);
        pathologytext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=4;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(pathologytext, textposition);
        JLabel pathologyfield = new JLabel(profile.getDiseasename());
        pathologyfield.setFont(inforfieldFont);
        pathologyfield.setPreferredSize(new Dimension(600,70));
        textposition.insets = new Insets(0,150,0,150) ;
        rightpanel.add(pathologyfield, textposition);

        JLabel attendingtext = new JLabel("ATTENDING PHYSICIAN: ");
        attendingtext.setFont(infortextFont);
        attendingtext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=5;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(attendingtext,textposition);
        JLabel attendingfield = new JLabel(profile.getAttending());
        attendingfield.setFont(inforfieldFont);
        attendingfield.setPreferredSize(new Dimension(600,70));
        textposition.insets = new Insets(0,150,0,150) ;
        rightpanel.add(attendingfield, textposition);


        JLabel departmenttext = new JLabel("DEPARTMENT: ");
        departmenttext.setFont(infortextFont);
        departmenttext.setForeground(Color.black);
        textposition.gridx=0;
        textposition.gridy=6;
        textposition.insets = new Insets(0,0,0,0);
        leftin4.add(departmenttext, textposition);
        JLabel departmentfield = new JLabel(profile.getSpecialization());
        departmentfield.setFont(inforfieldFont);
        departmentfield.setPreferredSize(new Dimension(600,70));
        textposition.insets = new Insets(0,150,0,150) ;
        rightpanel.add(departmentfield,textposition);

        JPanel textandfield = new JPanel(new BorderLayout());
        textandfield.add(leftin4, BorderLayout.WEST);
        textandfield.add(rightpanel, BorderLayout.CENTER);
        textposition.gridx=1;
        textposition.gridy=1;
        textposition.insets= new Insets(0,0,0,0);

        information.add(textandfield, textposition);


        choose.add(information, inforposition);
        this.add(choose, BorderLayout.SOUTH);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}