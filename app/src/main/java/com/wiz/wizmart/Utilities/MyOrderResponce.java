package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrderResponce {
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("devtime")
    @Expose
    private String devtime;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("confirm_delivery")
    @Expose
    private String confirmDelivery;
    @SerializedName("dboy")
    @Expose
    private String dboy;

    public String getDevtime() {
        return devtime;
    }

    public void setDevtime(String devtime) {
        this.devtime=devtime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType=orderType;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor=vendor;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid=oid;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate=orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    public String getConfirmDelivery() {
        return confirmDelivery;
    }

    public void setConfirmDelivery(String confirmDelivery) {
        this.confirmDelivery=confirmDelivery;
    }


    public String getDboy() {
        return dboy;
    }

    public void setDboy(String dboy) {
        this.dboy=dboy;
    }
}
