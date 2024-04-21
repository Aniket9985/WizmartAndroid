package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DuesResponce {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("packsize")
    @Expose
    private Integer packsize;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("on_date")
    @Expose
    private String onDate;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId=orderId;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount=paidAmount;
    }

    public Integer getPacksize() {
        return packsize;
    }

    public void setPacksize(Integer packsize) {
        this.packsize=packsize;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode=paymentMode;
    }

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate=onDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total=total;
    }
}
