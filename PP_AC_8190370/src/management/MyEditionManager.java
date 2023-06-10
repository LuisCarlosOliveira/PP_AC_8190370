/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
 */
/**
 * @file: MyEditionManager.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the MyEditionManager class.
 * MyEditionManager is a concrete implementation of the EditionManager
 * interface. It represents a Edition Manager.
 */
package management;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import ma02_resources.project.exceptions.TaskAlreadyInProject;
import myInterfaces.EditionManager;

public class MyEditionManager implements EditionManager {

    private Edition[] editions;
    private int numberOfEditions;
    private int activeEditionIndex;
    private static final int SIZE = 10;

    public MyEditionManager() {
        this.editions = new Edition[SIZE];
        this.numberOfEditions = 0;
        this.activeEditionIndex = -1;
    }

    /**
     * Getter method for Editions
     *
     * @return the Editions
     */
    public Edition[] getEditions() {
        return this.editions;
    }

    /**
     * Checks if a string is valid (not null and not empty).
     *
     * @param str The string to check.
     * @param errorMessage The error message to include in the exception if the
     * string is not valid.
     * @throws IllegalArgumentException If str is null or empty.
     */
    private void checkStringValidity(String str, String errorMessage) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Resizes the editions array when it becomes full. Doubles the size of the
     * array to accommodate more editions.
     */
    private void resizeEditionsArray() {
        Edition[] temp = new Edition[this.editions.length * 2];
        for (int i = 0; i < this.numberOfEditions; i++) {
            temp[i] = this.editions[i];
        }

        this.editions = temp;
    }

    /**
     * Adds an edition to the manager.
     *
     * @param edition The edition to add.
     * @throws IllegalArgumentException If the edition is null or if there's
     * already an active edition.
     */
    @Override
    public void addEdition(Edition edition) {
        if (edition == null) {
            throw new IllegalArgumentException("Edition can't be null.");
        }
        if (edition.getStatus() == Status.ACTIVE && this.activeEditionIndex != -1) {
            throw new IllegalArgumentException("There is already an active edition. A new edition can't be added with ACTIVE status.");
        }

        if (this.numberOfEditions == this.editions.length) {
            resizeEditionsArray();
        }

        if (edition.getStatus() == Status.ACTIVE) {
            this.activeEditionIndex = this.numberOfEditions;
        }
        this.editions[this.numberOfEditions++] = edition;
    }

    /**
     * Removes an edition from the manager.
     *
     * @param editionName The name of the edition to remove.
     * @throws IllegalArgumentException If the edition name is invalid or if the
     * edition is not found.
     */
    @Override
    public void removeEdition(String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        for (int i = 0; i < this.numberOfEditions; i++) {
            if (this.editions[i].getName().equals(editionName)) {
                for (int j = i; j < numberOfEditions - 1; j++) {
                    this.editions[j] = this.editions[j + 1];
                }
                this.editions[--this.numberOfEditions] = null;
                if (this.activeEditionIndex == i) {
                    this.activeEditionIndex = -1;
                } else if (this.activeEditionIndex > i) {
                    this.activeEditionIndex--;
                }
                return; //breaks loop
            }
        }
        throw new IllegalArgumentException("Edition with name " + editionName
                + " not found.");
    }

    /**
     * Returns the edition with the specified name.
     *
     * @param editionName The name of the edition.
     * @return The edition with the specified name.
     * @throws IllegalArgumentException If editionName is null or empty, or if
     * no edition with the specified name is found.
     */
    @Override
    public Edition getEdition(String editionName) {
        for (int i = 0; i < this.numberOfEditions; i++) {
            if (this.editions[i].getName().equals(editionName)) {
                return this.editions[i];
            }
        }
        throw new IllegalArgumentException("Edition with name " + editionName + " not found.");
    }

