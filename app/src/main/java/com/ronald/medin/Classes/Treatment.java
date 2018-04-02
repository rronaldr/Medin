package com.ronald.medin.Classes;

/**
 * Created by Ronald on 01.04.2018.
 */

public class Treatment {
    private int id;
    private int treatment_used;

    public Treatment(){

    }

    public Treatment(int Id, int Treatment_used){
        this.id = Id;
        this.treatment_used = Treatment_used;
    }

    public Treatment(int Treatment_used){
        this.treatment_used = Treatment_used;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTreatment_used() {
        return treatment_used;
    }

    public void setTreatment_used(int treatment_used) {
        this.treatment_used = treatment_used;
    }
}
