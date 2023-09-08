package com.example.jfx_project;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Class for holding data from databse
 */
public class Person implements Serializable {

    final private int id;
    private String postalCode;
    private String city;
    private String phoneNumber;
    private String surname;
    private Date orderPlacementDate;
    private Date orderReceiptDate;
    private String species;
    private double price;
    private int amount;
    private int status;
    private String stringStatus="";
    private String info;

    /**
     * Constructs new Person object.
     *
     * @param id The unique ID of placed order
     * @param postalCode Postal code on which order was placed
     * @param city City on which order was placed
     * @param phoneNumber Phone number on which order was placed
     * @param surname Surname on which order was placed
     * @param orderPlacementDate Date at which the order was placed
     * @param orderReceiptDate Date at which th order is expected to be delivered
     * @param species Species name of the ordered product
     * @param price Price of product per item
     * @param amount Amount of ordered products
     * @param status Status of ordered product
     * @param info Additional information about order
     */
    public Person(int id, String postalCode, String city, String phoneNumber, String surname, Date orderPlacementDate, Date orderReceiptDate, String species, double price, int amount, int status, String info) {
        this.id = id;
        this.postalCode = postalCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.surname = surname;
        this.orderPlacementDate = orderPlacementDate;
        this.orderReceiptDate = orderReceiptDate;
        this.species = species;
        this.amount = amount;
        this.price = price;
        this.info = info;
        setStatus(status); // sets both, status and stringStatus values
    }

    /**
     * Updates 'Person' objects data.
     *
     * @param postalCode         Postal code on which order was placed.
     * @param city               City on which order was placed.
     * @param phoneNumber        Phone number on which order was placed.
     * @param surname            Surname on which order was placed.
     * @param orderPlacementDate Date at which the order was placed.
     * @param orderReceiptDate   Date at which th order is expected to be delivered.
     * @param species            Species name of the ordered product.
     * @param price              Price of product per item.
     * @param amount             Amount of ordered products.
     * @param status             Status of ordered product.
     * @param info               Additional information about order.
     */
    public void updateAll(String postalCode, String city, String phoneNumber, String surname, Date orderPlacementDate, Date orderReceiptDate, String species, double price, int amount, int status, String info){
        setPostalCode(postalCode);
        setCity(city);
        setPhoneNumber(phoneNumber);
        setSurname(surname);
        setOrderPlacementDate(orderPlacementDate);
        setOrderReceiptDate(orderReceiptDate);
        setSpecies(species);
        setAmount(amount);
        setPrice(price);
        setInfo(info);
        setStatus(status);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return  Objects.equals(id, other.id)
                && Objects.equals(postalCode, other.postalCode)
                && Objects.equals(city, other.city)
                && Objects.equals(phoneNumber, other.phoneNumber)
                && Objects.equals(surname, other.surname)
                && Objects.equals(orderPlacementDate, other.orderPlacementDate)
                && Objects.equals(orderReceiptDate, other.orderReceiptDate)
                && Objects.equals(species, other.species)
                && Objects.equals(amount, other.amount)
                && Objects.equals(info, other.info)
                && Objects.equals(stringStatus, other.stringStatus)
                && Objects.equals(status, other.status);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postalCode, city, phoneNumber, surname, orderPlacementDate, orderReceiptDate, species, amount, status, info);
    }

    /**
     * Retrieves the ID of the person.
     *
     * @return The ID of the person.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the postal code of the person.
     *
     * @return The postal code of the person.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the person.
     *
     * @param postalCode The postal code to set for the person.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Retrieves the city of the person.
     *
     * @return The city of the person.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the person.
     *
     * @param city The city to set for the person.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Retrieves the phone number of the person.
     *
     * @return The phone number of the person.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phoneNumber The phone number to set for the person.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the surname of the person.
     *
     * @return The surname of the person.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the person.
     *
     * @param surname The surname to set for the person.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Retrieves the order placement date of the person.
     *
     * @return The order placement date of the person.
     */
    public Date getOrderPlacementDate() {
        return orderPlacementDate;
    }

    /**
     * Sets the order placement date of the person's order.
     *
     * @param orderPlacementDate The order placement date to set for the person's order.
     */
    public void setOrderPlacementDate(Date orderPlacementDate) {
        this.orderPlacementDate = orderPlacementDate;
    }

    /**
     * Retrieves the order receipt date of the person.
     *
     * @return The order receipt date of the person.
     */
    public Date getOrderReceiptDate() {
        return orderReceiptDate;
    }

    /**
     * Sets the order receipt date of the person's order.
     *
     * @param orderReceiptDate The order receipt date to set for the person's order.
     */
    public void setOrderReceiptDate(Date orderReceiptDate) {
        this.orderReceiptDate = orderReceiptDate;
    }

    /**
     * Retrieves the species of the person.
     *
     * @return The species of the person.
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Retrieves the amount ordered by the person.
     *
     * @return The amount ordered by the person.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount ordered by the person.
     *
     * @param amount The amount to set for the person's order.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the price of the person's order.
     *
     * @return The price of the person's order.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the person's order.
     *
     * @param price The price to set for the person's order.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the additional information about the person's order.
     *
     * @return The additional information about the person's order.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the additional information about the person's order.
     *
     * @param info The additional information to set for the person's order.
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Retrieves the status code of the person's order.
     *
     * @return The status code of the person's order.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status code of the person's order and updates the corresponding string representation of the status.
     *
     * @param status The status code to set for the person's order.
     */
    public void setStatus(int status) {
        this.status = status;
        switch (status) {
            case 1:
                this.stringStatus = "AKTYWNE";
                break;
            case 2:
                this.stringStatus = "ZAKOŃCZONE";
                break;
            case 3:
                this.stringStatus = "UWAGA I";
                break;
            case 4:
                this.stringStatus = "UWAGA II";
                break;
            default:
                break;
        }
    }

    /**
     * Retrieves the string representation of the person's order status.
     *
     * @return The string representation of the person's order status.
     */
    public String getStringStatus() {
        return stringStatus;
    }

    /**
     * Sets the species of the person's order.
     *
     * @param species The species to set for the person's order.
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * Returns a string representation of the person.
     *
     * @return A string representation of the person, containing their ID.
     */
    public String toString() {
        return "Person: " + this.getId();
    }

}