    /**
     * Sets an edition as the active edition.
     *
     * @param editionName The name of the edition to set as active.
     * @throws IllegalArgumentException If the edition name is invalid or if the
     * edition is not found.
     */
    @Override
    public void setActiveEdition(String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        for (int i = 0; i < this.numberOfEditions; i++) {
            if (this.editions[i].getName().equals(editionName)) {
                if (this.activeEditionIndex != -1) {
                    editions[activeEditionIndex].setStatus(Status.INACTIVE);
                }
                this.activeEditionIndex = i;
                this.editions[i].setStatus(Status.ACTIVE);
                return;
            }
        }
        throw new IllegalArgumentException("Edition with name " + editionName
                + " not found.");
    }

    /**
     * Returns the active edition.
     *
     * @return The active edition.
     * @throws IllegalArgumentException If there is no active edition.
     */
    @Override
    public Edition getActiveEdition() {
        if (this.activeEditionIndex == -1) {
            throw new IllegalArgumentException("No active edition found.");
        }
        return this.editions[this.activeEditionIndex];
    }

    /**
     * Adds a project to a specific edition.
     *
     * @param projectName The name of the project to add.
     * @param projectDescription The description of the project to add.
     * @param projectTags The tags of the project to add.
     * @param editionName The name of the edition to which the project will be
     * added.
     * @throws IOException If there is an error reading the file.
     * @throws ParseException If there is an error parsing the file.
     * @throws IllegalArgumentException If any of the arguments are invalid or
     * if the edition is not found.
     */
    @Override
    public void addProjectToEdition(String projectName, String projectDescription,
            String[] projectTags, String editionName) throws IOException, ParseException {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        if (projectName == null || projectDescription == null || projectTags == null) {
            throw new IllegalArgumentException("Project details can't be null.");
        }

        Edition temp = this.getEdition(editionName);

        temp.addProject(projectName, projectDescription, projectTags);
    }

    /**
     * Returns all editions that have at least one project not completed.
     *
     * @return An array of incomplete editions.
     */
    @Override
    public Edition[] getIncompleteEditions() {
        // incomplete editions count
        int incompleteEditionsCount = 0;
        for (int i = 0; i < this.numberOfEditions; i++) {
            Project[] tempProjects = editions[i].getProjects();
            for (int j = 0; j < tempProjects.length; j++) {
                if (tempProjects[j] != null && !tempProjects[j].isCompleted()) {
                    incompleteEditionsCount++;
                    break;
                }
            }
        }

        // add incomplete edition to array
        Edition[] incompleteEditions = new Edition[incompleteEditionsCount];
        int count = 0;
        for (int i = 0; i < this.numberOfEditions; i++) {
            Project[] tempProjects = editions[i].getProjects();
            for (int j = 0; j < tempProjects.length; j++) {
                if (tempProjects[j] != null && !tempProjects[j].isCompleted()) {
                    incompleteEditions[count++] = editions[i];
                    break;
                }
            }
        }
        return incompleteEditions;
    }

    /**
     * Returns an array of projects from the active edition and a specified
     * edition that are incomplete.
     *
     * @param editionName The name of the specified edition.
     * @throws IllegalArgumentException If the edition name is invalid or if the
     * edition is not found.
     * @return An array of incomplete projects from the active edition and the
     * specified edition.
     */
    public Project[] getIncompleteProjectsFromEditions(String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        Edition activeEdition = getActiveEdition();
        Edition specifiedEdition = getEdition(editionName);

        if (activeEdition == null || specifiedEdition == null) {
            throw new IllegalArgumentException("Edition not found.");
        }

        int incompleteProjectsCount = 0;
        Project[] activeEditionProjects = activeEdition.getProjects();
        for (int i = 0; i < activeEdition.getNumberOfProjects(); i++) {
            if (activeEditionProjects[i] != null && !activeEditionProjects[i].isCompleted()) {
                incompleteProjectsCount++;
            }
        }

        Project[] specifiedEditionProjects = specifiedEdition.getProjects();
        for (int i = 0; i < specifiedEdition.getNumberOfProjects(); i++) {
            if (specifiedEditionProjects[i] != null && !specifiedEditionProjects[i].isCompleted()) {
                incompleteProjectsCount++;
            }
        }

        Project[] incompleteProjects = new Project[incompleteProjectsCount];
        int projectCount = 0;

        for (int i = 0; i < activeEdition.getNumberOfProjects(); i++) {
            if (activeEditionProjects[i] != null && !activeEditionProjects[i].isCompleted()) {
                incompleteProjects[projectCount++] = activeEditionProjects[i];
            }
        }

        for (int i = 0; i < specifiedEdition.getNumberOfProjects(); i++) {
            if (specifiedEditionProjects[i] != null && !specifiedEditionProjects[i].isCompleted()) {
                incompleteProjects[projectCount++] = specifiedEditionProjects[i];
            }
        }

        return incompleteProjects;
    }

