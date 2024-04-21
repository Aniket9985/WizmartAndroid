package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("psize")
    @Expose
    private String psize;
    @SerializedName("price2")
    @Expose
    private String price2;
    @SerializedName("uom2")
    @Expose
    private String uom2;
    @SerializedName("psize2")
    @Expose
    private String psize2;
    @SerializedName("price3")
    @Expose
    private String price3;
    @SerializedName("uom3")
    @Expose
    private String uom3;
    @SerializedName("psize3")
    @Expose
    private String psize3;
    @SerializedName("vusername")
    @Expose
    private String vusername;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("instock")
    @Expose
    private String instock;

    public DetailsModel(String id, String img, String item, String category, String type, String description, String price, String uom, String psize, String price2, String uom2, String psize2, String price3, String uom3, String psize3, String vusername, String displayname, String instock) {
        this.id=id;
        this.img=img;
        this.item=item;
        this.category=category;
        this.type=type;
        this.description=description;
        this.price=price;
        this.uom=uom;
        this.psize=psize;
        this.price2=price2;
        this.uom2=uom2;
        this.psize2=psize2;
        this.price3=price3;
        this.uom3=uom3;
        this.psize3=psize3;
        this.vusername=vusername;
        this.displayname=displayname;
        this.instock=instock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img=img;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item=item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category=category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price=price;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom=uom;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize=psize;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2=price2;
    }

    public String getUom2() {
        return uom2;
    }

    public void setUom2(String uom2) {
        this.uom2=uom2;
    }

    public String getPsize2() {
        return psize2;
    }

    public void setPsize2(String psize2) {
        this.psize2=psize2;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3=price3;
    }

    public String getUom3() {
        return uom3;
    }

    public void setUom3(String uom3) {
        this.uom3=uom3;
    }

    public String getPsize3() {
        return psize3;
    }

    public void setPsize3(String psize3) {
        this.psize3=psize3;
    }

    public String getVusername() {
        return vusername;
    }

    public void setVusername(String vusername) {
        this.vusername=vusername;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname=displayname;
    }

    public String getInstock() {
        return instock;
    }

    public void setInstock(String instock) {
        this.instock=instock;
    }
}
