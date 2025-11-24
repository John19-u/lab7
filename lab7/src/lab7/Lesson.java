package lab7;

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private String quizId; // Reference to associated quiz
    private int order;
    
    public Lesson(String lessonId, String title, String content, int order) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.order = order;
        this.quizId = null;
    }
    
    // Getters and setters
    public String getLessonId() { return lessonId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getOrder() { return order; }
    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }
    
    public boolean hasQuiz() {
        return quizId != null && !quizId.trim().isEmpty();
    }
    
    // For resource compatibility (you can remove if not needed)
    public boolean hasResources() {
        return false; // Or implement if you have resources
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    
    public String[] getResources() {
        return new String[0]; // Or implement if you have resources
    }
}