package lab7;

import java.util.ArrayList;

public class StudentManagement {
    private String username;
    private String role;
    private String passwordHash; 
    private String userId; 
    private String email;
    private ArrayList<String> enrolledCourses; 
    private ArrayList<String> progress; 

    public StudentManagement(String username, String role, String passwordHash, String userId, String email) {
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.userId = userId;
        this.email = email;
        this.enrolledCourses = new ArrayList<>();
        this.progress = new ArrayList<>();
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


    public void enrollCourse(String courseId) {
        if (courseId != null && !courseId.trim().isEmpty() && !enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void unenrollCourse(String courseId) {
        enrolledCourses.remove(courseId);
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
}