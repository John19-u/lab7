package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AddLessonFrame extends javax.swing.JFrame {

    private Instructor instructor;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private JLabel jLabel6;
    private JTextField lessonOrderField;

    public AddLessonFrame(Instructor instructor, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.instructor = instructor;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Add Lesson");
        loadInstructorCourses();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        courseComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        lessonIdField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lessonTitleField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lessonContentArea = new javax.swing.JTextArea();
        addBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lessonOrderField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Add New Lesson");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Select Course:");

        courseComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14));
        courseComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setText("Lesson ID:");

        lessonIdField.setFont(new java.awt.Font("Segoe UI", 0, 14));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel4.setText("Lesson Title:");

        lessonTitleField.setFont(new java.awt.Font("Segoe UI", 0, 14));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel5.setText("Lesson Content:");

        lessonContentArea.setColumns(20);
        lessonContentArea.setRows(5);
        lessonContentArea.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jScrollPane1.setViewportView(lessonContentArea);

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        addBtn.setText("Add Lesson");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel6.setText("Lesson Order:");

        lessonOrderField.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lessonOrderField.setText("0");

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
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(courseComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lessonIdField)
                            .addComponent(lessonTitleField)
                            .addComponent(lessonOrderField)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(backBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(lessonIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(5, 5, 5)
                .addComponent(lessonTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(5, 5, 5)
                .addComponent(lessonOrderField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        if (instructorCourses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to create a course first before adding lessons.", "No Courses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void courseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // Auto-calculate next order number when course is selected
        if (courseComboBox.getSelectedItem() != null) {
            String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
            CourseManagement course = courseDatabase.findCourseById(courseId);
            if (course != null) {
                int nextOrder = course.getLessons().size() + 1;
                lessonOrderField.setText(String.valueOf(nextOrder));
            }
        }
    }

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (courseComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a course.", "No Course Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
        String lessonId = lessonIdField.getText().trim();
        String title = lessonTitleField.getText().trim();
        String content = lessonContentArea.getText().trim();
        String orderText = lessonOrderField.getText().trim();

        if (lessonId.isEmpty() || title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lesson ID and Title are required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate order
        int order;
        try {
            order = Integer.parseInt(orderText);
            if (order < 0) {
                JOptionPane.showMessageDialog(this, "Lesson order must be a positive number.", "Invalid Order", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lesson order must be a valid number.", "Invalid Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CourseManagement course = courseDatabase.findCourseById(courseId);
        if (course == null) {
            JOptionPane.showMessageDialog(this, "Selected course not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate lesson ID
        if (course.getLessonById(lessonId) != null) {
            JOptionPane.showMessageDialog(this, "Lesson ID already exists in this course.", "Duplicate Lesson ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Use the updated constructor with order
            Lesson newLesson = new Lesson(lessonId, title, content, order);
            instructor.addLesson(newLesson, courseId, courseDatabase);
            
            JOptionPane.showMessageDialog(this, 
                "Lesson added successfully: " + title + "\nYou can now add a quiz to this lesson.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Ask if they want to add a quiz
            int option = JOptionPane.showConfirmDialog(this, 
                "Would you like to create a quiz for this lesson now?", 
                "Add Quiz?", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                // Open quiz creation frame
                CreateQuizFrame quizFrame = new CreateQuizFrame(instructor, courseDatabase, newLesson);
                quizFrame.setVisible(true);
            }
            
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding lesson: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        lessonIdField.setText("");
        lessonTitleField.setText("");
        lessonContentArea.setText("");
        // Don't clear order field, keep it for next lesson
        if (courseComboBox.getSelectedItem() != null) {
            courseComboBoxActionPerformed(null); // Recalculate order
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        InstructorDashboard dashboard = new InstructorDashboard(instructor, userDatabase, courseDatabase);
        dashboard.setVisible(true);
    }

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

    // Variables declaration - CORRECTED VERSION
    private javax.swing.JButton addBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JComboBox<CourseComboItem> courseComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lessonIdField;
    private javax.swing.JTextArea lessonContentArea;
    private javax.swing.JTextField lessonTitleField;
    // End of variables declaration
}