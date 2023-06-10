/*
* Nome: Luís Carlos Mendes de Oliveira
* Número: 8190370
* Turma: LEI12T2
*/

/**
 * @file MyContact.java
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief This file contains the implementation of the MyContact class.
 * MyContact is a concrete implementation of the Contact interface. It
 * represents a contact with various fields such as city, country, phone, etc.
 *
 */
package my.cbl.participants;

import ma02_resources.participants.Contact;

public class MyContact implements Contact {

    private String city;
    private String country;
    private String phone;
    private String state;
    private String street;
    private String zipCode;

    /**
     * Constructs a MyContact object with the specified contact information.
     *
     * @param city the city of the contact
     * @param country the country of the contact
     * @param phone the phone number of the contact
     * @param state the state of the contact
     * @param street the street address of the contact
     * @param zipCode the zip code of the contact
     */
    public MyContact(String city, String country, String phone, String state, String street, String zipCode) {
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.state = state;
        this.street = street;
        this.zipCode = zipCode;
    }

    /**
     * Getter method for street
     *
     * @return the street
     */
    @Override
    public String getStreet() {
        return this.street;
    }

    /**
     * Getter method for city
     *
     * @return the city
     */
    @Override
    public String getCity() {
        return this.city;
    }

    /**
     * Getter method for the state
     *
     * @return the state
     */
    @Override
    public String getState() {
        return this.state;
    }

    /**
     * Getter method for the zip code
     *
     * @return the zip code
     */
    @Override
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Getter method for the country
     *
     * @return the country
     */
    @Override
    public String getCountry() {
        return this.country;
    }

    /**
     * Getter method for the phone number.
     *
     * @return the phone number
     */
    @Override
    public String getPhone() {
        return this.phone;
    }

    /**
     * This method is used to compare two Contacts. Two Contacts are equal if
     * they have the same info.
     *
     * @param obj the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MyContact)) {
            return false;
        }
        MyContact temp = (MyContact) obj;

        return this.city.equals(temp.city)
                && this.country.equals(temp.country)
                && this.phone.equals(temp.phone)
                && this.state.equals(temp.state)
                && this.street.equals(temp.street)
                && this.zipCode.equals(temp.zipCode);
    }

    /**
     * Returns a string representation of the MyContact object.
     *
     * @return a string representation of the MyContact object
     */
    @Override
    public String toString() {
        String cityString = (this.city != null) ? this.city : "None";
        String countryString = (this.country != null) ? this.country : "None";
        String phoneString = (this.phone != null) ? this.phone : "None";
        String stateString = (this.state != null) ? this.state : "None";
        String streetString = (this.street != null) ? this.street : "None";
        String zipCodeString = (this.zipCode != null) ? this.zipCode : "None";

        return "Contact Information:\n"
                + "City: " + cityString + "\n"
                + "Country: " + countryString + "\n"
                + "Phone: " + phoneString + "\n"
                + "State: " + stateString + "\n"
                + "Street: " + streetString + "\n"
                + "Zip Code: " + zipCodeString + "\n";
    }

}
