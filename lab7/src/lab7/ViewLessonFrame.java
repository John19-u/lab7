package lab7;

import javax.swing.*;

public class ViewLessonFrame extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private CourseManagement course;
    private Lesson lesson;

    public ViewLessonFrame(StudentManagement student, UserDatabase userDatabase, CourseDatabase courseDatabase, CourseManagement course, Lesson lesson) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        this.course = course;
        this.lesson = lesson;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Lesson: " + lesson.getTitle());
        loadLessonContent();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        contentArea = new javax.swing.JTextArea();
        backBtn = new javax.swing.JButton();
        markCompleteBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lessonTitleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        resourcesLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        contentArea.setEditable(false);
        contentArea.setColumns(20);
        contentArea.setLineWrap(true);
        contentArea.setRows(5);
        contentArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(contentArea);

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Lessons");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        markCompleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        markCompleteBtn.setText("Mark as Completed");
        markCompleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markCompleteBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Lesson Content");

        lessonTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lessonTitleLabel.setText(lesson.getTitle());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Resources:");

        resourcesLabel.setText("No resources available");

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
                        .addComponent(markCompleteBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lessonTitleLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(resourcesLabel)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(lessonTitleLabel)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(resourcesLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(markCompleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadLessonContent() {
        contentArea.setText(lesson.getContent());
        
        // Load resources
        if (lesson.hasResources()) {
            StringBuilder resourcesText = new StringBuilder();
            for (String resource : lesson.getResources()) {
                resourcesText.append("• ").append(resource).append("\n");
            }
            resourcesLabel.setText("<html>" + resourcesText.toString().replace("\n", "<br>") + "</html>");
        } else {
            resourcesLabel.setText("No resources available");
        }
        
        // Update button text if already completed
        if (student.hasCompletedLesson(lesson.getLessonId())) {
            markCompleteBtn.setText("Already Completed");
            markCompleteBtn.setEnabled(false);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void markCompleteBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!student.hasCompletedLesson(lesson.getLessonId())) {
            student.markLessonCompleted(lesson.getLessonId());
            userDatabase.editStudent(student.getUserId(), student);
            
            JOptionPane.showMessageDialog(this, "Lesson marked as completed: " + lesson.getTitle(), "Success", JOptionPane.INFORMATION_MESSAGE);
            markCompleteBtn.setText("Already Completed");
            markCompleteBtn.setEnabled(false);
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backBtn;
    private javax.swing.JTextArea contentArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lessonTitleLabel;
    private javax.swing.JButton markCompleteBtn;
    private javax.swing.JLabel resourcesLabel;
    // End of variables declaration                   
}