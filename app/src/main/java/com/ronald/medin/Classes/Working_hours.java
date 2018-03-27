package com.ronald.medin.Classes;

/**
 * Created by Ronald on 20.03.2018.
 */

public class Working_hours {

    private int id;
    private int day;
    private String fromwh;
    private String towh;

    public Working_hours(){

    }

    public Working_hours(int Id, int Day, String Fromwh, String Towh){
        this.id = Id;
        this.day = Day;
        this.fromwh = Fromwh;
        this.towh = Towh;
    }
    public Working_hours(int Day, String Fromwh, String Towh){
        this.day = Day;
        this.fromwh = Fromwh;
        this.towh = Towh;
    }

    //Id
    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}

    //Day
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    //From
    public String getFromwh() {
        return fromwh;
    }
    public void setFromwh(String fromwh) {
        this.fromwh = fromwh;
    }

    //To
    public String getTowh() {
        return towh;
    }
    public void setTowh(String towh) {
        this.towh = towh;
    }
}
