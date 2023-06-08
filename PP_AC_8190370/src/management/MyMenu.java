/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package management;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import ma02_resources.project.Edition;
import my.ma02_resources.project.MyEdition;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MyMenu {

    private MyEditionManager myEditionManager;

    public MyMenu() {
        this.myEditionManager = new MyEditionManager();
    }

    public void mainMenu() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);

        while (runMenu) {

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1- ADD EDITION");
            System.out.println("2- ADD PROJECT TO EDITION");
            System.out.println("9- LISTS AND REPORTS");
            System.out.println("0 - EXIT");
            System.out.print("Option:");

            try {
                int op = scan.nextInt();

                switch (op) {
                    case 0:
                        System.out.print("\nBYE\n");
                        System.exit(0);
                    case 1:
                        this.addEditionToManagement();
                        break;
                    case 2:
                        this.userAddProjectToEdition();
                        break;
                    case 9:
                        this.listsAndReports();
                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine(); // Limpar o input inválido do Scanner
            }
        }
    }

    public void listsAndReports() {
        boolean runMenu = true;
        Scanner scan = new Scanner(System.in);
        while (runMenu) {
            System.out.println("\n--LISTS AND REPORTS--");
            System.out.println("1- List Edtions Names");
            System.out.println("2- List Of IncompleteEditions");
            System.out.println("3- List Of Incomplete Projects");
            System.out.println("4- Edition Progress");
            System.out.println("5- List Of Incomplete Projects");
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
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
                scan.nextLine(); // Limpar o input inválido do Scanner
            }
        }

    }

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
                System.out.println(e.getMessage()); // Exibe a mensagem de erro personalizada
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
        }
    }

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

}
