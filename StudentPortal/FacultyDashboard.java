import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FacultyDashboard extends JFrame {

    private List<Student> students;
    private JComboBox<String> comboSubjects;

    public FacultyDashboard() {
        students = DataLoader.loadStudents();
        setTitle("Faculty Portal");
        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top: title + subject
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Faculty Portal – Mark Attendance & Internal Marks");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(lblTitle);

        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Subject:"));
        String[] subjects = {"JAVA", "DBMS", "MATHS"};
        comboSubjects = new JComboBox<>(subjects);
        topPanel.add(comboSubjects);

        add(topPanel, BorderLayout.NORTH);

        // Center: student list (simple labels)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Students"));

        for (Student s : students) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.add(new JLabel(s.rollNo + " – " + s.name + " (" + s.branch + ")"));
            centerPanel.add(row);
        }

        add(centerPanel, BorderLayout.CENTER);

        // Bottom: buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAttendance = new JButton("Mark Attendance");
        JButton btnMarks = new JButton("Enter Marks");
        JButton btnLogout = new JButton("Logout");

        bottomPanel.add(btnAttendance);
        bottomPanel.add(btnMarks);
        bottomPanel.add(btnLogout);

        add(bottomPanel, BorderLayout.SOUTH);

        btnAttendance.addActionListener(this::onMarkAttendance);
        btnMarks.addActionListener(this::onEnterMarks);
        btnLogout.addActionListener(this::onLogout);
    }

    private void onMarkAttendance(ActionEvent e) {
        String subject = (String) comboSubjects.getSelectedItem();
        JDialog dialog = new MarkAttendanceDialog(this, students, subject);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // After dialog closes, save attendance
        try {
            FileHandler.saveAttendance(students);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not save attendance: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEnterMarks(ActionEvent e) {
        String subject = (String) comboSubjects.getSelectedItem();
        JDialog dialog = new EnterMarksDialog(this, students, subject);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        try {
            FileHandler.saveMarks(students);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not save marks: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLogout(ActionEvent e) {
        dispose();
        JFrame login = new LoginFrame();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }
}