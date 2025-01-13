package main;


import Features.AddPatientPanel;
import Listener.AddPatientListener;

import javax.swing.*;
import java.awt.*;

public class AddPatientDialog extends JDialog {
    private AddPatientPanel addPatientPanel;
    private JButton submitButton;

    public AddPatientDialog(SwitchCard parent, String customID, String doctorName, String specialization) {
        super(parent,"Thêm bệnh nhân", true);

        addPatientPanel = new AddPatientPanel();
        submitButton = new JButton("Thêm bệnh nhân");

        AddPatientListener listener = new AddPatientListener(addPatientPanel, customID, doctorName, specialization);
        submitButton.addActionListener(listener);

        JPanel titlebar = new JPanel(new GridBagLayout());
        GridBagConstraints logo = new GridBagConstraints();
        titlebar.setPreferredSize(new Dimension(800, 102));
        titlebar.setBackground(new Color(0x38C1CE));
        logo.weightx=1;
        logo.weighty=0;
        logo.insets= new Insets(0,100,0,0);
        JLabel logohos = new JLabel(new ImageIcon("src/imagesource/Group 1.png"));
        logohos.setPreferredSize(new Dimension(353, 70));

        titlebar.add(logohos,logo);


        // Thiết lập bố cục
        setLayout(new BorderLayout());
        add(titlebar,BorderLayout.NORTH);
        add(addPatientPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);


        setSize(800, 800);
        setLocationRelativeTo(parent);
    }

}
