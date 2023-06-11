/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file MyFacilitator.java
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date 
 * @brief This file contains the implementation of the MyFacilitator class.
 * MyFacilitator is a concrete implementation of the Facilitator interface.
 * It represents a Facilitator, a type of Participant, with various fields such as areaOfExpertise.
 * 
 */
package my.cbl.participants;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;

public class MyFacilitator extends MyParticipant implements Facilitator {

    private String areaOfExpertise;
    
    
     /**
     * Constructs a MyFacilitator object with the specified facilitator information.
     * @param name the name of the Facilitator
     * @param email the email of the Facilitator
     * @param instituition the instituition of the Facilitator
     * @param areaOfExpertise the area of expertise of the Facilitator
     */
    public MyFacilitator(String name, String email, Instituition instituition,  String areaOfExpertise) {
        super(name, email, instituition);
        this.areaOfExpertise = areaOfExpertise;
    }

    /**
     * Getter method for areaOfExpertise
     *
     * @return the areaOfExpertise
     */
    @Override
    public String getAreaOfExpertise() {
        return this.areaOfExpertise;
    }

    /**
     * Setter method for the areaOfExpertise.
     *
     * @param setAreaOfExpertise - the areaOfExpertise to set.
     */
    @Override
    public void setAreaOfExpertise(String setAreaOfExpertise) {
        this.areaOfExpertise = setAreaOfExpertise;
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
