package com.wiz.wizmart.retrofit;

import com.wiz.wizmart.Utilities.AddToCartResponce;
import com.wiz.wizmart.Utilities.CalenderResponce;
import com.wiz.wizmart.Utilities.CartModel;
import com.wiz.wizmart.Utilities.CatModel;
import com.wiz.wizmart.Utilities.ChangeStatusResponse;
import com.wiz.wizmart.Utilities.CheckOutResponce;
import com.wiz.wizmart.Utilities.ConfirmOrderResponce;
import com.wiz.wizmart.Utilities.DataUpdate;
import com.wiz.wizmart.Utilities.DboyResponse;
import com.wiz.wizmart.Utilities.DeleteCartResponce;
import com.wiz.wizmart.Utilities.DetailsModel;
import com.wiz.wizmart.Utilities.DuesResponce;
import com.wiz.wizmart.Utilities.GetOtpResponce;
import com.wiz.wizmart.Utilities.LoginResponce;
import com.wiz.wizmart.Utilities.MontStatusResponse;
import com.wiz.wizmart.Utilities.MonthelyResponce;
import com.wiz.wizmart.Utilities.MyOrderResponce;
import com.wiz.wizmart.Utilities.OneDayResponse;
import com.wiz.wizmart.Utilities.PlaceOederResponce;
import com.wiz.wizmart.Utilities.ProductUtilits;
import com.wiz.wizmart.Utilities.RegisterResponce;
import com.wiz.wizmart.Utilities.SetPassResponce;
import com.wiz.wizmart.Utilities.SetStatusResponce;
import com.wiz.wizmart.Utilities.UpdateCartModel;
import com.wiz.wizmart.Utilities.UpdateModel;
import com.wiz.wizmart.Utilities.VendorLoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterFace {
    @GET("api/services.php")
    Call<List<CatModel>> getCat();

    @GET("api/catproduct.php")
    Call<List<ProductUtilits>> getProduct(
            @Query ( "username" ) String username,
            @Query ( "cat" ) String cat
     );

    @GET("api/prodetails.php")
    Call<List<DetailsModel>> getCart(
            @Query ( "username" ) String username,
            @Query ( "pid" ) String  pid
    );

    @GET("api/login.php")
    Call<LoginResponce> loginData(
           @Query ( "username" ) String username,
           @Query ( "password" ) String password
    );

    @GET("api/addtocart.php")
    Call<AddToCartResponce> addCartData(
            @Query ( "username" ) String username,
            @Query ( "order_type" ) String order_type,
            @Query ( "pid" ) String pid,
            @Query ( "psize" ) String psize,
            @Query ( "uom" ) String uom,
            @Query ( "qty" ) String qty,
            @Query ( "order_date" ) String order_date,
            @Query("vuser") String vuser,
            @Query ( "order_time" ) String order_time
    );

    @GET("api/displaycart.php")
    Call<List<CartModel>> getCartData(
            @Query ( "username" ) String  username
    );

    @GET("api/updatecart.php")
    Call<UpdateCartModel> cartUpdate(
           @Query ( "username" ) String username,
           @Query ( "qty" ) int qty,
           @Query ( "id" ) String id
    );

    @GET("api/deletecart.php")
    Call<DeleteCartResponce> cartDelete(
            @Query ( "username" ) String username,
            @Query ( "id" ) String id
     );

    @POST("api/cust_register.php")
    Call<RegisterResponce> dataRegister(
            @Query("name") String name,
            @Query("email") String email,
            @Query("username") String username,
            @Query("password") String password,
            @Query("pin") String pin,
            @Query("adress") String adress,
            @Query("phone") String phone,
            @Query("city") String city,
            @Query("area") String area,
            @Query("sector") String sector,
            @Query("date") String date,
            @Query("state") String state,
            @Query ( "cpassword" ) String cpassword
    );

    @GET("api/checkout.php")
    Call<List<CheckOutResponce>> chechoutData(
            @Query ( "username" ) String username,
            @Query ( "vusername" ) String vusername
    );

    @GET("api/placeorder.php")
    Call<PlaceOederResponce> getPlaceOrder(
            @Query ( "username" ) String username,
            @Query ( "address" ) String address,
            @Query ( "CreditCardType" ) String CreditCardType,
            @Query ( "zip_code" ) String zip_code,
            @Query ( "state" ) String state,
            @Query ( "city" ) String city
    );

    @GET("api/myorder.php")
    Call<List<MyOrderResponce>> getMyOrder(
            @Query ( "username" ) String username
    );

    @GET("api/displayprof.php")
    Call<List<UpdateModel>> getUpdateData(
            @Query ( "username" ) String username
    );

    @GET("api/updateprof.php")
    Call<DataUpdate> updateData(
            @Query ( "username" ) String username,
            @Query ( "area" ) String area,
            @Query ( "sector" ) String sector,
            @Query ( "pin" ) String pin,
            @Query ( "address" ) String address,
            @Query ( "state" ) String state,
            @Query ( "city" ) String city,
            @Query ( "name" ) String name,
            @Query ( "email" ) String email,
            @Query ( "phone" ) String phone,
            @Query ( "adress" ) String adress
    );
    @GET("api/getotp.php")
    Call<GetOtpResponce> callWeb(
            @Query ( "email" ) String email
    );
    @GET("api/setpass.php")
    Call<SetPassResponce> setPassword(
            @Query ( "username" ) String username,
            @Query ( "newpass" ) String newpass,
            @Query ( "conpass" ) String conpass,
            @Query ( "otp" ) String otp
    );

    @GET("api/calendar.php")
    Call<List<CalenderResponce>> getCalenderData(
            @Query ( "username" ) String username,
            @Query ( "oid" ) String oid
    );
    @GET("api/statupdate.php")
    Call<SetStatusResponce> setStatus(
            @Query ( "username" ) String username,
            @Query ( "oid" ) String oid,
            @Query ( "pid" ) String pid,
            @Query ( "packsize" ) String packsize,
            @Query ( "uom" ) String uom,
            @Query ( "date" ) String date,
            @Query ( "qty" ) String qty,
            @Query ( "setstatus" ) String setstatus
    );
    @GET("api/viewdue.php")
    Call<List<DuesResponce>> getDues(
            @Query ( "username" ) String username,
            @Query ( "oid" ) String oid
    );

    @GET("api/confirmdel.php")
    Call<ConfirmOrderResponce> getResponse(
            @Query ( "username" ) String username,
            @Query ( "id" ) String id

    );
    @GET("api/vlogin.php")
    Call<VendorLoginResponse> vendorLogin(
            @Query ( "username" ) String username,
            @Query ( "password" ) String password
    );
    @GET("api/vorders.php")
    Call<List<OneDayResponse>> oneday(
            @Query ( "vusername" ) String vusername
    );
    @GET("api/dboy.php")
    Call<List<DboyResponse>> deliveryBoy(
            @Query ( "vusername" ) String username
    );
    @GET("api/onestat.php")
    Call<ChangeStatusResponse> statusChange(
            @Query ( "username" ) String username,
            @Query ( "status" ) String status,
            @Query ( "orderid" ) String orderid,
            @Query ( "dboy" ) String dboy
    );
    @GET("api/vorderm.php")
    Call<List<MonthelyResponce>> monthelyOrder(
            @Query ( "vusername" ) String vusername
    );

    @GET("api/monstat.php")
    Call<MontStatusResponse> statusMonth(
            @Query ( "username" ) String username,
            @Query ( "date" ) String date,
            @Query ( "orderid" ) String orderid
    );


}
