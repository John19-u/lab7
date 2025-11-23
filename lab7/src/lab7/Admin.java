package lab7;

import java.util.ArrayList;

/**
 *
 * @author PXC
 */
public class Admin {

    private String username;
    private String role;
    private String passwordHash;
    private String userId;
    private String email;

    public Admin(String username, String role, String passwordHash, String userId, String email) {
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.userId = userId;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
    public boolean approveCourse(CourseDatabase database, String courseId) {
        if (database == null || courseId == null || courseId.trim().isEmpty()) {
            return false;
        }
        
        CourseManagement course = database.findCourseById(courseId);
        if (course != null) {
            course.setStatus("APPROVED");
            return database.editCourse(courseId, course);
        }
        return false;
    }

    public boolean rejectCourse(CourseDatabase database, String courseId) {
        if (database == null || courseId == null || courseId.trim().isEmpty()) {
            return false;
        }
        
        CourseManagement course = database.findCourseById(courseId);
        if (course != null) {
            course.setStatus("REJECTED");
            return database.editCourse(courseId, course);
        }
        return false;
    }

 
    public ArrayList<CourseManagement> getPendingCourses(CourseDatabase database) {
        ArrayList<CourseManagement> pendingCourses = new ArrayList<>();
        if (database != null) {
            for (CourseManagement course : database.getAllCourses()) {
                if ("PENDING".equals(course.getStatus())) {
                    pendingCourses.add(course);
                }
            }
        }
        return pendingCourses;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}