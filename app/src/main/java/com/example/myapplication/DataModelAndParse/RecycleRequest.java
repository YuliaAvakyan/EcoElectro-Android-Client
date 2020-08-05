package com.example.myapplication.DataModelAndParse;

public class RecycleRequest {
    private int id;
    private String name;
    private String phone;
    private String address;
    private int weight;
    private int service_id;
    private int delivery;
    private int pay_way;

    public RecycleRequest(){ }

    public RecycleRequest(String name, String phone, String address, int weight, int service_id, int delivery, int pay_way){
        this.setName(name);
        this.setPhone(phone);
        this.setAddress(address);
        this.setWeight(weight);
        this.setService_id(service_id);
        this.setDelivery(delivery);
        this.setPay_way(pay_way);
    }

    public RecycleRequest(int id, String name, String phone, String address, int weight, int service_id, int delivery, int pay_way){
        this.setId(id);
        this.setName(name);
        this.setPhone(phone);
        this.setAddress(address);
        this.setWeight(weight);
        this.setService_id(service_id);
        this.setDelivery(delivery);
        this.setPay_way(pay_way);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getPay_way() {
        return pay_way;
    }

    public void setPay_way(int pay_way) {
        this.pay_way = pay_way;
    }
}
