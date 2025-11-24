package lab7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentManagement {
    private String username;
    private String role;
    private String passwordHash; 
    private String userId; 
    private String email;
    private ArrayList<String> enrolledCourses; 
    private ArrayList<String> progress; 
    private Map<String, Certificate> certificates; 
    private Map<String, QuizResult> quizResults; 

    public StudentManagement(String username, String role, String passwordHash, String userId, String email) {
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.userId = userId;
        this.email = email;
        this.enrolledCourses = new ArrayList<>();
        this.progress = new ArrayList<>();
        this.certificates = new HashMap<>();
        this.quizResults = new HashMap<>();
    }

    public StudentManagement(String username, String role, String passwordHash, String userId, String email, 
                           ArrayList<String> enrolledCourses, ArrayList<String> progress) {
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.userId = userId;
        this.email = email;
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.progress = progress != null ? progress : new ArrayList<>();
        this.certificates = new HashMap<>();
        this.quizResults = new HashMap<>();
    }

    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public ArrayList<String> getEnrolledCourses() { 
        return new ArrayList<>(enrolledCourses); 
    }
    
    public void setEnrolledCourses(ArrayList<String> enrolledCourses) { 
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
    }

    public ArrayList<String> getProgress() { 
        return new ArrayList<>(progress); 
    }
    
    public void setProgress(ArrayList<String> progress) { 
        this.progress = progress != null ? progress : new ArrayList<>();
    }

    
    public Map<String, Certificate> getCertificates() {
        return new HashMap<>(certificates);
    }
    
    public void addCertificate(Certificate certificate) {
        this.certificates.put(certificate.getCourseId(), certificate);
    }
    
    public Certificate getCertificateForCourse(String courseId) {
        return certificates.get(courseId);
    }
    
    public boolean hasCertificateForCourse(String courseId) {
        return certificates.containsKey(courseId);
    }
    
    public void addQuizResult(QuizResult result) {
        this.quizResults.put(result.getLessonId(), result);
    }
    
    public QuizResult getQuizResult(String lessonId) {
        return quizResults.get(lessonId);
    }
    
    public boolean hasPassedQuiz(String lessonId) {
        QuizResult result = quizResults.get(lessonId);
        return result != null && result.isPassed();
    }

    public void enrollCourse(String courseId) {
        if (courseId != null && !courseId.trim().isEmpty() && !enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void unenrollCourse(String courseId) {
        enrolledCourses.remove(courseId);
        certificates.remove(courseId); // Remove certificate if unenrolled
    }

    public void markLessonCompleted(String lessonId) {
        if (lessonId != null && !lessonId.trim().isEmpty() && !progress.contains(lessonId)) {
            progress.add(lessonId);
        }
    }

    public boolean hasCompletedLesson(String lessonId) {
        return progress.contains(lessonId);
    }

    public boolean isEnrolledInCourse(String courseId) {
        return enrolledCourses.contains(courseId);
    }
    
    
    public boolean hasCompletedAllLessons(CourseManagement course) {
        if (course == null) return false;
        
        for (Lesson lesson : course.getLessons()) {
            if (!hasCompletedLesson(lesson.getLessonId())) {
                return false;
            }
        }
        return true;
    }
    
   
    public boolean hasPassedAllQuizzes(CourseManagement course) {
        if (course == null) return false;
        
        for (Lesson lesson : course.getLessons()) {
            if (!hasPassedQuiz(lesson.getLessonId())) {
                return false;
            }
        }
        return true;
    }
    
    public double getAverageQuizScore(CourseManagement course) {
        if (course == null || course.getLessons().isEmpty()) return 0.0;
        
        double totalScore = 0.0;
        int quizCount = 0;
        
        for (Lesson lesson : course.getLessons()) {
            QuizResult result = getQuizResult(lesson.getLessonId());
            if (result != null && result.isPassed()) {
                totalScore += result.getScore();
                quizCount++;
            }
        }
        
        return quizCount > 0 ? totalScore / quizCount : 0.0;
    }
  
}