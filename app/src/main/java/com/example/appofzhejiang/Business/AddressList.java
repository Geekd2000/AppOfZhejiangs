package com.example.appofzhejiang.Business;

public class AddressList {

    private String username, phone, address, select;

    public AddressList() {
    }

    public AddressList(String username, String phone, String address, String select) {
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.select = select;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
