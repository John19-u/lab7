package lab7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorAnalytics {
    private Instructor instructor;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    
    public InstructorAnalytics(Instructor instructor, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.instructor = instructor;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
    }
    
    public Map<String, Object> getCoursePerformance(String courseId) {
        Map<String, Object> performance = new HashMap<>();
        
        CourseManagement course = courseDatabase.findCourseById(courseId);
        
        if (course == null) {
            return performance;
        }
        
        if (!course.getInstructorId().equals(instructor.getUserId())) {
            return performance;
        }
        
        ArrayList<String> studentIds = course.getStudents();
        int totalStudents = studentIds != null ? studentIds.size() : 0;
        int totalLessons = course.getLessons() != null ? course.getLessons().size() : 0;
        
        double overallCompletion = 0.0;
        double overallQuizAverage = 0.0;
        int totalQuizzesTaken = 0;
        Map<String, Integer> lessonCompletion = new HashMap<>();
        Map<String, Double> lessonQuizAverages = new HashMap<>();
        
       
        if (course.getLessons() != null) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson != null) {
                    lessonCompletion.put(lesson.getLessonId(), 0);
                    lessonQuizAverages.put(lesson.getLessonId(), 0.0);
                }
            }
        }
        
       
        int studentsProcessed = 0;
        for (String studentId : studentIds) {
            StudentManagement student = userDatabase.findStudentById(studentId);
            if (student != null) {
                StudentAnalytics studentAnalytics = new StudentAnalytics(student, courseDatabase);
                Map<String, Object> progress = studentAnalytics.getCourseProgress(courseId);
                
                if (!progress.isEmpty()) {
                    overallCompletion += (Double) progress.get("completionPercentage");
                    double avgScore = (Double) progress.get("averageQuizScore");
                    int completedQuizzes = (Integer) progress.get("completedQuizzes");
                    
                    if (completedQuizzes > 0) {
                        overallQuizAverage += avgScore * completedQuizzes;
                        totalQuizzesTaken += completedQuizzes;
                    }
                    
                
                    if (course.getLessons() != null) {
                        for (Lesson lesson : course.getLessons()) {
                            if (lesson != null && student.hasCompletedLesson(lesson.getLessonId())) {
                                lessonCompletion.put(lesson.getLessonId(), 
                                    lessonCompletion.get(lesson.getLessonId()) + 1);
                            }
                        }
                    }
                    studentsProcessed++;
                }
            }
        }
        
    
        overallCompletion = studentsProcessed > 0 ? overallCompletion / studentsProcessed : 0;
        overallQuizAverage = totalQuizzesTaken > 0 ? overallQuizAverage / totalQuizzesTaken : 0;
        
       
        Map<String, Double> lessonCompletionPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : lessonCompletion.entrySet()) {
            double percentage = studentsProcessed > 0 ? (entry.getValue() * 100.0) / studentsProcessed : 0;
            lessonCompletionPercentages.put(entry.getKey(), percentage);
        }
        
        performance.put("courseId", courseId);
        performance.put("courseTitle", course.getTitle());
        performance.put("totalStudents", totalStudents);
        performance.put("totalLessons", totalLessons);
        performance.put("overallCompletion", overallCompletion);
        performance.put("overallQuizAverage", overallQuizAverage);
        performance.put("totalQuizzesTaken", totalQuizzesTaken);
        performance.put("lessonCompletion", lessonCompletionPercentages);
        
        return performance;
    }
    
    public ArrayList<Map<String, Object>> getAllCoursesPerformance() {
        ArrayList<Map<String, Object>> allPerformance = new ArrayList<>();
        ArrayList<CourseManagement> instructorCourses = courseDatabase.getCoursesByInstructor(instructor.getUserId());
        
        if (instructorCourses != null) {
            for (CourseManagement course : instructorCourses) {
                Map<String, Object> performance = getCoursePerformance(course.getCourseId());
                if (!performance.isEmpty()) {
                    allPerformance.add(performance);
                }
            }
        }
        
        return allPerformance;
    }
    
    public Map<String, Object> getInstructorOverview() {
        Map<String, Object> overview = new HashMap<>();
        ArrayList<Map<String, Object>> allPerformance = getAllCoursesPerformance();
        
        int totalCourses = allPerformance.size();
        int totalStudents = 0;
        double overallCompletion = 0.0;
        double overallQuizAverage = 0.0;
        int totalQuizzesTaken = 0;
        
        for (Map<String, Object> performance : allPerformance) {
            totalStudents += (Integer) performance.get("totalStudents");
            overallCompletion += (Double) performance.get("overallCompletion");
            overallQuizAverage += (Double) performance.get("overallQuizAverage");
            totalQuizzesTaken += (Integer) performance.get("totalQuizzesTaken");
        }
        
        overallCompletion = totalCourses > 0 ? overallCompletion / totalCourses : 0;
        overallQuizAverage = totalCourses > 0 ? overallQuizAverage / totalCourses : 0;
        
        overview.put("totalCourses", totalCourses);
        overview.put("totalStudents", totalStudents);
        overview.put("averageStudentsPerCourse", totalCourses > 0 ? (double) totalStudents / totalCourses : 0);
        overview.put("overallCompletion", overallCompletion);
        overview.put("overallQuizAverage", overallQuizAverage);
        overview.put("totalQuizzesTaken", totalQuizzesTaken);
        
        return overview;
    }
    
    public ArrayList<Map<String, Object>> getStudentProgressDetails(String courseId) {
        ArrayList<Map<String, Object>> studentProgress = new ArrayList<>();
        
        CourseManagement course = courseDatabase.findCourseById(courseId);
        
        if (course != null && course.getInstructorId().equals(instructor.getUserId())) {
            ArrayList<String> studentIds = course.getStudents();
            if (studentIds != null) {
                for (String studentId : studentIds) {
                    StudentManagement student = userDatabase.findStudentById(studentId);
                    if (student != null) {
                        StudentAnalytics studentAnalytics = new StudentAnalytics(student, courseDatabase);
                        Map<String, Object> progress = studentAnalytics.getCourseProgress(courseId);
                        
                        if (!progress.isEmpty()) {
                            progress.put("studentId", studentId);
                            progress.put("studentName", student.getUsername());
                            progress.put("studentEmail", student.getEmail());
                            studentProgress.add(progress);
                        }
                    }
                }
            }
        }
        
        return studentProgress;
    }
}