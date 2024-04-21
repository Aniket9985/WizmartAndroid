
package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CartModel;
import com.wiz.wizmart.adapters.CartAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private String username;
    private List<CartModel> cartModelList;
    private CartAdapter adapter;
    private ImageView imgNew,imgcart;
    private int mainTotal=0,subTotal=0,grandtotal=0;
    private AppCompatButton btnCheckOut;
    private CartModel catModel;
    private TextView viewAll,itemCount,textView,total;
    private String vUsername;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_cart );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        if (sharedPreferences != null) {
            username=sharedPreferences.getString ( KEY_NAME,"" );
        }

        recyclerView=findViewById ( R.id.recyclerPic );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager ( this,RecyclerView.HORIZONTAL,false );
        recyclerView.setLayoutManager ( linearLayoutManager );
        recyclerView.hasFixedSize ();
        cartModelList=new ArrayList<> ();

        imgcart=findViewById ( R.id.imgcart );
        imgcart.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (CartActivity.this,CategoryActivity.class) );
            }
        } );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();
        getData ();
        init ();
    }
    private void init()
    {
       textView=findViewById ( R.id.subTotal );
        total=findViewById ( R.id.total );
        itemCount=findViewById ( R.id.itemCount );
        viewAll=findViewById ( R.id.viewAll );
        viewAll.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (CartActivity.this,CartProductShowActivity.class);
                startActivity ( intent );
            }
        } );
        btnCheckOut=findViewById ( R.id.btnCheckOut );
        btnCheckOut.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent (CartActivity.this,CheckoutActivity.class);
               startActivity ( intent );
            }
        } );

    }
    private void getData()
    {
        Call<List<CartModel>> call=RetrofitIntance.getService ().getCartData ( username );
        call.enqueue ( new Callback<List<CartModel>> () {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                if (response.isSuccessful ())
                {
                  List<CartModel> cartModels=response.body ();
                    adapter=new CartAdapter ( CartActivity.this,cartModels);
                    recyclerView.setAdapter ( adapter );
                    subTotal=adapter.subTotal ();
                    mainTotal=adapter.grandTotal ();
                    total.setText ("Rs."+ String.valueOf ( mainTotal ) );
                    textView.setText ( "Rs."+ String.valueOf ( subTotal ) );
                    grandtotal=adapter.getItemCount ();
                    itemCount.setText ( String.valueOf ( grandtotal ) +"  items in cart" );

                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();

                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {
                Toast.makeText ( CartActivity.this, t.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
                dialog.dismiss ();

            }
        } );
    }



    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged ();
        Intent intent=new Intent (CartActivity.this,CategoryActivity.class);
        startActivity ( intent );
        super.onBackPressed ();
    }
}