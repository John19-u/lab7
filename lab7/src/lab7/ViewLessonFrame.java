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
        takeQuizBtn = new javax.swing.JButton();
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
        backBtn.addActionListener(evt -> backBtnActionPerformed(evt));

        markCompleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        markCompleteBtn.setText("Mark as Completed");
        markCompleteBtn.addActionListener(evt -> markCompleteBtnActionPerformed(evt));

        takeQuizBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        takeQuizBtn.setText("Take Quiz");
        takeQuizBtn.addActionListener(evt -> takeQuizBtnActionPerformed(evt));

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
                        .addComponent(markCompleteBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(takeQuizBtn))
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
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(takeQuizBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadLessonContent() {
        contentArea.setText(lesson.getContent());

        if (lesson.hasResources()) {
            StringBuilder resourcesText = new StringBuilder();
            for (String resource : lesson.getResources()) {
                resourcesText.append("• ").append(resource).append("\n");
            }
            resourcesLabel.setText("<html>" + resourcesText.toString().replace("\n", "<br>") + "</html>");
        } else {
            resourcesLabel.setText("No resources available");
        }

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

    private void takeQuizBtnActionPerformed(java.awt.event.ActionEvent evt) {
     System.out.println("DEBUG: Take Quiz button clicked");
    System.out.println("DEBUG: Lesson ID: " + lesson.getLessonId());
    System.out.println("DEBUG: Lesson has quiz: " + lesson.hasQuiz());
    
    if (lesson.hasQuiz()) {
        String quizId = lesson.getQuizId();
        System.out.println("DEBUG: Quiz ID from lesson: " + quizId);
        
       
        QuizService quizService = QuizService.getInstance();
        Quiz lessonQuiz = quizService.getQuiz(quizId);
        System.out.println("DEBUG: Found quiz: " + (lessonQuiz != null));
        
        if (lessonQuiz != null) {
            System.out.println("DEBUG: Quiz title: " + lessonQuiz.getTitle());
            System.out.println("DEBUG: Quiz questions: " + lessonQuiz.getQuestions().size());
            
            if (student.getQuizResult("dummy") == null) {
   
                System.out.println("DEBUG: Initialized quizResults for student");
            }
    
            boolean canRetry = quizService.canRetryQuiz(student.getUserId(), quizId);
            boolean hasPassed = student.hasPassedQuiz(lesson.getLessonId());
            
            System.out.println("DEBUG: Can retry: " + canRetry);
            System.out.println("DEBUG: Has passed: " + hasPassed);
            
            if (canRetry || !hasPassed) {
                QuizFrame quizFrame = new QuizFrame(lessonQuiz, student, userDatabase, lesson, course);
                quizFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "You have already passed this quiz with maximum attempts.", 
                    "Quiz Completed", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Quiz not found! This might be a temporary issue. Please try again or contact administrator.\nQuiz ID: " + quizId, 
                "Quiz Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, 
            "No quiz available for this lesson. Please contact instructor.", 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    }

    // Variables declaration                     
    private javax.swing.JButton backBtn;
    private javax.swing.JButton markCompleteBtn;
    private javax.swing.JButton takeQuizBtn;
    private javax.swing.JTextArea contentArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lessonTitleLabel;
    private javax.swing.JLabel resourcesLabel;
    // End of variables declaration                   
}
