package main;

import Apply2D.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static Apply2D.Application_Graphics.drawRoundedBorder;

public class Signup extends JFrame {
    public Signup(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(829, 768);
        //this is background
        BorderLayout bd = new BorderLayout();
        JPanel background_panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int height = getHeight();
                int width = getWidth();
                Application_Graphics.applyGradient(g, width, height, new Color(0xFBBF83), new Color(0xB5BA8B), new Color(0x61B696), false);
            }
        };
        background_panel.setPreferredSize(new Dimension(829, 768));
        background_panel.setOpaque(true);
        add(background_panel, bd.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);

        GridBagConstraints tlg = new GridBagConstraints();
        JPanel panel_text_login = new JPanel(new GridBagLayout());
        panel_text_login.setOpaque(false);
        panel_text_login.setLayout(new GridBagLayout());
        JLabel text_login = new JLabel("SIGNUP");
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
        background_panel.add(panel_text_login, tlg);


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
        background_panel.add(username_field_panel, usertf);

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
        passtf.gridy = 3;
        passtf.weightx = 0;
        passtf.weighty = 0;
        passtf.fill = GridBagConstraints.NONE;
        passtf.insets = new Insets(0, 0, 280, 0);
        background_panel.add(pass_field_panel, passtf);


        //this is textfield with repassword icon
        JPanel textFieldWithRePass = new JPanel(new BorderLayout());
        textFieldWithRePass.setOpaque(false);
        textFieldWithRePass.setPreferredSize(new Dimension(522, 62));
        JPasswordField repass_field = new JPasswordField(15);
        repass_field.setEchoChar('*');
        repass_field.setPreferredSize(new Dimension(300, 25));
        repass_field.setOpaque(false);
        repass_field.setForeground(Color.BLACK);
        repass_field.setFont(new Font("Arial", Font.PLAIN, 24));
        repass_field.setCaretColor(Color.BLACK);
        repass_field.setBorder(BorderFactory.createEmptyBorder());
        repass_field.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        textFieldWithRePass.add(repass_field, BorderLayout.CENTER);
        JLabel repassLabel = new JLabel(new ImageIcon("src/imagesource/lock.png"));
        repassLabel.setPreferredSize(new Dimension(60, 50));
        repassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        repassLabel.setVerticalAlignment(SwingConstants.CENTER);
        repassLabel.setBorder(BorderFactory.createEmptyBorder(0, 9, 4, 27));
        textFieldWithRePass.add(repassLabel, BorderLayout.EAST);
        RoundedTextFieldPanel repass_field_panel = new RoundedTextFieldPanel(60);
        repass_field_panel.setPreferredSize(new Dimension(522, 62));
        repass_field_panel.add(textFieldWithRePass);
        repass_field_panel.setOpaque(false);
        GridBagConstraints repasstf = new GridBagConstraints();
        repasstf.gridheight = 0;
        repasstf.gridwidth = 0;
        repasstf.gridx = 0;
        repasstf.gridy = 4;
        repasstf.weightx = 0;
        repasstf.weighty = 0;
        repasstf.fill = GridBagConstraints.NONE;
        repasstf.insets = new Insets(0, 0, 100, 0);
        background_panel.add(repass_field_panel, repasstf);


        //this í show password checkbox
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
                    repass_field.setEchoChar((char) 0);
                } else {
                    repass_field.setEchoChar('*');
                }
            }
        });
        JPanel showpass_panel = new JPanel();
        showpass_panel.add(showPass);
        showpass_panel.setOpaque(false);
        GridBagConstraints showpassposition = new GridBagConstraints();
        showpassposition.gridx = 0;
        showpassposition.gridy = 5;
        showpassposition.gridwidth = 0;
        showpassposition.gridheight = 0;
        showpassposition.weightx = 0;
        showpassposition.weighty = 0;
        showpassposition.fill = GridBagConstraints.NONE;
        showpassposition.anchor = GridBagConstraints.CENTER;
        showpassposition.insets = new Insets(10, 0, 0, 280);
        background_panel.add(showpass_panel, showpassposition);


        //this is signup button
        Font loginbuttonFont = FontInput.loadFont("src/SourceFont/JetBrainsMono-Bold.ttf", 32f);
        RoundedButtonPanel sign_button_panel = new RoundedButtonPanel("Sigup", 30,new Color(0x242E49),new Color(0x1A2A47), new Color(0x09122B)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoundedBorder(g, 0, 0, getWidth() , getHeight() , 37, 37, new Color(0xE3B6B1), 1);
            }
        };
        sign_button_panel.setFont(loginbuttonFont);
        sign_button_panel.setPreferredSize(new Dimension(150, 50));
        sign_button_panel.setFont(loginbuttonFont);
        sign_button_panel.setForeground(Color.WHITE);

        sign_button_panel.setPreferredSize(new Dimension(649, 79));
        GridBagConstraints SignButton = new GridBagConstraints();
        SignButton.gridheight = 0;
        SignButton.gridwidth = 0;
        SignButton.gridx = 0;
        SignButton.gridy = 6;
        SignButton.weightx = 0;
        SignButton.weighty = 0;
        SignButton.fill = GridBagConstraints.NONE;
        SignButton.insets = new Insets(180, 0, 0, 0);
        background_panel.add(sign_button_panel, SignButton);

        background_panel.setOpaque(true);
        add(background_panel, bd.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
