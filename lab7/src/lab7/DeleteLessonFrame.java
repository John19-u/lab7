package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DeleteLessonFrame extends javax.swing.JFrame {

    private Instructor instructor;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;

    public DeleteLessonFrame(Instructor instructor, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.instructor = instructor;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Delete Lesson");
        loadInstructorCourses();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        courseComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lessonsTable = new javax.swing.JTable();
        deleteBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Delete Lesson");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Select Course:");

        courseComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14));
        courseComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setText("Lessons:");

        lessonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Lesson ID", "Title", "Content Length"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        deleteBtn.setText("Delete Selected Lesson");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(courseComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(backBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadInstructorCourses() {
        courseComboBox.removeAllItems();
        
        ArrayList<CourseManagement> instructorCourses = courseDatabase.getCoursesByInstructor(instructor.getUserId());
        for (CourseManagement course : instructorCourses) {
            courseComboBox.addItem(new CourseComboItem(course.getCourseId(), course.getTitle()));
        }
    }

    private void loadLessonsForCourse(String courseId) {
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);
        
        CourseManagement course = courseDatabase.findCourseById(courseId);
        if (course != null) {
            for (Lesson lesson : course.getLessons()) {
                model.addRow(new Object[]{
                    lesson.getLessonId(),
                    lesson.getTitle(),
                    lesson.getContent().length()
                });
            }
        }
    }

    private void courseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        if (courseComboBox.getSelectedItem() != null) {
            String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
            loadLessonsForCourse(courseId);
        }
    }

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (courseComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a course.", "No Course Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int selectedRow = lessonsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        String lessonTitle = (String) lessonsTable.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete the lesson:\n" + lessonTitle + "\n\nThis action cannot be undone!", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            instructor.deleteLesson(lessonId, courseId, courseDatabase);
            JOptionPane.showMessageDialog(this, "Lesson deleted successfully: " + lessonTitle, "Success", JOptionPane.INFORMATION_MESSAGE);
            loadLessonsForCourse(courseId);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        InstructorDashboard dashboard = new InstructorDashboard(instructor, userDatabase, courseDatabase);
        dashboard.setVisible(true);
    }

    // Helper class for combo box items
    private class CourseComboItem {
        private String id;
        private String title;
        
        public CourseComboItem(String id, String title) {
            this.id = id;
            this.title = title;
        }
        
        public String getId() { return id; }
        
        @Override
        public String toString() {
            return title + " (" + id + ")";
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JComboBox<CourseComboItem> courseComboBox;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lessonsTable;
    // End of variables declaration                   
}