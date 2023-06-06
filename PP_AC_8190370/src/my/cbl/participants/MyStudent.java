/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
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
    
    public MyStudent(String name, String email, Instituition instituition, Contact contact, 
            int number) {
        super(name, email, instituition, contact);
        this.number = number;
    }

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
