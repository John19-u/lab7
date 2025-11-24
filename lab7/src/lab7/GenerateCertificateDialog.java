package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GenerateCertificateDialog extends javax.swing.JDialog {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private CertificateService certificateService;
    private boolean certificateGenerated = false;

    public GenerateCertificateDialog(java.awt.Frame parent, boolean modal, 
                                   StudentManagement student, UserDatabase userDatabase, 
                                   CourseDatabase courseDatabase) {
        super(parent, modal);
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        this.certificateService = new CertificateService(userDatabase, courseDatabase);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle("Generate Certificate");
        loadEligibleCourses();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        generateBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course", "Lessons Completed", "Quizzes Passed", "Eligible"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(coursesTable);

        generateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        generateBtn.setText("Generate Certificate");
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBtnActionPerformed(evt);
            }
        });

        cancelBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Generate Certificate");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabel2.setText("Select a course to generate certificate:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadEligibleCourses() {
        DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
        model.setRowCount(0);
        
        ArrayList<String> enrolledCourses = student.getEnrolledCourses();
        
        for (String courseId : enrolledCourses) {
            CourseManagement course = courseDatabase.findCourseById(courseId);
            if (course != null) {
              
                boolean hasCompletedAllLessons = student.hasCompletedAllLessons(course);
                boolean hasPassedAllQuizzes = student.hasPassedAllQuizzes(course);
                boolean alreadyHasCertificate = userDatabase.hasCertificateForCourse(student.getUserId(), courseId);
                boolean isEligible = hasCompletedAllLessons && hasPassedAllQuizzes && !alreadyHasCertificate;
                
                String lessonsStatus = hasCompletedAllLessons ? "✓ All Completed" : "Incomplete";
                String quizzesStatus = hasPassedAllQuizzes ? "✓ All Passed" : "Not Passed";
                String eligibleStatus = isEligible ? "✓ ELIGIBLE" : alreadyHasCertificate ? "Already Certified" : "Not Eligible";
                
                model.addRow(new Object[]{
                    course.getTitle(),
                    lessonsStatus,
                    quizzesStatus,
                    eligibleStatus
                });
            }
        }
    }

    private void generateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a course to generate certificate.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseTitle = (String) coursesTable.getValueAt(selectedRow, 0);
        String eligibleStatus = (String) coursesTable.getValueAt(selectedRow, 3);
        
        if (!eligibleStatus.equals("✓ ELIGIBLE")) {
            JOptionPane.showMessageDialog(this, 
                "This course is not eligible for certificate generation.\n" +
                "Make sure you have completed all lessons and passed all quizzes.", 
                "Not Eligible", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
       
        String courseId = null;
        for (String enrolledCourseId : student.getEnrolledCourses()) {
            CourseManagement course = courseDatabase.findCourseById(enrolledCourseId);
            if (course != null && course.getTitle().equals(courseTitle)) {
                courseId = enrolledCourseId;
                break;
            }
        }
        
        if (courseId != null) {
            try {
                Certificate certificate = certificateService.generateCertificate(student.getUserId(), courseId);
                certificateGenerated = true;
                
                JOptionPane.showMessageDialog(this, 
                    "Certificate generated successfully!\n\n" +
                    "Course: " + certificate.getCourseTitle() + "\n" +
                    "Certificate ID: " + certificate.getCertificateId() + "\n" +
                    "Final Score: " + String.format("%.1f%%", certificate.getFinalScore()), 
                    "Certificate Generated", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error generating certificate: " + e.getMessage(), 
                    "Generation Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }
    
    public boolean isCertificateGenerated() {
        return certificateGenerated;
    }

                   
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton generateBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
                      
}