    /**
     * Returns the number of projects in a specific edition.
     *
     * @param editionName The name of the edition.
     * @return The number of projects in the specified edition.
     * @throws IllegalArgumentException If editionName is null or empty, or if
     * no edition with the specified name is found.
     */
    @Override
    public int getEditionNumberOfProjects(String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        Edition temp = this.getEdition(editionName);

        return temp.getNumberOfProjects();
    }

    /**
     * Returns the total number of editions.
     *
     * @return The total number of editions.
     */
    @Override
    public int getNumberOfEditions() {
        return this.numberOfEditions;
    }

    /**
     * Returns a text representation of the progress of a specific project from
     * a specific edition.
     *
     * @param projectName The name of the project.
     * @param editionName The name of the edition.
     * @throws IllegalArgumentException If any of the arguments are invalid or
     * if the edition or project is not found.
     * @return A string describing the progress of the specified project.
     */
    @Override
    public String getProjectProgress(String projectName, String editionName) {

        checkStringValidity(editionName, "Edition name can't be null or empty.");

        checkStringValidity(projectName, "Project name can't be null or empty.");

        Edition targetEdition = getEdition(editionName);

        Project targetProject = targetEdition.getProject(projectName);

        Task[] tasks = targetProject.getTasks();
        int totalSubmissions = 0;
        int tasksWithSubmissions = 0;

        for (int i = 0; i < tasks.length; i++) {
            int taskSubmissions = tasks[i].getNumberOfSubmissions();
            totalSubmissions += taskSubmissions;
            if (taskSubmissions > 0) {
                tasksWithSubmissions++;
            }
        }

        double progressPercentage = 0;
        if (tasks.length > 0) {
            progressPercentage = ((double) tasksWithSubmissions / tasks.length) * 100;
        }

        int tasksWithoutSubmissions = tasks.length - tasksWithSubmissions;

        String text = "Edition " + editionName + " Project: " + projectName + " Progress:\n"
                + "Total Tasks: " + tasks.length + "\n"
                + "Total Submissions: " + totalSubmissions + "\n"
                + "Tasks with Submissions: " + tasksWithSubmissions + "\n"
                + "Tasks without Submissions: " + tasksWithoutSubmissions + "\n"
                + "Progress Percentage: " + progressPercentage + "%\n";

        return text;
    }

