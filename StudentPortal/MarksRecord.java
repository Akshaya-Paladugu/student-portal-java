public class MarksRecord {
    public String subject;
    public int mid1;
    public int mid2;
    public int assignments;
    public int total;

    public MarksRecord(String subject, int mid1, int mid2, int assignments) {
        this.subject = subject;
        this.mid1 = mid1;
        this.mid2 = mid2;
        this.assignments = assignments;
        this.total = mid1 + mid2 + assignments;
    }
}