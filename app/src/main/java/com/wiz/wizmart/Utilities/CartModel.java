package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartModel{
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
    @SerializedName("cartpsize")
    @Expose
    private String cartpsize;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("cartpid")
    @Expose
    private String cartpid;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("maintotal")
    @Expose
    private Integer maintotal;
    @SerializedName("delcharge")
    @Expose
    private String delcharge;
    @SerializedName("pack_price")
    @Expose
    private Integer packPrice;
    @SerializedName("vusername")
    @Expose
    private String vusername;
    @SerializedName("displayname")
    @Expose
    private String displayname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCartpsize() {
        return cartpsize;
    }

    public void setCartpsize(String cartpsize) {
        this.cartpsize = cartpsize;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getCartpid() {
        return cartpid;
    }

    public void setCartpid(String cartpid) {
        this.cartpid = cartpid;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getMaintotal() {
        return maintotal;
    }

    public void setMaintotal(Integer maintotal) {
        this.maintotal = maintotal;
    }

    public String getDelcharge() {
        return delcharge;
    }

    public void setDelcharge(String delcharge) {
        this.delcharge = delcharge;
    }


    public String getVusername() {
        return vusername;
    }

    public void setVusername(String vusername) {
        this.vusername = vusername;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty=qty;
    }

    public Integer getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(Integer packPrice) {
        this.packPrice=packPrice;
    }
}
