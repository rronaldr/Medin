package com.ronald.medin.Classes;

public class Doctor {

    private int id;
    private String specialization;
    private String name;
    private String surname;
    private String email;
    private int telephone;
    private String workingHours;

    public Doctor(){

    }
    public Doctor(int ID, String Specialization ,String Name, String Surname, String Email, int Telephone, String WorkingHours){
        this.id = ID;
        this.specialization = Specialization;
        this.name = Name;
        this.surname = Surname;
        this.email = Email;
        this.telephone = Telephone;
        this.workingHours = WorkingHours;
    }
    public Doctor(String Specialization ,String Name, String Surname, String Email, int Telephone, String WorkingHours){
        this.specialization = Specialization;
        this.name = Name;
        this.surname = Surname;
        this.email = Email;
        this.telephone = Telephone;
        this.workingHours = WorkingHours;
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

    //Working Hours
    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }
}
