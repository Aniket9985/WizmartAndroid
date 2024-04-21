package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("username")
    @Expose
    private String username;

    public UpdateModel(String id, String adress, String city, String pin, String state, String phone, String email, String name, String area, String sector, String username) {
        this.id=id;
        this.adress=adress;
        this.city=city;
        this.pin=pin;
        this.state=state;
        this.phone=phone;
        this.email=email;
        this.name=name;
        this.area=area;
        this.sector=sector;
        this.username=username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin=pin;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area=area;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector=sector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }
}
