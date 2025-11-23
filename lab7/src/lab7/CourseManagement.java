package lab7;

import java.util.ArrayList;

public class CourseManagement {

   
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private String status;
    private ArrayList<Lesson> lessons;
    private ArrayList<String> students;

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
        this.status = STATUS_PENDING; 
    }

    public CourseManagement(String courseId, String title, String description, String instructorId,
            ArrayList<Lesson> lessons, ArrayList<String> students, String status) {
        this(courseId, title, description, instructorId);
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
        this.status = (status != null && !status.trim().isEmpty()) ? status : STATUS_PENDING;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        if (status != null && 
            (status.equals(STATUS_PENDING) || 
             status.equals(STATUS_APPROVED) || 
             status.equals(STATUS_REJECTED))) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status value. Must be PENDING, APPROVED, or REJECTED");
        }
    }

    public boolean isPending() {
        return STATUS_PENDING.equals(this.status);
    }

    public boolean isApproved() {
        return STATUS_APPROVED.equals(this.status);
    }

    public boolean isRejected() {
        return STATUS_REJECTED.equals(this.status);
    }

    public void enrollStudent(String studentId) {
        if (!isApproved()) {
            throw new IllegalStateException("Cannot enroll in a course that is not approved");
        }
        if (studentId != null && !studentId.trim().isEmpty() && !students.contains(studentId)) {
            students.add(studentId);
        }
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
        return new ArrayList<>(lessons);
    }

    public ArrayList<String> getStudents() {
        return new ArrayList<>(students);
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be null or empty");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null) {
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
                ", status='" + status + '\'' +
                ", lessonsCount=" + lessons.size() +
                ", studentsCount=" + students.size() +
                '}';
    }

    public String getDisplayInfo() {
        return title + " (ID: " + courseId + ") - " + description + " [" + status + "]";
    }

    public String getAdminReviewInfo() {
        return String.format(
            "Course: %s\nID: %s\nInstructor: %s\nStatus: %s\nStudents: %d\nLessons: %d\nDescription: %s",
            title, courseId, instructorId, status, students.size(), lessons.size(), description
        );
    }
}