
package Listener;
import ConnectData.UserLogin;
import main.HomePage_Doctor;
import main.Homepage_Manage;
import main.Homepage_Profile;
import main.SwitchCard;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginListener implements MouseListener {

    private static String loggedInUserId;
    private JTextField userIDField;
    private JPasswordField passwordField;
    private Connection connection;


    public LoginListener(JTextField userIDField, JPasswordField passwordField, Connection connection) {
        this.userIDField = userIDField;
        this.passwordField = passwordField;
        this.connection = connection;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            handleLogin();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // Phương thức xử lý đăng nhập
    private void handleLogin() throws SQLException {
        String userId = userIDField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        UserLogin loginHandler = new UserLogin(connection);
        String result = loginHandler.login(userId, password);

        if (result.startsWith("SUCCESS:")) {
            loggedInUserId = userId;
            String splitfullName = result.split(":")[1];
            String fullnameandrole = splitfullName;
            String fullnameonly = splitfullName.split("\n")[0];
            String role = splitfullName.split("\n")[1];
            JOptionPane.showMessageDialog(null, "Đăng nhập thành công!\nTên: " + fullnameonly + "\nVai trò:  " + role, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(SwitchCard::new);}
            else{
                JOptionPane.showMessageDialog(null, result.replace("ERROR:", ""), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } public static String getLoggedInUserId () {
        return loggedInUserId;
    }
    }

