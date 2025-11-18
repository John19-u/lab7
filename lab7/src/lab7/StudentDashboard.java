package lab7;

import javax.swing.JFrame;

public class StudentDashboard extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;

    public StudentDashboard(StudentManagement student, UserDatabase userDatabase) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = new CourseDatabase("courses.json");
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Student Dashboard - " + student.getUsername());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        viewCoursesBtn = new javax.swing.JButton();
        enrollCoursesBtn = new javax.swing.JButton();
        progressBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Student Dashboard");

        viewCoursesBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        viewCoursesBtn.setText("View Available Courses");
        viewCoursesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewCoursesBtnActionPerformed(evt);
            }
        });

        enrollCoursesBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        enrollCoursesBtn.setText("My Enrolled Courses");
        enrollCoursesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enrollCoursesBtnActionPerformed(evt);
            }
        });

        progressBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        progressBtn.setText("View Progress");
        progressBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progressBtnActionPerformed(evt);
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
        welcomeLabel.setText("Welcome, " + student.getUsername() + "!");

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
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(viewCoursesBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(enrollCoursesBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(progressBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
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
                .addComponent(viewCoursesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(enrollCoursesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(progressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    private void viewCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        BrowseCoursesFrame browseFrame = new BrowseCoursesFrame(student, userDatabase, courseDatabase);
        browseFrame.setVisible(true);
    }

    private void enrollCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        EnrolledCoursesFrame enrolledFrame = new EnrolledCoursesFrame(student, userDatabase, courseDatabase);
        enrolledFrame.setVisible(true);
    }

    private void progressBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        StudentProgressFrame progressFrame = new StudentProgressFrame(student, userDatabase, courseDatabase);
        progressFrame.setVisible(true);
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
                StudentManagement testStudent = new StudentManagement("Test", "Student", "hash", "123", "test@test.com");
                new StudentDashboard(testStudent, userDb).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton enrollCoursesBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton progressBtn;
    private javax.swing.JButton viewCoursesBtn;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration                   
}