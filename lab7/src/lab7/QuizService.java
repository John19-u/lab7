
package lab7;

import java.util.*;

public class QuizService {
    private static QuizService instance;
    private Map<String, Quiz> quizzes;
    private Map<String, List<QuizAttempt>> quizAttempts;
    
    public QuizService() {
       this.quizzes = new HashMap<>();
        this.quizAttempts = new HashMap<>();
        System.out.println("DEBUG: QuizService singleton initialized");
    }
        public static QuizService getInstance() {
        if (instance == null) {
            instance = new QuizService();
        }
        return instance;
    }
       public static void resetInstance() {
        instance = null;
    }
    public void addQuiz(Quiz quiz) {
           quizzes.put(quiz.getQuizId(), quiz);
        quizAttempts.put(quiz.getQuizId(), new ArrayList<>());
        System.out.println("DEBUG: Quiz added - ID: " + quiz.getQuizId() + ", Title: " + quiz.getTitle());
        System.out.println("DEBUG: Total quizzes in service: " + quizzes.size());
    }
    
    public Quiz getQuiz(String quizId) {
         Quiz quiz = quizzes.get(quizId);
        System.out.println("DEBUG: Looking for quiz ID: " + quizId);
        System.out.println("DEBUG: Available quiz IDs: " + quizzes.keySet());
        return quiz;
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
        
        return true;
    }

    public Map<String, Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Map<String, Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Map<String, List<QuizAttempt>> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(Map<String, List<QuizAttempt>> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }
}