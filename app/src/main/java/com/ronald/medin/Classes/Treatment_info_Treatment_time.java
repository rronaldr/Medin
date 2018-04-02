package com.ronald.medin.Classes;

/**
 * Created by Ronald on 01.04.2018.
 */

public class Treatment_info_Treatment_time {
    private int id;
    private int treatment_infoId;
    private int treatment_timeId;

    public Treatment_info_Treatment_time(){

    }
    public Treatment_info_Treatment_time(int Id, int Treatment_infoId, int Treatment_timeId){
        this.id = Id;
        this.treatment_infoId = Treatment_infoId;
        this.treatment_timeId = Treatment_timeId;
    }
    public Treatment_info_Treatment_time(int Treatment_infoId, int Treatment_timeId){
        this.treatment_infoId = Treatment_infoId;
        this.treatment_timeId = Treatment_timeId;
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

    public int getTreatment_timeId() {
        return treatment_timeId;
    }

    public void setTreatment_timeId(int treatment_timeId) {
        this.treatment_timeId = treatment_timeId;
    }
}
