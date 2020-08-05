package com.example.myapplication.DataModelAndParse;

public class City {

    private int id;
    private String cityName;

    public City() {}

    public City(int id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    public City(String cityName) {
        this.cityName = cityName;
    }


    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }
}
