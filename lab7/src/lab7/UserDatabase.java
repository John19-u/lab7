package lab7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

import java.io.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

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

    public void addStudent(StudentManagement s) {
        if (findStudentById(s.getUserid()) != null) {
            throw new IllegalArgumentException("Student ID already exists!");
        }

        studentsList.add(s);
        save();
    }

    public StudentManagement findStudentById(String id) {
        for (StudentManagement s : studentsList) {
            if (s.getUserid().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<StudentManagement> getAllStudents() {
        return studentsList;
    }

    public void addInstructorManagement(Instructor i) {
        if (findInstructorManagementById(i.getUserID()) != null) {
            throw new IllegalArgumentException("InstructorManagement ID already exists!");
        }

        instructorsList.add(i);
        save();
    }

    public Instructor findInstructorManagementById(String id) {
        for (Instructor i : instructorsList) {
            if (i.getUserID().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<Instructor> getAllInstructorManagements() {
        return instructorsList;
    }

    public void deleteStudent(String studentId) {
        for (int i = 0; i < studentsList.size(); i++) {
            if (studentsList.get(i).getUserid().equals(studentId)) {
                studentsList.remove(i);
                save();

            }
        }

    }

    public void editInstructor(String instructorId, Instructor updatedInstructor) {
        for (int i = 0; i < instructorsList.size(); i++) {
            if (instructorsList.get(i).getUserID().equals(instructorId)) {
                instructorsList.set(i, updatedInstructor);
                save();

            }
        }

    }

    public void deleteInstructor(String instructorId) {
        for (int i = 0; i < instructorsList.size(); i++) {
            if (instructorsList.get(i).getUserID().equals(instructorId)) {
                instructorsList.remove(i);
                save();

            }
        }

    }

    public void editStudent(String studentId, StudentManagement updatedStudent) {
        for (int i = 0; i < studentsList.size(); i++) {
            if (studentsList.get(i).getUserid().equals(studentId)) {
                studentsList.set(i, updatedStudent);
                save();

            }
        }

    }

    private static class DatabaseWrapper {

        ArrayList<StudentManagement> studentsList;
        ArrayList<Instructor> instructorsList;
    }
}
