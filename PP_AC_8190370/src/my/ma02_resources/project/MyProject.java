/*
 * @file: MyProject.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the Project class.
 * MyProject is a concrete implementation of the Task interface.
 * It represents a Project of an Edition.
 * 
 */
package my.ma02_resources.project;

import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Partner;
import ma02_resources.participants.Student;
import ma02_resources.project.Project;
import ma02_resources.project.Task;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import ma02_resources.project.exceptions.TaskAlreadyInProject;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MyProject implements Project {

    private String name;
    private String description;
    private String[] tags;
    private int maximumNumberOfParticipants;
    private int maximumNumberOfStudents;
    private int maximumNumberOfPartners;
    private int maximumNumberOfFacilitators;
    private int maximumNumberOfTasks;
    private Participant[] participants;
    private Task[] tasks;
    private int numberOfParticipants;
    private int numberOfStudents;
    private int numberOfPartners;
    private int numberOfFacilitators;
    private int numberOfTasks;

    /**
     * Constructs a MyProject object with the specified project information
     * @param name       project name
     * @param description project description
     * @param tags       project tags
     * @param maximumNumberOfStudents  project maximum Number Of Students
     * @param maximumNumberOfPartners       project maximum Number Of Partners
     * @param maximumNumberOfFacilitators  project maximum Number Of Facilitators
     * @param maximumNumberOfTasks       project maximum Number Of Tasks
     * @param tasks    project tasks
     */
    public MyProject(String name, String description, String[] tags,
            int maximumNumberOfStudents, int maximumNumberOfPartners, int maximumNumberOfFacilitators,
            int maximumNumberOfTasks, Task[] tasks) {

        if (name == null || description == null || tags == null) {
            throw new IllegalArgumentException("Name, description and tags mustn't be null.");
        }
        if (maximumNumberOfStudents <= 0 || maximumNumberOfPartners <= 0
                || maximumNumberOfFacilitators <= 0 || maximumNumberOfTasks <= 0) {
            throw new IllegalArgumentException("Number of participants or tasks must be positive.");
        }

        this.maximumNumberOfParticipants = maximumNumberOfStudents + maximumNumberOfPartners
                + maximumNumberOfFacilitators;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.maximumNumberOfStudents = maximumNumberOfStudents;
        this.maximumNumberOfPartners = maximumNumberOfPartners;
        this.maximumNumberOfFacilitators = maximumNumberOfFacilitators;

        this.participants = new Participant[this.maximumNumberOfParticipants];
        this.tasks = tasks;
        //set maximum to double of tasks
        this.maximumNumberOfTasks = tasks.length * 2;
        this.numberOfTasks = tasks.length;
        this.numberOfParticipants = 0;
        this.numberOfStudents = 0;
        this.numberOfPartners = 0;
        this.numberOfFacilitators = 0;

    }

    /**
     * Getter method for project name
     *
     * @return the project name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for project description
     *
     * @return the project description
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method for project NumberOfParticipants
     *
     * @return the project NumberOfParticipants
     */
    @Override
    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    /**
     * Getter method for project numberOfStudents
     *
     * @return the project NumberOfParticipants
     */
    @Override
    public int getNumberOfStudents() {
        return this.numberOfStudents;
    }

    /**
     * Getter method for project numberOfPartners
     *
     * @return the project numberOfPartners
     */
    @Override
    public int getNumberOfPartners() {
        return this.numberOfPartners;
    }

    /**
     * Getter method for project numberOfFacilitators
     *
     * @return the project numberOfFacilitators
     */
    @Override
    public int getNumberOfFacilitators() {
        return this.numberOfFacilitators;
    }

    /**
     * Getter method for project numberOfTasks
     *
     * @return the project numberOfTasks
     */
    @Override
    public int getNumberOfTasks() {
        return this.numberOfTasks;
    }

    /**
     * Getter method for project maximumNumberOfTasks
     *
     * @return the project maximumNumberOfTasks
     */
    @Override
    public int getMaximumNumberOfTasks() {
        return this.maximumNumberOfTasks;
    }

    /**
     * Getter method for project maximumNumberOfParticipants
     *
     * @return the project maximumNumberOfParticipants
     */
    @Override
    public long getMaximumNumberOfParticipants() {
        return this.maximumNumberOfParticipants;
    }

    /**
     * Getter method for project maximumNumberOfStudents
     *
     * @return the project maximumNumberOfStudents
     */
    @Override
    public int getMaximumNumberOfStudents() {
        return this.maximumNumberOfStudents;
    }

    /**
     * Getter method for project maximumNumberOfPartners
     *
     * @return the project maximumNumberOfPartners
     */
    @Override
    public int getMaximumNumberOfPartners() {
        return this.maximumNumberOfPartners;
    }

    /**
     * Getter method for project maximumNumberOfFacilitators
     *
     * @return the project maximumNumberOfFacilitators
     */
    @Override
    public int getMaximumNumberOfFacilitators() {
        return this.maximumNumberOfFacilitators;
    }

    /**
     * The method adds a participant to the project.
     * @param p  represents a participant in a project.
     * @throws IllegalNumberOfParticipantType - if the number of participants of 
     * a certain type is already at the maximum.
     * @throws ParticipantAlreadyInProject - if the participant is already in the project.
     */
    @Override
    public void addParticipant(Participant p) throws IllegalNumberOfParticipantType, ParticipantAlreadyInProject {
        if (p == null) {
            throw new IllegalArgumentException("Participant cannot be null.");
        }

        // check participant exists arready
        for (int i = 0; i < this.numberOfParticipants; i++) {
            if (this.participants[i] != null && this.participants[i].equals(p)) {
                throw new ParticipantAlreadyInProject("Participant " + p.getName() 
                        + " already exists in the project.");
            }
        }

        // checks participant type and maximum
        if (p instanceof Student) {
            if (this.numberOfStudents == this.maximumNumberOfStudents) {
                throw new IllegalNumberOfParticipantType("Maximum number of students reached." 
                        + p.getName() + " not added");
            }
            this.numberOfStudents++;
        } else if (p instanceof Partner) {
            if (this.numberOfPartners == this.maximumNumberOfPartners) {
                throw new IllegalNumberOfParticipantType("Maximum number of partners reached."
                + p.getName() + " not added");
            }
            numberOfPartners++;
        } else if (p instanceof Facilitator) {
            if (this.numberOfFacilitators == this.maximumNumberOfFacilitators) {
                throw new IllegalNumberOfParticipantType("Maximum number of facilitators reached."
                + p.getName() + " not added");
            }
            this.numberOfFacilitators++;
        }

        // Adicionar o participante ao array de participantes e incrementar o contador geral
        this.participants[this.numberOfParticipants] = p;
        this.numberOfParticipants++;
    }

    /**
     * The method removes a participant from the project.
     * @param email - represents the email of the participant..
     * @throws IllegalNumberOfParticipantType - if the number of participants of 
     * a certain type is already at the maximum.
     * @throws ParticipantAlreadyInProject - if the participant is already in the project.
     */
    @Override
    public Participant removeParticipant(String email) {
        // find participant 
        for (int i = 0; i < this.numberOfParticipants; i++) {
            if (this.participants[i].getEmail().equals(email)) {
                Participant removedParticipant = this.participants[i];
                if (this.participants[i] instanceof Student) {
                    numberOfStudents--;
                } else if (this.participants[i] instanceof Partner) {
                    numberOfPartners--;
                } else if (this.participants[i] instanceof Facilitator) {
                    numberOfFacilitators--;
                }
                //remove
                for (int j = i; j < this.numberOfParticipants;) {
                    this.participants[j] = this.participants[++j];

                }
                this.participants[--this.numberOfParticipants] = null;
                return removedParticipant;
            }
        }
        throw new IllegalArgumentException("Participant with email : " + email + 
                " doesn't exist.");
    }

    /**
     * The method  takes a string parameter "email" and returns a Participant object.
     * @param email - A string representing the email of the participant to be retrieved.
     * @return The method is returning a Participant object with the specified email
     * @throws IllegalArgumentException - if the participant does not exist.
     */
    @Override
    public Participant getParticipant(String email) {
        for (int i = 0; i < this.numberOfParticipants; i++) {
            if (this.participants[i].getEmail().equals(email)) {
                return this.participants[i];
            }
        }
        throw new IllegalArgumentException("Participant doesn't exist.");
    }

    /**
     * Getter method for project tags
     *
     * @return the project tags
     */
    @Override
    public String[] getTags() {
        return tags;
    }

     /**
     * This method checks if a given tag exists in a list of tags
     * @param tag - The parameter "tag" is a String representing the tag that we 
     * want to check if it exists in the array of tags.
     * @return     A boolean value is being returned, either true or false, depending 
     * on whether the input tag is found in the list of tags or not.
     */
    @Override
    public boolean hasTag(String tag) {
        for (int i = 0; i < this.tags.length; i++) {
            if (this.tags[i].equals(tag)) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * The method adds a task to the project.
     * @param task - represents a task in a project.
     * @throws IllegalNumberOfTasks - if the number of tasks is already at the maximum.
     * @throws TaskAlreadyInProject - if the task is already in the project.
     */
    @Override
    public void addTask(Task task) throws IllegalNumberOfTasks, TaskAlreadyInProject {
        if (this.numberOfTasks == this.maximumNumberOfTasks) {
            throw new IllegalNumberOfTasks("Maximum number of tasks reached.");
        }

        for (int i = 0; i < this.numberOfTasks; i++) {
            if (this.tasks[i].equals(task)) {
                throw new TaskAlreadyInProject("Task already exists in the project.");
            }
        }

        this.tasks[this.numberOfTasks] = task;
        this.numberOfTasks++;
    }

    /**
     * The method takes a string parameter "title" and returns a Task object.
     * @param title - A string representing the title of the task to be retrieved.
     * @return The method is returning a Task object with the specified title
     */
    @Override
    public Task getTask(String title) {
        for (int i = 0; i < this.numberOfTasks; i++) {
            if (this.tasks[i].getTitle().equals(title)) {
                return tasks[i];
            }
        }
        return null; // task not finded
    }

    /**
     * Getter method for project tasks
     *
     * @return the project tasks
     */
    @Override
    public Task[] getTasks() {
        return this.tasks;
    }

    /**
     * The method checks if all tasks have at least one submission.
     *
     * @return The method isCompleted() returns a boolean value. It returns true 
     * if all tasks have at least one submission, and false otherwise. If the 
     * number of tasks inserted is not equal to the number specified upon 
     * creation it must return false.
     */
    @Override
    public boolean isCompleted() {
        if (this.numberOfTasks != this.tasks.length) {
            return false;
        }

            // Check if all tasks have at least one submission
        for (int i = 0; i < this.numberOfTasks; i++) {
            if (this.tasks[i].getNumberOfSubmissions() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * This is an implementation of the equals() method in Java that checks if 
     *  two Project objects are equal based on their name attribute.
     *
     * @param o  The parameter "o" is an object of type Object, which is the 
     * superclass of all other classes in Java. It is used to compare the 
     * equality of two Project objects.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyProject temp = (MyProject) o;
        return this.name.equals(temp.name);
    }

    
}
