package com.example.myapplication.DataModelAndParse;

public class Delivery {

    private int id;
    private String type;

    public Delivery() {}

    public Delivery(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Delivery(String type) {
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
