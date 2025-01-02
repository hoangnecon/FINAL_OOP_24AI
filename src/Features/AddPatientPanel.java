package Features;

import javax.swing.*;
import java.awt.*;


public class AddPatientPanel extends JPanel {
    private JTextField tfPatientName, tfGender, tfDateOfBirth, tfPhoneNumber, tfAddress, tfDiseaseName;

    public AddPatientPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Họ và tên bệnh nhân:"));
        tfPatientName = new JTextField();
        add(tfPatientName);

        add(new JLabel("Giới tính (Nam/Nữ):"));
        tfGender = new JTextField();
        add(tfGender);

        add(new JLabel("Ngày sinh (YYYY-MM-DD):"));
        tfDateOfBirth = new JTextField();
        add(tfDateOfBirth);

        add(new JLabel("Số điện thoại:"));
        tfPhoneNumber = new JTextField();
        add(tfPhoneNumber);

        add(new JLabel("Địa chỉ:"));
        tfAddress = new JTextField();
        add(tfAddress);

        add(new JLabel("Tên bệnh:"));
        tfDiseaseName = new JTextField();
        add(tfDiseaseName);
    }

    public String getPatientName() {
        return tfPatientName.getText();
    }

    public String getGender() {
        return tfGender.getText();
    }

    public String getDateOfBirth() {
        return tfDateOfBirth.getText();
    }

    public String getPhoneNumber() {
        return tfPhoneNumber.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public String getDiseaseName() {
        return tfDiseaseName.getText();
    }
}
