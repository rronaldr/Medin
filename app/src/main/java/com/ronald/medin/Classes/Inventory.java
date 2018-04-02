package com.ronald.medin.Classes;

/**
 * Created by Ronald on 01.04.2018.
 */

public class Inventory {
    private int id;
    private int quantity;

    public Inventory(){

    }

    public Inventory(int Id, int Quantity){
        this.id = Id;
        this.id = Quantity;
    }
    public Inventory(int Quantity){
        this.id = Quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
