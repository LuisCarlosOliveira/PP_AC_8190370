/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
 */
/**
 * @file: MyEdition.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the Edition class. MyEdition
 * is a concrete implementation of the Edition interface. It represents an
 * Edition of a Challenge Based Learning .
 *
 */
package my.ma02_resources.project;

import model_classes.ProjectModel;
import model_classes.TaskModel;
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
     *
     * @param name Edition name
     * @param start Edition starting date
     * @param projectTemplate project Template
     */
    public MyEdition(String name, LocalDate start, String projectTemplate) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Edition name can't be empty or null.");
        }
        if (start == null || start.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid start date. Start date "
                    + "must be a future date or can't be empty or null.");
        }
        if (projectTemplate == null || projectTemplate.isEmpty()) {
            throw new IllegalArgumentException("Project template can't be empty or null");
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

    /**
     * Adds a project to the edition. The project is created from the template.
     *
     * @param name The name of the project.
     * @param description The description of the project.
     * @param tags The tags of the project.
     * @throws IOException if the project template is not found.
     * @throws ParseException if the project template is not valid.
     * @throws IllegalArgumentException if the project name is null or empty, if
     * the project already exists, if the description is null or empty, or if
     * the tags are null or empty.
     */
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
                    projectModel.getMaximumNumberOfStudents(),
                    projectModel.getMaximumNumberOfPartners(),
                    projectModel.getMaximumNumberOfFacilitators(),
                    tasks.length * 2,
                    tasks
            );

            // Add project to projects array
            addProjectToArray(project);
        } catch (IOException e) {
            throw new IOException("Project template not found.");
        }
    }

    /**
     * Removes a project from the edition.
     *
     * @param projectName The name of the project.
     * @throws IllegalArgumentException if the project name is null or empty, or
     * if the project does not exist.
     */
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

    /**
     * Returns a project from the edition.
     *
     * @param name The name of the project.
     * @return The project.
     * @throws IllegalArgumentException if the project name is null or empty, or
     * if the project does not exist.
     */
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

    /**
     * Returns all the projects of the edition.
     *
     * @return An array of projects.
     */
    @Override
    public Project[] getProjects() {
        return this.projects;
    }

    /**
     * Returns all the projects of the edition that have a specific tag.
     *
     * @param tag The tag of the projects.
     * @return An array of projects.
     */
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

    /**
     * Returns all the projects of the edition that have a specific participant.
     *
     * @param email The email of the participant.
     * @return An array of projects.
     */
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

    /**
     * Returns the number of existing projects in the edition.
     *
     * @return The number of existing projects.
     */
    @Override
    public int getNumberOfProjects() {
        return this.numberOfProjects;
    }

    /**
     * Returns the date of the last task ending date in the projects.
     *
     * @return The date of the last task ending date in the projects.
     */
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

    /**
     * Checks if two objects are equal based on their name.
     *
     * @param obj The object to be compared.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MyEdition)) {
            return false;
        }
        MyEdition temp = (MyEdition) obj;
        return name.equals(temp.name);
    }

}
