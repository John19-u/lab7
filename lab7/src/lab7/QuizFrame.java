package lab7;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizFrame extends JFrame {
    private Quiz quiz;
    private StudentManagement student;
    private UserDatabase userDatabase;
    private Lesson lesson;
    private CourseManagement course; // Add course reference
    private Map<String, Integer> studentAnswers;
    private int currentQuestionIndex;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JLabel timerLabel;
    private JLabel progressLabel;
    private JButton nextBtn;
    private JButton prevBtn;
    private JButton submitBtn;
    private Timer timer;
    private int timeLeft; // in seconds

    // Updated constructor to include course
    public QuizFrame(Quiz quiz, StudentManagement student, UserDatabase userDatabase, Lesson lesson, CourseManagement course) {
        this.quiz = quiz;
        this.student = student;
        this.userDatabase = userDatabase;
        this.lesson = lesson;
        this.course = course; // Initialize course
        this.studentAnswers = new HashMap<>();
        this.currentQuestionIndex = 0;
        this.timeLeft = quiz.getTimeLimit() * 60; // Convert minutes to seconds
        
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Quiz: " + lesson.getTitle());
        loadQuestion(0);
        startTimer();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Quiz: " + lesson.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        timerLabel = new JLabel("Time: " + formatTime(timeLeft));
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);
        headerPanel.add(timerLabel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

        // Progress Panel
        JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        progressLabel = new JLabel();
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressPanel.add(progressLabel);
        add(progressPanel, BorderLayout.NORTH);

        // Question Panel
        JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        // Options Panel
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            buttonGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.CENTER);

        // Navigation Panel
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        prevBtn = new JButton("Previous");
        prevBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        prevBtn.addActionListener(e -> prevQuestion());
        
        nextBtn = new JButton("Next");
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nextBtn.addActionListener(e -> nextQuestion());
        
        submitBtn = new JButton("Submit Quiz");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        submitBtn.setBackground(new Color(0, 153, 0));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.addActionListener(e -> submitQuiz());
        
        navPanel.add(prevBtn);
        navPanel.add(nextBtn);
        navPanel.add(Box.createHorizontalStrut(50));
        navPanel.add(submitBtn);
        
        add(navPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        updateProgress();
        updateNavigationButtons();
    }

    private void loadQuestion(int index) {
        if (index < 0 || index >= quiz.getQuestions().size()) return;
        
        currentQuestionIndex = index;
        Question question = quiz.getQuestions().get(index);
        
        questionLabel.setText("<html><b>Question " + (index + 1) + ":</b> " + question.getQuestionText() + "</html>");
        
        // Load options
        List<String> options = question.getOptions();
        for (int i = 0; i < 4; i++) {
            if (i < options.size()) {
                optionButtons[i].setText(options.get(i));
                optionButtons[i].setVisible(true);
            } else {
                optionButtons[i].setVisible(false);
            }
        }
        
        // Load saved answer
        buttonGroup.clearSelection();
        Integer savedAnswer = studentAnswers.get(question.getQuestionId());
        if (savedAnswer != null && savedAnswer < optionButtons.length) {
            optionButtons[savedAnswer].setSelected(true);
        }
        
        updateProgress();
        updateNavigationButtons();
    }

    private void saveCurrentAnswer() {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                Question currentQuestion = quiz.getQuestions().get(currentQuestionIndex);
                studentAnswers.put(currentQuestion.getQuestionId(), i);
                break;
            }
        }
    }

    private void nextQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex < quiz.getQuestions().size() - 1) {
            loadQuestion(currentQuestionIndex + 1);
        }
    }

    private void prevQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex > 0) {
            loadQuestion(currentQuestionIndex - 1);
        }
    }

    private void updateProgress() {
        progressLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + quiz.getQuestions().size() + 
                            " | Answered: " + studentAnswers.size() + "/" + quiz.getQuestions().size());
    }

    private void updateNavigationButtons() {
        prevBtn.setEnabled(currentQuestionIndex > 0);
        nextBtn.setEnabled(currentQuestionIndex < quiz.getQuestions().size() - 1);
        submitBtn.setEnabled(studentAnswers.size() > 0);
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + formatTime(timeLeft));
            
            if (timeLeft <= 0) {
                timer.stop();
                autoSubmitQuiz();
            } else if (timeLeft <= 300) { // 5 minutes warning
                timerLabel.setForeground(Color.RED);
            }
        });
        timer.start();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    private void autoSubmitQuiz() {
        int result = JOptionPane.showConfirmDialog(this,
            "Time's up! Your quiz will be automatically submitted.",
            "Time Expired",
            JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION || result == JOptionPane.CLOSED_OPTION) {
            submitQuiz();
        }
    }

    private void submitQuiz() {
        saveCurrentAnswer(); // Save current question
        
        // Calculate score
        int correctAnswers = 0;
        int totalQuestions = quiz.getQuestions().size();
        
        for (Question question : quiz.getQuestions()) {
            Integer studentAnswer = studentAnswers.get(question.getQuestionId());
            if (studentAnswer != null && question.isCorrectAnswer(studentAnswer)) {
                correctAnswers++;
            }
        }
        
        double score = (double) correctAnswers / totalQuestions * 100;
        boolean passed = score >= quiz.getPassingScore();
        
        // Create quiz result - FIXED: Use course.getCourseId() instead of course.getId()
        QuizResult quizResult = new QuizResult(student.getUserId(), lesson.getLessonId(), 
                                             course.getCourseId(), score); // Changed to getCourseId()
        quizResult.setTotalQuestions(totalQuestions);
        quizResult.setCorrectAnswers(correctAnswers);
        
        // Save to student
        student.addQuizResult(quizResult);
        userDatabase.editStudent(student.getUserId(), student);
        
        // Stop timer
        if (timer != null) {
            timer.stop();
        }
        
        // Show results
        showQuizResults(score, correctAnswers, totalQuestions, passed);
    }

    private void showQuizResults(double score, int correctAnswers, int totalQuestions, boolean passed) {
        String message = String.format(
            "<html><div style='text-align: center;'>" +
            "<h2>Quiz Results</h2>" +
            "<p><b>Score:</b> %.1f%%</p>" +
            "<p><b>Correct Answers:</b> %d/%d</p>" +
            "<p><b>Status:</b> %s</p>" +
            "<p>%s</p>" +
            "</div></html>",
            score, correctAnswers, totalQuestions,
            passed ? "PASSED" : "FAILED",
            passed ? "Congratulations! You passed the quiz." : "You need to score " + quiz.getPassingScore() + "% to pass."
        );
        
        JOptionPane.showMessageDialog(this, message, "Quiz Completed", JOptionPane.INFORMATION_MESSAGE);
        
        // Mark lesson as completed if passed
        if (passed && !student.hasCompletedLesson(lesson.getLessonId())) {
            student.markLessonCompleted(lesson.getLessonId());
            userDatabase.editStudent(student.getUserId(), student);
            JOptionPane.showMessageDialog(this, "Lesson marked as completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        
        this.dispose();
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.stop();
        }
        super.dispose();
    }
}