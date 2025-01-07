package main;

import Apply2D.*;
import Listener.LoginListener;
import Listener.LogoutListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;

import static Apply2D.Application_Graphics.drawRoundedBorder;

public class LoginMain extends JFrame {
    public LoginMain() {

        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1440, 1024);

        //this is background
        GridBagConstraints gbc = new GridBagConstraints();
        BorderLayout bd = new BorderLayout();
        JPanel panel1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
               int width = getWidth();
               int height = getHeight();
                Application_Graphics.applyGradient(g, width, height,new Color(0xFBBF83), new Color(0xB5BA8B),new Color(0x61B696)  ,false);

            }
        };
        panel1.setPreferredSize(new Dimension(1440, 1024));
        panel1.setOpaque(true);
        add(panel1, bd.CENTER);

        //this is inner background
        JPanel panel2 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color darkOverlay = new Color(0, 0, 0, 30);
                g2d.setColor(darkOverlay);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                setOpaque(false);
            }
        };
        GridBagConstraints innerlay = new GridBagConstraints();
        panel2.setPreferredSize(new Dimension(829, 768));
        panel2.setOpaque(false);
        innerlay.gridx = 0;
        innerlay.gridy = 0;
        innerlay.gridwidth = 1;
        innerlay.gridheight = 1;
        innerlay.weightx = 0;
        innerlay.weighty = 0;
        innerlay.fill = GridBagConstraints.NONE;
        innerlay.anchor = GridBagConstraints.CENTER;

        //            this is text login
        GridBagConstraints tlg = new GridBagConstraints();
        JPanel panel_text_login = new JPanel(new GridBagLayout());
        panel_text_login.setOpaque(false);
        panel_text_login.setLayout(new GridBagLayout());
        JLabel text_login = new JLabel("LOGIN");
        Font loginFont = FontInput.loadFont("src/SourceFont/JetBrainsMono-Bold.ttf", 75f);
        text_login.setFont(loginFont);
        text_login.setForeground(new Color(0x000000));
        panel_text_login.add(text_login);
        tlg.gridx = 0;
        tlg.gridy = 1;
        tlg.gridwidth = 1;
        tlg.gridheight = 1;
        tlg.weighty = 1;
        tlg.weightx = 1;
        tlg.insets = new Insets(80, 0, 0, 0);
        tlg.anchor = GridBagConstraints.PAGE_START;
        tlg.fill = GridBagConstraints.NONE;
        panel2.add(panel_text_login, tlg);


        //this is text field with user icon
        JPanel textFieldWithUsername = new JPanel(new BorderLayout());
        textFieldWithUsername.setOpaque(false);
        textFieldWithUsername.setPreferredSize(new Dimension(522, 62));
        JTextField username_field = new JTextField(15);
        username_field.setPreferredSize(new Dimension(300, 25));
        username_field.setOpaque(false);
        username_field.setForeground(Color.BLACK);
        username_field.setFont(new Font("Arial", Font.PLAIN, 24));
        username_field.setCaretColor(Color.BLACK);
        username_field.setBorder(BorderFactory.createEmptyBorder());
        username_field.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        textFieldWithUsername.add(username_field, BorderLayout.CENTER);
        JLabel usernameLabel = new JLabel(new ImageIcon("src/imagesource/user.png"));
        usernameLabel.setPreferredSize(new Dimension(50, 40));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 4, 27));
        textFieldWithUsername.add(usernameLabel, BorderLayout.EAST);
        RoundedTextFieldPanel username_field_panel = new RoundedTextFieldPanel(60);
        username_field_panel.setPreferredSize(new Dimension(522, 62));
        username_field_panel.add(textFieldWithUsername);
        username_field_panel.setOpaque(false);
        GridBagConstraints usertf = new GridBagConstraints();
        usertf.gridheight = 0;
        usertf.gridwidth = 0;
        usertf.gridx = 0;
        usertf.gridy = 2;
        usertf.weightx = 0;
        usertf.weighty = 0;
        usertf.fill = GridBagConstraints.NONE;
        usertf.insets = new Insets(0, 0, 450, 0);
        panel2.add(username_field_panel, usertf);

        //this is textfield with password icon
        JPanel textFieldWithPass = new JPanel(new BorderLayout());
        textFieldWithPass.setOpaque(false);
        textFieldWithPass.setPreferredSize(new Dimension(522, 62));
        JPasswordField pass_field = new JPasswordField(15);
        pass_field.setEchoChar('*');
        pass_field.setPreferredSize(new Dimension(300, 25));
        pass_field.setOpaque(false);
        pass_field.setForeground(Color.BLACK);
        pass_field.setFont(new Font("Arial", Font.PLAIN, 24));
        pass_field.setCaretColor(Color.BLACK);
        pass_field.setBorder(BorderFactory.createEmptyBorder());
        pass_field.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        textFieldWithPass.add(pass_field, BorderLayout.CENTER);
        JLabel passLabel = new JLabel(new ImageIcon("src/imagesource/lock.png"));
        passLabel.setPreferredSize(new Dimension(60, 50));
        passLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passLabel.setVerticalAlignment(SwingConstants.CENTER);
        passLabel.setBorder(BorderFactory.createEmptyBorder(0, 9, 4, 27));
        textFieldWithPass.add(passLabel, BorderLayout.EAST);
        RoundedTextFieldPanel pass_field_panel = new RoundedTextFieldPanel(60);
        pass_field_panel.setPreferredSize(new Dimension(522, 62));
        pass_field_panel.add(textFieldWithPass);
        pass_field_panel.setOpaque(false);
        GridBagConstraints passtf = new GridBagConstraints();
        passtf.gridheight = 0;
        passtf.gridwidth = 0;
        passtf.gridx = 0;
        passtf.gridy = 0;
        passtf.weightx = 0;
        passtf.weighty = 0;
        passtf.fill = GridBagConstraints.NONE;
        passtf.insets = new Insets(0, 0, 0, 0);
        panel2.add(pass_field_panel, passtf);

        //this is show password checkbox
        ImageIcon checkedicon = new ImageIcon("src/imagesource/view.png");
        ImageIcon uncheckedicon = new ImageIcon("src/imagesource/eye.png");
        uncheckedicon = ImageCheckbox.resizeIcon(uncheckedicon, 20, 20);
        checkedicon = ImageCheckbox.resizeIcon(checkedicon, 20, 20);
        JCheckBox showPass = ImageCheckbox.createCustomCheckbox_byImage("Show password", uncheckedicon, checkedicon);
        showPass.setFont(new Font("Arial", Font.PLAIN, 18));
        showPass.setPreferredSize(new Dimension(200, 40));
        showPass.setFocusPainted(false);
        showPass.setOpaque(false);
        showPass.setBorder(BorderFactory.createEmptyBorder());
        showPass.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    pass_field.setEchoChar((char) 0);
                } else {
                    pass_field.setEchoChar('*');
                }
            }
        });
        JPanel showpass_panel = new JPanel();
        showpass_panel.add(showPass);
        showpass_panel.setOpaque(false);
        GridBagConstraints showpassposition = new GridBagConstraints();
        showpassposition.gridx = 0;
        showpassposition.gridy = 3;
        showpassposition.gridwidth = 0;
        showpassposition.gridheight = 0;
        showpassposition.weightx = 0;
        showpassposition.weighty = 0;
        showpassposition.fill = GridBagConstraints.NONE;
        showpassposition.anchor = GridBagConstraints.CENTER;
        showpassposition.insets = new Insets(0, 0, 120, 300);
        panel2.add(showpass_panel, showpassposition);

        //this is login button
        Font loginbuttonFont = FontInput.loadFont("src/SourceFont/JetBrainsMono-Bold.ttf", 32f);
        RoundedButtonPanel login_button_panel = new RoundedButtonPanel("LOGIN", 30, new Color(0x5B87AF), new Color(0x466B8A),  new Color(0x284053)){
          @Override
          protected void paintComponent(Graphics g){
                  super.paintComponent(g);
                  drawRoundedBorder(g, 0, 0, getWidth() , getHeight() , 37, 37, new Color(0xE3B6B1), 1);
          }
        };

        Connection finalConnection = connection;
        login_button_panel.addMouseListener(new LoginListener(username_field, pass_field, finalConnection));
        login_button_panel.setFont(loginbuttonFont);
        login_button_panel.setForeground(Color.WHITE);
        login_button_panel.setBackground(new Color(0x522C5D));
        login_button_panel.setPreferredSize(new Dimension(649, 79));

        GridBagConstraints loginButton = new GridBagConstraints();
        loginButton.gridheight = 0;
        loginButton.gridwidth = 0;
        loginButton.gridx = 0;
        loginButton.gridy = 4;
        loginButton.weightx = 0;
        loginButton.weighty = 0;
        loginButton.fill = GridBagConstraints.NONE;
        loginButton.insets = new Insets(100, 0, 0, 0);
        panel2.add(login_button_panel, loginButton);

        panel1.setLayout(new GridBagLayout());
        panel1.add(panel2, innerlay);
        setVisible(true);
    }
}
