package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthelyResponce {
    @SerializedName("vusername")
    @Expose
    private String vusername;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("delivery_adress")
    @Expose
    private String deliveryAdress;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getVusername() {
        return vusername;
    }

    public void setVusername(String vusername) {
        this.vusername=vusername;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId=orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate=orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime=orderTime;
    }

    public String getDeliveryAdress() {
        return deliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        this.deliveryAdress=deliveryAdress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state=state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }
}
