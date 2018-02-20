package com.ronald.medin.Classes;


import java.sql.Timestamp;

public class Measurment {

    private int id;
    private String measurement_name;
    private int measurement_value;
    private String measurement_unit;
    private String measurement_created;

    public Measurment(){

    }
    public Measurment(int Id, String Measurement_name, int Measurement_value, String Measurement_unit){
        this.id = Id;
        this.measurement_name = Measurement_name;
        this.measurement_value = Measurement_value;
        this.measurement_unit = Measurement_unit;
    }

    public Measurment(String Measurement_name, int Measurement_value, String Measurement_unit){
        this.measurement_name = Measurement_name;
        this.measurement_value = Measurement_value;
        this.measurement_unit = Measurement_unit;
    }

    //Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Measurement name
    public String getMeasurement_name() {
        return measurement_name;
    }
    public void setMeasurement_name(String measurement_name) {
        this.measurement_name = measurement_name;
    }

    //Measurement value
    public int getMeasurement_value() {
        return measurement_value;
    }
    public void setMeasurement_value(int measurement_value) {
        this.measurement_value = measurement_value;
    }

    //Measurement unit
    public String getMeasurement_unit() {
        return measurement_unit;
    }
    public void setMeasurement_unit(String measurement_unit) {
        this.measurement_unit = measurement_unit;
    }

    //Measurement datetime
    public String getMeasurement_created() {
        return measurement_created;
    }
    public void setMeasurement_created(String measurement_created) {
        this.measurement_created = measurement_created;
    }
}
