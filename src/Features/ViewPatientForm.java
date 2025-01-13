package Features;

import javax.swing.*;
import java.awt.*;

public class ViewPatientForm extends JDialog {

    public ViewPatientForm(JDialog parentDialog, String patientName, String gender, String dateOfBirth, String phoneNumber, String address, String diseaseName) {
        super(parentDialog, "Thông tin bệnh nhân", true);
        setLayout(new GridLayout(7, 2));
        setSize(400, 300);

        addLabeledValue("Tên bệnh nhân:", patientName);
        addLabeledValue("Giới tính:", gender);
        addLabeledValue("Ngày sinh:", dateOfBirth);
        addLabeledValue("Số điện thoại:", phoneNumber);
        addLabeledValue("Địa chỉ:", address);
        addLabeledValue("Tên bệnh:", diseaseName);

        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        setLocationRelativeTo(parentDialog);
        setVisible(true);
    }

    private void addLabeledValue(String labelText, String value) {
        add(new JLabel(labelText));
        add(new JLabel(value));
    }
}