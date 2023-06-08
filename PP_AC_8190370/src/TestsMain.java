
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import ma02_resources.participants.InstituitionType;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import ma02_resources.project.exceptions.TaskAlreadyInProject;
import my.cbl.participants.MyContact;
import my.cbl.participants.MyFacilitator;
import my.cbl.participants.MyInstituition;
import my.cbl.participants.MyPartner;
import my.cbl.participants.MyStudent;
import my.ma02_resources.project.MyEdition;
import my.ma02_resources.project.MyProject;
import my.ma02_resources.project.MySubmission;
import my.ma02_resources.project.MyTask;


/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class TestsMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        /*
        // Create a contact
        MyContact contact = new MyContact("City", "Country", "123456789", "State", "Street", "12345");

        // Test getter methods
        System.out.println("City: " + contact.getCity());
        System.out.println("Country: " + contact.getCountry());
        System.out.println("Phone: " + contact.getPhone());
        System.out.println("State: " + contact.getState());
        System.out.println("Street: " + contact.getStreet());
        System.out.println("Zip Code: " + contact.getZipCode());

        // Test toString method
        System.out.println(contact);

        // Test equals method
        MyContact sameContact = new MyContact("City", "Country", "123456789", "State", "Street", "12345");
        MyContact differentContact = new MyContact("DifferentCity", "Country", "123456789", "State", "Street", "12345");

        System.out.println("contact equals sameContact?: " + contact.equals(sameContact));
        System.out.println("contact equals differentContact?: " + contact.equals(differentContact));

        System.out.println("\n==============================================\n");

        //  Create a MyInstituition 
        MyInstituition myInstituition = new MyInstituition(
                "My Institution",
                "info@myinstitution.com",
                InstituitionType.COMPANY,
                contact,
                "Description",
                "www.myinstitution.com"
        );

        // Test getter methods
        System.out.println("Name: " + myInstituition.getName());
        System.out.println("Email: " + myInstituition.getEmail());
        System.out.println("Type: " + myInstituition.getType());
        System.out.println("Contact: " + myInstituition.getContact());
        System.out.println("Description: " + myInstituition.getDescription());
        System.out.println("Website: " + myInstituition.getWebsite());
        System.out.println("TOSTRING:");
        System.out.println(myInstituition.toString());

        // Test equals method
        MyInstituition sameInstituition = new MyInstituition(
                "My Institution",
                "info2@myinstitution.com",
                InstituitionType.COMPANY,
                contact,
                "Description",
                "www.myinstitution.com"
        );

        MyInstituition difInstituition = new MyInstituition(
                "Mi Institution",
                "info2@myinstitution.com",
                InstituitionType.COMPANY,
                contact,
                "Description",
                "www.myinstitution.com"
        );

        System.out.println("myInstituition equals sameInstituition?: " + myInstituition.equals(sameInstituition));
        System.out.println("myInstituition equals difInstituition?: " + myInstituition.equals(difInstituition));

        System.out.println("\n==================MYSTUDENT============================\n");

        // Criação de uma instância de MyStudent para testar os métodos
        MyStudent myStudent = new MyStudent(
                "Luis",
                "luis@example.com",
                myInstituition,
                contact,
                1111
        );

        // Teste dos métodos da classe MyStudent
        System.out.println("Name: " + myStudent.getName());
        System.out.println("Email: " + myStudent.getEmail());
        System.out.println("Instituition: " + myStudent.getInstituition());
        System.out.println("Contact: " + myStudent.getContact());
        System.out.println("Student number: " + myStudent.getNumber());

        // Test equals method
        MyStudent sameStudent = new MyStudent(
                "Luis",
                "luis@example.com",
                myInstituition,
                contact,
                1111
        );

        MyStudent difStudent = new MyStudent(
                "Carlos",
                "luis@example.com",
                myInstituition,
                contact,
                1111
        );

        System.out.println("myStudent equals sameStudent?: " + myStudent.equals(sameStudent));
        System.out.println("myStudent equals difStudent?: " + myStudent.equals(difStudent));

        System.out.println("\n==================MYPARTNER============================\n");

        MyPartner myPartner = new MyPartner(
                "Partner",
                "partner@example.com",
                myInstituition,
                contact,
                "123456789",
                "www.partner.com"
        );

        // Teste dos métodos da classe MyPartner
        System.out.println("Name: " + myPartner.getName());
        System.out.println("Email: " + myPartner.getEmail());
        System.out.println("Instituition: " + myPartner.getInstituition());
        System.out.println("Contact: " + myPartner.getContact());
        System.out.println("VAT: " + myPartner.getVat());
        System.out.println("Website: " + myPartner.getWebsite());

        // Test equals method
        MyPartner difPartner = new MyPartner(
                "Partner",
                "partner@exampli.com",
                myInstituition,
                contact,
                "123456789",
                "www.partner.com"
        );

        MyPartner samePartner = new MyPartner(
                "Partner",
                "partner@example.com",
                myInstituition,
                contact,
                "123456789",
                "www.partner.com"
        );

        System.out.println("myPartner equals samePartner?: " + myPartner.equals(samePartner));
        System.out.println("myPartner equals difPartner?: " + myPartner.equals(difPartner));

        System.out.println("\n==================MYFACILITATOR============================\n");

        MyFacilitator myFacilitator = new MyFacilitator(
                "Facilitator Name",
                "facilitator@example.com",
                myInstituition,
                contact,
                "MONGO"
        );

        // Teste dos métodos da classe MyFacilitator
        System.out.println("Name: " + myFacilitator.getName());
        System.out.println("Email: " + myFacilitator.getEmail());
        System.out.println("Instituition: " + myFacilitator.getInstituition());
        System.out.println("Contact: " + myFacilitator.getContact());
        System.out.println("Area of Expertise: " + myFacilitator.getAreaOfExpertise());
        System.out.println(myFacilitator);

        // Test equals method
        MyFacilitator sameFacilitator = new MyFacilitator(
                "Facilitator Name",
                "facilitator@example.com",
                myInstituition,
                contact,
                "MONGO"
        );
        MyFacilitator difFacilitator = new MyFacilitator(
                "Facilitators Name",
                "facilitator@example.com",
                myInstituition,
                contact,
                "MONGO"
        );

        System.out.println("myFacilitator equals sameFacilitator?: " + myFacilitator.equals(sameFacilitator));
        System.out.println("myFacilitator equals difFacilitator?: " + myFacilitator.equals(difFacilitator));

        System.out.println("\n==================MYSUBMISSION============================\n");

        // Create 1 object MySubmission
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime beforeDate = LocalDateTime.of(2023, 5, 2, 15, 30);
        LocalDateTime afterDate = LocalDateTime.of(2023, 8, 2, 15, 30);
        String text1 = "Primeiro de submissão";
        String text2 = "Segundo de submissão";
        String text3 = "Terceiro de submissão";
        MySubmission submission = new MySubmission(date, myStudent, text1);
        MySubmission submissionBefore = new MySubmission(beforeDate, myStudent, text2);
        MySubmission submissionAfter = new MySubmission(afterDate, myStudent, text3);

        // Test MySubmission methods
        System.out.println("Date: " + submission.getDate());
        System.out.println("Student: " + submission.getStudent());
        System.out.println("Text: " + submission.getText());
        System.out.println("Must return positive = " + submission.compareTo(submissionBefore));
        System.out.println("Must return negative = " + submission.compareTo(submissionAfter));

        System.out.println("\n==================MYTASK============================\n");

        // Create 1 task
        Task task = new MyTask("Tarefa 1", "Descrição da tarefa 1", LocalDate.of(2023, 5, 1), 7);
        Task sameTask = new MyTask("Tarefa 1", "Descrição da tarefa 1", LocalDate.of(2023, 5, 1), 7);
        Task difTask = new MyTask("Tarefa 2", "Descrição da tarefa 1", LocalDate.of(2023, 5, 1), 7);

        // Add submissions
        task.addSubmission(submission);
        task.addSubmission(submissionBefore);
        task.addSubmission(submissionAfter);

        // Print task info
        System.out.println("Título: " + task.getTitle());
        System.out.println("Descrição: " + task.getDescription());
        System.out.println("Início: " + task.getStart());
        System.out.println("Fim: " + task.getEnd());
        System.out.println("Duração: " + task.getDuration() + " dias");

        System.out.println("SUBMISSION of the TASK:");
        //Submission[] submissions = task.getSubmissions();
        System.out.println("Submissões:");
        for (int i = 0; i < task.getNumberOfSubmissions(); i++) {
            System.out.println("Submissão " + (i + 1) + ": " + task.getSubmissions()[i].getText());
        }

        System.out.println("task equals sameTask?: " + task.equals(sameTask));
        System.out.println("task equals difTask?: " + task.equals(difTask));

        System.out.println("\n==================MYPROJECT============================\n");

        // Create project
        Project project = new MyProject("Meu Projeto", "Descrição do projeto", new String[]{"tag1", "tag2"},
                2, 1, 1, 2, new Task[]{task, difTask});

        try {
            project.addParticipant(myStudent);
            project.addParticipant(sameStudent);
            project.addParticipant(myPartner);
            project.addParticipant(difPartner);
            project.addParticipant(myFacilitator);
        } catch (IllegalNumberOfParticipantType | ParticipantAlreadyInProject e) {
            e.printStackTrace();
        }
        
        // Print Number of Participants
        System.out.println("Number of Participants: " + project.getNumberOfParticipants());

        try {
            project.addTask(task);
        } catch (IllegalNumberOfTasks | TaskAlreadyInProject e) {
            e.printStackTrace();
        }
        // Print Number of Tasks
        System.out.println("Number of Tasks: " + project.getNumberOfTasks());

        // Print Project Completion Status
        System.out.println("Is Project Completed? " + project.isCompleted());
        
        System.out.println("Number of Participants before remove: " 
                + project.getNumberOfParticipants());
        // Remove a participant
        project.removeParticipant(myStudent.getName());
        
        System.out.println("Number of Participants after remove: " 
                + project.getNumberOfParticipants());
        
        System.out.println("TERMINAR ");
         */

        // Criação de uma nova Edition
        Edition edition1 = new MyEdition("Edition 1", LocalDate.now().plusDays(1), "project_template");

        // Adicionando alguns projetos
        try {
            edition1.addProject("Project 1", "Description 1", new String[]{"tag1", "tag2"});
            edition1.addProject("Project 2", "Description 2", new String[]{"tag1", "tag3"});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Imprimindo o número de projetos
        System.out.println("Number of projects in Edition 1: " + edition1.getNumberOfProjects());

        // Obtendo um projeto
        Project project1 = edition1.getProject("Project 1");
        System.out.println("Project 1 description: " + project1.getDescription());

        // Imprimindo a data de término da edição
        System.out.println("Edition 1 end date: " + edition1.getEnd());

        // Removendo um projeto
        edition1.removeProject("Project 1");

        // Imprimindo o número de projetos após a remoção
        System.out.println("Number of projects in Edition 1 after removal: " + edition1.getNumberOfProjects());

        // Verificando o método equals
        MyEdition edition2 = new MyEdition("Edition 1", LocalDate.now().plusDays(2), "projectTemplate2");
        System.out.println("Are Edition 1 and Edition 2 equal? " + edition1.equals(edition2));
    }
}
