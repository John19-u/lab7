package lab7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;

public class CourseDatabase {

    private ArrayList<CourseManagement> coursesList;
    private final File file;
    private final Gson gson;
  private QuizService quizService;
    public CourseDatabase(String filename) {
        this.file = new File(filename);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.coursesList = new ArrayList<>();
        load();
    }

    private void load() {
        try {
            if (!file.exists()) {
                save();
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            DatabaseWrapper wrapper = gson.fromJson(br, DatabaseWrapper.class);
            br.close();

            if (wrapper != null && wrapper.coursesList != null) {
                coursesList = wrapper.coursesList;
            }
        } catch (Exception e) {
            System.out.println("Error loading courses DB: " + e.getMessage());
        }
    }

    public void save() {
        try {
            DatabaseWrapper wrapper = new DatabaseWrapper();
            wrapper.coursesList = coursesList;

            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(gson.toJson(wrapper));
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving courses DB: " + e.getMessage());
        }
    }

    public void addCourse(CourseManagement c) {
        if (findCourseById(c.getCourseId()) != null) {
            throw new IllegalArgumentException("Course ID already exists!");
        }
        coursesList.add(c);
        save();
    }

    public boolean editCourse(String courseId, CourseManagement updatedCourse) {
        for (int i = 0; i < coursesList.size(); i++) {
            if (coursesList.get(i).getCourseId().equals(courseId)) {
                coursesList.set(i, updatedCourse);
                save();
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse(String courseId) {
        for (int i = 0; i < coursesList.size(); i++) {
            if (coursesList.get(i).getCourseId().equals(courseId)) {
                coursesList.remove(i);
                save();
                return true;
            }
        }
        return false;
    }

    public CourseManagement findCourseById(String courseId) {
        for (CourseManagement c : coursesList) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<CourseManagement> getAllCourses() {
        return new ArrayList<>(coursesList);
    }

    
    public ArrayList<CourseManagement> getAllApprovedCourses() {
        ArrayList<CourseManagement> approvedList = new ArrayList<>();
        for (CourseManagement c : coursesList) {
            if (c.isApproved()) {
                approvedList.add(c);
            }
        }
        return approvedList;
    }

    public ArrayList<CourseManagement> getPendingCourses() {
        ArrayList<CourseManagement> pendingList = new ArrayList<>();
        for (CourseManagement c : coursesList) {
            if (c.isPending()) {
                pendingList.add(c);
            }
        }
        return pendingList;
    }

    public ArrayList<CourseManagement> getRejectedCourses() {
        ArrayList<CourseManagement> rejectedList = new ArrayList<>();
        for (CourseManagement c : coursesList) {
            if (c.isRejected()) {
                rejectedList.add(c);
            }
        }
        return rejectedList;
    }

    public ArrayList<CourseManagement> getCoursesByStatus(String status) {
        ArrayList<CourseManagement> result = new ArrayList<>();
        for (CourseManagement c : coursesList) {
            if (c.getStatus().equals(status)) {
                result.add(c);
            }
        }
        return result;
    }

    public ArrayList<CourseManagement> getCoursesByInstructor(String instructorId) {
        ArrayList<CourseManagement> result = new ArrayList<>();
        for (CourseManagement course : coursesList) {
            if (course.getInstructorId().equals(instructorId)) {
                result.add(course);
            }
        }
        return result;
    }

    public ArrayList<CourseManagement> getApprovedCoursesByInstructor(String instructorId) {
        ArrayList<CourseManagement> result = new ArrayList<>();
        for (CourseManagement course : coursesList) {
            if (course.getInstructorId().equals(instructorId) && course.isApproved()) {
                result.add(course);
            }
        }
        return result;
    }

    private static class DatabaseWrapper {
        ArrayList<CourseManagement> coursesList;
    }
}