    /**
     * Returns a textual representation of the progress of a specific edition.
     *
     * @param editionName The name of the edition.
     * @throws IllegalArgumentException If the edition name is invalid or if the
     * edition is not found.
     * @return A string describing the progress of the specified edition.
     */
    @Override
    public String getEditionProgress(String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        Edition targetEdition = getEdition(editionName);

        if (targetEdition == null) {
            return "No edition found with the provided name.";
        }

        Project[] projects = targetEdition.getProjects();

        if (projects == null) {
            return "No projects found for the edition.";
        }

        int totalTasks = 0;
        int totalSubmissions = 0;
        int tasksWithSubmissions = 0;
        int projectsWithTasksWithoutSubmissions = 0;

        for (int i = 0; i < projects.length; i++) {
            if (projects[i] != null) {
                boolean hasTaskWithoutSubmission = false;
                Task[] tasks = projects[i].getTasks();

                if (tasks == null) {
                    continue;  // skip to the next iteration of the for loop
                }

                totalTasks += tasks.length;

                for (int j = 0; j < tasks.length; j++) {
                    if (tasks[j] != null) {
                        int taskSubmissions = tasks[j].getNumberOfSubmissions();
                        totalSubmissions += taskSubmissions;
                        if (taskSubmissions > 0) {
                            tasksWithSubmissions++;
                        } else {
                            hasTaskWithoutSubmission = true;
                        }
                    }
                }

                if (hasTaskWithoutSubmission) {
                    projectsWithTasksWithoutSubmissions++;
                }
            }
        }

        double progressPercentage = 0;
        if (totalTasks > 0) {
            progressPercentage = ((double) tasksWithSubmissions / totalTasks) * 100;
        }

        double projectsWithoutSubmissionsPercentage = 0;
        if (projects.length > 0) {
            projectsWithoutSubmissionsPercentage = ((double) projectsWithTasksWithoutSubmissions / projects.length) * 100;
        }

        int tasksWithoutSubmissions = totalTasks - tasksWithSubmissions;

        String text = "";
        text += "Edition " + editionName + " Progress:\n";
        text += "Total Projects: " + projects.length + "\n";
        text += "Total Tasks: " + totalTasks + "\n";
        text += "Tasks without Submissions: " + tasksWithoutSubmissions + "\n";
        text += "Total Submissions: " + totalSubmissions + "\n";
        text += "Tasks with Submissions: " + tasksWithSubmissions + "\n";
        text += "Progress Percentage: " + progressPercentage + "%\n";
        text += "Projects with tasks without Submissions: " + projectsWithTasksWithoutSubmissions + "\n";
        text += "Percentage of Projects with tasks without Submissions: " + projectsWithoutSubmissionsPercentage + "%\n";

        return text;
    }

    /**
     * Adds a submission to a specific project.
     *
     * @param projectName The name of the project.
     * @param taskName The name of the task.
     * @param submission The submission to add.
     * @throws IllegalArgumentException If any of the arguments are invalid or
     * if the project or task is not found.
     */
    @Override
    public void addSubmissionToProject(String projectName, String taskName, Submission submission) {
        checkStringValidity(projectName, "Project name can't be null or empty.");

        checkStringValidity(taskName, "Task name can't be null or empty.");

        if (submission == null || submission.getStudent() == null) {
            throw new IllegalArgumentException("tudent or Submission cannot be null or empty.");
        }

        Edition activeEdition = getActiveEdition();

        Project targetProject = activeEdition.getProject(projectName);

        // Verifies student is in project
        String studentEmail = submission.getStudent().getEmail();
        if (targetProject.getParticipant(studentEmail) == null) {
            throw new IllegalArgumentException("Student with email " + studentEmail + " is not part of the project " + projectName + ".");
        }

        Task targetTask = targetProject.getTask(taskName);
        if (targetTask == null) {
            throw new IllegalArgumentException("Task with name " + taskName + " not found in the project " + projectName + ".");
        }

        targetTask.addSubmission(submission);

    }

    /**
     * Adds a participant to a specific project in the active edition.
     *
     * @param participant The participant to add.
     * @param projectName The name of the project.
     * @throws ma02_resources.project.exceptions.IllegalNumberOfParticipantType
     * If the number of a specific type of participant in the project exceeds a
     * certain limit.
     * @throws ma02_resources.project.exceptions.ParticipantAlreadyInProject If
     * the participant is already in the project.
     * @throws IllegalArgumentException If any of the arguments are invalid, if
     * the project is not found, or if the participant is null.
     */
    @Override
    public void addParticipantToProject(Participant participant, String projectName) throws IllegalNumberOfParticipantType, ParticipantAlreadyInProject {
        checkStringValidity(projectName, "Project name can't be null or empty.");

        Edition activeEdition = this.getActiveEdition();

        Project targetProject = activeEdition.getProject(projectName);

        if (targetProject == null) {
            throw new IllegalArgumentException("Project with name " + projectName + " not found in the active edition.");
        }

        targetProject.addParticipant(participant);
    }

    /**
     * Removes a participant from a specific project in the active edition.
     *
     * @param participantEmail The email of the participant to remove.
     * @param projectName The name of the project.
     * @throws IllegalArgumentException If any of the arguments are invalid, if
     * the project is not found, or if the participant with the given email is
     * not found in the project.
     */
    @Override
    public void removeParticipantFromProject(String participantEmail, String projectName) {
        checkStringValidity(projectName, "Project name can't be null or empty.");
        checkStringValidity(participantEmail, "Participant email can't be null or empty.");

        Edition activeEdition = getActiveEdition();

        Project targetProject = activeEdition.getProject(projectName);

        targetProject.removeParticipant(participantEmail);
    }

