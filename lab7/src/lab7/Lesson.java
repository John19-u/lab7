
package lab7;

import java.util.ArrayList;
import java.util.HashSet;

public class Lesson {
    
    private static HashSet<String> usedLessonIds = new HashSet<>();

    private String lessonId;
    private String title;
    private String content;
    private ArrayList<String> resources;
    
    public Lesson(String lessonId, String title, String content) { 
         if (usedLessonIds.contains(lessonId)) {
            throw new IllegalArgumentException("Lesson ID already exists: " + lessonId);
        }
        usedLessonIds.add(lessonId);
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>();
    }
    
     public Lesson(String lessonId, String title, String content, ArrayList<String> resources) {
          if (usedLessonIds.contains(lessonId)) {
            throw new IllegalArgumentException("Lesson ID already exists: " + lessonId);
        }
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = resources;
    }
    
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
        return resources;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

   
    public void addResource(String resource) {
        resources.add(resource);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

