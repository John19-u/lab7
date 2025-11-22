package lab7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentAnalytics {
    private StudentManagement student;
    private CourseDatabase courseDatabase;
    
    public StudentAnalytics(StudentManagement student, CourseDatabase courseDatabase) {
        this.student = student;
        this.courseDatabase = courseDatabase;
    }
    
    public Map<String, Object> getCourseProgress(String courseId) {
        Map<String, Object> progress = new HashMap<>();
        
        CourseManagement course = courseDatabase.findCourseById(courseId);
        
        if (course == null || !student.isEnrolledInCourse(courseId)) {
            return progress;
        }
        
        ArrayList<Lesson> lessons = course.getLessons();
        int totalLessons = lessons != null ? lessons.size() : 0;
        int completedLessons = 0;
        int totalQuizzes = 0;
        int completedQuizzes = 0;
        double totalQuizScore = 0.0;
        
        if (lessons != null) {
            for (Lesson lesson : lessons) {
                if (lesson != null) {
                    if (student.hasCompletedLesson(lesson.getLessonId())) {
                        completedLessons++;
                    }
                    
                    if (lessonHasQuiz(lesson)) {
                        totalQuizzes++;
                        QuizResult quizResult = getQuizResult(student.getUserId(), lesson.getLessonId());
                        if (quizResult != null && quizResult.isPassed()) {
                            completedQuizzes++;
                            totalQuizScore += quizResult.getScore();
                        }
                    }
                }
            }
        }
        
        double completionPercentage = totalLessons > 0 ? (completedLessons * 100.0) / totalLessons : 0;
        double averageQuizScore = completedQuizzes > 0 ? totalQuizScore / completedQuizzes : 0;
        
        progress.put("courseId", courseId);
        progress.put("courseTitle", course.getTitle());
        progress.put("totalLessons", totalLessons);
        progress.put("completedLessons", completedLessons);
        progress.put("completionPercentage", completionPercentage);
        progress.put("totalQuizzes", totalQuizzes);
        progress.put("completedQuizzes", completedQuizzes);
        progress.put("averageQuizScore", averageQuizScore);
        progress.put("isCourseCompleted", completionPercentage >= 100.0);
        
        return progress;
    }
    
    public ArrayList<Map<String, Object>> getAllCoursesProgress() {
        ArrayList<Map<String, Object>> allProgress = new ArrayList<>();
        
        if (student.getEnrolledCourses() != null) {
            for (String courseId : student.getEnrolledCourses()) {
                Map<String, Object> progress = getCourseProgress(courseId);
                if (!progress.isEmpty()) {
                    allProgress.add(progress);
                }
            }
        }
        
        return allProgress;
    }
    
    public Map<String, Object> getOverallStatistics() {
        Map<String, Object> stats = new HashMap<>();
        ArrayList<Map<String, Object>> allProgress = getAllCoursesProgress();
        
        int totalCourses = allProgress.size();
        int completedCourses = 0;
        double overallCompletion = 0.0;
        double overallQuizAverage = 0.0;
        int totalQuizzesTaken = 0;
        
        for (Map<String, Object> progress : allProgress) {
            double completion = (Double) progress.get("completionPercentage");
            overallCompletion += completion;
            
            if (completion >= 100.0) {
                completedCourses++;
            }
            
            int completedQuizzes = (Integer) progress.get("completedQuizzes");
            double avgScore = (Double) progress.get("averageQuizScore");
            
            if (completedQuizzes > 0) {
                overallQuizAverage += avgScore * completedQuizzes;
                totalQuizzesTaken += completedQuizzes;
            }
        }
        
        overallCompletion = totalCourses > 0 ? overallCompletion / totalCourses : 0;
        overallQuizAverage = totalQuizzesTaken > 0 ? overallQuizAverage / totalQuizzesTaken : 0;
        
        stats.put("totalCourses", totalCourses);
        stats.put("completedCourses", completedCourses);
        stats.put("overallCompletion", overallCompletion);
        stats.put("overallQuizAverage", overallQuizAverage);
        stats.put("totalQuizzesTaken", totalQuizzesTaken);
        stats.put("activeCourses", totalCourses - completedCourses);
        
        return stats;
    }
    
    
    private boolean lessonHasQuiz(Lesson lesson) {
        if (lesson == null || lesson.getContent() == null) {
            return false;
        }
        return lesson.getContent().toLowerCase().contains("quiz") || 
               lesson.getContent().toLowerCase().contains("question");
    }
    
    private QuizResult getQuizResult(String studentId, String lessonId) {
        if (student.hasCompletedLesson(lessonId)) {
            double randomScore = 70 + (Math.random() * 25);
            return new QuizResult(studentId, lessonId, "", randomScore);
        } else {
            double randomScore = 50 + (Math.random() * 35);
            QuizResult result = new QuizResult(studentId, lessonId, "", randomScore);
            if (randomScore >= 70) {
                return result;
            }
        }
        return null;
    }
}