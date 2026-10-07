import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterMarksDialog extends JDialog {

    private List<Student> students;
    private String subject;

    private Map<Student, JTextField> txtMid1Map;
    private Map<Student, JTextField> txtMid2Map;
    private Map<Student, JTextField> txtAssignmentsMap;

    public EnterMarksDialog(JFrame owner, List<Student> students, String subject) {
        super(owner, "Enter Internal Marks – " + subject, true);
        this.students = students;
        this.subject = subject;

        txtMid1Map = new HashMap<>();
        txtMid2Map = new HashMap<>();
        txtAssignmentsMap = new HashMap<>();

        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        for (Student s : students) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.add(new JLabel(s.rollNo + " – " + s.name + "   "));

            JTextField txtMid1 = new JTextField(4);
            JTextField txtMid2 = new JTextField(4);
            JTextField txtAssignments = new JTextField(4);

            // Pre-fill with existing marks if present
            MarksRecord mr = findMarks(s, subject);
            if (mr != null) {
                txtMid1.setText(String.valueOf(mr.mid1));
                txtMid2.setText(String.valueOf(mr.mid2));
                txtAssignments.setText(String.valueOf(mr.assignments));
            } else {
                txtMid1.setText("0");
                txtMid2.setText("0");
                txtAssignments.setText("0");
            }

            txtMid1Map.put(s, txtMid1);
            txtMid2Map.put(s, txtMid2);
            txtAssignmentsMap.put(s, txtAssignments);

            row.add(new JLabel("Mid1:"));
            row.add(txtMid1);
            row.add(new JLabel("Mid2:"));
            row.add(txtMid2);
            row.add(new JLabel("Assign:"));
            row.add(txtAssignments);

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
        try {
            for (Student s : students) {
                int mid1 = Integer.parseInt(txtMid1Map.get(s).getText().trim());
                int mid2 = Integer.parseInt(txtMid2Map.get(s).getText().trim());
                int assignments = Integer.parseInt(txtAssignmentsMap.get(s).getText().trim());

                MarksRecord mr = findOrCreateMarks(s, subject);
                mr.mid1 = mid1;
                mr.mid2 = mid2;
                mr.assignments = assignments;
                mr.total = mid1 + mid2 + assignments;
            }
            JOptionPane.showMessageDialog(this, "Marks updated successfully.");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for marks.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private MarksRecord findMarks(Student s, String subject) {
        for (MarksRecord mr : s.marksList) {
            if (mr.subject.equals(subject)) return mr;
        }
        return null;
    }

    private MarksRecord findOrCreateMarks(Student s, String subject) {
        MarksRecord mr = findMarks(s, subject);
        if (mr != null) return mr;
        MarksRecord newMr = new MarksRecord(subject, 0, 0, 0);
        s.marksList.add(newMr);
        return newMr;
    }
}