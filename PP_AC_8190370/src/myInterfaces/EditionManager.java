/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package myInterfaces;

import java.io.IOException;
import java.text.ParseException;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Student;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import ma02_resources.project.exceptions.TaskAlreadyInProject;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public interface EditionManager {

    public void addEdition(Edition edition);

    public void removeEdition(String editionName);

    public Edition getEdition(String editionName);

    public void setActiveEdition(String editionName);

    public Edition getActiveEdition();

    public void addProjectToEdition(String projectName, String projectDescription,
            String[] projectTags, String editionName) throws IOException, ParseException;

    public Edition[] getIncompleteEditions();

    public Project[] getIncompleteProjectsFromEditions(String editionName);

    public int getEditionNumberOfProjects(String editionName);

    public int getNumberOfEditions();

    public String getProjectProgress(String projectName, String editionName);

    public String getEditionProgress(String editionName);

    public void addSubmissionToProject(String projectName, String taskName, Submission submission);

    public void addParticipantToProject(Participant participant, String projectName)
            throws IllegalNumberOfParticipantType, ParticipantAlreadyInProject;

    public void removeProjectFromEdition(String projectName);

    public void removeParticipantFromProject(String participantEmail, String projectName);

    public void addTaskToProject(String projectName, Task task) throws IllegalNumberOfTasks, TaskAlreadyInProject;

    public String listOfIncompleteEditions();

    public String listOfIncompleteProjectsFromEditions(String editionName);
    
    public String printEditionsInfo();
    
    public String printEditionInfo(String editionName);
}
