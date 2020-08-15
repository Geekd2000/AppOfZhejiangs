package com.example.appofzhejiang.pay;

import android.widget.ImageView;
import android.widget.TextView;

public class FileList {
    private String Order, Message, Number, Money, Money1, Status;
    private String Picture;

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getMoney1() {
        return Money1;
    }

    public void setMoney1(String money1) {
        Money1 = money1;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}
