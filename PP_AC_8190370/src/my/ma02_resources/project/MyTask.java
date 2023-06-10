/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file: MyTask.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the MyTask class.
 * MyTask is a concrete implementation of the Task interface.
 * It represents a Task of a project.
 * 
 */
package my.ma02_resources.project;

import java.time.LocalDate;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;


public class MyTask implements Task {

    private static final int SIZE = 10;
    private String title;
    private String description;
    private LocalDate start;
    private LocalDate end;
    private int duration; //nr of days after start date
    private Submission[] submissions;
    private int numberOfSubmissions;

     /**
     * Constructs a MyTask object with the specified task information
     * @param title       task title
     * @param description task description
     * @param start       task starting date
     * @param duration    task duration in days
     */
    public MyTask(String title, String description, LocalDate start, int duration) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.duration = duration;
        this.end = start.plusDays(duration);
        this.submissions = new Submission[SIZE];
        this.numberOfSubmissions = 0;
    }

    /**
     * Getter method for starting date
     *
     * @return the starting date
     */
    @Override
    public LocalDate getStart() {
        return this.start;
    }

    /**
     * Getter method for ending date
     *
     * @return the ending date
     */
    @Override
    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Getter method for Task duration 
     *
     * @return the duration 
     */
    @Override
    public int getDuration() {
        return this.duration;
    }

    /**
     * Getter method for title
     *
     * @return the title 
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter method for description
     *
     * @return the description 
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method for the submissions of the task
     *
     * @return the the submissions of the task
     */
    @Override
    public Submission[] getSubmissions() {
        return this.submissions;
    }

    /**
     * Getter method for the number of Submissions
     *
     * @return the the number of Submissions
     */
    @Override
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }

    /**
     * Method to Add a submission to the task. If the submissions array is full, 
     * expand it by a factor of 2.
     * @param submission the submission to add
     * @throws IllegalArgumentException If the submission is null
     */
    @Override
    public void addSubmission(Submission submission) {
        if (submission == null) {
            throw new IllegalArgumentException("Submission cannot be null.");
        }
        // if array is full, doubles it
        if (this.numberOfSubmissions == this.submissions.length) {
            Submission[] temp = new Submission[this.submissions.length * 2];
            System.arraycopy(this.submissions, 0, temp, 0, this.submissions.length);
            submissions = temp;
        }

        submissions[numberOfSubmissions++] = submission;
        
    }

    /**
     * Method to Extend the deadline of the task by the given number of days.
     * @param i the number of days 
     * @throws IllegalArgumentException  if the number of days is negative
     */
    @Override
    public void extendDeadline(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Number of days cannot be negative.");
        }

        this.duration += i;
        this.end = this.end.plusDays(i);
    }

    /**
     * Compares two tasks by their start date.
     * @param task the task to be compared with the current task 
     * @return     a negative integer, zero, or a positive integer as this task 
     * is less than, equal to, or greater than the specified task.
     * 
    */
    @Override
    public int compareTo(Task task) {
        return this.start.compareTo(task.getStart());
    }
    
    /**
     * This is an implementation of the equals() method in Java that checks if 
     * two TaskImpl objects are equal based on their title attribute.
     *
     * @param obj  The object to be compared with the current object..
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MyTask)) {
            return false;
        }
        MyTask temp = (MyTask) obj;
        return  this.title.equals(temp.title);
                
    }

}
