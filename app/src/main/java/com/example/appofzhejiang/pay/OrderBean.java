package com.example.appofzhejiang.pay;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.appofzhejiang.R;

import java.io.Serializable;
import java.net.PortUnreachableException;

public class OrderBean implements Serializable {

    private boolean _pay;//订单是否支付
    private String datetime;//下单时间
    private String delivery_way;//收货地址
    private double money;//总金额
    private String name;//真实姓名
    private int num;//数量
    private String param;//商品规格
    private String order_id;//订单ID
    private String order_no;//订单编号
    private boolean pin;//订单是否完成
    private int product_id;//产品ID
    private String remarks;//备注 用来放图片好了
    private String shop_name;//公司名称
    private String tel;//联系电话
    private int user_id;//购买的用户ID
    private String type;//商品类型

    public OrderBean() {
    }

    public OrderBean(String datetime, String delivery_way, double money, String name,
                     int num, String param, String order_id, String order_no,
                     String remarks, String shop_name, String tel, String type,
                     boolean _pay, boolean pin, int product_id, int user_id) {
        this.datetime = datetime;
        this.delivery_way = delivery_way;
        this.money = money;
        this.name = name;
        this.num = num;
        this.param = param;
        this.order_id = order_id;
        this.order_no = order_no;
        this.remarks = remarks;
        this.shop_name = shop_name;
        this.tel = tel;
        this.type = type;
        this._pay = _pay;
        this.pin = pin;
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDelivery_way() {
        return delivery_way;
    }

    public void setDelivery_way(String delivery_way) {
        this.delivery_way = delivery_way;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getPay() {
        return _pay;
    }

    public void setPay(boolean _pay) {
        this._pay = _pay;
    }

    public boolean getPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
