import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class StudentDashboard extends JFrame {

    private Student student;
    private JComboBox<String> comboSubjects;
    private JLabel lblTotal, lblPresent, lblPercentage;
    private JLabel lblMid1, lblMid2, lblAssignments, lblTotalMarks;

    public StudentDashboard(Student student) {
        this.student = student;
        setTitle("Student Dashboard – " + student.name + " (" + student.rollNo + ")");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top panel: welcome
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblWelcome = new JLabel("Welcome, " + student.name + " (" + student.rollNo + ")");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(lblWelcome);
        add(topPanel, BorderLayout.NORTH);

        // Center: subject selection + details
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subjectPanel.add(new JLabel("Select Subject:"));
        String[] subjects = {"JAVA", "DBMS", "MATHS"};
        comboSubjects = new JComboBox<>(subjects);
        subjectPanel.add(comboSubjects);
        centerPanel.add(subjectPanel);

        // Attendance panel
        JPanel attendancePanel = new JPanel(new GridLayout(4, 2, 10, 10));
        attendancePanel.setBorder(BorderFactory.createTitledBorder("Attendance"));

        attendancePanel.add(new JLabel("Total Classes:"));
        lblTotal = new JLabel("");
        attendancePanel.add(lblTotal);

        attendancePanel.add(new JLabel("Present:"));
        lblPresent = new JLabel("");
        attendancePanel.add(lblPresent);

        attendancePanel.add(new JLabel("Percentage:"));
        lblPercentage = new JLabel("");
        attendancePanel.add(lblPercentage);

        centerPanel.add(attendancePanel);

        // Marks panel
        JPanel marksPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        marksPanel.setBorder(BorderFactory.createTitledBorder("Internal Marks"));

        marksPanel.add(new JLabel("Mid-1:"));
        lblMid1 = new JLabel("");
        marksPanel.add(lblMid1);

        marksPanel.add(new JLabel("Mid-2:"));
        lblMid2 = new JLabel("");
        marksPanel.add(lblMid2);

        marksPanel.add(new JLabel("Assignments:"));
        lblAssignments = new JLabel("");
        marksPanel.add(lblAssignments);

        marksPanel.add(new JLabel("Total:"));
        lblTotalMarks = new JLabel("");
        marksPanel.add(lblTotalMarks);

        centerPanel.add(marksPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom: logout
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        bottomPanel.add(btnLogout);
        add(bottomPanel, BorderLayout.SOUTH);

        btnLogout.addActionListener(e -> {
            dispose();
            JFrame login = new LoginFrame();
            login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });

        comboSubjects.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateDashboard();
            }
        });

        // Initial load
        updateDashboard();
    }

    private void updateDashboard() {
        String subject = (String) comboSubjects.getSelectedItem();
        if (subject == null) return;

        AttendanceRecord ar = findAttendance(subject);
        MarksRecord mr = findMarks(subject);

        if (ar != null) {
            lblTotal.setText(String.valueOf(ar.totalClasses));
            lblPresent.setText(String.valueOf(ar.presentClasses));
            lblPercentage.setText(String.format("%.2f %%", ar.getPercentage()));

            // Optional: highlight low attendance
            if (ar.getPercentage() < 75.0) {
                lblPercentage.setForeground(Color.RED);
            } else {
                lblPercentage.setForeground(Color.BLACK);
            }
        } else {
            lblTotal.setText("0");
            lblPresent.setText("0");
            lblPercentage.setText("0.00 %");
        }

        if (mr != null) {
            lblMid1.setText(String.valueOf(mr.mid1));
            lblMid2.setText(String.valueOf(mr.mid2));
            lblAssignments.setText(String.valueOf(mr.assignments));
            lblTotalMarks.setText(String.valueOf(mr.total));
        } else {
            lblMid1.setText("0");
            lblMid2.setText("0");
            lblAssignments.setText("0");
            lblTotalMarks.setText("0");
        }
    }

    private AttendanceRecord findAttendance(String subject) {
        for (AttendanceRecord ar : student.attendanceList) {
            if (ar.subject.equals(subject)) return ar;
        }
        return null;
    }

    private MarksRecord findMarks(String subject) {
        for (MarksRecord mr : student.marksList) {
            if (mr.subject.equals(subject)) return mr;
        }
        return null;
    }
}