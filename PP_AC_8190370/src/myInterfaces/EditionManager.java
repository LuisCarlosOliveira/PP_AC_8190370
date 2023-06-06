/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package myInterfaces;

import java.io.IOException;
import java.text.ParseException;
import ma02_resources.participants.Student;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Submission;

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

    public void addProjectToEdition(Project project, String editionName) throws IOException, ParseException;

    public Edition[] getIncompleteEditions();
    
    public Project[] getIncompleteProejctsFromEditions(String editionName);
    
    public int getEditionNumberOfProjects(String editionName);
    
    public int getNumberOfEditions();

    public String getProjectProgress(String projectName, String editionName);
    
    public String getEditionProgress(String editionName) ;

    public void addSubmissionToProject(Submission submission, Project project, Student student);
}
