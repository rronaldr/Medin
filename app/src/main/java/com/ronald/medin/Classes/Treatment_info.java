package com.ronald.medin.Classes;

/**
 * Created by Ronald on 01.04.2018.
 */

public class Treatment_info {

    private int id;
    private int days_to_use;
    private String usage_type;
    private int usage_amount;
    private int amount_to_notify;
    private String note;
    private int medicineId;
    private int inventoryId;

    public Treatment_info(){

    }

    public Treatment_info(int Id, int Days_to_use, String Usage_type, int Usage_amount, int Amount_to_notify, String Note, int MedicineId, int InventoryId){
        this.id = Id;;
        this.days_to_use = Days_to_use;
        this.usage_type = Usage_type;
        this.usage_amount = Usage_amount;
        this.amount_to_notify = Amount_to_notify;
        this.note = Note;
        this.medicineId = MedicineId;
        this.inventoryId = InventoryId;
    }

    public Treatment_info(int Days_to_use, String Usage_type, int Usage_amount, int Amount_to_notify, String Note, int MedicineId, int InventoryId){
        this.days_to_use = Days_to_use;
        this.usage_type = Usage_type;
        this.usage_amount = Usage_amount;
        this.amount_to_notify = Amount_to_notify;
        this.note = Note;
        this.medicineId = MedicineId;
        this.inventoryId = InventoryId;
    }

    //Get Set methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDays_to_use() {
        return days_to_use;
    }

    public void setDays_to_use(int days_to_use) {
        this.days_to_use = days_to_use;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public int getUsage_amount() {
        return usage_amount;
    }

    public void setUsage_amount(int usage_amount) {
        this.usage_amount = usage_amount;
    }

    public int getAmount_to_notify() {
        return amount_to_notify;
    }

    public void setAmount_to_notify(int amount_to_notify) {
        this.amount_to_notify = amount_to_notify;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
}
