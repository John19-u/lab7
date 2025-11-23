package lab7;

import java.util.ArrayList;

public class Instructor {

    private String userId; 
    private String role;
    private String username;
    private String email;
    private String passwordHash;
    private ArrayList<CourseManagement> createdCourses;


    public Instructor(String userId, String role, String username, String email, String passwordHash) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdCourses = new ArrayList<>();
    }

    public Instructor(String userId, String role, String username, String email, String passwordHash,
            ArrayList<CourseManagement> createdCourses) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdCourses = createdCourses != null ? createdCourses : new ArrayList<>();
    }

  
    public String getUserId() {
        return userId;
    } 

    public void setUserId(String userId) {
        this.userId = userId;
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
        return new ArrayList<>(createdCourses);
    }

    public void setCreatedCourses(ArrayList<CourseManagement> createdCourses) {
        this.createdCourses = createdCourses != null ? createdCourses : new ArrayList<>();
    }

    public void createCourse(CourseManagement course, CourseDatabase database) {
        if (course != null && database != null) {
            createdCourses.add(course);
            database.addCourse(course);
        }
    }

    public void editCourse(String courseId, CourseManagement updatedCourse, CourseDatabase database) {
        for (CourseManagement course : createdCourses) {
            if (course.getCourseId().equals(courseId)) {
           
                createdCourses.set(createdCourses.indexOf(course), updatedCourse);
              
                database.editCourse(courseId, updatedCourse);
                break;
            }
        }
    }

    public void addLesson(Lesson lesson, String courseId, CourseDatabase database) {
        for (CourseManagement course : createdCourses) {
            if (course.getCourseId().equals(courseId)) {
                course.addLesson(lesson);
                database.editCourse(courseId, course);
                break;
            }
        }
    }

    public void deleteLesson(String lessonId, String courseId, CourseDatabase database) {
        for (CourseManagement course : createdCourses) {
            if (course.getCourseId().equals(courseId)) {
                ArrayList<Lesson> lessons = course.getLessons();
                for (int i = 0; i < lessons.size(); i++) {
                    if (lessons.get(i).getLessonId().equals(lessonId)) {
                        lessons.remove(i);
                        database.editCourse(courseId, course);
                        return;
                    }
                }
            }
        }
    }

    public void editLesson(String lessonId, String courseId, Lesson newLesson, CourseDatabase database) {
        for (CourseManagement course : createdCourses) {
            if (course.getCourseId().equals(courseId)) {
                ArrayList<Lesson> lessons = course.getLessons();
                for (int i = 0; i < lessons.size(); i++) {
                    if (lessons.get(i).getLessonId().equals(lessonId)) {
                        lessons.set(i, newLesson);
                        database.editCourse(courseId, course);
                        return;
                    }
                }
            }
        }
    }

    public void viewEnrolledStudents(CourseDatabase database) {
        if (createdCourses == null || createdCourses.isEmpty()) {
            System.out.println("No courses created.");
            return;
        }

        for (CourseManagement course : createdCourses) {
            CourseManagement dbCourse = database.findCourseById(course.getCourseId());
            if (dbCourse != null) {
                ArrayList<String> students = dbCourse.getStudents();
                System.out.println("Course: " + course.getTitle());
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
    }

    public boolean ownsCourse(String courseId) {
        for (CourseManagement course : createdCourses) {
            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }
}
