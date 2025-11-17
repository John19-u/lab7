
package lab7;

import java.util.ArrayList;
import java.util.Date;

public class Instructor {
    
    private String userID;
    private String role;
    private String username;
    private String email;
    private String passwordHash;
    private ArrayList<CourseManagement> createdCourses;
    private CourseDatabase database;

    public Instructor(String userID, String role, String username, String email, String passwordHash) {
        this.userID = userID;
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Instructor(String userID, String role, String username, String email, String passwordHash, ArrayList<CourseManagement> createdCourses, CourseDatabase database) {
        this.userID = userID;
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdCourses = createdCourses;
        this.database = database;
    }

    
    
    
    public void createCourse(CourseManagement course){
        createdCourses.add(course);
        database.addCourse(course);
        
    }
    public void editCourse(String courseID){
        for(CourseManagement course : createdCourses){
            if(course.getCourseId().equals(courseID)){
                database.editCourse(courseID, course);
                break;
            }
        }
    }
    public void addLesson(Lesson lesson, String courseID){
         for (CourseManagement course : createdCourses) {
        if (course.getCourseId().equals(courseID)) {
            course.addLesson(lesson);
            database.editCourse(courseID, course);
            break;
        }
    }
    }
    public void deleteLesson(String lessonID, String courseID){
        for(CourseManagement course : createdCourses){
            if(course.getCourseId().equals(courseID)){
                ArrayList<Lesson> lessons = course.getLessons();
                for (int i = 0; i < lessons.size(); i++) {
                if (lessons.get(i).getLessonId().equals(lessonID)) {
                    lessons.remove(i);
                    database.editCourse(courseID, course);
                    
                    return;
                }
                }
            }
        }
    }
    public void editLesson(String lessonID, String courseId, Lesson newLesson){
        for(CourseManagement course : createdCourses){
            if(course.getCourseId().equals(courseId)){
                ArrayList<Lesson> lessons = course.getLessons();
                for (int i = 0; i < lessons.size(); i++) {
                if (lessons.get(i).getLessonId().equals(lessonID)) {
                    lessons.set(i, newLesson);
                    database.editCourse(courseId, course);
                    return;
                }
                }
            }
        }
    }
    
    public void viewEnrolledStudents() {
    if (createdCourses == null || createdCourses.isEmpty()) {
        return;
    }

    for (CourseManagement course : createdCourses) {
        ArrayList<String> students = course.getStudents();

        if (students.isEmpty()) {
            System.out.println("  No students enrolled.");
        } else {
            System.out.println("  Enrolled Students:");
            for (String studentId : students) {
                System.out.println("    - " + studentId);
            }
        }

    }
}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public ArrayList<CourseManagement> getCreatedCourses() {
        return createdCourses;
    }

    public void setCreatedCourses(ArrayList<CourseManagement> createdCourses) {
        this.createdCourses = createdCourses;
    }

    public CourseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(CourseDatabase database) {
        this.database = database;
    }

}
