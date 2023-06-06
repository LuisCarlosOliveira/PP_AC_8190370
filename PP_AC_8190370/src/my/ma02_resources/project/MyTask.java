/*
 * @file: MyTask.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date:
 * @brief: MyTask is a concrete implementation of the Task interface
 */
package my.ma02_resources.project;

import java.time.LocalDate;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
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

    @Override
    public LocalDate getStart() {
        return this.start;
    }

    @Override
    public LocalDate getEnd() {
        return this.end;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Submission[] getSubmissions() {
        return this.submissions;
    }

    @Override
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }

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

    @Override
    public void extendDeadline(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Number of days cannot be negative.");
        }

        this.duration += i;
        this.end = this.end.plusDays(i);
    }

    /*
    0 (Zero) if both the date-times represent the same time instance of the day. 
    Positive integer if given date-times is later than the otherDate. Negative 
    integer if given date-times is earlier than the otherDate
    */
    @Override
    public int compareTo(Task task) {
        return this.start.compareTo(task.getStart());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyTask temp = (MyTask) obj;
        return  this.title.equals(temp.title);
                
    }

}
