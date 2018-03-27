package com.ronald.medin.Classes;

/**
 * Created by Ronald on 20.03.2018.
 */

public class Contact {

    private int id;
    private String email;
    private int telephone;

    public Contact(){

    }

    public Contact(int Id, String Email, int Telephone){
        this.id = Id;
        this.email = Email;
        this.telephone = Telephone;
    }

    public Contact(String Email, int Telephone){
        this.email = Email;
        this.telephone = Telephone;
    }

    //Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //Telephone
    public int getTelephone() {
        return telephone;
    }
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
}
