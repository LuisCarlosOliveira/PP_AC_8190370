/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file: ProjectModel.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: model class to retrieve Project information from JSON
 */
package ModelClasses;

import com.google.gson.annotations.SerializedName;

public class ProjectModel {

    @SerializedName("number_of_facilitors")
    private int maximumNumberOfFacilitators;
    
    @SerializedName("number_of_students")
    private int maximumNumberOfStudents;
    
    @SerializedName("number_of_partners")
    private int maximumNumberOfPartners;
    
    @SerializedName("tasks")
    private TaskModel[] tasks;

    public ProjectModel(int maximumNumberOfFacilitators, int maximumNumberOfStudents,
            int maximumNumberOfPartners, TaskModel[] tasks) {
        this.maximumNumberOfFacilitators = maximumNumberOfFacilitators;
        this.maximumNumberOfStudents = maximumNumberOfStudents;
        this.maximumNumberOfPartners = maximumNumberOfPartners;
        this.tasks = tasks;
    }

    public int getMaximumNumberOfFacilitators() {
        return this.maximumNumberOfFacilitators;
    }

    public int getMaximumNumberOfStudents() {
        return this.maximumNumberOfStudents;
    }

    public int getMaximumNumberOfPartners() {
        return this.maximumNumberOfPartners;
    }

    public TaskModel[] getTasks() {
        return this.tasks;
    }

    @Override
    public String toString() {
        String textTasks = "";
        for (int i = 0; i < this.tasks.length; i++) {
            textTasks += this.tasks[i].toString();
            textTasks += "\n";
        }
        return "ProjectModel{" + "maximumNumberOfFacilitators="
                + maximumNumberOfFacilitators + ", maximumNumberOfStudents="
                + maximumNumberOfStudents + ", maximumNumberOfPartners="
                + maximumNumberOfPartners + ", \ntasks=" + textTasks + '}';
    }

}
