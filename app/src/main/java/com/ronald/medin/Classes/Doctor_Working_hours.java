package com.ronald.medin.Classes;

/**
 * Created by Ronald on 25.03.2018.
 */

public class Doctor_Working_hours {

    private int id;
    private int doctorId;
    private int working_hours_id;

    public Doctor_Working_hours(){

    }

    public Doctor_Working_hours(int Id, int DoctorId, int Working_hoursId){
        this.id = Id;
        this.doctorId = DoctorId;
        this.working_hours_id = Working_hoursId;
    }

    public Doctor_Working_hours(int DoctorId, int Working_hoursId){
        this.doctorId = DoctorId;
        this.working_hours_id = Working_hoursId;
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

    //Working_hoursId
    public int getWorking_hours_id() {
        return working_hours_id;
    }
    public void setWorking_hours_id(int working_hours_id) {
        this.working_hours_id = working_hours_id;
    }
}
