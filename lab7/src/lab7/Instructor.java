
package lab7;

import java.util.ArrayList;
import java.util.Date;
import 

public class Instructor {
    
    String userID;
    String role;
    String username;
    String email;
    String passwordHash;
    ArrayList<Course> createdCourses;

    public Instructor(String userID, String role, String username, String email, String passwordHash, ArrayList<Course> createdCourses) {
        this.userID = userID;
        this.role = "Instructor";
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdCourses = createdCourses;
    }
    
    
    public void createCourse(Course course){
        createdCourses.add(course);
        
    }
    public void editCourse(courseID){
        for(Course course : createdCourses){
            if(course.getCourseID.equals(courseID)){
                
            }
        }
    }
    public void addLesson(Lesson lesson, String courseID){
         for (Course course : createdCourses) {
        if (course.getCourseID().equals(courseID)) {
            course.addLesson(lesson);
            break;
        }
    }
    }
    public void deleteLesson(String lessonID, String courseID){
        for (Course course : createdCourses) {
        course.removeLesson(lessonID);
    }
    }
    public void editLesson(String lessonID){
        for(Course course : createdCourses){
            if(course.getLessonID.equals(lessonID)){
                
                break;
            }
        }
    }
    
    public void viewEnrolledStudents(String filename){
        
    }
}
