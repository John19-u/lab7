package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentCertificatesFrame extends javax.swing.JFrame {

    private StudentManagement student;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private CertificateService certificateService;

    public StudentCertificatesFrame(StudentManagement student, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.student = student;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        this.certificateService = new CertificateService(userDatabase, courseDatabase);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("My Certificates - " + student.getUsername());
        loadCertificates();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        certificatesTable = new javax.swing.JTable();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        viewCertificateBtn = new javax.swing.JButton();
        downloadCertificateBtn = new javax.swing.JButton();
        generateCertificateBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        certificatesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course", "Certificate ID", "Issue Date", "Final Score", "Instructor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(certificatesTable);

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("My Certificates");

        viewCertificateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        viewCertificateBtn.setText("View Certificate");
        viewCertificateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewCertificateBtnActionPerformed(evt);
            }
        });

        downloadCertificateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        downloadCertificateBtn.setText("Download Certificate");
        downloadCertificateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadCertificateBtnActionPerformed(evt);
            }
        });

        generateCertificateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        generateCertificateBtn.setText("Generate Certificate");
        generateCertificateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateCertificateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateCertificateBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downloadCertificateBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewCertificateBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewCertificateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(downloadCertificateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateCertificateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadCertificates() {
        DefaultTableModel model = (DefaultTableModel) certificatesTable.getModel();
        model.setRowCount(0);
        
        List<Certificate> certificates = userDatabase.getCertificatesForStudent(student.getUserId());
        
        if (certificates.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "You haven't earned any certificates yet.\nComplete courses to earn certificates!", 
                "No Certificates", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (Certificate cert : certificates) {
            model.addRow(new Object[]{
                cert.getCourseTitle(),
                cert.getCertificateId(),
                cert.getIssueDate().toString(),
                String.format("%.1f%%", cert.getFinalScore()),
                cert.getInstructorName()
            });
        }
    }

    private void viewCertificateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = certificatesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a certificate to view.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String certificateId = (String) certificatesTable.getValueAt(selectedRow, 1);
        Certificate certificate = userDatabase.getCertificateById(certificateId);
        
        if (certificate != null) {
           
            JTextArea textArea = new JTextArea(certificate.toString());
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                "Certificate Details - " + certificate.getCourseTitle(), 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void downloadCertificateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = certificatesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a certificate to download.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String certificateId = (String) certificatesTable.getValueAt(selectedRow, 1);
        Certificate certificate = userDatabase.getCertificateById(certificateId);
        
        if (certificate != null) {
            
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Certificate As");
                fileChooser.setSelectedFile(new java.io.File(
                    "Certificate_" + certificate.getCourseTitle().replace(" ", "_") + ".txt"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileChooser.getSelectedFile();
                    java.io.PrintWriter writer = new java.io.PrintWriter(file);
                    writer.println(certificate.toString());
                    writer.close();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Certificate saved successfully!\nFile: " + file.getName(), 
                        "Download Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error saving certificate: " + e.getMessage(), 
                    "Download Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateCertificateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        
        GenerateCertificateDialog dialog = new GenerateCertificateDialog(this, true, student, userDatabase, courseDatabase);
        dialog.setVisible(true);
        
        
        if (dialog.isCertificateGenerated()) {
            loadCertificates();
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        StudentDashboard dashboard = new StudentDashboard(student, userDatabase);
        dashboard.setVisible(true);
    }

                     
    private javax.swing.JButton backBtn;
    private javax.swing.JTable certificatesTable;
    private javax.swing.JButton downloadCertificateBtn;
    private javax.swing.JButton generateCertificateBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton viewCertificateBtn;
                  
}