package com.ronald.medin.Classes;

/**
 * Created by Ronald on 25.03.2018.
 */

public class Doctor_Address {

    private int id;
    private int doctorId;
    private int addressId;

    public Doctor_Address(){

    }

    public Doctor_Address(int Id, int DoctorId, int AddressId){
        this.id = Id;
        this.doctorId = DoctorId;
        this.addressId = AddressId;
    }
    public Doctor_Address(int DoctorId, int AddressId){
        this.doctorId = DoctorId;
        this.addressId = AddressId;
    }

    //Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //DoctorId
    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    //AddressId
    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
