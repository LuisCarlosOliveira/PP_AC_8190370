/*
 * @file: MyInstituition.java
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief: This file contains the implementation of the MyInstituition class.
 * MyInstituition is a concrete implementation of the Instituition interface.
 * It represents an institution participating in the CBL.
 * 
 */
package my.cbl.participants;


import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.InstituitionType;


public class MyInstituition implements Instituition {

    private String name;
    private String email;
    private InstituitionType type;
    private Contact contact;
    private String description;
    private String website;

    /**
     * Constructs a MyInstituition object with the specified name, email, and
     * type.
     *
     * @param name the name of the institution (mandatory)
     * @param email the email of the institution (mandatory)
     * @param type the type of the institution (mandatory)
     * @throws IllegalArgumentException if name, email, or type is null or empty
     */
    public MyInstituition(String name, String email, InstituitionType type) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || type == null) {
            throw new IllegalArgumentException("Name, email, and type are mandatory attributes.");
        }
        this.name = name;
        this.email = email;
        this.type = type;
    }

    /**
     * Constructs a MyInstituition object with the specified name, email, type,
     * contact, description and website
     *
     * @param name the name of the institution (mandatory)
     * @param email the email of the institution (mandatory)
     * @param type the type of the institution (mandatory)
     * @param contact the contact of the institution
     * @param description the description of the institution
     * @param website the website of the institution
     * @throws IllegalArgumentException if name, email, or type is null or empty
     */
    public MyInstituition(String name, String email, InstituitionType type,
            Contact contact, String description, String website) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || type == null) {
            throw new IllegalArgumentException("Name, email, and type are mandatory attributes.");
        }
        this.name = name;
        this.email = email;
        this.type = type;
        this.contact = contact;
        this.description = description;
        this.website = website;
    }

    /**
     * Getter method for the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for the email.
     *
     * @return the email
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter method for the type.
     *
     * @return the type
     */
    @Override
    public InstituitionType getType() {
        return this.type;
    }

    /**
     * Getter method for the contact.
     *
     * @return the contact
     */
    @Override
    public Contact getContact() {
        return this.contact;
    }

    /**
     * Getter method for the website.
     *
     * @return the website.
     */
    @Override
    public String getWebsite() {
        return this.website;
    }

    /**
     * Getter method for the description.
     *
     * @return the description
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter method for the website.
     *
     * @param website - the website to set.
     */
    @Override
    public void setWebsite(String string) {
        this.website = string;
    }

    /**
     * Setter method for the description.
     *
     * @param description - the description to set.
     */
    @Override
    public void setDescription(String string) {
        this.description = string;
    }

    /**
     * Setter method for the contact.
     *
     * @param contact - the contact to set.
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    /**
     * Setter method for the type.
     *
     * @param type - the type to set.
     */
    @Override
    public void setType(InstituitionType it) {
        this.type = it;
    }

    /**
     * This method is used to compare two Institutions. Two Institutions are
     * equal if they have the same name.
     *
     * @param o the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MyInstituition temp = (MyInstituition) o;

        return this.name.equals(temp.name);
    }

    /**
     * Returns a string representation of the MyInstituition object.
     *
     * @return a string representation of the MyInstituition object
     */
    @Override
    public String toString() {
        String contactString = (this.contact != null) ? this.contact.toString() : "None";
        String descriptionString = (this.description != null) ? this.description : "None";
        String websiteString = (this.website != null) ? this.website : "None";

        return "Instituition:\n"
                + "\nname='" + name 
                + "\nemail='" + email
                + "\ntype=" + type
                + "\ncontact=" + contactString
                + "\ndescription='" + descriptionString
                + "\nwebsite='" + websiteString;
    }
}