    /**
     * Removes a project from the active edition.
     *
     * @param projectName The name of the project to remove.
     * @throws IllegalArgumentException If the projectName argument is invalid
     * or if the project is not found.
     */
    @Override
    public void removeProjectFromEdition(String projectName) {
        Edition activeEdition = getActiveEdition();

        Project targetProject = activeEdition.getProject(projectName);

        activeEdition.removeProject(projectName);
    }

    /**
     * Adds a task to a specific project in the active edition.
     *
     * @param projectName The name of the project.
     * @param task The task to add.
     * @throws ma02_resources.project.exceptions.IllegalNumberOfTasks If the
     * number of tasks in the project exceeds a certain limit.
     * @throws ma02_resources.project.exceptions.TaskAlreadyInProject If the
     * task is already in the project.
     * @throws IllegalArgumentException If any of the arguments are invalid or
     * if the project is not found.
     */
    @Override
    public void addTaskToProject(String projectName, Task task) throws IllegalNumberOfTasks, TaskAlreadyInProject {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        Edition activeEdition = getActiveEdition();

        Project targetProject = activeEdition.getProject(projectName);

        targetProject.addTask(task);

    }

    /**
     * Returns the list of incomplete editions.
     *
     * @return the text representation of incomplete editions
     */
    @Override
    public String listOfIncompleteEditions() {
        Edition[] incompleteEditions = this.getIncompleteEditions();

        String text = "";

        if (this.numberOfEditions == 0) {
            text += "NO EDITION ADD";
        } else if (incompleteEditions.length == 0) {
            text += "ALL EDITION ARE COMPLETE";
        } else {

            text += "List of incomplete Editions:\n";
            for (int i = 0; i < incompleteEditions.length; i++) {
                text += incompleteEditions[i].getName();
                text += "\n";
            }
        }
        return text;

    }

    /**
     * Returns the list of incomplete projects from the specified edition.
     *
     * @param editionName the name of the edition
     * @return the text representation of incomplete projects from the specified
     * edition
     */
    @Override
    public String listOfIncompleteProjectsFromEditions(String editionName) {
        Project[] incompleteProjects = this.getIncompleteProjectsFromEditions(editionName);

        String text = "";

        if (incompleteProjects.length == 0) {
            text += "ALL PROJECTS ARE COMPLETE";
        } else {

            text += "List of incomplete Projects from Active Edition and Edition: "
                    + editionName + " \n";
            for (int i = 0; i < incompleteProjects.length; i++) {
                text += incompleteProjects[i].getName();
                text += "\n";
            }
        }
        return text;
    }

    /**
     * Prints information about all editions and their projects.
     *
     * @return the text representation of editions and their projects
     * information
     */
    public String printEditionsInfo() {
        String text = "";

        for (int i = 0; i < this.numberOfEditions; i++) {
            Edition edition = this.editions[i];
            text += "Edition Name: " + edition.getName() + "\n"
                    + "Start Date: " + edition.getStart() + "\n"
                    + "Project Template: " + edition.getProjectTemplate() + "\n"
                    + "Status: " + edition.getStatus() + "\n"
                    + "Number of Projects: " + edition.getNumberOfProjects() + "\n";

            // check if there are projects to print
            if (edition.getNumberOfProjects() > 0) {
                for (int j = 0; j < edition.getNumberOfProjects(); j++) {
                    Project project = edition.getProjects()[j];

                    text += "-- Project Information --\n"
                            + "Project Name: " + project.getName() + "\n"
                            + "Project Description: " + project.getDescription() + "\n"
                            + "Project Tags: " + Arrays.toString(project.getTags()) + "\n"
                            + "Maximum Number of Participants: " + project.getMaximumNumberOfParticipants() + "\n"
                            + "Maximum Number of Students: " + project.getMaximumNumberOfStudents() + "\n"
                            + "Maximum Number of Partners: " + project.getMaximumNumberOfPartners() + "\n"
                            + "Maximum Number of Facilitators: " + project.getMaximumNumberOfFacilitators() + "\n"
                            + "Maximum Number of Tasks: " + project.getMaximumNumberOfTasks() + "\n"
                            + "Number of Participants: " + project.getNumberOfParticipants() + "\n"
                            + "Number of Students: " + project.getNumberOfStudents() + "\n"
                            + "Number of Partners: " + project.getNumberOfPartners() + "\n"
                            + "Number of Facilitators: " + project.getNumberOfFacilitators() + "\n"
                            + "Number of Tasks: " + project.getNumberOfTasks() + "\n";
                }
            }

            text += "---------------------------------------------\n";
        }

        return text;
    }

