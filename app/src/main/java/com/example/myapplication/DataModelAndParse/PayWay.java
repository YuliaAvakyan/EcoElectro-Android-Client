package com.example.myapplication.DataModelAndParse;

public class PayWay {
    private int id;
    private String way;

    public PayWay() {}

    public PayWay(int id, String way) {
        this.id = id;
        this.way = way;
    }

    public PayWay(String way) {
        this.way = way;
    }


    public int getId() {
        return id;
    }

    public String getWay() {
        return way;
    }
}
