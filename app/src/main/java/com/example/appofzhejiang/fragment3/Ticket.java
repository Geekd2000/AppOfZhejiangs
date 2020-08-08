package com.example.appofzhejiang.fragment3;

public class Ticket {
    private int imageId;
    private String name;
    private String price;
    private String location;
    private String count;

    public Ticket(int imageId, String name, String price, String location, String count) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.location = location;
        this.count = count;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getCount() {
        return count;
    }
}
