package com.wiz.wizmart.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatModel {
   @SerializedName("id")
   @Expose
   private String id;
   @SerializedName("service_name")
   @Expose
   private String serviceName;
   @SerializedName("img")
   @Expose
   private String img;
   public CatModel(String id, String serviceName, String  img) {
      this.id=id;
      this.serviceName=serviceName;
      this.img=img;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getServiceName() {
      return serviceName;
   }

   public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
   }

   public String getImg() {
      return img;
   }

   public void setImg(String img) {
      this.img = img;
   }

}
