package lab7;

import java.util.*;

public class Question {
    private String questionId;
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private int points;
    private String type; // "multiple_choice", "true_false", etc.
    
    public Question(String questionId, String questionText, List<String> options, int correctAnswerIndex) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = new ArrayList<>(options);
        this.correctAnswerIndex = correctAnswerIndex;
        this.points = 1; // default 1 point per question
        this.type = "multiple_choice";
    }
    
    // Getters and setters
    public String getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return new ArrayList<>(options); }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    public int getPoints() { return points; }
    public String getType() { return type; }
    
    public void setPoints(int points) { this.points = points; }
    public void setType(String type) { this.type = type; }
    
    public boolean isCorrectAnswer(int selectedIndex) {
        return selectedIndex == correctAnswerIndex;
    }
    
    public String getCorrectAnswerText() {
        if (correctAnswerIndex >= 0 && correctAnswerIndex < options.size()) {
            return options.get(correctAnswerIndex);
        }
        return "";
    }
 
}