package com.ronald.medin.Classes;

/**
 * Created by Ronald on 25.03.2018.
 */

public class Doctor_Contact {

    private int id;
    private int doctorId;
    private int contactId;

    public Doctor_Contact(){

    }

    public Doctor_Contact(int Id, int DoctorId, int ContactId){
        this.id = Id;
        this.doctorId = DoctorId;
        this.contactId = ContactId;
    }
    public Doctor_Contact(int DoctorId, int ContactId){
        this.doctorId = DoctorId;
        this.contactId = ContactId;
    }

    //Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //DoctrId
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    //ContactId
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
