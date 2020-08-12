package com.example.appofzhejiang.recyclerpage;

public class RecyclerBean {
    private int imageId;
    private String name;
    private String type;
    private String time;
    private String count;

    public RecyclerBean(int imageId, String name, String type, String time, String count) {
        this.imageId = imageId;
        this.name = name;
        this.type = type;
        this.time = time;
        this.count = count;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
