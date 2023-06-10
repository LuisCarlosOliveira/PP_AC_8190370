/**
 * @file MyParticipant.java
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date 
 * @brief This file contains the implementation of the MyParticipant class.
 * MyParticipant is a concrete implementation of the Participant interface.
 * It represents a Participant with various fields such as name, email,contact.
 * 
 */
package my.cbl.participants;


import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Participant;

public abstract class MyParticipant implements Participant {

    private String name;
    private String email;
    private Instituition instituition;
    private Contact contact;

    /**
     * Constructs a MyParticipant object with the specified participant information.
     * @param name the name of the Participant
     * @param email the email of the Participant
     * @param instituition the instituition of the Participant
     * @param contact the contact of the Participant
     */
    public MyParticipant(String name, String email, Instituition instituition, Contact contact) {
        if (name == null || email == null || instituition == null) {
            throw new IllegalArgumentException("Name, email, and institution are mandatory.");
        }
        this.name = name;
        this.email = email;
        this.instituition = instituition;
        this.contact = contact;
    }

    /**
     * Getter method for name
     *
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for email
     *
     * @return the enaik
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter method for contact
     *
     * @return the contact
     */
    @Override
    public Contact getContact() {
        return this.contact;
    }

    /**
     * Getter method for instituition
     *
     * @return the instituition
     */
    @Override
    public Instituition getInstituition() {
        return this.instituition;
    }

    /**
     * Setter method for the Instituition.
     *
     * @param instn - the Instituition to set.
     */
    @Override
    public void setInstituition(Instituition instn) {
        this.instituition = instn;
    }

    /**
     * Setter method for the Instituition.
     *
     * @param instn - the Instituition to set.
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    /**
     * This method is used to compare two Participants. Two Participant are
     * equal if they have the same email.
     *
     * @param obj the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyParticipant temp = (MyParticipant) obj;
        return this.email.equals(temp.email) && this.name.equals(temp.name) ;
    }

    @Override
    public String toString() {
        String contactString = (this.contact != null) ? this.contact.toString() : "None";

        return "Participant Information:\n"
                + "Name: " + this.name + "\n"
                + "Email: " + this.email + "\n"
                + "Institution: " + this.instituition.toString() + "\n"
                + "Contact: " + contactString;
    }

}
