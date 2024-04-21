package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOutResponce {
    @SerializedName("vusername")
    @Expose
    private String vusername;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gpay")
    @Expose
    private Object gpay;
    @SerializedName("scanner")
    @Expose
    private String scanner;
    @SerializedName("pin")
    @Expose
    private String pin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin=pin;
    }

    public CheckOutResponce(String vusername, String adress, String city, String state, String phone, String email, Object gpay, String scanner) {
        this.vusername=vusername;
        this.adress=adress;
        this.city=city;
        this.state=state;
        this.phone=phone;
        this.email=email;
        this.gpay=gpay;
        this.scanner=scanner;
    }

    public String getVusername() {
        return vusername;
    }

    public void setVusername(String vusername) {
        this.vusername=vusername;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress=adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public Object getGpay() {
        return gpay;
    }

    public void setGpay(Object gpay) {
        this.gpay=gpay;
    }

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner=scanner;
    }
}
