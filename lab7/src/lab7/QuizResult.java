package lab7;

public class QuizResult {
    private String studentId;
    private String lessonId;
    private String courseId;
    private double score;
    private String timestamp;
    private int timeTaken;
    private int totalQuestions;
    private int correctAnswers;
    
    public QuizResult(String studentId, String lessonId, String courseId, double score) {
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.score = score;
        this.timestamp = java.time.LocalDateTime.now().toString();
        this.totalQuestions = 10;
        this.correctAnswers = (int) Math.round((score / 100.0) * totalQuestions);
    }
    
    public String getStudentId() { return studentId; }
    public String getLessonId() { return lessonId; }
    public String getCourseId() { return courseId; }
    public double getScore() { return score; }
    public String getTimestamp() { return timestamp; }
    public int getTimeTaken() { return timeTaken; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    
    public void setTimeTaken(int timeTaken) { this.timeTaken = timeTaken; }
    public void setTotalQuestions(int totalQuestions) { 
        this.totalQuestions = totalQuestions; 
        this.correctAnswers = (int) Math.round((score / 100.0) * totalQuestions);
    }
    
    public boolean isPassed() {
        return score >= 70.0;
    }
    
    @Override
    public String toString() {
        return String.format("QuizResult{score=%.1f%%, passed=%s, questions=%d/%d}", 
                           score, isPassed(), correctAnswers, totalQuestions);
    }
}