package lab7;

public class CertificateService {
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    
    public CertificateService(UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
    }
    
    public Certificate generateCertificate(String studentId, String courseId) {
        StudentManagement student = userDatabase.findStudentById(studentId);
        CourseManagement course = courseDatabase.findCourseById(courseId);
        
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student or course not found");
        }
        
        // Check if student is enrolled
        if (!student.isEnrolledInCourse(courseId)) {
            throw new IllegalStateException("Student is not enrolled in this course");
        }
        
        // Check if student has completed all lessons
        if (!student.hasCompletedAllLessons(course)) {
            throw new IllegalStateException("Student has not completed all lessons");
        }
        
        // Check if student has passed all quizzes
        if (!student.hasPassedAllQuizzes(course)) {
            throw new IllegalStateException("Student has not passed all quizzes");
        }
        
        // Get instructor name
        String instructorName = "Unknown Instructor";
        Instructor instructor = userDatabase.findInstructorById(course.getInstructorId());
        if (instructor != null) {
            instructorName = instructor.getUsername();
        }
        
        // Calculate final score (average of all quiz scores)
        double finalScore = student.getAverageQuizScore(course);
        
        // Create certificate
        Certificate certificate = new Certificate(
            studentId, 
            student.getUsername(), 
            courseId, 
            course.getTitle(), 
            instructorName, 
            finalScore
        );
        
        // Save certificate to student and database
        student.addCertificate(certificate);
        userDatabase.saveCertificate(certificate);
        userDatabase.editStudent(studentId, student);
        
        return certificate;
    }
    
    public boolean isEligibleForCertificate(String studentId, String courseId) {
        try {
            StudentManagement student = userDatabase.findStudentById(studentId);
            CourseManagement course = courseDatabase.findCourseById(courseId);
            
            return student != null && 
                   course != null && 
                   student.isEnrolledInCourse(courseId) &&
                   student.hasCompletedAllLessons(course) &&
                   student.hasPassedAllQuizzes(course);
        } catch (Exception e) {
            return false;
        }
    }
    
    public Certificate getStudentCertificate(String studentId, String courseId) {
        StudentManagement student = userDatabase.findStudentById(studentId);
        if (student != null) {
            return student.getCertificateForCourse(courseId);
        }
        return null;
    }
}