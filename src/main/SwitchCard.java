package main;

import javax.swing.*;
import java.awt.*;

public class SwitchCard extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;
    public SwitchCard() {
      container = new JPanel(new BorderLayout());


      Homepage_Profile profile = new Homepage_Profile(this);
      HomePage_Doctor doctor = new HomePage_Doctor(this);
      Homepage_Manage manage = new Homepage_Manage(this);

        showPanel(profile);

        this.add(container);
        this.setSize(1440, 1024);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setVisible(true);

    }
    public void showPanel(JPanel panel) {
        container.removeAll();
        container.add(panel, BorderLayout.CENTER);
        container.revalidate();
        container.repaint();
    }

}
