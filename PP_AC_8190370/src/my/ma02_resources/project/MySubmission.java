/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file: MySubmission.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the MySubmission class.
 * MySubmission is a concrete implementation of the Submission interface.
 * It represents a Submission of a task by a student.
 * 
 */
package my.ma02_resources.project;

import java.time.LocalDateTime;
import ma02_resources.participants.Student;
import ma02_resources.project.Submission;


public class MySubmission implements Submission {

    private LocalDateTime date;
    private Student student;
    private String text;

    /**
     * Constructs a MySubmission object with the specified submission
     * information.
     *
     * @param date the date of the Submission
     * @param student the student of the Submission
     * @param text the text of the Submission
     */
    public MySubmission(LocalDateTime date, Student student, String text) {
        this.date = date;
        this.student = student;
        this.text = text;
    }

    /**
     * Getter method for date
     *
     * @return the date
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Getter method for student
     *
     * @return the student
     */
    @Override
    public Student getStudent() {
        return student;
    }

    /**
     * Getter method for text
     *
     * @return the text
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * This is the implementation of the `compareTo` method from the `Submission` 
     * interface. It compares the date of this submission with the date of another 
     * submission (`o`) and returns an integer value that indicates the order of 
     * the two submissions. 
     * @param sbmsn the Submission to compare
     * @return  If the date of this submission is before the date of `o`, it 
     * returns a negative value. If the dates are equal, it returns 0. If the date
     * of this submission is after the date of `o`, it returns a positive value.
     */
    @Override
    public int compareTo(Submission sbmsn) {
        return date.compareTo(sbmsn.getDate());
    }

}
