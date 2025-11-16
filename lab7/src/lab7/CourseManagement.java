
package lab7;

import java.util.ArrayList;
import java.util.HashSet;


public class CourseManagement { 
    
    private static HashSet<String> usedCourseIds = new HashSet<>();
    
    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private ArrayList<Lesson> lessons;
    private ArrayList<String> students;

    
    public CourseManagement(String courseId, String title, String description, String instructorId) { 
        if (usedCourseIds.contains(courseId)) {
            throw new IllegalArgumentException("Course ID already exists: " + courseId);
        }
        usedCourseIds.add(courseId);
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
    }
     
    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<String> getStudents() {
        return students;
    }
    
     public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    } 
    
    public boolean removeLesson(String lessonId) {
        return lessons.removeIf(l -> l.getLessonId().equals(lessonId));
    }
    
     public void enrollStudent(String studentId) {
        if (!students.contains(studentId)) {
            students.add(studentId);
        }
    }
     
     public void removeStudent(String studentId) {
        students.remove(studentId);
    }

    @Override
     public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", instructorId='" + instructorId + '\'' +
                '}';
    }
}
