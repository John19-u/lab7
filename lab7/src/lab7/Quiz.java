package lab7;

import java.util.*;

public class Quiz {
    private String quizId;
    private String lessonId;
    private String title;
    private List<Question> questions;
    private int passingScore;
    private int maxAttempts;
    private int timeLimit; // in minutes
    
    public Quiz(String quizId, String lessonId, String title) {
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.title = title;
        this.questions = new ArrayList<>();
        this.passingScore = 70; // default 70%
        this.maxAttempts = 3; // default 3 attempts
        this.timeLimit = 30; // default 30 minutes
    }
    
    // Getters and setters
    public String getQuizId() { return quizId; }
    public String getLessonId() { return lessonId; }
    public String getTitle() { return title; }
    public List<Question> getQuestions() { return questions; }
    public int getPassingScore() { return passingScore; }
    public int getMaxAttempts() { return maxAttempts; }
    public int getTimeLimit() { return timeLimit; }
    
    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }
    public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    
    public void addQuestion(Question question) {
        questions.add(question);
    }
    
    public void removeQuestion(String questionId) {
        questions.removeIf(q -> q.getQuestionId().equals(questionId));
    }
    
    public int getTotalPoints() {
        return questions.stream().mapToInt(Question::getPoints).sum();
    }
    
    public boolean isPassingScore(double score) {
        return score >= passingScore;
    }
    
    
}