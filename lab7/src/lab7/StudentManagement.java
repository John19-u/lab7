package lab7;

import java.util.ArrayList;

public class StudentManagement {

    private String username;
    private String role;
    private String password;
    private String userid;
    private String email;
    ArrayList<String> enrolledCourse;
    ArrayList<String> progress;

    public StudentManagement(String username, String role, String password, String userid, String email, ArrayList<String> enrolledCourse, ArrayList<String> progress) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.userid = userid;
        this.email = email;
        this.enrolledCourse = enrolledCourse;
        this.progress = progress;
    }

    public StudentManagement(String username, String role, String password, String userid, String email) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.userid = userid;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getEnrolledCourse() {
        return enrolledCourse;
    }

    public void setEnrolledCourse(ArrayList<String> enrolledCourse) {
        this.enrolledCourse = enrolledCourse;
    }

    public ArrayList<String> getProgress() {
        return progress;
    }

    public void setProgress(ArrayList<String> progress) {
        this.progress = progress;
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourse;
    }

    public void enrollCourse(String courseId) {
        if (!enrolledCourse.contains(courseId)) {
            enrolledCourse.add(courseId);
        }
    }

    public void markLessonCompleted(String lessonId) {
        enrolledCourse.add(lessonId);
    }
}
