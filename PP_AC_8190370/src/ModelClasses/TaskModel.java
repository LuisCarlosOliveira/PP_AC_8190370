/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file: TaskModel.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: model class to retrieve Task information from JSON
 */
package ModelClasses;
import com.google.gson.annotations.SerializedName;


public class TaskModel {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("start_at")
    private int startAt;
    @SerializedName("duration")
    private int duration;

    public TaskModel(String title, String description, int startAt, int duration) {
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.duration = duration;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getStartAt() {
        return this.startAt;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        return "TaskModel{" + "title=" + title + ", description=" + description + ", startAt=" + startAt + ", duration=" + duration + '}';
    }
    
    
}

