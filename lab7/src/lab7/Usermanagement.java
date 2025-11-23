package lab7;

import java.security.NoSuchAlgorithmException;

public class Usermanagement {
    private UserDatabase userDatabase; 
    
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
    
       public Admin loginAdmin(String email, String password) throws NoSuchAlgorithmException {
        String passwordHash = SHA256Hasher.hashPassword(password);
        return userDatabase.authenticateAdmin(email, passwordHash);
    }
    public boolean signupStudent(String userId, String username, String email, String password) {
        try {
            
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
    
   
     public boolean signupAdmin(String userId, String username, String email, String password) {
        try {
            
            if (userDatabase.findAdminById(userId) != null || userDatabase.findAdminByEmail(email) != null) {
                return false;
            }
            
            String passwordHash = SHA256Hasher.hashPassword(password);
             Admin admin = new Admin(username, "Admin", passwordHash, userId, email);
            userDatabase.addAdmin(admin);
            return true;
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error during signup: " + ex.getMessage());
            return false;
        }
    }
    
    
    public Object login(String email, String password) throws NoSuchAlgorithmException {
        String passwordHash = SHA256Hasher.hashPassword(password);
        
        
        StudentManagement student = userDatabase.authenticateStudent(email, passwordHash);
        if (student != null) {
            return student;
        }
        
      
        Instructor instructor = userDatabase.authenticateInstructor(email, passwordHash);
        if (instructor != null) {
            return instructor;
        }
        Admin admin = userDatabase.authenticateAdmin(email, passwordHash);
        if (admin != null) {
            return admin;
        }
        
        return null; 
    }
}