package com.ronald.medin.Classes;

public class Doctor {

    private int id;
    private String name;
    private String surname;
    private String email;
    private int telephone;

    public Doctor(){

    }
    public Doctor(int ID, String Name, String Surname, String Email, int Telephone){
        this.id = ID;
        this.name = Name;
        this.surname = Surname;
        this.email = Email;
        this.telephone = Telephone;
    }
    public Doctor(String Name, String Surname, String Email, int Telephone){
        this.name = Name;
        this.surname = Surname;
        this.email = Email;
        this.telephone = Telephone;
    }

    //Id
    public int getId(){
        return id;
    }
    public void setId(int ID){
        this.id = ID;
    }

    //Name
    public String getName(){
        return name;
    }
    public void setName(String Name){
        this.name = Name;
    }

    //Surname
    public String getSurname (){
        return surname;
    }
    public void setSurname(String Surname){
        this.surname = Surname;
    }

    //Email
    public String getEmail (){
        return email;
    }
    public void setEmail (String Email){
        this.email = Email;
    }

    //Telephone
    public int getTelephone(){
        return telephone;
    }
    public void setTelephone (int Telephone){
        this.telephone = Telephone;
    }

}
