package com.example.appofzhejiang.pay;

import android.widget.ImageView;
import android.widget.TextView;

public class FileList {
    private String Order, Title, Type, Count, Money1, Money2, Status;
    private int Picture;

    public FileList(int picture, String order, String title, String type,
                    String count, String money1, String money2, String status) {
        this.Picture = picture;
        this.Order = order;
        this.Title = title;
        this.Type = type;
        this.Count = count;
        this.Money1 = money1;
        this.Money2 = money2;
        this.Status = status;
    }

    public int getPicture() {
        return Picture;
    }

    public String getOrder() {
        return Order;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getCount() {
        return Count;
    }

    public String getMoney1() {
        return Money1;
    }

    public String getMoney2() {
        return Money2;
    }

    public String getStatus() {
        return Status;
    }

}
