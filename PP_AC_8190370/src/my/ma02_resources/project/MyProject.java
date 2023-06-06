/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
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

    //when reading from JSON, if one of the maximum doesn't appear, will be zero
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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    @Override
    public int getNumberOfStudents() {
        return this.numberOfStudents;
    }

    @Override
    public int getNumberOfPartners() {
        return this.numberOfPartners;
    }

    @Override
    public int getNumberOfFacilitators() {
        return this.numberOfFacilitators;
    }

    @Override
    public int getNumberOfTasks() {
        return this.numberOfTasks;
    }

    @Override
    public int getMaximumNumberOfTasks() {
        return this.maximumNumberOfTasks;
    }

    @Override
    public long getMaximumNumberOfParticipants() {
        return this.maximumNumberOfParticipants;
    }

    @Override
    public int getMaximumNumberOfStudents() {
        return this.maximumNumberOfStudents;
    }

    @Override
    public int getMaximumNumberOfPartners() {
        return this.maximumNumberOfPartners;
    }

    @Override
    public int getMaximumNumberOfFacilitators() {
        return this.maximumNumberOfFacilitators;
    }

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

    @Override
    public Participant removeParticipant(String name) {
        // find participant 
        for (int i = 0; i < this.numberOfParticipants; i++) {
            if (this.participants[i].getName().equals(name)) {
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
        throw new IllegalArgumentException("Participant " + name + " doesn't exist.");
    }

    @Override
    public Participant getParticipant(String email) {
        for (int i = 0; i < this.numberOfParticipants; i++) {
            if (this.participants[i].getEmail().equals(email)) {
                return this.participants[i];
            }
        }
        throw new IllegalArgumentException("Participant doesn't exist.");
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public boolean hasTag(String tag) {
        for (int i = 0; i < this.tags.length; i++) {
            if (this.tags[i].equals(tag)) {
                return true;
            }
        }
        return false;
    }

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

    @Override
    public Task getTask(String string) {
        for (int i = 0; i < this.numberOfTasks; i++) {
            if (this.tasks[i].getTitle().equals(string)) {
                return tasks[i];
            }
        }
        return null; // task not finded
    }

    @Override
    public Task[] getTasks() {
        return this.tasks;
    }

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
