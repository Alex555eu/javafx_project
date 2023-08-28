package com.example.jfx_project;

import java.io.Serializable;
import java.sql.Date;


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

    public int getId() {
        return id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getOrderPlacementDate() {
        return orderPlacementDate;
    }

    public Date getOrderReceiptDate() {
        return orderReceiptDate;
    }

    public String getSpecies() {
        return species;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

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

    public String getStringStatus() {
        return stringStatus;
    }

    public void setOrderPlacementDate(Date orderPlacementDate) {
        this.orderPlacementDate = orderPlacementDate;
    }

    public void setOrderReceiptDate(Date orderReceiptDate) {
        this.orderReceiptDate = orderReceiptDate;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String toString() {
        return "Person: " + this.getId();
    }
}
