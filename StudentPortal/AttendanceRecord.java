public class AttendanceRecord {
    public String subject;
    public int totalClasses;
    public int presentClasses;

    public AttendanceRecord(String subject, int totalClasses, int presentClasses) {
        this.subject = subject;
        this.totalClasses = totalClasses;
        this.presentClasses = presentClasses;
    }

    public double getPercentage() {
        if (totalClasses == 0) return 0.0;
        return (presentClasses * 100.0) / totalClasses;
    }
}