package com.example.appofzhejiang.Business;

import java.io.Serializable;

public class AddressBean implements Serializable {

    private boolean _default;
    private String address;
    private String mobile;
    private String name;
    private int id;
    private int user_id;

    public AddressBean() {
    }

    public AddressBean(boolean _default, String address, String mobile, String name,int id,int user_id) {
        this._default = _default;
        this.address = address;
        this.mobile = mobile;
        this.name = name;
        this.id = id;
        this.user_id = user_id;
    }

    public boolean getDefault() {
        return _default;
    }

    public void setDefault(boolean _default) {
        this._default = _default;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
