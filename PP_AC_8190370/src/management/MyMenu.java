/**
 * @file: MyMenu.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the MyMenu class.
 * MyEdition is a concrete implementation of the Menu interface.
 * It represents the menu interface for interacting with
 * the CBL management system. It provides options to navigate through various
 * menus and perform actions related to editions, projects, participants, and
 * lists/reports.
 */
package management;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import ma02_resources.participants.Contact;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.InstituitionType;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Partner;
import ma02_resources.participants.Student;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Submission;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import my.cbl.participants.MyContact;
import my.cbl.participants.MyFacilitator;
import my.cbl.participants.MyInstituition;
import my.cbl.participants.MyPartner;
import my.cbl.participants.MyStudent;
import my.ma02_resources.project.MyEdition;
import my.ma02_resources.project.MySubmission;
import myInterfaces.Menu;

public class MyMenu implements Menu {

    private MyEditionManager myEditionManager;

    /**
     * Creates a new instance of `MyMenu` with a `MyEditionManager` object.
     */
    public MyMenu() {
        this.myEditionManager = new MyEditionManager();
    }

    /**
     * Displays the main menu and handles user input to navigate to different
     * menus or exit the program.
     */
    public void mainMenu() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);

        while (runMenu) {

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1- Edition Menu");
            System.out.println("2- Projects Menu");
            System.out.println("3- Participants Menu");
            System.out.println("4 - ----");
            System.out.println("9- Lists AND Reports");
            System.out.println("0 - EXIT");
            System.out.print("Option:");

            try {
                int op = scan.nextInt();

                switch (op) {
                    case 0:
                        System.out.print("\nBYE\n");
                        System.exit(0);
                    case 1:
                        this.editionsMenu();
                        break;

                    case 2:
                        this.projectsMenu();
                        break;
                    case 3:
                        this.participantsMenu();
                        break;
                    case 4:
                        break;

                    case 9:
                        this.listsAndReports();
                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine();
            }
        }
    }

    /**
     * Displays the lists and reports menu and handles user input to perform
     * specific actions.
     */
    public void listsAndReports() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while (runMenu) {
            System.out.println("\n--LISTS AND REPORTS--");
            System.out.println("1- List of Edition Names");
            System.out.println("2- List Of IncompleteEditions");
            System.out.println("3- List Of Incomplete Projects From Ative and Other Edition");
            System.out.println("4- Edition Progress");
            System.out.println("5- List Of Complete Editions");
            System.out.println("5- List Of Editions Between Dates");
            System.out.println("0- Back to MAIN MENU");
            System.out.print("Option:");
            try {
                int op = scan.nextInt();

                switch (op) {
                    case 0:
                        this.mainMenu();
                        break;
                    case 1:
                        System.out.println(this.myEditionManager.printEditionsInfo());
                        break;
                    case 2:
                        System.out.println(this.myEditionManager.listOfIncompleteEditions());
                        break;
                    case 3:
                        this.getListOfIncompleteProjectsFromEditions();
                        break;
                    case 4:
                        this.userGetEditionProgress();
                        break;
                    case 5:
                        System.out.println(this.myEditionManager.listOfCompleteEditions());
                        break;
                    case 6:
                        this.listEditionsInRange();
                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine();
            }
        }

    }

    /**
     * Displays the projects menu and handles user input to perform specific
     * actions.
     */
    public void projectsMenu() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while (runMenu) {
            System.out.println("\n--PROJECTS MENUS--");
            System.out.println("1- Add Project To Edition");
            System.out.println("1- Remove Project From Edition");
            System.out.println("3- -");
            System.out.println("4- - Progress");
            System.out.println("5- -");
            System.out.println("0- Back to MAIN MENU");
            System.out.print("Option:");
            try {
                int op = scan.nextInt();

                switch (op) {
                    case 0:
                        this.mainMenu();
                        break;
                    case 1:
                        this.userAddProjectToEdition();
                        break;
                    case 2:

                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine();
            }
        }

    }

    /**
     * Displays the editions menu and handles user input to perform specific
     * actions.
     */
    public void editionsMenu() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while (runMenu) {
            System.out.println("\n--EDITIONS MENUS--");
            System.out.println("1- Add Edition");
            System.out.println("1- Remove Edition");
            System.out.println("3- Edition Information");
            System.out.println("4- Set Edition Ative");
            System.out.println("5- Number of Projects in Edition");
            System.out.println("6- Number of Editions");
            System.out.println("0- Back to MAIN MENU");
            System.out.print("Option:");
            try {
                int op = scan.nextInt();

                switch (op) {
                    case 0:
                        this.mainMenu();
                        break;
                    case 1:
                        this.addEditionToManagement();
                        break;
                    case 2:
                        this.userRemoveEdition();
                        break;
                    case 3:
                        this.userPrintEditionInfo();
                        break;
                    case 4:
                        this.userSetActiveEdition();
                        break;
                    case 5:
                        this.getEditionProjectsNumber();
                        break;
                    case 6:
                        this.userGetNumberOfEditions();
                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine();
            }
        }

    }

    /**
     * Displays the participants menu and handles user input to perform specific
     * actions.
     */
    public void participantsMenu() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while (runMenu) {
            System.out.println("\n--Participants MENUS--");
            System.out.println("1- Add Submission to project");
            System.out.println("2- Add Participant To Project");
            System.out.println("3- Projects of Participant");
            System.out.println("4- ---");
            System.out.println("5- -");
            System.out.println("0- Back to MAIN MENU");
            System.out.print("Option:");
            try {
                int op = scan.nextInt();

                switch (op) {
                    case 0:
                        this.mainMenu();
                        break;
                    case 1:
                        this.userAddSubmissionToProject();
                        break;
                    case 2:
                        this.userAddParticipantToProject();
                        break;
                    case 3:
                        // this.getParticipantProjects();
                        break;
                    case 4:

                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine();
            }
        }

    }

    /**
     * Adds a new edition to the management system.
     */
    public void addEditionToManagement() {

        System.out.println("\n--ADD EDITION--");

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter edition name: ");
        String name = scan.nextLine();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateString = scan.nextLine();

        try {
            LocalDate start = LocalDate.parse(startDateString);
            System.out.print("Enter project template: ");
            String projectTemplate = scan.nextLine();

            try {
                Edition edition = new MyEdition(name, start, projectTemplate);
                this.myEditionManager.addEdition(edition);
                System.out.println("Edition added successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
        }
    }

    /**
     * Adds a project to an edition.
     */
    public void userAddProjectToEdition() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n-- ADD PROJECT TO EDITION --");

        System.out.print("Enter project name: ");
        String projectName = scan.nextLine();

        System.out.print("Enter project description: ");
        String projectDescription = scan.nextLine();

        System.out.print("Enter project tags (separated by comma): ");
        String tagsString = scan.nextLine();
        String[] projectTags = tagsString.split(",");

        System.out.print("Enter the name of the edition to which the project will be added: ");
        String editionName = scan.nextLine();

        try {
            this.myEditionManager.addProjectToEdition(projectName, projectDescription, projectTags, editionName);
            System.out.println("Project added successfully to the edition.");
        } catch (IOException | ParseException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

     /**
     * Displays the edition progress based on the edition name entered by the user.
     */
    public void userGetEditionProgress() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--GET Edition Progress--");

        System.out.print("Enter edition name: ");
        String editionName = scan.nextLine();

        try {
            String result = this.myEditionManager.getEditionProgress(editionName);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the list of incomplete projects from the active edition and other editions based on the edition name entered by the user.
     */
    public void getListOfIncompleteProjectsFromEditions() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--GET LIST OF INCOMPLETE PROJECTS FROM ATIVE EDITION AND OTHER EDITION--");

        System.out.print("Enter edition name: ");
        String editionName = scan.nextLine();

        try {
            String result = this.myEditionManager.listOfIncompleteProjectsFromEditions(editionName);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes an edition from the management system.
     */
    public void userRemoveEdition() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--REMOVE EDITION--");

        System.out.print("Enter edition name: ");
        String editionName = scan.nextLine();

        try {
            this.myEditionManager.removeEdition(editionName);
            System.out.println("Edition removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the information of an edition based on the edition name entered by the user.
     */
    public void userPrintEditionInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--Edition Information--");

        System.out.print("Enter edition name: ");
        String editionName = scan.nextLine();

        try {
            System.out.println(this.myEditionManager.printEditionInfo(editionName));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

     /**
     * Sets the active edition based on the edition name entered by the user.
     */
    public void userSetActiveEdition() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--Set Edition Active--");
        //System.out.println("\n > Current Active Edition: " + this.myEditionManager.getActiveEdition().getName());
        System.out.print("Enter edition name to set Active: ");
        String editionName = scan.nextLine();

        try {
            this.myEditionManager.setActiveEdition(editionName);
            System.out.println("New Active Edition");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates an institution object by taking input from the user.
     */
    private Instituition createInstituition() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Creating a new Institution...");

        System.out.print("Enter Institution's name: ");
        String name = scan.nextLine();

        System.out.print("Enter Institution's email: ");
        String email = scan.nextLine();

        InstituitionType type = null;
        while (type == null) {
            try {
                System.out.println("Enter Institution's type (UNIVERSITY, COMPANY, NGO, OTHER): ");
                type = InstituitionType.valueOf(scan.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type. Please enter one of the following: UNIVERSITY, COMPANY, NGO, OTHER.");
            }
        }

        return new MyInstituition(name, email, type);
    }

    /**
     * Creates a contact object by taking input from the user.
     */
    private Contact createContact() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Contact's city: ");
        String contactCity = scan.nextLine();

        System.out.print("Enter Contact's country: ");
        String contactCountry = scan.nextLine();

        System.out.print("Enter Contact's phone: ");
        String contactPhone = scan.nextLine();

        System.out.print("Enter Contact's state: ");
        String contactState = scan.nextLine();

        System.out.print("Enter Contact's street: ");
        String contactStreet = scan.nextLine();

        System.out.print("Enter Contact's zipcode: ");
        String contactZipcode = scan.nextLine();

        return new MyContact(contactCity, contactCountry, contactPhone, contactState, contactStreet, contactZipcode);
    }

    /**
     * Creates a student object by taking input from the user.
     */
    private Student createStudent() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Creating a new Student...");

        System.out.print("Enter Student's name: ");
        String name = scan.nextLine();

        System.out.print("Enter Student's email: ");
        String email = scan.nextLine();

        System.out.println("Enter Student's number: ");
        int number = scan.nextInt();
        scan.nextLine(); // Consume newline left-over

        System.out.println("Please enter the details of the Student's Instituition.");
        Instituition instituition = createInstituition();

        System.out.println("Please enter the details of the Student's Contact.");
        Contact contact = createContact();

        return new MyStudent(name, email, instituition, contact, number);
    }

    /**
     * Adds a submission to a project based on the input from the user.
     */
    public void userAddSubmissionToProject() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--ADD SUBMISSION TO PROJECT--");

        System.out.print("Enter project name: ");
        String projectName = scan.nextLine();

        System.out.print("Enter task name: ");
        String taskName = scan.nextLine();

        // Create a new Student
        Student student = createStudent();

        System.out.print("Enter submission text: ");
        String submissionText = scan.nextLine();

        // Create a new Submission
        Submission submission = new MySubmission(LocalDateTime.now(), student, submissionText);

        try {
            this.myEditionManager.addSubmissionToProject(projectName, taskName, submission);
            System.out.println("Submission added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Exibe a mensagem de erro personalizada
        }
    }

    /**
     * Creates a partner object by taking input from the user.
     */
    private Partner createPartner() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Creating a new Partner...");

        System.out.print("Enter Partner's name: ");
        String name = scan.nextLine();

        System.out.print("Enter Partner's email: ");
        String email = scan.nextLine();

        System.out.println("Please enter the details of the Partner's Institution.");
        Instituition institution = createInstituition();

        System.out.println("Please enter the details of the Partner's Contact.");
        Contact contact = createContact();

        System.out.print("Enter Partner's VAT number: ");
        String vat = scan.nextLine();

        System.out.print("Enter Partner's website: ");
        String website = scan.nextLine();

        return new MyPartner(name, email, institution, contact, vat, website);
    }

    /**
     * Creates a facilitator object by taking input from the user.
     */
    private Facilitator createFacilitator() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Creating a new Facilitator...");

        System.out.print("Enter Facilitator's name: ");
        String name = scan.nextLine();

        System.out.print("Enter Facilitator's email: ");
        String email = scan.nextLine();

        System.out.println("Please enter the details of the Facilitator's Institution.");
        Instituition institution = createInstituition();

        System.out.println("Please enter the details of the Facilitator's Contact.");
        Contact contact = createContact();

        System.out.print("Enter Facilitator's area of expertise: ");
        String areaOfExpertise = scan.nextLine();

        return new MyFacilitator(name, email, institution, contact, areaOfExpertise);
    }

    /**
     * Gets the number of projects in an edition based on the edition name entered by the user.
     */
    public void getEditionProjectsNumber() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--Number of Projects in Edition--");

        System.out.print("Enter edition name: ");
        String editionName = scan.nextLine();

        try {

            System.out.println(this.myEditionManager.getEdition(editionName).getNumberOfProjects());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Gets the number of editions in the management system and displays it to the user.
     */
    public void userGetNumberOfEditions() {
        int temp = this.myEditionManager.getNumberOfEditions();

        if (temp != 0) {
            System.out.println("Number of Editionsthis: " + this.myEditionManager.getNumberOfEditions());
        } else {
            System.out.println("No editions created ");
        }

    }

    /**
     * Adds a participant to a project based on the input from the user.
     */
    public void userAddParticipantToProject() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please select the type of participant to add:");
        System.out.println("1. Student");
        System.out.println("2. Partner");
        System.out.println("3. Facilitator");
        int participantType = scan.nextInt();
        scan.nextLine(); // Consumes newline left-over

        Participant participant = null;

        switch (participantType) {
            case 1:
                participant = createStudent();
                break;
            case 2:
                participant = createPartner();
                break;
            case 3:
                participant = createFacilitator();
                break;
            default:
                System.out.println("Invalid option, please try again.");
                return;
        }

        System.out.print("Enter the name of the project to which you want to add the participant: ");
        String projectName = scan.nextLine();

        try {
            this.myEditionManager.addParticipantToProject(participant, projectName);
            System.out.println(participant.getName() + " was added successfully to the project " + projectName);
        } catch (IllegalArgumentException | IllegalNumberOfParticipantType | ParticipantAlreadyInProject ex) {
            System.out.println("An error occurred: " + ex.getMessage());
        }
    }

    /**
     * Lists the editions within a specified date range based on the input from
     * the user.
     */
    public void listEditionsInRange() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the start date (yyyy-mm-dd):");
        String startDateStr = scan.nextLine();

        System.out.println("Enter the end date (yyyy-mm-dd):");
        String endDateStr = scan.nextLine();

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            String editionsText = this.myEditionManager.getEditionsInRange(startDate, endDate);
            System.out.println("Editions within the specified date range:\n");
            System.out.println(editionsText);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please provide dates in yyyy-mm-dd format.");
        }
    }

    /*
    private void getParticipantProjects() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--Number of Projects in Edition--");

        System.out.print("Enter participant email: ");
        String participantEmail = scan.nextLine();
        
        System.out.print("Enter edition name: ");
        String editionName = scan.nextLine();
        
        
    }
     */
}
