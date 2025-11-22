package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

public class InstructorInsightsFrame extends javax.swing.JFrame {

    private Instructor instructor;
    private UserDatabase userDatabase;
    private CourseDatabase courseDatabase;
    private InstructorAnalytics analytics;

    
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JPanel overviewPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel totalCoursesLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel totalStudentsLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel avgCompletionLabel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel avgQuizScoreLabel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel avgStudentsLabel;
    private javax.swing.JPanel coursesPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton viewDetailsBtn;
    private javax.swing.JPanel studentDetailsPanel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<CourseComboItem> courseComboBox;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable studentsTable;
    private javax.swing.JButton backBtn;
    private javax.swing.JButton refreshBtn;

    public InstructorInsightsFrame(Instructor instructor, UserDatabase userDatabase, CourseDatabase courseDatabase) {
        this.instructor = instructor;
        this.userDatabase = userDatabase;
        this.courseDatabase = courseDatabase;
        this.analytics = new InstructorAnalytics(instructor, userDatabase, courseDatabase);
        
        
        initComponents();
        
        setLocationRelativeTo(null);
        setTitle("Instructor Insights - " + instructor.getUsername());
        

        SwingUtilities.invokeLater(() -> {
            loadOverview();
            loadCoursesPerformance();
        });
    }

    private void initComponents() {
    
        tabbedPane = new javax.swing.JTabbedPane();
        overviewPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalCoursesLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        totalStudentsLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        avgCompletionLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        avgQuizScoreLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        avgStudentsLabel = new javax.swing.JLabel();
        coursesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        viewDetailsBtn = new javax.swing.JButton();
        studentDetailsPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        courseComboBox = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentsTable = new javax.swing.JTable();
        backBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

      
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabel1.setText("Total Courses:");

        totalCoursesLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        totalCoursesLabel.setText("0");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabel2.setText("Total Students:");

        totalStudentsLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        totalStudentsLabel.setText("0");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabel3.setText("Average Completion:");

        avgCompletionLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        avgCompletionLabel.setText("0%");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabel4.setText("Average Quiz Score:");

        avgQuizScoreLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        avgQuizScoreLabel.setText("0%");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabel6.setText("Avg Students/Course:");

        avgStudentsLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        avgStudentsLabel.setText("0");

        javax.swing.GroupLayout overviewPanelLayout = new javax.swing.GroupLayout(overviewPanel);
        overviewPanel.setLayout(overviewPanelLayout);
        overviewPanelLayout.setHorizontalGroup(
            overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overviewPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(overviewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(totalCoursesLabel))
                    .addGroup(overviewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(totalStudentsLabel))
                    .addGroup(overviewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(avgStudentsLabel))
                    .addGroup(overviewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(avgCompletionLabel))
                    .addGroup(overviewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(avgQuizScoreLabel)))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        overviewPanelLayout.setVerticalGroup(
            overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overviewPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalCoursesLabel))
                .addGap(15, 15, 15)
                .addGroup(overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(totalStudentsLabel))
                .addGap(15, 15, 15)
                .addGroup(overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(avgStudentsLabel))
                .addGap(15, 15, 15)
                .addGroup(overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(avgCompletionLabel))
                .addGap(15, 15, 15)
                .addGroup(overviewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(avgQuizScoreLabel))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Overview", overviewPanel);

     
        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course", "Students", "Completion %", "Avg Quiz Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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

        viewDetailsBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        viewDetailsBtn.setText("View Student Details");
        viewDetailsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDetailsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout coursesPanelLayout = new javax.swing.GroupLayout(coursesPanel);
        coursesPanel.setLayout(coursesPanelLayout);
        coursesPanelLayout.setHorizontalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(viewDetailsBtn)))
                .addGap(20, 20, 20))
        );
        coursesPanelLayout.setVerticalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(viewDetailsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Courses Performance", coursesPanel);

   
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel5.setText("Select Course:");

        courseComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14));
        courseComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBoxActionPerformed(evt);
            }
        });

        studentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Student", "Completion %", "Quizzes Taken", "Avg Quiz Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        jScrollPane2.setViewportView(studentsTable);

        javax.swing.GroupLayout studentDetailsPanelLayout = new javax.swing.GroupLayout(studentDetailsPanel);
        studentDetailsPanel.setLayout(studentDetailsPanelLayout);
        studentDetailsPanelLayout.setHorizontalGroup(
            studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        studentDetailsPanelLayout.setVerticalGroup(
            studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Student Details", studentDetailsPanel);

        // Buttons
        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        refreshBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        refreshBtn.setText("Refresh Data");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        // Main Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshBtn)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadOverview() {
        try {
            System.out.println("Loading overview...");
            Map<String, Object> overview = analytics.getInstructorOverview();
            
            System.out.println("Overview data: " + overview);
            
     
            System.out.println("totalCoursesLabel is null: " + (totalCoursesLabel == null));
            System.out.println("totalStudentsLabel is null: " + (totalStudentsLabel == null));
            
            if (overview.isEmpty()) {
                showNoDataMessage("No overview data available. Make sure you have courses with enrolled students.");
                return;
            }
            
            
            totalCoursesLabel.setText(String.valueOf(overview.get("totalCourses")));
            totalStudentsLabel.setText(String.valueOf(overview.get("totalStudents")));
            avgStudentsLabel.setText(String.format("%.1f", overview.get("averageStudentsPerCourse")));
            avgCompletionLabel.setText(String.format("%.1f%%", overview.get("overallCompletion")));
            avgQuizScoreLabel.setText(String.format("%.1f%%", overview.get("overallQuizAverage")));
            
            System.out.println("Overview loaded successfully");
        } catch (Exception e) {
            System.err.println("Error in loadOverview: " + e.getMessage());
            e.printStackTrace();
            showErrorMessage("Error loading overview: " + e.getMessage());
        }
    }

    private void loadCoursesPerformance() {
        try {
            System.out.println("Loading courses performance...");
            System.out.println("coursesTable is null: " + (coursesTable == null));
            
            DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
            model.setRowCount(0);
            
            ArrayList<Map<String, Object>> performance = analytics.getAllCoursesPerformance();
            
            System.out.println("Courses performance data count: " + performance.size());
            
            if (performance.isEmpty()) {
                showNoDataMessage("No course performance data available. Make sure you have created courses and students are enrolled.");
                model.addRow(new Object[]{"No courses found", "0", "0%", "0%"});
                return;
            }
            
            for (Map<String, Object> coursePerf : performance) {
                model.addRow(new Object[]{
                    coursePerf.get("courseTitle"),
                    coursePerf.get("totalStudents"),
                    String.format("%.1f%%", coursePerf.get("overallCompletion")),
                    String.format("%.1f%%", coursePerf.get("overallQuizAverage"))
                });
            }
            
           
            courseComboBox.removeAllItems();
            for (Map<String, Object> coursePerf : performance) {
                courseComboBox.addItem(new CourseComboItem(
                    (String) coursePerf.get("courseId"), 
                    (String) coursePerf.get("courseTitle")
                ));
            }
            
           
            if (courseComboBox.getItemCount() > 0) {
                courseComboBox.setSelectedIndex(0);
            }
            
            System.out.println("Courses performance loaded successfully");
        } catch (Exception e) {
            System.err.println("Error in loadCoursesPerformance: " + e.getMessage());
            e.printStackTrace();
            showErrorMessage("Error loading courses performance: " + e.getMessage());
        }
    }

    private void loadStudentDetails(String courseId) {
        try {
            DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
            model.setRowCount(0);
            
            ArrayList<Map<String, Object>> studentProgress = analytics.getStudentProgressDetails(courseId);
            
            if (studentProgress.isEmpty()) {
               
                model.addRow(new Object[]{"No students enrolled", "0%", "0", "0%"});
                return;
            }
            
            for (Map<String, Object> progress : studentProgress) {
                model.addRow(new Object[]{
                    progress.get("studentName"),
                    String.format("%.1f%%", progress.get("completionPercentage")),
                    progress.get("completedQuizzes"),
                    String.format("%.1f%%", progress.get("averageQuizScore"))
                });
            }
        } catch (Exception e) {
            showErrorMessage("Error loading student details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void viewDetailsBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to view details.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseTitle = (String) coursesTable.getValueAt(selectedRow, 0);
        
       
        for (int i = 0; i < courseComboBox.getItemCount(); i++) {
            CourseComboItem item = courseComboBox.getItemAt(i);
            if (item.toString().contains(courseTitle)) {
                courseComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        tabbedPane.setSelectedIndex(2);
    }

    private void courseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        if (courseComboBox.getSelectedItem() != null) {
            String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
            loadStudentDetails(courseId);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {
        loadOverview();
        loadCoursesPerformance();
        JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
    }

  
    private void showNoDataMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "No Data Available", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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
}