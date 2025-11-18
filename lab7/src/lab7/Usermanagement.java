package lab7;

import java.security.NoSuchAlgorithmException;

public class Usermanagement {
    private UserDatabase userDatabase; // Fixed naming convention
    
    public Usermanagement(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }
    
    public StudentManagement loginStudent(String email, String password) throws NoSuchAlgorithmException {
        String passwordHash = SHA256Hasher.hashPassword(password);
        return userDatabase.authenticateStudent(email, passwordHash);
    }
    
    public Instructor loginInstructor(String email, String password) throws NoSuchAlgorithmException {
        String passwordHash = SHA256Hasher.hashPassword(password);
        return userDatabase.authenticateInstructor(email, passwordHash);
    }
    
    public boolean signupStudent(String userId, String username, String email, String password) {
        try {
            // Check if user already exists
            if (userDatabase.findStudentById(userId) != null || userDatabase.findStudentByEmail(email) != null) {
                return false;
            }
            
            String passwordHash = SHA256Hasher.hashPassword(password);
            StudentManagement student = new StudentManagement(username, "Student", passwordHash, userId, email);
            userDatabase.addStudent(student);
            return true;
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error during signup: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean signupInstructor(String userId, String username, String email, String password) {
        try {
            // Check if user already exists
            if (userDatabase.findInstructorById(userId) != null || userDatabase.findInstructorByEmail(email) != null) {
                return false;
            }
            
            String passwordHash = SHA256Hasher.hashPassword(password);
            Instructor instructor = new Instructor(userId, "Instructor", username, email, passwordHash);
            userDatabase.addInstructor(instructor);
            return true;
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error during signup: " + ex.getMessage());
            return false;
        }
    }
    
    // Combined login method that detects role automatically
    public Object login(String email, String password) throws NoSuchAlgorithmException {
        String passwordHash = SHA256Hasher.hashPassword(password);
        
        // Try student first
        StudentManagement student = userDatabase.authenticateStudent(email, passwordHash);
        if (student != null) {
            return student;
        }
        
        // Try instructor
        Instructor instructor = userDatabase.authenticateInstructor(email, passwordHash);
        if (instructor != null) {
            return instructor;
        }
        
        return null; // Authentication failed
    }
}