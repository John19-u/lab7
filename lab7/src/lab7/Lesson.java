package lab7;

import java.util.ArrayList;

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private ArrayList<String> resources;
    
    public Lesson(String lessonId, String title, String content) { 
        if (lessonId == null || lessonId.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson ID cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson title cannot be null or empty");
        }
        
        this.lessonId = lessonId;
        this.title = title;
        this.content = content != null ? content : "";
        this.resources = new ArrayList<>();
    }
    
    public Lesson(String lessonId, String title, String content, ArrayList<String> resources) {
        if (lessonId == null || lessonId.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson ID cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson title cannot be null or empty");
        }
        
        this.lessonId = lessonId;
        this.title = title;
        this.content = content != null ? content : "";
        this.resources = resources != null ? new ArrayList<>(resources) : new ArrayList<>();
    }
    
    // Getters
    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getResources() {
        return new ArrayList<>(resources); // Defensive copy
    }

    // Setters with validation
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson title cannot be null or empty");
        }
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content != null ? content : "";
    }
    
    // Resource management
    public void addResource(String resource) {
        if (resource != null && !resource.trim().isEmpty()) {
            resources.add(resource);
        }
    }
    
    public boolean removeResource(String resource) {
        return resources.remove(resource);
    }
    
    public void clearResources() {
        resources.clear();
    }
    
    public boolean hasResources() {
        return !resources.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", title='" + title + '\'' +
                ", contentLength=" + (content != null ? content.length() : 0) +
                ", resourcesCount=" + resources.size() +
                '}';
    }
    
    // Utility method for display
    public String getDisplayInfo() {
        return title + " (ID: " + lessonId + ")";
    }
}