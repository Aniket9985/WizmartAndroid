package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalenderResponce {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("packsize")
    @Expose
    private String packsize;
    @SerializedName("myuom")
    @Expose
    private String myuom;
    @SerializedName("orders_date")
    @Expose
    private String ordersDate;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("is_delivered")
    @Expose
    private String is_delivered;
    @SerializedName("statusis")
    @Expose
    private String statusis;

    public String getIs_delivered() {
        return is_delivered;
    }

    public void setIs_delivered(String is_delivered) {
        this.is_delivered=is_delivered;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId=orderId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty=qty;
    }

    public String getPacksize() {
        return packsize;
    }

    public void setPacksize(String packsize) {
        this.packsize=packsize;
    }

    public String getMyuom() {
        return myuom;
    }

    public void setMyuom(String myuom) {
        this.myuom=myuom;
    }

    public String getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(String ordersDate) {
        this.ordersDate=ordersDate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid=pid;
    }

    public String getStatusis() {
        return statusis;
    }

    public void setStatusis(String statusis) {
        this.statusis=statusis;
    }
}