    /**
     * Prints information about the specified edition and its projects.
     *
     * @param editionName the name of the edition
     * @return the text representation of the specified edition and its projects
     * information
     */
    @Override
    public String printEditionInfo(String editionName) {
        Edition edition = this.getEdition(editionName);

        String text = "";
        text += "Edition Name: " + edition.getName() + "\n"
                + "Start Date: " + edition.getStart() + "\n"
                + "Project Template: " + edition.getProjectTemplate() + "\n"
                + "Status: " + edition.getStatus() + "\n"
                + "Number of Projects: " + edition.getNumberOfProjects() + "\n";

        if (edition.getNumberOfProjects() > 0) {
            text += "---- Projects in the Edition ----\n";
            for (int i = 0; i < edition.getNumberOfProjects(); i++) {
                Project project = edition.getProjects()[i];
                text += "Project Name: " + project.getName() + "\n"
                        + "Project Description: " + project.getDescription() + "\n"
                        + "Number of Participants: " + project.getNumberOfParticipants() + "\n"
                        + "Number of Facilitators: " + project.getNumberOfFacilitators() + "\n"
                        + "Number of Partners: " + project.getNumberOfPartners() + "\n"
                        + "Number of Students: " + project.getNumberOfStudents() + "\n"
                        + "Number of Tasks: " + project.getNumberOfTasks() + "\n"
                        + "---------------------------------\n";
            }
        }

        return text;
    }

    /**
     * Returns the names of all projects from the specified edition.
     *
     * @param editionName the name of the edition
     * @return the text representation of project names from the specified
     * edition
     */
    @Override
    public String getAllProjectNamesFromEdition(String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");

        Edition targetEdition = this.getEdition(editionName);
        Project[] projects = targetEdition.getProjects();

        String text = "Projects in Edition " + editionName + ":\n";
        for (int i = 0; i < projects.length; i++) {
            text += projects[i].getName() + "\n";
        }

        return text;
    }

