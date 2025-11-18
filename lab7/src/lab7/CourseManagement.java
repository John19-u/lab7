package lab7;

import java.util.ArrayList;

public class CourseManagement {
    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private ArrayList<Lesson> lessons;
    private ArrayList<String> students; // Student IDs enrolled in this course
    
    public CourseManagement(String courseId, String title, String description, String instructorId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be null or empty");
        }
        if (instructorId == null || instructorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Instructor ID cannot be null or empty");
        }
        
        this.courseId = courseId;
        this.title = title;
        this.description = description != null ? description : "";
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
    }
    
    public CourseManagement(String courseId, String title, String description, String instructorId, 
                           ArrayList<Lesson> lessons, ArrayList<String> students) {
        this(courseId, title, description, instructorId);
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
    }
    
    // Getters
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
        return new ArrayList<>(lessons); // Defensive copy
    }

    public ArrayList<String> getStudents() {
        return new ArrayList<>(students); // Defensive copy
    }
    
    // Setters with validation
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be null or empty");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }
    
    // Lesson management
    public void addLesson(Lesson lesson) {
        if (lesson != null) {
            // Check for duplicate lesson ID
            for (Lesson existingLesson : lessons) {
                if (existingLesson.getLessonId().equals(lesson.getLessonId())) {
                    throw new IllegalArgumentException("Lesson with ID " + lesson.getLessonId() + " already exists in this course");
                }
            }
            lessons.add(lesson);
        }
    }
    
    public boolean removeLesson(String lessonId) {
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(lessonId)) {
                lessons.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
    }
    
    // Student management
    public void enrollStudent(String studentId) {
        if (studentId != null && !studentId.trim().isEmpty() && !students.contains(studentId)) {
            students.add(studentId);
        }
    }
    
    public boolean unenrollStudent(String studentId) {
        return students.remove(studentId);
    }
    
    public boolean isStudentEnrolled(String studentId) {
        return students.contains(studentId);
    }
    
    public int getStudentCount() {
        return students.size();
    }
    
    public int getLessonCount() {
        return lessons.size();
    }
    
    @Override
    public String toString() {
        return "CourseManagement{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", instructorId='" + instructorId + '\'' +
                ", lessonsCount=" + lessons.size() +
                ", studentsCount=" + students.size() +
                '}';
    }
    
    // Utility method for display
    public String getDisplayInfo() {
        return title + " (ID: " + courseId + ") - " + description;
    }
}