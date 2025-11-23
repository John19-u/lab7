
package lab7;
import java.util.*;


public class Quiz {
    private List<String> questions;
    private List<String[]> options;
    private List<Integer> correctAnswers;

    public Quiz() {
        questions = new ArrayList<>();
        options = new ArrayList<>();
        correctAnswers = new ArrayList<>();
    }

    public void addQuestion(String q, String[] opts, int correctIndex) {
        questions.add(q);
        options.add(opts);
        correctAnswers.add(correctIndex);
    }

    public List<String> getQuestions() { 
        return questions; 
    }
    public List<String[]> getOptions() { 
        return options; 
    }
    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    
}
