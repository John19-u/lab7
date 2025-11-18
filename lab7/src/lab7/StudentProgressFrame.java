package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class StudentProgressFrame extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;

    public StudentProgressFrame(StudentManagement student, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("My Progress - " + student.getUsername());
        loadProgress();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        progressTable = new javax.swing.JTable();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        totalCoursesLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        completedLessonsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        progressTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course", "Total Lessons", "Completed", "Progress"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(progressTable);

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("My Learning Progress");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Total Courses Enrolled:");

        totalCoursesLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        totalCoursesLabel.setText("0");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setText("Total Lessons Completed:");

        completedLessonsLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        completedLessonsLabel.setText("0");

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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(totalCoursesLabel)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(completedLessonsLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(totalCoursesLabel)
                    .addComponent(jLabel3)
                    .addComponent(completedLessonsLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadProgress() {
        DefaultTableModel model = (DefaultTableModel) progressTable.getModel();
        model.setRowCount(0);
        
        int totalCourses = student.getEnrolledCourses().size();
        int totalCompletedLessons = student.getProgress().size();
        
        totalCoursesLabel.setText(String.valueOf(totalCourses));
        completedLessonsLabel.setText(String.valueOf(totalCompletedLessons));
        
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
                
                String progress;
                if (totalLessons > 0) {
                    int percentage = (completedLessons * 100) / totalLessons;
                    progress = percentage + "%";
                } else {
                    progress = "No lessons";
                }
                
                model.addRow(new Object[]{
                    course.getTitle(),
                    totalLessons,
                    completedLessons,
                    progress
                });
            }
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        StudentDashboard dashboard = new StudentDashboard(student, userDatabase);
        dashboard.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel completedLessonsLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable progressTable;
    private javax.swing.JLabel totalCoursesLabel;
    // End of variables declaration                   
}