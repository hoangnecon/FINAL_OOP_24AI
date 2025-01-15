package main;

import Features.AddDoctorPanel;
import Listener.AddDoctorListener;

import javax.swing.*;
import java.awt.*;

public class AddDoctorDialog extends JDialog {
    private AddDoctorPanel addDoctorPanel;
    private JButton submitButton;

    public AddDoctorDialog(SwitchCard parent, String doctorID, String doctorName, String specialization) {
        super(parent, "Thêm bác sĩ", true);

        addDoctorPanel = new AddDoctorPanel();
        submitButton = new JButton("Thêm bác sĩ");

        AddDoctorListener listener = new AddDoctorListener(addDoctorPanel, doctorID, doctorName, specialization);
        submitButton.addActionListener(listener);

        JPanel titleBar = new JPanel(new GridBagLayout());
        GridBagConstraints logoConstraints = new GridBagConstraints();
        titleBar.setPreferredSize(new Dimension(800, 102));
        titleBar.setBackground(new Color(0x38C1CE));
        logoConstraints.weightx = 1;
        logoConstraints.weighty = 0;
        logoConstraints.insets = new Insets(0, 100, 0, 0);
        JLabel logo = new JLabel(new ImageIcon("src/imagesource/Group 1.png"));
        logo.setPreferredSize(new Dimension(353, 70));

        titleBar.add(logo, logoConstraints);

        // Thiết lập bố cục
        setLayout(new BorderLayout());
        add(titleBar, BorderLayout.NORTH);
        add(addDoctorPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setSize(800, 800);
        setLocationRelativeTo(parent);
    }
}

