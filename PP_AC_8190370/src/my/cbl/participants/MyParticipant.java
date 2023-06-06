/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package my.cbl.participants;


import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Participant;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public abstract class MyParticipant implements Participant {

    private String name;
    private String email;
    private Instituition instituition;
    private Contact contact;

    public MyParticipant(String name, String email, Instituition instituition, Contact contact) {
        if (name == null || email == null || instituition == null) {
            throw new IllegalArgumentException("Name, email, and institution are mandatory.");
        }
        this.name = name;
        this.email = email;
        this.instituition = instituition;
        this.contact = contact;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public Contact getContact() {
        return this.contact;
    }

    @Override
    public Instituition getInstituition() {
        return this.instituition;
    }

    @Override
    public void setInstituition(Instituition instn) {
        this.instituition = instn;
    }

    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

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
