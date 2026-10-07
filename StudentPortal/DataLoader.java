import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();

        // Sample students – edit with your class details
        Student s1 = new Student("2301", "Arun Kumar", "CSE", "s123");
        Student s2 = new Student("2302", "Bala Devi", "CSE", "s123");
        Student s3 = new Student("2303", "Chandu Reddy", "CSE", "s123");

        // Sample attendance
        s1.attendanceList.add(new AttendanceRecord("JAVA", 20, 16));
        s1.attendanceList.add(new AttendanceRecord("DBMS", 18, 15));
        s1.attendanceList.add(new AttendanceRecord("MATHS", 22, 18));

        s2.attendanceList.add(new AttendanceRecord("JAVA", 20, 14));
        s2.attendanceList.add(new AttendanceRecord("DBMS", 18, 12));
        s2.attendanceList.add(new AttendanceRecord("MATHS", 22, 20));

        s3.attendanceList.add(new AttendanceRecord("JAVA", 20, 18));
        s3.attendanceList.add(new AttendanceRecord("DBMS", 18, 16));
        s3.attendanceList.add(new AttendanceRecord("MATHS", 22, 19));

        // Sample marks
        s1.marksList.add(new MarksRecord("JAVA", 25, 27, 18));
        s1.marksList.add(new MarksRecord("DBMS", 22, 24, 16));
        s1.marksList.add(new MarksRecord("MATHS", 20, 22, 14));

        s2.marksList.add(new MarksRecord("JAVA", 20, 22, 15));
        s2.marksList.add(new MarksRecord("DBMS", 18, 20, 14));
        s2.marksList.add(new MarksRecord("MATHS", 16, 18, 12));

        s3.marksList.add(new MarksRecord("JAVA", 28, 29, 19));
        s3.marksList.add(new MarksRecord("DBMS", 26, 27, 18));
        s3.marksList.add(new MarksRecord("MATHS", 24, 26, 17));

        students.add(s1);
        students.add(s2);
        students.add(s3);

        // Load from files if they exist (will be created after first save)
        try {
            FileHandler.loadAttendance(students);
            FileHandler.loadMarks(students);
        } catch (Exception e) {
            // If files don't exist or error, ignore and use in-memory sample data
        }

        return students;
    }

    public static Student validateStudent(String rollNo, String password) {
        for (Student s : loadStudents()) {
            if (s.rollNo.equals(rollNo) && s.password.equals(password)) {
                return s;
            }
        }
        return null;
    }

    public static boolean validateFaculty(String userId, String password) {
        // Simple hardcoded faculty login
        return "f001".equals(userId) && "f123".equals(password);
    }
}