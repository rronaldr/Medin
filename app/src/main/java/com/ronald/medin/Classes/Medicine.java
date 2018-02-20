package com.ronald.medin.Classes;

public class Medicine {

    private int id;
    private String name;
    private String type;
    private int packageQuantity;

    public Medicine(){

    }

    public Medicine(int ID, String Name, String Type, int PackageQuantity){
        this.id = ID;
        this.name = Name;
        this.type = Type;
        this.packageQuantity = PackageQuantity;
    }
    public Medicine(String Name, String Type, int PackageQuantity){
        this.name = Name;
        this.type = Type;
        this.packageQuantity = PackageQuantity;
    }

    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Type
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //PackageQuantity
    public int getPackageQuantity() {
        return packageQuantity;
    }
    public void setPackageQuantity(int packageQuantity) {
        this.packageQuantity = packageQuantity;
    }
}
