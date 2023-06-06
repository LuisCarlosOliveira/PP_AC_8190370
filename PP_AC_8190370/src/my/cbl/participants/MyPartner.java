/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package my.cbl.participants;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Partner;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class MyPartner extends MyParticipant implements Partner {

    private String vat;
    private String website;

    public MyPartner(String name, String email, Instituition instituition, Contact contact,
            String vat, String website) {
        super(name, email, instituition, contact);
        this.vat = vat;
        this.website = website;
    }

    @Override
    public String getVat() {
        return vat;
    }

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
