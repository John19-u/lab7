package lab7;

import java.util.UUID;

public class Certificate {
    private String certificateId;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseTitle;
    private String issueDate; // Changed from LocalDate to String
    private String instructorName;
    private double finalScore;
    
    public Certificate(String studentId, String studentName, String courseId, 
                      String courseTitle, String instructorName, double finalScore) {
        this.certificateId = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;
        this.finalScore = finalScore;
        this.issueDate = java.time.LocalDate.now().toString(); // Store as String
    }
    
    // Getters
    public String getCertificateId() { return certificateId; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }
    public String getIssueDate() { return issueDate; } // Return String
    public String getInstructorName() { return instructorName; }
    public double getFinalScore() { return finalScore; }
    
    // JSON representation for storage
    public String toJson() {
        return String.format(
            "{\"certificateId\":\"%s\",\"studentId\":\"%s\",\"studentName\":\"%s\"," +
            "\"courseId\":\"%s\",\"courseTitle\":\"%s\",\"instructorName\":\"%s\"," +
            "\"issueDate\":\"%s\",\"finalScore\":%.2f}",
            certificateId, studentId, studentName, courseId, courseTitle, 
            instructorName, issueDate, finalScore
        );
    }
    
    @Override
    public String toString() {
        return String.format(
            "Certificate of Completion\n" +
            "This certifies that %s\n" +
            "has successfully completed the course\n" +
            "%s\n" +
            "with a final score of %.1f%%\n" +
            "Instructor: %s\n" +
            "Issued on: %s\n" +
            "Certificate ID: %s",
            studentName, courseTitle, finalScore, instructorName, 
            issueDate, certificateId
        );
    }
}