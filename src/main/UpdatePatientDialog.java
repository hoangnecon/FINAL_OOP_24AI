package main;

import Listener.EditPatientListener;

import javax.swing.*;
import java.awt.*;

public class UpdatePatientDialog extends JDialog {
    private JTextField searchField;
    private JButton searchButton;

    public UpdatePatientDialog(Frame parent, String customID, String doctorName, String specialization) {
        super(parent, "Chỉnh sửa thông tin bệnh nhân", true);
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);

        // Tạo form tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout());

        JLabel searchLabel = new JLabel("Nhập Patient ID hoặc tên bệnh nhân:");
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.CENTER);

        // Thêm sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText();
            if (searchInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new EditPatientListener(this, searchInput, customID, doctorName, specialization).actionPerformed(e);
        });

        setVisible(true);
    }

    public String getSearchInput() {
        return searchField.getText();
    }
}
