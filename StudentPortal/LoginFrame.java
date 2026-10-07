import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField txtUserId;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMessage;

    public LoginFrame() {
        setTitle("Student Portal – Login");
        setSize(350, 220);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("User ID (Roll / Faculty ID):"));
        txtUserId = new JTextField();
        add(txtUserId);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Login");
        add(btnLogin);

        lblMessage = new JLabel("");
        lblMessage.setForeground(Color.RED);
        add(lblMessage);

        btnLogin.addActionListener(this::onLogin);
    }

    private void onLogin(ActionEvent e) {
        String userId = txtUserId.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (userId.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter User ID and Password.");
            return;
        }

        // Try student login
        Student student = DataLoader.validateStudent(userId, password);
        if (student != null) {
            dispose();
            JFrame studentDash = new StudentDashboard(student);
            studentDash.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            studentDash.setLocationRelativeTo(null);
            studentDash.setVisible(true);
            return;
        }

        // Try faculty login
        if (DataLoader.validateFaculty(userId, password)) {
            dispose();
            JFrame facultyDash = new FacultyDashboard();
            facultyDash.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            facultyDash.setLocationRelativeTo(null);
            facultyDash.setVisible(true);
            return;
        }

        lblMessage.setText("Invalid User ID or Password.");
    }
}