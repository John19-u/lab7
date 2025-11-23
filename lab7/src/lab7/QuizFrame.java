package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizFrame extends JFrame {
    private Quiz quiz;
    private StudentManagement student;
    private UserDatabase userDatabase;
    private Lesson lesson;
    private CourseManagement course;

    private int currentQuestion = 0;
    private int correctAnswersCount = 0;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton submitBtn;
    private JButton nextBtn;
    private JLabel feedbackLabel;

    public QuizFrame(Quiz quiz, StudentManagement student, UserDatabase userDatabase, Lesson lesson) {
        this.quiz = quiz;
        this.student = student;
        this.userDatabase = userDatabase;
        this.lesson = lesson;

        setTitle("Quiz: " + lesson.getTitle());
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        displayQuestion();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

       
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

       
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            buttonGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feedbackLabel.setForeground(Color.BLUE);
        bottomPanel.add(feedbackLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        submitBtn = new JButton("Submit Answer");
        nextBtn = new JButton("Next Question");
        nextBtn.setEnabled(false);

        buttonsPanel.add(submitBtn);
        buttonsPanel.add(nextBtn);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener(e -> submitAnswer());
        nextBtn.addActionListener(e -> nextQuestion());
    }

    private void displayQuestion() {
        buttonGroup.clearSelection();
        feedbackLabel.setText(" ");

        String q = quiz.getQuestions().get(currentQuestion);
        String[] opts = quiz.getOptions().get(currentQuestion);

        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + q);

        for (int i = 0; i < opts.length; i++) {
            optionButtons[i].setText(opts[i]);
            optionButtons[i].setEnabled(true);
        }

        submitBtn.setEnabled(true);
        nextBtn.setEnabled(false);
    }

    private void submitAnswer() {
        int selected = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int correct = quiz.getCorrectAnswers().get(currentQuestion);
        if (selected == correct) {
            feedbackLabel.setText("Correct!");
            correctAnswersCount++;
        } else {
            feedbackLabel.setText("Incorrect! Correct answer: " + quiz.getOptions().get(currentQuestion)[correct]);
        }

       
        for (JRadioButton btn : optionButtons) {
            btn.setEnabled(false);
        }
        submitBtn.setEnabled(false);
        nextBtn.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestion++;
        if (currentQuestion < quiz.getQuestions().size()) {
            displayQuestion();
        } else {
            showFinalScore();
        }
    }

    private void showFinalScore() {
        int totalQuestions = quiz.getQuestions().size();
        double scorePercent = ((double) correctAnswersCount / totalQuestions) * 100;

        
        saveResult(scorePercent, totalQuestions);

        JOptionPane.showMessageDialog(this,
                "Quiz completed!\nYour score: " + correctAnswersCount + "/" + totalQuestions +
                        " (" + String.format("%.1f", scorePercent) + "%)",
                "Result", JOptionPane.INFORMATION_MESSAGE);

        this.dispose();
    }

    private void saveResult(double scorePercent, int totalQuestions) {
        if (student != null && userDatabase != null && lesson != null) {
            QuizResult result = new QuizResult(
                    student.getUserId(),
                    lesson.getLessonId(),
                    course.getCourseId(),
                    scorePercent
            );
            result.setTotalQuestions(totalQuestions);

            userDatabase.saveQuizResult(result);
            userDatabase.editStudent(student.getUserId(), student);
        }
    }
}
