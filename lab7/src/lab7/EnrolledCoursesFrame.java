package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EnrolledCoursesFrame extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;

    public EnrolledCoursesFrame(StudentManagement student, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("My Enrolled Courses - " + student.getUsername());
        loadEnrolledCourses();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        enrolledTable = new javax.swing.JTable();
        viewLessonsBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        enrolledTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course ID", "Title", "Description", "Lessons", "Progress"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        jScrollPane1.setViewportView(enrolledTable);

        viewLessonsBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        viewLessonsBtn.setText("View Lessons");
        viewLessonsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewLessonsBtnActionPerformed(evt);
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
        jLabel1.setText("My Enrolled Courses");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(viewLessonsBtn))
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
                    .addComponent(viewLessonsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadEnrolledCourses() {
        DefaultTableModel model = (DefaultTableModel) enrolledTable.getModel();
        model.setRowCount(0);
        
        for (String courseId : student.getEnrolledCourses()) {
            CourseManagement course = courseDatabase.findCourseById(courseId);
            if (course != null) {
                int totalLessons = course.getLessons().size();
                int completedLessons = 0;
                
                for (Lesson lesson : course.getLessons()) {
                    if (student.hasCompletedLesson(lesson.getLessonId())) {
                        completedLessons++;
                    }
                }
                
                String progress = completedLessons + "/" + totalLessons;
                if (totalLessons > 0) {
                    int percentage = (completedLessons * 100) / totalLessons;
                    progress += " (" + percentage + "%)";
                }
                
                model.addRow(new Object[]{
                    course.getCourseId(),
                    course.getTitle(),
                    course.getDescription(),
                    totalLessons,
                    progress
                });
            }
        }
    }

    private void viewLessonsBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = enrolledTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to view lessons.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseId = (String) enrolledTable.getValueAt(selectedRow, 0);
        CourseManagement course = courseDatabase.findCourseById(courseId);
        
        if (course != null) {
            this.dispose();
            CourseLessonsFrame lessonsFrame = new CourseLessonsFrame(student, userDatabase, courseDatabase, course);
            lessonsFrame.setVisible(true);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        StudentDashboard dashboard = new StudentDashboard(student, userDatabase);
        dashboard.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backBtn;
    private javax.swing.JTable enrolledTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton viewLessonsBtn;
    // End of variables declaration                   
}