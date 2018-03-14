package com.ronald.medin.Classes;

public class Medicine_info {
    private int id;
    private String description;
    private String warning;
    private String usageInstructions;
    private String sideEffects;
    private String storageInstructions;
    private String otherInformation;

    public Medicine_info(){

    }
    public Medicine_info(int ID, String Description, String Warning, String UsageInstructions, String SideEffects, String StorageInstructions, String OtherInformation){
        this.id = ID;
        this.description = Description;
        this.warning = Warning;
        this.usageInstructions = UsageInstructions;
        this.sideEffects = SideEffects;
        this.storageInstructions = StorageInstructions;
        this.otherInformation = OtherInformation;
    }
    public Medicine_info(String Description, String Warning, String UsageInstructions, String SideEffects, String StorageInstructions, String OtherInformation){
        this.description = Description;
        this.warning = Warning;
        this.usageInstructions = UsageInstructions;
        this.sideEffects = SideEffects;
        this.storageInstructions = StorageInstructions;
        this.otherInformation = OtherInformation;
    }

    //ID
    public int getId() {

        return id;
    }
    public void setId(int id) {

        this.id = id;
    }

    //Description
    public String getDescription() {

        return description;
    }
    public void setDescription(String description) {

        this.description = description;
    }

    //Warning
    public String getWarning() {

        return warning;
    }
    public void setWarning(String warning)
    {
        this.warning = warning;
    }

    //Usage instructions
    public String getUsageInstructions() {
        return usageInstructions;
    }
    public void setUsageInstructions(String usageInstructions) {
        this.usageInstructions = usageInstructions;
    }

    //Side effects
    public String getSideEffects() {
        return sideEffects;
    }
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    //Storage instructions
    public String getStorageInstructions() {
        return storageInstructions;
    }
    public void setStorageInstructions(String storageInstructions) {
        this.storageInstructions = storageInstructions;
    }

    //Other information
    public String getOtherInformation() {
        return otherInformation;
    }
    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }
}
