
package lab7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateQuizFrame extends JFrame {
    private Instructor instructor;
    private CourseDatabase courseDatabase;
    private Lesson lesson;
    private QuizService quizService;
    private List<Question> questions;
    private DefaultTableModel questionsTableModel;
    
    private JComboBox<CourseComboItem> courseComboBox;
    private JComboBox<LessonComboItem> lessonComboBox;
    private JTextField quizTitleField;
    private JTextField passingScoreField;
    private JTextField timeLimitField;
    private JTextField maxAttemptsField;
    private JTable questionsTable;
    private JButton addQuestionBtn;
    private JButton editQuestionBtn;
    private JButton deleteQuestionBtn;
    private JButton createQuizBtn;
    private JButton backBtn;

    public CreateQuizFrame(Instructor instructor, CourseDatabase courseDatabase, Lesson lesson) {
        this.instructor = instructor;
        this.courseDatabase = courseDatabase;
        this.lesson = lesson;
        this.quizService = new QuizService();
        this.questions = new ArrayList<>();
        
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Create Quiz for: " + lesson.getTitle());
        loadInstructorCourses();
        setupQuestionsTable();
        
        // Pre-select the course and lesson if provided
        if (lesson != null) {
            selectLesson(lesson);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("Create New Quiz");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Quiz Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Course selection
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        courseComboBox = new JComboBox<>();
        courseComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        courseComboBox.addActionListener(e -> courseSelected());
        formPanel.add(courseComboBox, gbc);

        // Lesson selection
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Lesson:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        lessonComboBox = new JComboBox<>();
        lessonComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lessonComboBox, gbc);

        // Quiz title
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Quiz Title:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        quizTitleField = new JTextField();
        quizTitleField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(quizTitleField, gbc);

        // Passing score
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Passing Score (%):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        passingScoreField = new JTextField("70");
        passingScoreField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(passingScoreField, gbc);

        // Time limit
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Time Limit (minutes):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        timeLimitField = new JTextField("30");
        timeLimitField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(timeLimitField, gbc);

        // Max attempts
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Max Attempts:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        maxAttemptsField = new JTextField("3");
        maxAttemptsField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(maxAttemptsField, gbc);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Questions table
        JPanel questionsPanel = new JPanel(new BorderLayout());
        questionsPanel.setBorder(BorderFactory.createTitledBorder("Questions"));

        questionsTableModel = new DefaultTableModel(
            new Object[]{"Question", "Options", "Correct Answer", "Points"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        questionsTable = new JTable(questionsTableModel);
        questionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(questionsTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 150));
        questionsPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Question buttons
        JPanel questionButtonsPanel = new JPanel(new FlowLayout());
        addQuestionBtn = new JButton("Add Question");
        addQuestionBtn.addActionListener(e -> addQuestion());
        
        editQuestionBtn = new JButton("Edit Question");
        editQuestionBtn.addActionListener(e -> editQuestion());
        
        deleteQuestionBtn = new JButton("Delete Question");
        deleteQuestionBtn.addActionListener(e -> deleteQuestion());
        
        questionButtonsPanel.add(addQuestionBtn);
        questionButtonsPanel.add(editQuestionBtn);
        questionButtonsPanel.add(deleteQuestionBtn);
        questionsPanel.add(questionButtonsPanel, BorderLayout.SOUTH);

        mainPanel.add(questionsPanel, BorderLayout.CENTER);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backBtn = new JButton("Back");
        backBtn.addActionListener(e -> goBack());
        
        createQuizBtn = new JButton("Create Quiz");
        createQuizBtn.setBackground(new Color(0, 153, 0));
        createQuizBtn.setForeground(Color.WHITE);
        createQuizBtn.addActionListener(e -> createQuiz());
        
        actionPanel.add(backBtn);
        actionPanel.add(createQuizBtn);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(700, 600);
        updateButtons();
    }

    private void loadInstructorCourses() {
        courseComboBox.removeAllItems();
        ArrayList<CourseManagement> instructorCourses = courseDatabase.getCoursesByInstructor(instructor.getUserId());
        for (CourseManagement course : instructorCourses) {
            courseComboBox.addItem(new CourseComboItem(course.getCourseId(), course.getTitle()));
        }
    }

    private void courseSelected() {
        lessonComboBox.removeAllItems();
        if (courseComboBox.getSelectedItem() != null) {
            String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
            CourseManagement course = courseDatabase.findCourseById(courseId);
            if (course != null) {
                for (Lesson lesson : course.getLessons()) {
                    lessonComboBox.addItem(new LessonComboItem(lesson.getLessonId(), lesson.getTitle()));
                }
            }
        }
    }

    private void selectLesson(Lesson targetLesson) {
        // Find and select the course that contains this lesson
        for (int i = 0; i < courseComboBox.getItemCount(); i++) {
            CourseComboItem courseItem = courseComboBox.getItemAt(i);
            CourseManagement course = courseDatabase.findCourseById(courseItem.getId());
            if (course != null && course.getLessonById(targetLesson.getLessonId()) != null) {
                courseComboBox.setSelectedIndex(i);
                courseSelected(); // Load lessons for this course
                
                // Select the target lesson
                for (int j = 0; j < lessonComboBox.getItemCount(); j++) {
                    LessonComboItem lessonItem = lessonComboBox.getItemAt(j);
                    if (lessonItem.getId().equals(targetLesson.getLessonId())) {
                        lessonComboBox.setSelectedIndex(j);
                        break;
                    }
                }
                break;
            }
        }
        
        // Auto-generate quiz title
        quizTitleField.setText("Quiz: " + targetLesson.getTitle());
    }

    private void setupQuestionsTable() {
        questionsTable.getSelectionModel().addListSelectionListener(e -> updateButtons());
    }

    private void updateButtons() {
        boolean hasSelection = questionsTable.getSelectedRow() != -1;
        editQuestionBtn.setEnabled(hasSelection);
        deleteQuestionBtn.setEnabled(hasSelection);
        createQuizBtn.setEnabled(!questions.isEmpty());
    }

    private void addQuestion() {
        AddQuestionDialog dialog = new AddQuestionDialog(this, true, null);
        dialog.setVisible(true);
        
        if (dialog.isQuestionSaved()) {
            Question question = dialog.getQuestion();
            questions.add(question);
            updateQuestionsTable();
        }
    }

    private void editQuestion() {
        int selectedRow = questionsTable.getSelectedRow();
        if (selectedRow != -1) {
            Question question = questions.get(selectedRow);
            AddQuestionDialog dialog = new AddQuestionDialog(this, true, question);
            dialog.setVisible(true);
            
            if (dialog.isQuestionSaved()) {
                questions.set(selectedRow, dialog.getQuestion());
                updateQuestionsTable();
            }
        }
    }

    private void deleteQuestion() {
        int selectedRow = questionsTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this question?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                questions.remove(selectedRow);
                updateQuestionsTable();
            }
        }
    }

    private void updateQuestionsTable() {
        questionsTableModel.setRowCount(0);
        for (Question question : questions) {
            String options = String.join(" | ", question.getOptions());
            String correctAnswer = question.getCorrectAnswerText();
            questionsTableModel.addRow(new Object[]{
                question.getQuestionText(),
                options,
                correctAnswer,
                question.getPoints()
            });
        }
        updateButtons();
    }

    private void createQuiz() {
        // Validate inputs
        if (lessonComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a lesson.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quizTitleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a quiz title.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one question to the quiz.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Get lesson ID
            String lessonId = ((LessonComboItem) lessonComboBox.getSelectedItem()).getId();
            
            // Create quiz
            String quizId = UUID.randomUUID().toString().substring(0, 8);
            Quiz quiz = new Quiz(quizId, lessonId, quizTitleField.getText().trim());
            
            // Set quiz properties
            quiz.setPassingScore(Integer.parseInt(passingScoreField.getText()));
            quiz.setTimeLimit(Integer.parseInt(timeLimitField.getText()));
            quiz.setMaxAttempts(Integer.parseInt(maxAttemptsField.getText()));
            
            // Add questions
            for (Question question : questions) {
                quiz.addQuestion(question);
            }
            
            // Save quiz
            quizService.addQuiz(quiz);
            
            // Link quiz to lesson
            Lesson selectedLesson = getSelectedLesson();
            if (selectedLesson != null) {
                selectedLesson.setQuizId(quizId);
                // Update lesson in database
                CourseManagement course = getSelectedCourse();
                if (course != null) {
                    instructor.editLesson(selectedLesson.getLessonId(), course.getCourseId(), selectedLesson, courseDatabase);
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Quiz created successfully!\n" +
                "Total Questions: " + questions.size() + "\n" +
                "Passing Score: " + quiz.getPassingScore() + "%",
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numbers for passing score, time limit, and max attempts.", 
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error creating quiz: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private CourseManagement getSelectedCourse() {
        if (courseComboBox.getSelectedItem() != null) {
            String courseId = ((CourseComboItem) courseComboBox.getSelectedItem()).getId();
            return courseDatabase.findCourseById(courseId);
        }
        return null;
    }

    private Lesson getSelectedLesson() {
        if (lessonComboBox.getSelectedItem() != null) {
            String lessonId = ((LessonComboItem) lessonComboBox.getSelectedItem()).getId();
            CourseManagement course = getSelectedCourse();
            if (course != null) {
                return course.getLessonById(lessonId);
            }
        }
        return null;
    }

    private void goBack() {
        this.dispose();
    }

    // Combo item classes
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

    private class LessonComboItem {
        private String id;
        private String title;
        
        public LessonComboItem(String id, String title) {
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