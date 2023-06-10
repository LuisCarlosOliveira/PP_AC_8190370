/**
 * @file MyPartner.java
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date 
 * @brief This file contains the implementation of the MyPartner class.
 * MyPartner is a concrete implementation of the Partner interface.
 * It represents a Partner, a type of Participant, with various fields such as 
 * vat and website.
 */
package my.cbl.participants;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Partner;


public class MyPartner extends MyParticipant implements Partner {

    private String vat;
    private String website;

    /**
     * Constructs a MyPartner object with the specified partner information.
     * @param name the name of the partner
     * @param email the email of the partner
     * @param instituition the instituition of the partner
     * @param contact the contact of the partner
     * @param vat the VAT number of the partner
     * @param website the website of the partner
     */
    public MyPartner(String name, String email, Instituition instituition, Contact contact,
            String vat, String website) {
        super(name, email, instituition, contact);
        this.vat = vat;
        this.website = website;
    }

    /**
     * Getter method for VAT
     *
     * @return the VAT
     */
    @Override
    public String getVat() {
        return vat;
    }

    /**
     * Getter method for the website
     *
     * @return the website
     */
    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        String vatString = (this.vat != null) ? this.vat : "None";
        String websiteString = (this.website != null) ? this.website : "None";

        return "Partner Information:\n"
                + "Name: " + this.getName() + "\n"
                + "Email: " + this.getEmail() + "\n"
                + "Instituition: " + getInstituition().toString() + "\n"
                + "VAT: " + vatString + "\n"
                + "Website: " + websiteString + "\n";
    }

}
