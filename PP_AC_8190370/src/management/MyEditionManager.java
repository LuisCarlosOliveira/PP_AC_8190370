/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package management;

import java.io.IOException;
import java.text.ParseException;
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

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
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
                if (!tempProjects[j].isCompleted()) {
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
                if (!tempProjects[j].isCompleted()) {
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
    @Override
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
            if (!activeEditionProjects[i].isCompleted()) {
                incompleteProjectsCount++;
            }
        }

        Project[] specifiedEditionProjects = specifiedEdition.getProjects();
        for (int i = 0; i < specifiedEdition.getNumberOfProjects(); i++) {
            if (!specifiedEditionProjects[i].isCompleted()) {
                incompleteProjectsCount++;
            }
        }

        Project[] incompleteProjects = new Project[incompleteProjectsCount];
        int projectCount = 0;

        for (int i = 0; i < activeEdition.getNumberOfProjects(); i++) {
            if (!activeEditionProjects[i].isCompleted()) {
                incompleteProjects[projectCount++] = activeEditionProjects[i];
            }
        }

        for (int i = 0; i < specifiedEdition.getNumberOfProjects(); i++) {
            if (!specifiedEditionProjects[i].isCompleted()) {
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

        Project[] projects = targetEdition.getProjects();

        int totalTasks = 0;
        int totalSubmissions = 0;
        int tasksWithSubmissions = 0;
        int projectsWithTasksWithoutSubmissions = 0;

        for (int i = 0; i < projects.length; i++) {
            boolean hasTaskWithoutSubmission = false;
            Task[] tasks = projects[i].getTasks();
            totalTasks += tasks.length;
            for (int j = 0; j < tasks.length; j++) {
                int taskSubmissions = tasks[j].getNumberOfSubmissions();
                totalSubmissions += taskSubmissions;
                if (taskSubmissions > 0) {
                    tasksWithSubmissions++;
                } else {
                    hasTaskWithoutSubmission = true;
                }
            }
            if (hasTaskWithoutSubmission) {
                projectsWithTasksWithoutSubmissions++;
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

    @Override
    public String listOfIncompleteEditions() {
        Edition[] incompleteEditions = this.getIncompleteEditions();

        String text = "";

        if (incompleteEditions.length == 0) {
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
                        + "Number of Tasks: " + project.getNumberOfTasks() + "\n"
                        + "---------------------------------\n";
            }
        }

        return text;
    }

}
