package com.ronald.medin.Classes;

public class Doctor {

    private int id;
    private String specialization;
    private String name;
    private String surname;

    public Doctor(){

    }
    public Doctor(int ID, String Name, String Surname, String Specialization){
        this.id = ID;
        this.name = Name;
        this.surname = Surname;
        this.specialization = Specialization;
    }
    public Doctor(String Name, String Surname, String Specialization){
        this.specialization = Specialization;
        this.name = Name;
        this.surname = Surname;
    }

    //Id
    public int getId(){
        return id;
    }
    public void setId(int ID){
        this.id = ID;
    }

    //Degree
    public String getSpecialization(){ return specialization; }
    public void setSpecialization(String Specialization){ this.specialization = Specialization; }

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
}
