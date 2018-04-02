package com.ronald.medin.Classes;

/**
 * Created by Ronald on 01.04.2018.
 */

public class Treatment_info_Treatment {
    private int id;
    private int treatment_infoId;
    private int treatmentId;

    public Treatment_info_Treatment(){

    }

    public Treatment_info_Treatment(int Id, int Treatment_infoId, int TreatmentId){
        this.id = Id;
        this.treatment_infoId = Treatment_infoId;
        this.treatmentId = TreatmentId;
    }

    public Treatment_info_Treatment(int Treatment_infoId, int TreatmentId){
        this.treatment_infoId = Treatment_infoId;
        this.treatmentId = TreatmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTreatment_infoId() {
        return treatment_infoId;
    }

    public void setTreatment_infoId(int treatment_infoId) {
        this.treatment_infoId = treatment_infoId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }
}
