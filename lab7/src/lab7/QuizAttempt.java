package lab7;

import java.time.LocalDateTime;
import java.util.*;

public class QuizAttempt {
    private String attemptId;
    private String studentId;
    private String quizId;
    private String lessonId;
    private double score;
    private int attemptNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Map<String, Integer> studentAnswers; // questionId -> selectedOptionIndex
    private boolean passed;
    
    public QuizAttempt(String attemptId, String studentId, String quizId, String lessonId) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.attemptNumber = 1;
        this.startTime = LocalDateTime.now();
        this.studentAnswers = new HashMap<>();
        this.passed = false;
    }
    
    // Getters
    public String getAttemptId() { return attemptId; }
    public String getStudentId() { return studentId; }
    public String getQuizId() { return quizId; }
    public String getLessonId() { return lessonId; }
    public double getScore() { return score; }
    public int getAttemptNumber() { return attemptNumber; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Map<String, Integer> getStudentAnswers() { return new HashMap<>(studentAnswers); }
    public boolean isPassed() { return passed; }
    
    public void setAttemptNumber(int attemptNumber) { this.attemptNumber = attemptNumber; }
    
    public void submitAnswer(String questionId, int selectedOptionIndex) {
        studentAnswers.put(questionId, selectedOptionIndex);
    }
    
    public void calculateScore(Quiz quiz) {
        int totalPoints = 0;
        int earnedPoints = 0;
        
        for (Question question : quiz.getQuestions()) {
            totalPoints += question.getPoints();
            Integer studentAnswer = studentAnswers.get(question.getQuestionId());
            if (studentAnswer != null && question.isCorrectAnswer(studentAnswer)) {
                earnedPoints += question.getPoints();
            }
        }
        
        this.score = totalPoints > 0 ? (earnedPoints * 100.0) / totalPoints : 0;
        this.passed = quiz.isPassingScore(this.score);
        this.endTime = LocalDateTime.now();
    }
    
    public int getTimeTakenInMinutes() {
        if (endTime != null && startTime != null) {
            return (int) java.time.Duration.between(startTime, endTime).toMinutes();
        }
        return 0;
    }
}