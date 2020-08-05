package com.example.myapplication.DataModelAndParse;

public class Service {

    private int id;
    private String name;
    private String address;
    private String web;
    private String description;
    private String phone;
    private String delivery;
    private int recycle_fee;
    private int back_fee;
    private int pay_rate;
    private int pay_back_rate;
    private String city;

    public Service(){ }
    public Service(String name, String phone, String address, String delivery, String web, String description, int recycle_fee,
                   int back_fee, int pay_rate, int pay_back_rate, String city){

        this.name = name;
        this.phone = phone;
        this.address = address;
        this.delivery = delivery;
        this.web = web;
        this.description = description;
        this.recycle_fee = recycle_fee;
        this.back_fee = back_fee;
        this.pay_rate = pay_rate;
        this.pay_back_rate = pay_back_rate;
        this.city = city;

    }
    public Service(int id, String name, String phone, String address, String delivery,
                   String web, String description, int recycle_fee, int back_fee, int pay_rate, int pay_back_rate, String city){

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.delivery = delivery;
        this.web = web;
        this.description = description;
        this.recycle_fee = recycle_fee;
        this.back_fee = back_fee;
        this.pay_rate = pay_rate;
        this.pay_back_rate = pay_back_rate;
        this.city = city;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDelivery() {
        return delivery;
    }
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
    public int getRecycle_fee() { return recycle_fee; }
    public void setRecycle_fee(int recycle_fee) { this.recycle_fee = recycle_fee; }
    public int getBack_fee() { return back_fee; }
    public void setBack_fee(int v) { this.back_fee = back_fee; }
    public int getPay_rate() {
        return pay_rate;
    }
    public void setPay_rate(int pay_rate) {
        this.pay_rate = pay_rate;
    }
    public int getPay_back_rate() {
        return pay_back_rate;
    }
    public void setPay_back_rate(int pay_back_rate) {
        this.pay_back_rate = pay_back_rate;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }


    public String toString(){
        return  "Service: " + id + " - " + name + " - " + phone + " - " + address + " - " + delivery + " - " + web + " - " + description + " - " + recycle_fee;
    }
}