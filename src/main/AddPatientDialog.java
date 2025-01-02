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

        // Cấu hình giao diện
        addPatientPanel = new AddPatientPanel();
        submitButton = new JButton("Thêm bệnh nhân");

        // Gắn listener xử lý thêm bệnh nhân
        AddPatientListener listener = new AddPatientListener(addPatientPanel, customID, doctorName, specialization);
        submitButton.addActionListener(listener);

        // Thiết lập bố cục
        setLayout(new BorderLayout());
        add(addPatientPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
}
