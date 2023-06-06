/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package my.ma02_resources.project;

import java.time.LocalDateTime;
import ma02_resources.participants.Student;
import ma02_resources.project.Submission;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MySubmission implements Submission {

    private LocalDateTime date;
    private Student student;
    private String text;

    public MySubmission(LocalDateTime date, Student student, String text) {
        this.date = date;
        this.student = student;
        this.text = text;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public Student getStudent() {
        return student;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Submission sbmsn) {
        return date.compareTo(sbmsn.getDate());
    }

}
