/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package my.cbl.participants;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MyFacilitator extends MyParticipant implements Facilitator {

    private String areaOfExpertise;

    public MyFacilitator(String name, String email, Instituition instituition, 
            Contact contact, String areaOfExpertise) {
        super(name, email, instituition, contact);
        this.areaOfExpertise = areaOfExpertise;
    }

    @Override
    public String getAreaOfExpertise() {
        return this.areaOfExpertise;
    }

    @Override
    public void setAreaOfExpertise(String string) {
        this.areaOfExpertise = string;
    }

    @Override
    public String toString() {
        String contactString = (getContact() != null) ? getContact().toString() : "None";
        String areaOfExpertiseString = (this.areaOfExpertise != null) ? this.areaOfExpertise : "None";

        return "Facilitator Information:\n"
                + "Name: " + getName() + "\n"
                + "Email: " + getEmail() + "\n"
                + "Instituition: " + getInstituition().toString() + "\n"
                + "Contact: " + contactString + "\n"
                + "Area of Expertise: " + areaOfExpertiseString + "\n";
    }

}
