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

 
    public StudentManagement(String username, String role, String passwordHash, String userId, String email, 
                           ArrayList<String> enrolledCourses, ArrayList<String> progress,
                           Map<String, Certificate> certificates, Map<String, QuizResult> quizResults) {
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.userId = userId;
        this.email = email;
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.progress = progress != null ? progress : new ArrayList<>();
        this.certificates = certificates != null ? certificates : new HashMap<>();
        this.quizResults = quizResults != null ? quizResults : new HashMap<>(); 
    }

    public boolean hasPassedQuiz(String lessonId) {
        if (quizResults == null) {
            quizResults = new HashMap<>();
            return false;
        }
        QuizResult result = quizResults.get(lessonId);
        return result != null && result.isPassed();
    }

    public void addQuizResult(QuizResult result) {
        if (quizResults == null) {
            quizResults = new HashMap<>();
        }
        this.quizResults.put(result.getLessonId(), result);
    }

    public QuizResult getQuizResult(String lessonId) {
        if (quizResults == null) {
            quizResults = new HashMap<>();
            return null;
        }
        return quizResults.get(lessonId);
    }

    public Map<String, QuizResult> getQuizResults() {
        if (quizResults == null) {
            quizResults = new HashMap<>();
        }
        return new HashMap<>(quizResults);
    }
    
    public void setQuizResults(Map<String, QuizResult> quizResults) {
        this.quizResults = quizResults != null ? quizResults : new HashMap<>();
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
        if (certificates == null) {
            certificates = new HashMap<>();
        }
        return new HashMap<>(certificates);
    }
    
    public void addCertificate(Certificate certificate) {
        if (certificates == null) {
            certificates = new HashMap<>();
        }
        this.certificates.put(certificate.getCourseId(), certificate);
    }
    
    public Certificate getCertificateForCourse(String courseId) {
        if (certificates == null) {
            certificates = new HashMap<>();
            return null;
        }
        return certificates.get(courseId);
    }
    
    public boolean hasCertificateForCourse(String courseId) {
        if (certificates == null) {
            certificates = new HashMap<>();
            return false;
        }
        return certificates.containsKey(courseId);
    }

    public void enrollCourse(String courseId) {
        if (courseId != null && !courseId.trim().isEmpty() && !enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void unenrollCourse(String courseId) {
        enrolledCourses.remove(courseId);
        if (certificates != null) {
            certificates.remove(courseId); 
        }
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
        
        if (quizResults == null) {
            quizResults = new HashMap<>();
            return 0.0;
        }
        
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