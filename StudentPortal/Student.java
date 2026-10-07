import java.util.ArrayList;
import java.util.List;

public class Student {
    public String rollNo;
    public String name;
    public String branch;
    public String password;

    public List<AttendanceRecord> attendanceList;
    public List<MarksRecord> marksList;

    public Student(String rollNo, String name, String branch, String password) {
        this.rollNo = rollNo;
        this.name = name;
        this.branch = branch;
        this.password = password;
        this.attendanceList = new ArrayList<>();
        this.marksList = new ArrayList<>();
    }
}