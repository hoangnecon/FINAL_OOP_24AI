package Features;

import Apply2D.RoundedTextFieldPanel;

import javax.swing.*;
import java.awt.*;

public class AddDoctorPanel extends JPanel {
    private JTextField tfDoctorName, tfGender, tfDateOfBirth, tfPhoneNumber, tfAddress;

    public AddDoctorPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints po = new GridBagConstraints();

        po.fill = GridBagConstraints.HORIZONTAL;
        po.weightx = 1.0;

        JLabel title = new JLabel("Thêm Thông Tin Bác Sĩ");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        po.gridx = 0;
        po.gridy = 0;
        po.gridwidth = 2;
        po.insets = new Insets(0, 50, 50, 0);
        add(title, po);


        po.insets = new Insets(30, 10, 10, 10);
        po.gridwidth = 1;

        po.gridx = 0;
        po.gridy = 1;
        add(new JLabel("Họ và tên bác sĩ:"), po);
        tfDoctorName = new JTextField();
        tfDoctorName.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel patientNamePanel = new RoundedTextFieldPanel(20);
        patientNamePanel.add(tfDoctorName);
        tfDoctorName.setPreferredSize(new Dimension(300, 30));
        po.gridx = 1;
        add(patientNamePanel, po);

        po.gridx = 0;
        po.gridy = 2;
        add(new JLabel("Giới tính (Nam/Nữ):"), po);
        tfGender = new JTextField();
        tfGender.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel genderPanel = new RoundedTextFieldPanel(20);
        genderPanel.setPreferredSize(new Dimension(300,30));
        genderPanel.add(tfGender);
        tfGender.setPreferredSize(new Dimension(300, 30));
        po.gridx = 1;
        add(genderPanel, po);

        po.gridx = 0;
        po.gridy = 3;
        add(new JLabel("Ngày sinh (YYYY-MM-DD):"), po);
        tfDateOfBirth = new JTextField();
        tfDateOfBirth.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel dobPanel = new RoundedTextFieldPanel(20);
        dobPanel.setPreferredSize(new Dimension(300, 30));
        dobPanel.add(tfDateOfBirth);
        tfDateOfBirth.setPreferredSize(new Dimension(300, 30));
        po.gridx = 1;
        add(dobPanel, po);

        po.gridx = 0;
        po.gridy = 4;
        add(new JLabel("Số điện thoại:"), po);
        tfPhoneNumber = new JTextField();
        tfPhoneNumber.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel phoneNumberPanel = new RoundedTextFieldPanel(20);
        phoneNumberPanel.setPreferredSize(new Dimension(300,30));
        phoneNumberPanel.add(tfPhoneNumber);
        tfPhoneNumber.setPreferredSize(new Dimension(300, 30));
        po.gridx = 1;
        add(phoneNumberPanel, po);


        po.gridx = 0;
        po.gridy = 5;
        add(new JLabel("Địa chỉ:"), po);
        tfAddress = new JTextField();
        tfAddress.setBorder(BorderFactory.createEmptyBorder());
        RoundedTextFieldPanel addressPanel = new RoundedTextFieldPanel(20);
        addressPanel.setPreferredSize(new Dimension(300, 30));
        addressPanel.add(tfAddress);
        tfAddress.setPreferredSize(new Dimension(300, 30));
        po.gridx = 1;
        add(addressPanel, po);

        po.gridx = 0;
        po.gridy = 6;


    }

    // Getter cho các trường
    public String getPatientName() {
        return tfDoctorName.getText();
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


    public String getDoctorName() {
        return tfDoctorName.getText();
    }
}
