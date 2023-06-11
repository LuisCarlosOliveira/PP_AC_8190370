/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file: Menu.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief:It provides the methods to navigate through various menus and perform
 * actions related to editions, projects, participants, and lists/reports.
 */
package my_interfaces;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public interface Menu {

    public void mainMenu();

    public void listsAndReports();

    public void projectsMenu();

    public void editionsMenu();

    public void participantsMenu();

    public void addEditionToManagement();

    public void userAddProjectToEdition();

    public void userGetEditionProgress();

    public void getListOfIncompleteProjectsFromEditions();

    public void userRemoveEdition();

    public void userPrintEditionInfo();

    public void userSetActiveEdition();

    public void userAddSubmissionToProject();

    public void getEditionProjectsNumber();

    public void userGetNumberOfEditions();

    public void userAddParticipantToProject();

    public void listEditionsInRange();

    public void userRemoveProjectFromEdition();

    public void userAddTaskToProject();
    
    public void userRemoveParticipantFromProject();
    
    public void projectInformation();
    
    public void listTasksAndStudentsWithSubmissions();
}
