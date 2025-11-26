
package lab7;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class AddQuestionDialog extends JDialog {
    private Question question;
    private boolean questionSaved = false;
    
    private JTextField questionTextField;
    private JTextField[] optionFields;
    private JComboBox<String> correctAnswerCombo;
    private JTextField pointsField;

    public AddQuestionDialog(JFrame parent, boolean modal, Question existingQuestion) {
        super(parent, modal);
        this.question = existingQuestion;
        
        initComponents();
        setLocationRelativeTo(parent);
        setTitle(existingQuestion == null ? "Add Question" : "Edit Question");
        
        if (existingQuestion != null) {
            loadQuestionData(existingQuestion);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 400);

        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Question:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0;
        questionTextField = new JTextField();
        mainPanel.add(questionTextField, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        
        optionFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            gbc.gridx = 0; gbc.gridy = i + 1;
            mainPanel.add(new JLabel("Option " + (char)('A' + i) + ":"), gbc);
            gbc.gridx = 1; gbc.gridy = i + 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
            optionFields[i] = new JTextField();
            mainPanel.add(optionFields[i], gbc);
            gbc.gridwidth = 1; gbc.weightx = 0.0;
        }

        
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Correct Answer:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        correctAnswerCombo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        mainPanel.add(correctAnswerCombo, gbc);

        
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Points:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        pointsField = new JTextField("1");
        mainPanel.add(pointsField, gbc);

        add(mainPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> cancel());
        
        JButton saveBtn = new JButton("Save Question");
        saveBtn.setBackground(new Color(0, 153, 0));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> saveQuestion());
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadQuestionData(Question question) {
        questionTextField.setText(question.getQuestionText());
        pointsField.setText(String.valueOf(question.getPoints()));
        
        java.util.List<String> options = question.getOptions();
        for (int i = 0; i < Math.min(4, options.size()); i++) {
            optionFields[i].setText(options.get(i));
        }
        
        if (question.getCorrectAnswerIndex() >= 0 && question.getCorrectAnswerIndex() < 4) {
            correctAnswerCombo.setSelectedIndex(question.getCorrectAnswerIndex());
        }
    }

    private void saveQuestion() {
        
        if (questionTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the question text.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int filledOptions = 0;
        for (JTextField field : optionFields) {
            if (!field.getText().trim().isEmpty()) {
                filledOptions++;
            }
        }

        if (filledOptions < 2) {
            JOptionPane.showMessageDialog(this, "Please enter at least 2 options.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
        
            java.util.List<String> options = new java.util.ArrayList<>();
            for (JTextField field : optionFields) {
                String text = field.getText().trim();
                if (!text.isEmpty()) {
                    options.add(text);
                }
            }

        
            int points = Integer.parseInt(pointsField.getText());
            if (points <= 0) {
                JOptionPane.showMessageDialog(this, "Points must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (question == null) {
                String questionId = "Q" + System.currentTimeMillis();
                question = new Question(questionId, questionTextField.getText().trim(), 
                                      options, correctAnswerCombo.getSelectedIndex());
            } else {
                
                question = new Question(question.getQuestionId(), questionTextField.getText().trim(),options, correctAnswerCombo.getSelectedIndex());
            }
            question.setPoints(points);
            
            questionSaved = true;
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for points.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        questionSaved = false;
        dispose();
    }

    public boolean isQuestionSaved() {
        return questionSaved;
    }

    public Question getQuestion() {
        return question;
    }
}