    public Edition[] getCompleteEditions() {
        // complete editions count
        int completeEditionsCount = 0;
        for (int i = 0; i < this.numberOfEditions; i++) {
            boolean isComplete = true;
            Project[] tempProjects = editions[i].getProjects();
            for (int j = 0; j < tempProjects.length; j++) {
                if (tempProjects[j] == null || !tempProjects[j].isCompleted()) {
                    isComplete = false;
                    break;
                }//edition is only complete if all project are complete
            }
            if (isComplete) {
                completeEditionsCount++;
            }
        }

        // add complete edition to array
        Edition[] completeEditions = new Edition[completeEditionsCount];
        int count = 0;
        for (int i = 0; i < this.numberOfEditions; i++) {
            boolean isComplete = true;
            Project[] tempProjects = editions[i].getProjects();
            for (int j = 0; j < tempProjects.length; j++) {
                if (tempProjects[j] == null || !tempProjects[j].isCompleted()) {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete) {
                completeEditions[count++] = editions[i];
            }
        }
        return completeEditions;
    }

    /**
     * Returns a list of complete editions.
     *
     * @return A string representation of the complete editions.
     */
    @Override
    public String listOfCompleteEditions() {
        Edition[] completeEditions = this.getCompleteEditions();

        String text = "";

        if (this.numberOfEditions == 0) {
            text += "NO EDITIONS ADDED";
        } else if (completeEditions.length == 0) {
            text += "ALL EDITION ARE INCOMPLETE";
        } else {
            text += "List of complete Editions:\n";
            for (int i = 0; i < completeEditions.length; i++) {
                text += completeEditions[i].getName();
                text += "\n";
            }
        }
        return text;
    }

    /**
     * This method retrieves all complete editions. An edition is considered
     * complete when all of its projects are completed.
     *
     * @return A list of completed editions. If no editions are completed, an
     * empty list is returned.
     */
    @Override
    public String getEditionsInRange(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = LocalDate.now();

        if (startDate == null || endDate == null || startDate.isBefore(currentDate) || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        String text = "";

        for (int i = 0; i < this.numberOfEditions; i++) {
            LocalDate editionStartDate = this.editions[i].getStart();
            LocalDate editionEndDate = this.editions[i].getEnd();
            if ((editionStartDate.isEqual(startDate) || editionStartDate.isAfter(startDate))
                    && (editionEndDate.isEqual(endDate) || editionEndDate.isBefore(endDate))) {
                text += "Edition Name: " + this.editions[i].getName() + "\n";
                text += "Start Date: " + editionStartDate + "\n";
                text += "End Date: " + editionEndDate + "\n";
                text += "\n";
            }
        }

        return text;
    }

    /**
     * Returns information about a specific project in an edition.
     *
     * @param projectName The name of the project.
     * @param editionName The name of the edition.
     * @return A string containing the project name and the titles of its tasks
     * (if any).
     * @throws IllegalArgumentException If either the project name or the
     * edition name is null or empty, or if the project does not exist in the
     * specified edition.
     */
    @Override
    public String getProjectInformation(String projectName, String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");
        checkStringValidity(projectName, "Project name can't be null or empty.");

        Edition targetEdition = getEdition(editionName);
        Project targetProject = targetEdition.getProject(projectName);

        if (targetProject == null) {
            throw new IllegalArgumentException("The project with the name " + projectName + " does not exist in the edition " + editionName + ".");
        }

        String text = "Project Name: " + targetProject.getName() + "\n";

        // Get all the tasks in the project
        Task[] tasks = targetProject.getTasks();
        if (targetProject.getNumberOfTasks() > 0) {
            text += "Tasks:\n";
            for (int i = 0; i < tasks.length; i++) {
                text += "\t" + tasks[i].getTitle() + "\n";
                text += "\t" + tasks[i].getStart() + "\n";
                text += "\t" + tasks[i].getEnd() + "\n";
            }
        } else {
            text += "No tasks exist for this project.\n";
        }

        return text;
    }

    /**
     * returns the tasks and students with submissions for a specific project
     * and edition.
     *
     * @param projectName The name of the project.
     * @param editionName The name of the edition.
     * @return A string containing the tasks and students with submissions. If
     * no tasks are found for the project, a corresponding message is returned.
     * @throws IllegalArgumentException if either the edition name or project
     * name is null or empty.
     */
    @Override
    public String getTasksAndStudentsWithSubmissions(String projectName, String editionName) {
        checkStringValidity(editionName, "Edition name can't be null or empty.");
        checkStringValidity(projectName, "Project name can't be null or empty.");

        Edition targetEdition = getEdition(editionName);

        Project targetProject = targetEdition.getProject(projectName);

        Task[] tasks = targetProject.getTasks();
        if (tasks == null || targetProject.getNumberOfTasks() == 0) {
            return "No tasks found for project: " + projectName;
        }

        String text = "Edition " + editionName + " Project: " + projectName + " Progress:\n";

        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                continue;
            }

            int numberOfSubmissions = tasks[i].getNumberOfSubmissions();
            if (numberOfSubmissions > 0) {
                text += "Task " + tasks[i].getTitle() + " has " + numberOfSubmissions + " submissions by:\n";

                Submission[] submissions = tasks[i].getSubmissions();
                if (submissions == null) {
                    continue;
                }

                for (int j = 0; j < submissions.length; j++) {
                    if (submissions[j] == null || submissions[j].getStudent() == null) {
                        continue;
                    }

                    text += "   Student: " + submissions[j].getStudent().getName() + "\n";
                }
            }
        }

        return text;
    }

}
