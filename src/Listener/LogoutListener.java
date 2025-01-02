package Listener;

import main.LoginMain;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class LogoutListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        LoginMain loginPage = new LoginMain();
        loginPage.setVisible(true);
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(e.getComponent());
        currentFrame.dispose();
    }
}