package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BrowseCoursesFrame extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;

    public BrowseCoursesFrame(StudentManagement student, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Browse Courses - " + student.getUsername());
        loadCourses();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        enrollBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JButton(); 

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course ID", "Title", "Description", "Instructor", "Status" 
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(coursesTable);

        enrollBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        enrollBtn.setText("Enroll in Selected Course");
        enrollBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enrollBtnActionPerformed(evt);
            }
        });

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Available Approved Courses");

        refreshBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        refreshBtn.setText("Refresh Courses");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enrollBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enrollBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadCourses() {
        DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
        model.setRowCount(0); 
        
        ArrayList<CourseManagement> approvedCourses = courseDatabase.getAllApprovedCourses();
        
        if (approvedCourses.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No approved courses available at the moment.\nPlease check back later.", 
                "No Courses Available", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int availableCourses = 0;
        for (CourseManagement course : approvedCourses) {
        
            if (!student.isEnrolledInCourse(course.getCourseId())) {
                String instructorName = "Unknown Instructor";
                Instructor instructor = userDatabase.findInstructorById(course.getInstructorId());
                if (instructor != null) {
                    instructorName = instructor.getUsername();
                }
                
                model.addRow(new Object[]{
                    course.getCourseId(),
                    course.getTitle(),
                    course.getDescription(),
                    instructorName,
                    course.getStatus() 
                });
                availableCourses++;
            }
        }
        
        if (availableCourses == 0) {
            JOptionPane.showMessageDialog(this, 
                "You are already enrolled in all available courses!", 
                "All Courses Enrolled", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void enrollBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a course to enroll in.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseId = (String) coursesTable.getValueAt(selectedRow, 0);
        String courseTitle = (String) coursesTable.getValueAt(selectedRow, 1);
        CourseManagement course = courseDatabase.findCourseById(courseId);
        
        if (course != null) {
            
            if (!course.isApproved()) {
                JOptionPane.showMessageDialog(this, 
                    "This course is not available for enrollment. Please select an approved course.", 
                    "Course Not Available", 
                    JOptionPane.ERROR_MESSAGE);
                loadCourses(); 
                return;
            }
            
           
            if (student.isEnrolledInCourse(courseId)) {
                JOptionPane.showMessageDialog(this, 
                    "You are already enrolled in this course.", 
                    "Already Enrolled", 
                    JOptionPane.WARNING_MESSAGE);
                loadCourses(); 
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to enroll in:\n" + courseTitle + "?",
                "Confirm Enrollment",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    
                    student.enrollCourse(courseId);
                    course.enrollStudent(student.getUserId());
                  
                    userDatabase.editStudent(student.getUserId(), student);
                    courseDatabase.editCourse(courseId, course);
                    
                    JOptionPane.showMessageDialog(this, 
                        "Successfully enrolled in: " + course.getTitle(), 
                        "Enrollment Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
                  
                    loadCourses();
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error during enrollment: " + e.getMessage(), 
                        "Enrollment Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        StudentDashboard dashboard = new StudentDashboard(student, userDatabase);
        dashboard.setVisible(true);
    }

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {
        loadCourses();
        JOptionPane.showMessageDialog(this, 
            "Course list refreshed!", 
            "Refresh Complete", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backBtn;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton enrollBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshBtn;
    // End of variables declaration                   
}