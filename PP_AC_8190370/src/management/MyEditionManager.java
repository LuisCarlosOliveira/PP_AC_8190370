/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package management;

import java.io.IOException;
import java.text.ParseException;
import ma02_resources.participants.Student;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
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

    @Override
    public void addEdition(Edition edition) {
        if (edition == null) {
            throw new IllegalArgumentException("Edition can't be null.");
        }
        if (edition.getStatus() == Status.ACTIVE && this.activeEditionIndex != -1) {
            throw new IllegalArgumentException("There is already an active edition. A new edition can't be added with ACTIVE status.");
        }

        if (this.numberOfEditions == this.editions.length) {
            Edition[] temp = new Edition[this.editions.length * 2];
            for (int i = 0; i < this.editions.length; i++) {
                temp[i] = this.editions[i];
            }
            this.editions = temp;
        }
        if (edition.getStatus() == Status.ACTIVE) {
            this.activeEditionIndex = this.numberOfEditions;
        }
        this.editions[this.numberOfEditions++] = edition;
    }

    @Override
    public void removeEdition(String editionName) {
        if (editionName == null || editionName.isEmpty()) {
            throw new IllegalArgumentException("Edition name can't be null or empty.");
        }

        for (int i = 0; i < this.numberOfEditions; i++) {
            if (this.editions[i].getName().equals(editionName)) {
                for (int j = i; j < numberOfEditions - 1; j++) {
                    this.editions[j] = this.editions[j + 1];
                }
                this.editions[--this.numberOfEditions] = null;
                if (this.activeEditionIndex == i) {
                    this.activeEditionIndex = -1;
                }
                return; //breaks loop
            }
        }
        throw new IllegalArgumentException("Edition with name " + editionName
                + " not found.");
    }

    @Override
    public Edition getEdition(String editionName) {
        for (int i = 0; i < this.numberOfEditions; i++) {
            if (this.editions[i].getName().equals(editionName)) {
                return this.editions[i];
            }
        }
        return null;
    }

    @Override
    public void setActiveEdition(String editionName) {
        if (editionName == null || editionName.isEmpty()) {
            throw new IllegalArgumentException("Edition name can't be null or empty.");
        }

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

    @Override
    public Edition getActiveEdition() {
        if (this.activeEditionIndex != -1) {
            return this.editions[this.activeEditionIndex];
        }
        System.out.println("No ACTIVE Edition");
        return null;
    }

    @Override
    public void addProjectToEdition(Project project, String editionName) throws IOException, ParseException {
        if (project == null || editionName == null || editionName.isEmpty()) {
            throw new IllegalArgumentException("Project and Edition cannot be null or empty.");
        }

        Edition temp = this.getEdition(editionName);
        if (temp == null) {
            throw new IllegalArgumentException("Edition not found.");
        }

        temp.addProject(project.getName(), project.getDescription(), project.getTags());
    }

    @Override
    public Edition[] getIncompleteEditions() {
        Edition[] tempEditions = new Edition[this.editions.length];
        int count = 0;

        for (int i = 0; i < this.numberOfEditions; i++) {
            Project[] tempProjects = editions[i].getProjects();

            for (int j = 0; j < tempProjects.length; j++) {
                Project project = tempProjects[j];
                if (!project.isCompleted()) {
                    tempEditions[count++] = editions[i];
                    break;
                }
            }

        }

        Edition[] incompleteEditions = new Edition[count];
        for (int i = 0; i < count; i++) {
            incompleteEditions[i] = tempEditions[i];
        }

        return incompleteEditions;
    }

    //projetos com submissões em falta de uma edição e da edição ativa
    public Project[] getIncompleteProejctsFromEditions(String editionName) {
        Edition activeEdition = getActiveEdition();
        Edition specifiedEdition = getEdition(editionName);

        if (activeEdition == null || specifiedEdition == null) {
            throw new IllegalArgumentException("Edition not found.");
        }
        Project[] tempProjects = new Project[activeEdition.getNumberOfProjects()
                + specifiedEdition.getNumberOfProjects()];
        int projectCount = 0;

        Project[] activeEditionProjects = activeEdition.getProjects();
        Project[] specifiedEditionProjects = specifiedEdition.getProjects();

        for (int i = 0; i < activeEditionProjects.length; i++) {
            if (!activeEditionProjects[i].isCompleted()) {
                tempProjects[projectCount++] = activeEditionProjects[i];
            }
        }

        for (int i = 0; i < specifiedEditionProjects.length; i++) {
            if (!specifiedEditionProjects[i].isCompleted()) {
                tempProjects[projectCount++] = specifiedEditionProjects[i];
            }
        }
        Project[] incompleteProjects = new Project[projectCount];
        for (int i = 0; i < projectCount; i++) {
            incompleteProjects[i] = tempProjects[i];
        }

        return incompleteProjects;

    }

    @Override
    public int getEditionNumberOfProjects(String editionName) {
        if (editionName == null || editionName.isEmpty()) {
            throw new IllegalArgumentException("Edition name can't be null or empty.");
        }

        Edition temp = this.getEdition(editionName);
        if (temp == null) {
            throw new IllegalArgumentException("Edition not found.");
        }

        return temp.getNumberOfProjects();
    }

    @Override
    public int getNumberOfEditions() {
        return this.numberOfEditions;
    }

    @Override
    public String getProjectProgress(String projectName, String editionName) {
        if (projectName == null || projectName.isEmpty() || editionName == null || editionName.isEmpty()) {
            throw new IllegalArgumentException("Project name and Edition name cannot be null or empty.");
        }

        Edition targetEdition = getEdition(editionName);
        if (targetEdition == null) {
            throw new IllegalArgumentException("Edition with name " + editionName + " not found.");
        }

        Project targetProject = targetEdition.getProject(projectName);
        if (targetProject == null) {
            throw new IllegalArgumentException("Project with name " + projectName + " not found in the provided edition.");
        }

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

    @Override
    public String getEditionProgress(String editionName) {
        if (editionName == null || editionName.isEmpty()) {
            throw new IllegalArgumentException("Edition name cannot be null or empty.");
        }

        Edition targetEdition = getEdition(editionName);
        if (targetEdition == null) {
            throw new IllegalArgumentException("Edition with name " + editionName + " not found.");
        }

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

    @Override
    public void addSubmissionToProject(Submission submission, String nameProject, String studentEmail, ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
