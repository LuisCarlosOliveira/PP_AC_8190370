/*
 * @file: MyEdition.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the Edition class.
 * MyEdition is a concrete implementation of the Edition interface.
 * It represents an Edition of a Challenge Based Learning .
 * 
 */
package my.ma02_resources.project;

import ModelClasses.ProjectModel;
import ModelClasses.TaskModel;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import ma02_resources.project.Task;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MyEdition implements Edition {

    private String name;
    private LocalDate start;
    private String projectTemplate;
    private Status status;
    private Project[] projects;
    private int numberOfProjects;
    private final static int SIZE = 10;

    
    /**
     * Constructs a MyEdition object with the specified Edition information
     * @param name       Edition name
     * @param start Edition starting date
     * @param projectTemplate       project Template
     */
    public MyEdition(String name, LocalDate start, String projectTemplate) {
        if (name == null || name.isEmpty() || start == null
                || start.isBefore(LocalDate.now()) || projectTemplate == null
                || projectTemplate.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.start = start;
        this.projectTemplate = projectTemplate;
        this.status = Status.INACTIVE;
        this.projects = new Project[SIZE];
        this.numberOfProjects = 0;
    }

    /**
     * Getter method for edition name
     *
     * @return the edition name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for edition starting date
     *
     * @return the edition starting date
     */
    @Override
    public LocalDate getStart() {
        return this.start;
    }

    /**
     * Getter method for edition getProjectTemplate
     *
     * @return the edition getProjectTemplate
     */
    @Override
    public String getProjectTemplate() {
        return this.projectTemplate;
    }

    /**
     * Getter method for edition status
     *
     * @return the edition status
     */
    @Override
    public Status getStatus() {
        return this.status;
    }

    /**
     * Getter method for edition status
     *
     * @return the edition status
     */
    @Override
    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    private void resizeProjectsArray() {
        Project[] temp = new Project[this.projects.length * 2];

        for (int i = 0; i < this.numberOfProjects; i++) {
            temp[i] = this.projects[i];
        }

        this.projects = temp;
    }

    private void addProjectToArray(Project project) {
        for (int i = 0; i < this.numberOfProjects; i++) {
            if (this.projects[i].getName().equals(project.getName())) {
                throw new IllegalArgumentException("Project already exists.");
            }
        }

        if (this.numberOfProjects == this.projects.length) {
            resizeProjectsArray();
        }

        this.projects[this.numberOfProjects++] = project;
    }

    @Override
    public void addProject(String name, String description, String[] tags) throws IOException, ParseException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Project name can't be null or empty.");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Project description can't be null or empty.");
        }

        if (tags == null || tags.length == 0) {
            throw new IllegalArgumentException("Project tags can't be null or empty.");
        }

        Gson gson = new Gson();

        try (Reader reader = new FileReader(this.projectTemplate + ".json")) {
            // Convert JSON File to Java Object
            ProjectModel projectModel = gson.fromJson(reader, ProjectModel.class);

            // Create and add tasks to the project
            TaskModel[] tasksModels = projectModel.getTasks();
            Task[] tasks = new Task[tasksModels.length];
            for (int i = 0; i < tasksModels.length; i++) {
                LocalDate taskStartDate = this.start.plusDays(tasksModels[i].getStartAt());
                tasks[i] = new MyTask(tasksModels[i].getTitle(), tasksModels[i].getDescription(),
                        taskStartDate, tasksModels[i].getDuration());
                if (tasks[i] == null) {
                    throw new ParseException("Error creating task at index " + i, i);
                }
            }

            // Create Project object using data from ProjectModel
            Project project = new MyProject(
                    name,
                    description,
                    tags,
                    projectModel.getMaximumNumberOfFacilitators(),
                    projectModel.getMaximumNumberOfStudents(),
                    projectModel.getMaximumNumberOfPartners(),
                    tasks.length,
                    tasks
            );

            // Add project to projects array
            addProjectToArray(project);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Project template not found.");
        }
    }

    @Override
    public void removeProject(String projectName) {
        if (projectName == null || projectName.isEmpty()) {
            throw new IllegalArgumentException("Project name can't be null or empty.");
        }
        for (int i = 0; i < this.numberOfProjects; i++) {
            if (this.projects[i].getName().equals(projectName)) {
                for (int j = i; j < this.numberOfProjects - 1; j++) {
                    this.projects[j] = this.projects[j + 1];
                }
                this.projects[--this.numberOfProjects] = null;
                return;
            }
        }
        throw new IllegalArgumentException("Project not found.");
    }

    @Override
    public Project getProject(String projectName) {
        if (projectName == null || projectName.isEmpty()) {
            throw new IllegalArgumentException("Project name can't be null or empty.");
        }
        for (int i = 0; i < this.numberOfProjects; i++) {
            if (this.projects[i].getName().equals(projectName)) {
                return this.projects[i];
            }
        }
        throw new IllegalArgumentException("Project not found.");
    }

    @Override
    public Project[] getProjects() {
        return this.getProjects();
    }

    @Override
    public Project[] getProjectsByTag(String tag) {
        int count = 0;
        for (int i = 0; i < this.numberOfProjects; i++) {
            if (this.projects[i].hasTag(tag)) {
                count++;
            }
        }

        Project[] projectsWithTag = new Project[count];
        int index = 0;
        for (int i = 0; i < this.numberOfProjects; i++) {
            if (this.projects[i].hasTag(tag)) {
                projectsWithTag[index++] = this.projects[i];
            }
        }
        return projectsWithTag;
    }

    @Override
    public Project[] getProjectsOf(String email) {
        // array with the same size as the project
        Project[] participantProjects = new Project[this.numberOfProjects];
        int count = 0;

        for (int i = 0; i < this.numberOfProjects; i++) {
            Participant participant = this.projects[i].getParticipant(email);
            if (participant != null) {
                participantProjects[count++] = this.projects[i];
            }
        }

        // return array with no null values
        Project[] returningParticipantProjects = new Project[count];
        for (int i = 0; i < count; i++) {
            returningParticipantProjects[i] = participantProjects[i];
        }

        return returningParticipantProjects;
    }

    @Override
    public int getNumberOfProjects() {
        return this.numberOfProjects;
    }

    @Override
    public LocalDate getEnd() {
        LocalDate lastTaskEndDate = null;

        for (int i = 0; i < this.numberOfProjects; i++) {
            Task[] tasks = this.projects[i].getTasks();
            for (int j = 0; j < tasks.length; j++) {
                LocalDate taskEnd = tasks[j].getEnd();
                if (lastTaskEndDate == null || taskEnd.isAfter(lastTaskEndDate)) {
                    lastTaskEndDate = taskEnd;
                }
            }
        }

        return lastTaskEndDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MyEdition)) {
            return false;
        }
        MyEdition temp = (MyEdition) obj;
        return name.equals(temp.name);
    }

}
