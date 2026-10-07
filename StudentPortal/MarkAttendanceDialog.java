import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkAttendanceDialog extends JDialog {

    private List<Student> students;
    private String subject;
    private Map<Student, JCheckBox> checkBoxMap;

    public MarkAttendanceDialog(JFrame owner, List<Student> students, String subject) {
        super(owner, "Mark Attendance – " + subject, true);
        this.students = students;
        this.subject = subject;
        this.checkBoxMap = new HashMap<>();

        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        for (Student s : students) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.add(new JLabel(s.rollNo + " – " + s.name));

            JCheckBox chkPresent = new JCheckBox("Present");
            // Default: check if last attendance shows they were present often? 
            // For simplicity, leave unchecked.
            checkBoxMap.put(s, chkPresent);

            row.add(chkPresent);
            centerPanel.add(row);
        }

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        bottomPanel.add(btnSave);
        bottomPanel.add(btnCancel);
        add(bottomPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(this::onSave);
        btnCancel.addActionListener(e -> dispose());
    }

    private void onSave(ActionEvent e) {
        // For each student, increment totalClasses, and presentClasses if checked
        for (Student s : students) {
            AttendanceRecord ar = findOrCreateAttendance(s, subject);
            ar.totalClasses++;
            JCheckBox chk = checkBoxMap.get(s);
            if (chk != null && chk.isSelected()) {
                ar.presentClasses++;
            }
        }
        JOptionPane.showMessageDialog(this, "Attendance marked successfully.");
        dispose();
    }

    private AttendanceRecord findOrCreateAttendance(Student s, String subject) {
        for (AttendanceRecord ar : s.attendanceList) {
            if (ar.subject.equals(subject)) return ar;
        }
        AttendanceRecord newAr = new AttendanceRecord(subject, 0, 0);
        s.attendanceList.add(newAr);
        return newAr;
    }
}