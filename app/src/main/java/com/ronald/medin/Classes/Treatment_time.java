package com.ronald.medin.Classes;

/**
 * Created by Ronald on 01.04.2018.
 */

public class Treatment_time {
    private int id;
    private int hour;
    private int minute;

    public Treatment_time(){

    }

    public Treatment_time(int Id, int Hour, int Minute){
        this.id = Id;
        this.hour = Hour;
        this.minute = Minute;
    }

    public Treatment_time(int Hour, int Minute){
        this.hour = Hour;
        this.minute = Minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
