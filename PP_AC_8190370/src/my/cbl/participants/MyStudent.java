/*
 * @file MyStudent.java
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date 
 * @brief This file contains the implementation of the MyStudent class.
 * MyStudent is a concrete implementation of the Student interface.
 * It represents a Student, a type of Participant, with various fields such as 
 * vat and website.
 */
package my.cbl.participants;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Student;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MyStudent extends MyParticipant implements Student {

    private int number;
    
    /**
     * Constructs a MyStudent object with the specified Student information.
     * @param name the name of the Student
     * @param email the email of the Student
     * @param instituition the instituition of the Student
     * @param contact the contact of the Student
     * @param number the number of the Student
     */
    public MyStudent(String name, String email, Instituition instituition, Contact contact, 
            int number) {
        super(name, email, instituition, contact);
        this.number = number;
    }

    /**
     * Getter method for number
     *
     * @return the number
     */
     @Override
    public int getNumber() {
        return this.number;
    }
    
    @Override
    public String toString() {
        String contactString = (getContact() != null) ? getContact().toString() : "None";

        return "Student Information:\n"
                + "Name: " + getName() + "\n"
                + "Email: " + getEmail() + "\n"
                + "Instituition: " + getInstituition().toString() + "\n"
                + "Contact: " + contactString + "\n"
                + "Student number: " + this.number + "\n";
    }
}
