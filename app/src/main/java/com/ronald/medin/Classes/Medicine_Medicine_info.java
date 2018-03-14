package com.ronald.medin.Classes;

/**
 * Created by Ronald on 13.03.2018.
 */

public class Medicine_Medicine_info {
    private int id;
    private int medicineId;
    private int medicine_infoId;

    public Medicine_Medicine_info(int ID, int MedicineID, int Medicine_infoID){
        this.id = ID;
        this.medicineId = MedicineID;
        this.medicine_infoId = Medicine_infoID;
    }

    public Medicine_Medicine_info(int MedicineID, int Medicine_infoID){
        this.medicineId = MedicineID;
        this.medicine_infoId = Medicine_infoID;
    }

    //ID
    public int getId() {
        return id;
    }
    public void setId(int ID) {
        this.id = ID;
    }

    //MedicineID
    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int MedicineId) {
        this.medicineId = MedicineId;
    }

    //Medicine_infoID

    public int getMedicine_infoId() {
        return medicine_infoId;
    }

    public void setMedicine_infoId(int Medicine_infoId) {
        this.medicine_infoId = Medicine_infoId;
    }
}
