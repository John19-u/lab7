package lab7;

import javax.swing.*;

public class EditLessonDetailsFrame extends javax.swing.JFrame {

    private Instructor instructor;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private CourseManagement course;
    private Lesson lesson;

    public EditLessonDetailsFrame(Instructor instructor, UserDatabase userDatabase, CourseDatabase courseDatabase, CourseManagement course, Lesson lesson) {
        this.instructor = instructor;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        this.course = course;
        this.lesson = lesson;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Edit Lesson: " + lesson.getTitle());
        loadLessonData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lessonIdLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lessonTitleField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lessonContentArea = new javax.swing.JTextArea();
        updateBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        courseLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Edit Lesson");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Lesson ID:");

        lessonIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lessonIdLabel.setText(lesson.getLessonId());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setText("Lesson Title:");

        lessonTitleField.setFont(new java.awt.Font("Segoe UI", 0, 14));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel4.setText("Lesson Content:");

        lessonContentArea.setColumns(20);
        lessonContentArea.setRows(5);
        jScrollPane1.setViewportView(lessonContentArea);

        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        updateBtn.setText("Update Lesson");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        courseLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        courseLabel.setText("Course: " + course.getTitle());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(courseLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(lessonIdLabel)
                            .addComponent(lessonTitleField)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(backBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(courseLabel)
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(lessonIdLabel)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addComponent(lessonTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadLessonData() {
        lessonTitleField.setText(lesson.getTitle());
        lessonContentArea.setText(lesson.getContent());
    }

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String title = lessonTitleField.getText().trim();
        String content = lessonContentArea.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lesson title is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            lesson.setTitle(title);
            lesson.setContent(content);
            instructor.editLesson(lesson.getLessonId(), course.getCourseId(), lesson, courseDatabase);
            
            JOptionPane.showMessageDialog(this, "Lesson updated successfully: " + title, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating lesson: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        EditLessonFrame editFrame = new EditLessonFrame(instructor, userDatabase, courseDatabase);
        editFrame.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lessonIdLabel;
    private javax.swing.JTextArea lessonContentArea;
    private javax.swing.JTextField lessonTitleField;
    private javax.swing.JButton updateBtn;
    // End of variables declaration                   
}