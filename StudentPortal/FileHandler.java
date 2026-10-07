import java.io.*;
import java.util.List;

public class FileHandler {

    public static void saveAttendance(List<Student> students) throws IOException {
        FileWriter fw = new FileWriter("attendance.csv");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("rollNo,subject,totalClasses,presentClasses");
        bw.newLine();

        for (Student s : students) {
            for (AttendanceRecord ar : s.attendanceList) {
                bw.write(s.rollNo + "," + ar.subject + "," + ar.totalClasses + "," + ar.presentClasses);
                bw.newLine();
            }
        }
        bw.close();
        fw.close();
    }

    public static void loadAttendance(List<Student> students) throws IOException {
        File f = new File("attendance.csv");
        if (!f.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length < 4) continue;

            String rollNo = parts[0];
            String subject = parts[1];
            int total = Integer.parseInt(parts[2]);
            int present = Integer.parseInt(parts[3]);

            Student s = findStudentByRoll(students, rollNo);
            if (s != null) {
                // Avoid duplicates on repeated loads (simple check)
                boolean exists = false;
                for (AttendanceRecord ar : s.attendanceList) {
                    if (ar.subject.equals(subject)) {
                        exists = true;
                        ar.totalClasses = total;
                        ar.presentClasses = present;
                        break;
                    }
                }
                if (!exists) {
                    s.attendanceList.add(new AttendanceRecord(subject, total, present));
                }
            }
        }
        br.close();
    }

    public static void saveMarks(List<Student> students) throws IOException {
        FileWriter fw = new FileWriter("marks.csv");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("rollNo,subject,mid1,mid2,assignments,total");
        bw.newLine();

        for (Student s : students) {
            for (MarksRecord mr : s.marksList) {
                bw.write(s.rollNo + "," + mr.subject + "," + mr.mid1 + "," + mr.mid2 + "," + mr.assignments + "," + mr.total);
                bw.newLine();
            }
        }
        bw.close();
        fw.close();
    }

    public static void loadMarks(List<Student> students) throws IOException {
        File f = new File("marks.csv");
        if (!f.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length < 6) continue;

            String rollNo = parts[0];
            String subject = parts[1];
            int mid1 = Integer.parseInt(parts[2]);
            int mid2 = Integer.parseInt(parts[3]);
            int assignments = Integer.parseInt(parts[4]);

            Student s = findStudentByRoll(students, rollNo);
            if (s != null) {
                boolean exists = false;
                for (MarksRecord mr : s.marksList) {
                    if (mr.subject.equals(subject)) {
                        exists = true;
                        mr.mid1 = mid1;
                        mr.mid2 = mid2;
                        mr.assignments = assignments;
                        mr.total = mid1 + mid2 + assignments;
                        break;
                    }
                }
                if (!exists) {
                    s.marksList.add(new MarksRecord(subject, mid1, mid2, assignments));
                }
            }
        }
        br.close();
    }

    private static Student findStudentByRoll(List<Student> students, String rollNo) {
        for (Student s : students) {
            if (s.rollNo.equals(rollNo)) return s;
        }
        return null;
    }
}