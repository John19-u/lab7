package lab7;

import javax.swing.JFrame;

public class InstructorDashboard extends javax.swing.JFrame {

    private Instructor instructor;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;

    public InstructorDashboard(Instructor instructor, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.instructor = instructor;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Instructor Dashboard - " + instructor.getUsername());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        createCourseBtn = new javax.swing.JButton();
        editCourseBtn = new javax.swing.JButton();
        deleteCourseBtn = new javax.swing.JButton();
        addLessonBtn = new javax.swing.JButton();
        editLessonBtn = new javax.swing.JButton();
        deleteLessonBtn = new javax.swing.JButton();
        viewStudentsBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Instructor Dashboard");

        createCourseBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        createCourseBtn.setText("Create Course");
        createCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCourseBtnActionPerformed(evt);
            }
        });

        editCourseBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        editCourseBtn.setText("Edit Course");
        editCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCourseBtnActionPerformed(evt);
            }
        });

        deleteCourseBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        deleteCourseBtn.setText("Delete Course");
        deleteCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCourseBtnActionPerformed(evt);
            }
        });

        addLessonBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        addLessonBtn.setText("Add Lesson");
        addLessonBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLessonBtnActionPerformed(evt);
            }
        });

        editLessonBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        editLessonBtn.setText("Edit Lesson");
        editLessonBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editLessonBtnActionPerformed(evt);
            }
        });

        deleteLessonBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        deleteLessonBtn.setText("Delete Lesson");
        deleteLessonBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLessonBtnActionPerformed(evt);
            }
        });

        viewStudentsBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        viewStudentsBtn.setText("View Students");
        viewStudentsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStudentsBtnActionPerformed(evt);
            }
        });

        logoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        welcomeLabel.setText("Welcome, " + instructor.getUsername() + "!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(welcomeLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(createCourseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editCourseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteCourseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addLessonBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editLessonBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteLessonBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(viewStudentsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(welcomeLabel)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addLessonBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editLessonBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteLessonBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewStudentsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    private void createCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        CreateCourseFrame createFrame = new CreateCourseFrame(instructor, userDatabase, courseDatabase);
        createFrame.setVisible(true);
    }

    private void editCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        EditCourseFrame editFrame = new EditCourseFrame(instructor, userDatabase, courseDatabase);
        editFrame.setVisible(true);
    }

    private void deleteCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        DeleteCourseFrame deleteFrame = new DeleteCourseFrame(instructor, userDatabase, courseDatabase);
        deleteFrame.setVisible(true);
    }

    private void addLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        AddLessonFrame addFrame = new AddLessonFrame(instructor, userDatabase, courseDatabase);
        addFrame.setVisible(true);
    }

    private void editLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        EditLessonFrame editFrame = new EditLessonFrame(instructor, userDatabase, courseDatabase);
        editFrame.setVisible(true);
    }

    private void deleteLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        DeleteLessonFrame deleteFrame = new DeleteLessonFrame(instructor, userDatabase, courseDatabase);
        deleteFrame.setVisible(true);
    }

    private void viewStudentsBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        ViewStudentsFrame studentsFrame = new ViewStudentsFrame(instructor, userDatabase, courseDatabase);
        studentsFrame.setVisible(true);
    }

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        logIn loginFrame = new logIn();
        loginFrame.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // For testing only
                UserDatabase userDb = new UserDatabase("users.json");
                CourseDatabase courseDb = new CourseDatabase("courses.json");
                Instructor testInstructor = new Instructor("456", "Instructor", "Test Instructor", "instructor@test.com", "hash");
                new InstructorDashboard(testInstructor, userDb, courseDb).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton addLessonBtn;
    private javax.swing.JButton createCourseBtn;
    private javax.swing.JButton deleteCourseBtn;
    private javax.swing.JButton deleteLessonBtn;
    private javax.swing.JButton editCourseBtn;
    private javax.swing.JButton editLessonBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton viewStudentsBtn;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration                   
}