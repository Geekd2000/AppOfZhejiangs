package com.example.appofzhejiang.recyclerpage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.appofzhejiang.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecyclerBean {
    private Bitmap bitmapImg;
    private String pictures;
    private String title;
    private String type;
    private String time;
    private String dread;

    public RecyclerBean(Bitmap bitmapImg, String pictures, String title, String type, String time, String dread) {
        this.bitmapImg = bitmapImg;
        this.pictures = pictures;
        this.title = title;
        this.type = type;
        this.time = time;
        this.dread = dread;
    }

    public RecyclerBean(String pictures, String title, String type, String time, String dread) {
        this.bitmapImg = getImageBitmap(pictures);
        this.title = title;
        this.type = type;
        this.time = time;
        this.dread = dread;
    }

    /**
     * 返回的图片地址转为Bitmap格式的图片
     *
     * @param imgPath
     * @return
     */
    private static Bitmap getImageBitmap(String imgPath) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(imgPath).openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap getBitmapImg() {
        return bitmapImg;
    }

    public void setBitmapImg(Bitmap bitmapImg) {
        this.bitmapImg = bitmapImg;

    }

    public String pictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
        this.bitmapImg = getImageBitmap(pictures);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDread() {
        return dread;
    }

    public void setDread(String dread) {
        this.dread = dread;
    }
}