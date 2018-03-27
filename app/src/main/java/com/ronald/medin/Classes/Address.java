package com.ronald.medin.Classes;

/**
 * Created by Ronald on 20.03.2018.
 */

public class Address {

    private int id;
    private String city;
    private String street;
    private int street_number;

    public Address(){

    }

    public Address(int Id, String City, String Street, int Street_number){
        this.id = Id;
        this.city = City;
        this.street = Street;
        this.street_number = Street_number;
    }

    public Address(String City, String Street, int Street_number){
        this.city = City;
        this.street = Street;
        this.street_number = Street_number;
    }

    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //City
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    //Street
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    //Street_number
    public int getStreet_number() {
        return street_number;
    }
    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }
}
