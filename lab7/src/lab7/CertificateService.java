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
        
    
        if (!student.isEnrolledInCourse(courseId)) {
            throw new IllegalStateException("Student is not enrolled in this course");
        }
        
        
        if (!student.hasCompletedAllLessons(course)) {
            throw new IllegalStateException("Student has not completed all lessons");
        }
      
        if (!student.hasPassedAllQuizzes(course)) {
            throw new IllegalStateException("Student has not passed all quizzes");
        }
      
        String instructorName = "Unknown Instructor";
        Instructor instructor = userDatabase.findInstructorById(course.getInstructorId());
        if (instructor != null) {
            instructorName = instructor.getUsername();
        }
        
        
        double finalScore = student.getAverageQuizScore(course);
        
        
        Certificate certificate = new Certificate(
            studentId, 
            student.getUsername(), 
            courseId, 
            course.getTitle(), 
            instructorName, 
            finalScore
        );
        
       
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