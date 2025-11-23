package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDashboard extends JFrame {
    private Admin admin;
    private UserDatabase userDB;
    private CourseDatabase courseDB;
    
   
    private JTabbedPane tabbedPane;
    private JPanel pendingCoursesPanel;
    private JPanel allCoursesPanel;
    private JPanel usersPanel;
    
    
    private JTable pendingCoursesTable;
    private DefaultTableModel pendingTableModel;
    private JButton approveButton;
    private JButton rejectButton;
    private JTextArea courseDetailsArea;
   
    private JTable allCoursesTable;
    private DefaultTableModel allCoursesTableModel;
   
    private JTable usersTable;
    private DefaultTableModel usersTableModel;

    public AdminDashboard(Admin admin, UserDatabase userDB, CourseDatabase courseDB) {
        this.admin = admin;
        this.userDB = userDB;
        this.courseDB = courseDB;
        
        initializeUI();
        loadPendingCourses();
        loadAllCourses();
        loadUsers();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard - Course Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
       
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setBackground(new Color(70, 130, 180));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + admin.getUsername() + " (Admin)");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(logoutButton);
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        
        tabbedPane = new JTabbedPane();
        
        
        pendingCoursesPanel = createPendingCoursesPanel();
        allCoursesPanel = createAllCoursesPanel();
        usersPanel = createUsersPanel();
        
        tabbedPane.addTab("Pending Course Approval", pendingCoursesPanel);
        tabbedPane.addTab("All Courses", allCoursesPanel);
        tabbedPane.addTab("User Management", usersPanel);
        
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createPendingCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
       
        JLabel titleLabel = new JLabel("Pending Courses for Review");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
       
        String[] columnNames = {"Course ID", "Title", "Instructor", "Students", "Lessons", "Status"};
        pendingTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        pendingCoursesTable = new JTable(pendingTableModel);
        pendingCoursesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pendingCoursesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displaySelectedCourseDetails();
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(pendingCoursesTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 200));
        
        
        courseDetailsArea = new JTextArea(8, 50);
        courseDetailsArea.setEditable(false);
        courseDetailsArea.setLineWrap(true);
        courseDetailsArea.setWrapStyleWord(true);
        courseDetailsArea.setBorder(BorderFactory.createTitledBorder("Course Details"));
        JScrollPane detailsScrollPane = new JScrollPane(courseDetailsArea);
        
       
        JPanel buttonPanel = new JPanel(new FlowLayout());
        approveButton = new JButton("Approve Course");
        rejectButton = new JButton("Reject Course");
        JButton refreshButton = new JButton("Refresh List");
        
        approveButton.addActionListener(e -> approveSelectedCourse());
        rejectButton.addActionListener(e -> rejectSelectedCourse());
        refreshButton.addActionListener(e -> loadPendingCourses());
        
        approveButton.setEnabled(false);
        rejectButton.setEnabled(false);
        
        approveButton.setBackground(new Color(34, 139, 34));
        approveButton.setForeground(Color.WHITE);
        rejectButton.setBackground(new Color(220, 20, 60));
        rejectButton.setForeground(Color.WHITE);
        
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(refreshButton);
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.add(tableScrollPane, BorderLayout.NORTH);
        contentPanel.add(detailsScrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createAllCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columnNames = {"Course ID", "Title", "Instructor", "Status", "Students", "Lessons"};
        allCoursesTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        allCoursesTable = new JTable(allCoursesTableModel);
        JScrollPane scrollPane = new JScrollPane(allCoursesTable);
        
        
        JPanel filterPanel = new JPanel(new FlowLayout());
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All", "PENDING", "APPROVED", "REJECTED"});
        JButton filterButton = new JButton("Filter");
        JButton refreshButton = new JButton("Refresh All");
        
        filterButton.addActionListener(e -> {
            String selectedStatus = (String) statusFilter.getSelectedItem();
            loadAllCourses(selectedStatus.equals("All") ? null : selectedStatus);
        });
        
        refreshButton.addActionListener(e -> loadAllCourses());
        
        filterPanel.add(new JLabel("Filter by Status:"));
        filterPanel.add(statusFilter);
        filterPanel.add(filterButton);
        filterPanel.add(refreshButton);
        
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columnNames = {"User ID", "Username", "Email", "Role"};
        usersTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        usersTable = new JTable(usersTableModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        
        JButton refreshButton = new JButton("Refresh Users");
        refreshButton.addActionListener(e -> loadUsers());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        return panel;
    }

    private void loadPendingCourses() {
        pendingTableModel.setRowCount(0);
        
        ArrayList<CourseManagement> pendingCourses = courseDB.getPendingCourses();
        
        if (pendingCourses.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No pending courses for review.", 
                "Information", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        for (CourseManagement course : pendingCourses) {
            String instructorName = "Unknown";
            Instructor instructor = userDB.findInstructorById(course.getInstructorId());
            if (instructor != null) {
                instructorName = instructor.getUsername();
            }
            
            pendingTableModel.addRow(new Object[]{
                course.getCourseId(),
                course.getTitle(),
                instructorName,
                course.getStudentCount(),
                course.getLessonCount(),
                course.getStatus()
            });
        }
        
        courseDetailsArea.setText("");
        approveButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }

    private void displaySelectedCourseDetails() {
        int selectedRow = pendingCoursesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String courseId = (String) pendingTableModel.getValueAt(selectedRow, 0);
            CourseManagement course = courseDB.findCourseById(courseId);
            
            if (course != null) {
                String instructorName = "Unknown";
                Instructor instructor = userDB.findInstructorById(course.getInstructorId());
                if (instructor != null) {
                    instructorName = instructor.getUsername();
                }
                
                String details = String.format(
                    "Course ID: %s\n" +
                    "Title: %s\n" +
                    "Instructor: %s (ID: %s)\n" +
                    "Description: %s\n" +
                    "Status: %s\n" +
                    "Number of Lessons: %d\n" +
                    "Number of Enrolled Students: %d\n\n" +
                    "Please review this course and click Approve or Reject.",
                    course.getCourseId(),
                    course.getTitle(),
                    instructorName,
                    course.getInstructorId(),
                    course.getDescription(),
                    course.getStatus(),
                    course.getLessonCount(),
                    course.getStudentCount()
                );
                
                courseDetailsArea.setText(details);
                approveButton.setEnabled(true);
                rejectButton.setEnabled(true);
            }
        }
    }

    private void approveSelectedCourse() {
        int selectedRow = pendingCoursesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String courseId = (String) pendingTableModel.getValueAt(selectedRow, 0);
            String courseTitle = (String) pendingTableModel.getValueAt(selectedRow, 1);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to APPROVE the course:\n" + courseTitle + "?",
                "Confirm Approval",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = admin.approveCourse(courseDB, courseId);
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Course approved successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadPendingCourses();
                    loadAllCourses();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to approve course.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void rejectSelectedCourse() {
        int selectedRow = pendingCoursesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String courseId = (String) pendingTableModel.getValueAt(selectedRow, 0);
            String courseTitle = (String) pendingTableModel.getValueAt(selectedRow, 1);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to REJECT the course:\n" + courseTitle + "?",
                "Confirm Rejection",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = admin.rejectCourse(courseDB, courseId);
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Course rejected successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadPendingCourses();
                    loadAllCourses();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to reject course.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void loadAllCourses() {
        loadAllCourses(null);
    }
    
    private void loadAllCourses(String statusFilter) {
        allCoursesTableModel.setRowCount(0);
        
        ArrayList<CourseManagement> courses;
        if (statusFilter == null) {
            courses = courseDB.getAllCourses();
        } else {
            courses = courseDB.getCoursesByStatus(statusFilter);
        }
        
        for (CourseManagement course : courses) {
            String instructorName = "Unknown";
            Instructor instructor = userDB.findInstructorById(course.getInstructorId());
            if (instructor != null) {
                instructorName = instructor.getUsername();
            }
            
            allCoursesTableModel.addRow(new Object[]{
                course.getCourseId(),
                course.getTitle(),
                instructorName,
                course.getStatus(),
                course.getStudentCount(),
                course.getLessonCount()
            });
        }
    }

    private void loadUsers() {
        usersTableModel.setRowCount(0);
        
        
        for (StudentManagement student : userDB.getAllStudents()) {
            usersTableModel.addRow(new Object[]{
                student.getUserId(),
                student.getUsername(),
                student.getEmail(),
                "Student"
            });
        }
       
        for (Instructor instructor : userDB.getAllInstructors()) {
            usersTableModel.addRow(new Object[]{
                instructor.getUserId(),
                instructor.getUsername(),
                instructor.getEmail(),
                "Instructor"
            });
        }
        
        for (Admin admin : userDB.getAllAdmins()) {
            usersTableModel.addRow(new Object[]{
                admin.getUserId(),
                admin.getUsername(),
                admin.getEmail(),
                "Admin"
            });
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new logIn().setVisible(true);
        }
    }

    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(() -> {
            UserDatabase userDB = new UserDatabase("users.json");
            CourseDatabase courseDB = new CourseDatabase("courses.json");
            Admin admin = userDB.getAllAdmins().get(0); 
            
            new AdminDashboard(admin, userDB, courseDB).setVisible(true);
        });
    }
}