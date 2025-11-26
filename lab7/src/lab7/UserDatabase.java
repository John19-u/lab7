package lab7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class UserDatabase {

    private ArrayList<StudentManagement> studentsList;
    private ArrayList<Instructor> instructorsList;
    private ArrayList<Admin> adminsList;
    private final File file;
    private final Gson gson;
    private Map<String, List<QuizResult>> quizResults = new HashMap<>();
    private Map<String, List<Certificate>> certificates = new HashMap<>();

    public UserDatabase(String filename) {
        this.file = new File(filename);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.studentsList = new ArrayList<>();
        this.instructorsList = new ArrayList<>();
        this.adminsList = new ArrayList<>();
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

            if (wrapper != null) {
                if (wrapper.studentsList != null) {
                    studentsList = wrapper.studentsList;
                }
                if (wrapper.instructorsList != null) {
                    instructorsList = wrapper.instructorsList;
                }
                if (wrapper.adminsList != null) {
                    adminsList = wrapper.adminsList;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading DB: " + e.getMessage());
        }
    }

    public void save() {
        try {
            DatabaseWrapper wrapper = new DatabaseWrapper();
            wrapper.studentsList = studentsList;
            wrapper.instructorsList = instructorsList;
            wrapper.adminsList = adminsList;
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(gson.toJson(wrapper));
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving DB: " + e.getMessage());
        }
    }

    public void addStudent(StudentManagement s) {
        if (findStudentById(s.getUserId()) != null) {
            throw new IllegalArgumentException("Student ID already exists!");
        }
        studentsList.add(s);
        save();
    }

    public StudentManagement findStudentById(String id) {
        for (StudentManagement student : studentsList) {
        if (student.getUserId().equals(id)) {
            // Ensure quizResults is initialized
            if (student.getQuizResult("any") == null) {
                // This will trigger the initialization in the getter
            }
            return student;
        }
    }
    return null;
    }

    public StudentManagement findStudentByEmail(String email) {
        for (StudentManagement s : studentsList) {
            if (s.getEmail().equals(email)) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<StudentManagement> getAllStudents() {
        return new ArrayList<>(studentsList);
    }

    public boolean deleteStudent(String studentId) {
        for (int i = 0; i < studentsList.size(); i++) {
            if (studentsList.get(i).getUserId().equals(studentId)) {
                studentsList.remove(i);
                save();
                return true;
            }
        }
        return false;
    }

    public boolean editStudent(String studentId, StudentManagement updatedStudent) {
        for (int i = 0; i < studentsList.size(); i++) {
            if (studentsList.get(i).getUserId().equals(studentId)) {
                studentsList.set(i, updatedStudent);
                save();
                return true;
            }
        }
        return false;
    }

    public void addInstructor(Instructor i) {
        if (findInstructorById(i.getUserId()) != null) {
            throw new IllegalArgumentException("Instructor ID already exists!");
        }
        instructorsList.add(i);
        save();
    }

    public Instructor findInstructorById(String id) {
        for (Instructor i : instructorsList) {
            if (i.getUserId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public Instructor findInstructorByEmail(String email) {
        for (Instructor i : instructorsList) {
            if (i.getEmail().equals(email)) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<Instructor> getAllInstructors() {
        return new ArrayList<>(instructorsList);
    }

    public boolean deleteInstructor(String instructorId) {
        for (int i = 0; i < instructorsList.size(); i++) {
            if (instructorsList.get(i).getUserId().equals(instructorId)) {
                instructorsList.remove(i);
                save();
                return true;
            }
        }
        return false;
    }

    public boolean editInstructor(String instructorId, Instructor updatedInstructor) {
        for (int i = 0; i < instructorsList.size(); i++) {
            if (instructorsList.get(i).getUserId().equals(instructorId)) {
                instructorsList.set(i, updatedInstructor);
                save();
                return true;
            }
        }
        return false;
    }

    public void addAdmin(Admin a) {
        if (findStudentById(a.getUserId()) != null) {
            throw new IllegalArgumentException("Student ID already exists!");
        }
        adminsList.add(a);
        save();
    }

    public Admin findAdminById(String id) {
        for (Admin a : adminsList) {
            if (a.getUserId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public Admin findAdminByEmail(String email) {
        for (Admin a : adminsList) {
            if (a.getEmail().equals(email)) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Admin> getAllAdmins() {
        return new ArrayList<>(adminsList);
    }

    public boolean deleteAdmin(String studentId) {
        for (int i = 0; i < adminsList.size(); i++) {
            if (adminsList.get(i).getUserId().equals(studentId)) {
                adminsList.remove(i);
                save();
                return true;
            }
        }
        return false;
    }

    public boolean editAdmin(String AdminId, Admin updatedAdmin) {
        for (int i = 0; i < adminsList.size(); i++) {
            if (adminsList.get(i).getUserId().equals(AdminId)) {
                adminsList.set(i, updatedAdmin);
                save();
                return true;
            }
        }
        return false;
    }

    public StudentManagement authenticateStudent(String email, String passwordHash) {
        StudentManagement student = findStudentByEmail(email);
        if (student != null && student.getPasswordHash().equals(passwordHash)) {
            return student;
        }
        return null;
    }

    public Instructor authenticateInstructor(String email, String passwordHash) {
        Instructor instructor = findInstructorByEmail(email);
        if (instructor != null && instructor.getPasswordHash().equals(passwordHash)) {
            return instructor;
        }
        return null;
    }

    public Admin authenticateAdmin(String email, String passwordHash) {
        Admin admin = findAdminByEmail(email);
        if (admin != null && admin.getPasswordHash().equals(passwordHash)) {
            return admin;
        }
        return null;
    }

    
    public void saveCertificate(Certificate certificate) {
        certificates.putIfAbsent(certificate.getStudentId(), new ArrayList<>());
        
        
        List<Certificate> studentCertificates = certificates.get(certificate.getStudentId());
        for (Certificate existingCert : studentCertificates) {
            if (existingCert.getCourseId().equals(certificate.getCourseId())) {
              
                studentCertificates.remove(existingCert);
                break;
            }
        }
        
        studentCertificates.add(certificate);
        saveCertificatesToFile();
    }
    
    public List<Certificate> getCertificatesForStudent(String studentId) {
        return certificates.getOrDefault(studentId, new ArrayList<>());
    }
    
    public Certificate getCertificateById(String certificateId) {
        for (List<Certificate> certList : certificates.values()) {
            for (Certificate cert : certList) {
                if (cert.getCertificateId().equals(certificateId)) {
                    return cert;
                }
            }
        }
        return null;
    }
    
    public Certificate getCertificateForCourse(String studentId, String courseId) {
        List<Certificate> studentCertificates = getCertificatesForStudent(studentId);
        for (Certificate cert : studentCertificates) {
            if (cert.getCourseId().equals(courseId)) {
                return cert;
            }
        }
        return null;
    }
    
    public boolean hasCertificateForCourse(String studentId, String courseId) {
        return getCertificateForCourse(studentId, courseId) != null;
    }
    
    private void saveCertificatesToFile() {
        try {
            File certFile = new File("certificates.json");
            PrintWriter pw = new PrintWriter(new FileWriter(certFile));
            
            Map<String, Object> certWrapper = new HashMap<>();
            certWrapper.put("certificates", certificates);
            
            pw.print(gson.toJson(certWrapper));
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving certificates: " + e.getMessage());
        }
    }
    
    private void loadCertificatesFromFile() {
        try {
            File certFile = new File("certificates.json");
            if (!certFile.exists()) {
                return;
            }
            
            BufferedReader br = new BufferedReader(new FileReader(certFile));
            Map<String, Object> certWrapper = gson.fromJson(br, Map.class);
            br.close();
            
            if (certWrapper != null && certWrapper.containsKey("certificates")) {
               
                System.out.println("Certificates loaded from file");
            }
        } catch (Exception e) {
            System.out.println("Error loading certificates: " + e.getMessage());
        }
    }

    
    public void saveQuizResult(QuizResult result) {
        quizResults.putIfAbsent(result.getStudentId(), new ArrayList<>());
        
        
        List<QuizResult> studentResults = quizResults.get(result.getStudentId());
        for (QuizResult existingResult : studentResults) {
            if (existingResult.getLessonId().equals(result.getLessonId())) {
               
                studentResults.remove(existingResult);
                break;
            }
        }
        
        studentResults.add(result);
        saveQuizResultsToFile();
    }
    
    public List<QuizResult> getQuizResults(String studentId) {
        return quizResults.getOrDefault(studentId, new ArrayList<>());
    }
    
    public List<QuizResult> getQuizResultsForCourse(String studentId, String courseId) {
        List<QuizResult> results = new ArrayList<>();
        for (QuizResult result : getQuizResults(studentId)) {
            if (result.getCourseId().equals(courseId)) {
                results.add(result);
            }
        }
        return results;
    }
    
    public QuizResult getQuizResultForLesson(String studentId, String lessonId) {
        for (QuizResult result : getQuizResults(studentId)) {
            if (result.getLessonId().equals(lessonId)) {
                return result;
            }
        }
        return null;
    }
    
    public boolean hasPassedQuiz(String studentId, String lessonId) {
        QuizResult result = getQuizResultForLesson(studentId, lessonId);
        return result != null && result.isPassed();
    }
    
    public double getAverageQuizScoreForCourse(String studentId, String courseId) {
        List<QuizResult> courseResults = getQuizResultsForCourse(studentId, courseId);
        if (courseResults.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = 0.0;
        for (QuizResult result : courseResults) {
            totalScore += result.getScore();
        }
        
        return totalScore / courseResults.size();
    }
    
    private void saveQuizResultsToFile() {
        try {
            File quizFile = new File("quiz_results.json");
            PrintWriter pw = new PrintWriter(new FileWriter(quizFile));
            
            Map<String, Object> quizWrapper = new HashMap<>();
            quizWrapper.put("quizResults", quizResults);
            
            pw.print(gson.toJson(quizWrapper));
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving quiz results: " + e.getMessage());
        }
    }
    
    private void loadQuizResultsFromFile() {
        try {
            File quizFile = new File("quiz_results.json");
            if (!quizFile.exists()) {
                return;
            }
            
            BufferedReader br = new BufferedReader(new FileReader(quizFile));
            Map<String, Object> quizWrapper = gson.fromJson(br, Map.class);
            br.close();
            
            if (quizWrapper != null && quizWrapper.containsKey("quizResults")) {
            
                System.out.println("Quiz results loaded from file");
            }
        } catch (Exception e) {
            System.out.println("Error loading quiz results: " + e.getMessage());
        }
    }

    
    {
        loadCertificatesFromFile();
        loadQuizResultsFromFile();
    }

    private static class DatabaseWrapper {
        ArrayList<StudentManagement> studentsList;
        ArrayList<Instructor> instructorsList;
        ArrayList<Admin> adminsList;
    }
}