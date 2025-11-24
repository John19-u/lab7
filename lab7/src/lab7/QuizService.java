
package lab7;

import java.util.*;

public class QuizService {
    private Map<String, Quiz> quizzes;
    private Map<String, List<QuizAttempt>> quizAttempts;
    
    public QuizService() {
        this.quizzes = new HashMap<>();
        this.quizAttempts = new HashMap<>();
    }
    
    public void addQuiz(Quiz quiz) {
        quizzes.put(quiz.getQuizId(), quiz);
        quizAttempts.put(quiz.getQuizId(), new ArrayList<>());
    }
    
    public Quiz getQuiz(String quizId) {
        return quizzes.get(quizId);
    }
    
    public Quiz getQuizByLessonId(String lessonId) {
        return quizzes.values().stream()
                .filter(quiz -> lessonId.equals(quiz.getLessonId()))
                .findFirst()
                .orElse(null);
    }
    
    public QuizAttempt startQuizAttempt(String studentId, String quizId) {
        String attemptId = UUID.randomUUID().toString();
        Quiz quiz = quizzes.get(quizId);
        QuizAttempt attempt = new QuizAttempt(attemptId, studentId, quizId, quiz.getLessonId());
        
        // Set attempt number based on previous attempts
        List<QuizAttempt> attempts = getQuizAttempts(studentId, quizId);
        attempt.setAttemptNumber(attempts.size() + 1);
        
        quizAttempts.get(quizId).add(attempt);
        return attempt;
    }
    
    public List<QuizAttempt> getQuizAttempts(String studentId, String quizId) {
        List<QuizAttempt> attempts = quizAttempts.get(quizId);
        if (attempts != null) {
            return attempts.stream()
                    .filter(attempt -> studentId.equals(attempt.getStudentId()))
                    .toList();
        }
        return new ArrayList<>();
    }
    
    public boolean canRetryQuiz(String studentId, String quizId) {
        Quiz quiz = quizzes.get(quizId);
        List<QuizAttempt> attempts = getQuizAttempts(studentId, quizId);
        
        if (attempts.isEmpty()) return true;
        
        QuizAttempt lastAttempt = attempts.get(attempts.size() - 1);
        return attempts.size() < quiz.getMaxAttempts() && !lastAttempt.isPassed();
    }
    
    public boolean isLessonAccessible(String studentId, String lessonId, StudentManagement student) {
        // Check if student has passed the previous lesson's quiz
        // This implements the "prevent access to next lessons" feature
        // You'll need to implement the logic based on your course structure
        
        // For now, return true - you can enhance this later
        return true;
    }
}