package Features;

import javax.swing.*;
import java.awt.*;

public class ViewPatientForm extends JDialog {
    public ViewPatientForm(JDialog parentDialog, String patientName, String gender, String dateOfBirth, String phoneNumber, String address, String diseaseName, int patientID, String doctorName, String specialization) {
        super(parentDialog, "Thông tin bệnh nhân", true);
        setLayout(new GridLayout(8, 2));
        setSize(800, 600);


        // Hiển thị thông tin
        add(new JLabel("Tên bệnh nhân:"));
        add(new JLabel(patientName));
        add(new JLabel("Giới tính:"));
        add(new JLabel(gender));
        add(new JLabel("Ngày sinh:"));
        add(new JLabel(dateOfBirth));
        add(new JLabel("Số điện thoại:"));
        add(new JLabel(phoneNumber));
        add(new JLabel("Địa chỉ:"));
        add(new JLabel(address));
        add(new JLabel("Tên bệnh:"));
        add(new JLabel(diseaseName));
        add(new JLabel("Bác sĩ phụ trách:"));
        add(new JLabel(doctorName));

        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(e -> dispose());
        add(new JLabel());
        add(closeButton);
        setLocationRelativeTo(parentDialog);
        setVisible(true);
    }
}
