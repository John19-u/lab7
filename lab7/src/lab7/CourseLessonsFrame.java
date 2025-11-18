package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CourseLessonsFrame extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private CourseManagement course;

    public CourseLessonsFrame(StudentManagement student, UserDatabase userDatabase, CourseDatabase courseDatabase, CourseManagement course) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        this.course = course;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Lessons - " + course.getTitle());
        loadLessons();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lessonsTable = new javax.swing.JTable();
        viewLessonBtn = new javax.swing.JButton();
        markCompleteBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        courseTitleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lessonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Lesson ID", "Title", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(lessonsTable);

        viewLessonBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        viewLessonBtn.setText("View Lesson");
        viewLessonBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewLessonBtnActionPerformed(evt);
            }
        });

        markCompleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        markCompleteBtn.setText("Mark as Completed");
        markCompleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markCompleteBtnActionPerformed(evt);
            }
        });

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Courses");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Course Lessons");

        courseTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        courseTitleLabel.setText("Course: " + course.getTitle());

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
                        .addComponent(markCompleteBtn)
                        .addGap(18, 18, 18)
                        .addComponent(viewLessonBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(courseTitleLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(courseTitleLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewLessonBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(markCompleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadLessons() {
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);
        
        for (Lesson lesson : course.getLessons()) {
            String status = student.hasCompletedLesson(lesson.getLessonId()) ? "Completed" : "Not Started";
            model.addRow(new Object[]{
                lesson.getLessonId(),
                lesson.getTitle(),
                status
            });
        }
    }

    private void viewLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = lessonsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to view.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        Lesson lesson = course.getLessonById(lessonId);
        
        if (lesson != null) {
            ViewLessonFrame lessonFrame = new ViewLessonFrame(student, userDatabase, courseDatabase, course, lesson);
            lessonFrame.setVisible(true);
        }
    }

    private void markCompleteBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = lessonsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to mark as completed.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        String lessonTitle = (String) lessonsTable.getValueAt(selectedRow, 1);
        
        if (!student.hasCompletedLesson(lessonId)) {
            student.markLessonCompleted(lessonId);
            userDatabase.editStudent(student.getUserId(), student);
            
            JOptionPane.showMessageDialog(this, "Lesson marked as completed: " + lessonTitle, "Success", JOptionPane.INFORMATION_MESSAGE);
            loadLessons();
        } else {
            JOptionPane.showMessageDialog(this, "Lesson is already completed: " + lessonTitle, "Already Completed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        EnrolledCoursesFrame coursesFrame = new EnrolledCoursesFrame(student, userDatabase, courseDatabase);
        coursesFrame.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel courseTitleLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lessonsTable;
    private javax.swing.JButton markCompleteBtn;
    private javax.swing.JButton viewLessonBtn;
    // End of variables declaration                   
}