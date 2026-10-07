import javax.swing.*;

public class PortalApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new LoginFrame();
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setLocationRelativeTo(null); // center
            loginFrame.setVisible(true);
        });
    }
}