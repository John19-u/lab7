package lab7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;

public class UserDatabase {
    private ArrayList<StudentManagement> studentsList;
    private ArrayList<Instructor> instructorsList;
    private final File file;
    private final Gson gson;

    public UserDatabase(String filename) {
        this.file = new File(filename);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.studentsList = new ArrayList<>();
        this.instructorsList = new ArrayList<>();
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

            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(gson.toJson(wrapper));
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving DB: " + e.getMessage());
        }
    }

    // Student methods
    public void addStudent(StudentManagement s) {
        if (findStudentById(s.getUserId()) != null) {
            throw new IllegalArgumentException("Student ID already exists!");
        }
        studentsList.add(s);
        save();
    }

    public StudentManagement findStudentById(String id) {
        for (StudentManagement s : studentsList) {
            if (s.getUserId().equals(id)) {
                return s;
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

    // Instructor methods
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

    // Authentication methods
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

    private static class DatabaseWrapper {
        ArrayList<StudentManagement> studentsList;
        ArrayList<Instructor> instructorsList;
    }
}