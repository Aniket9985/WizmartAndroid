package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CartModel;
import com.wiz.wizmart.adapters.CartProductSowAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductShowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CartModel> cartModelList;
    private CartProductSowAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String username;
    private int Total=0;
    private int count=0;
    private ImageView imgcartS;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_cart_product_show );

        cartModelList=new ArrayList<> ();

        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        if (sharedPreferences != null) {
            username=sharedPreferences.getString ( KEY_NAME,"" );
        }

        recyclerView=findViewById ( R.id.recyclerView );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );
        recyclerView.setHasFixedSize ( true );

        imgcartS=findViewById ( R.id.imgcartS );
        imgcartS.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (CartProductShowActivity.this,CartActivity.class);
                startActivity ( intent );
            }
        } );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();

        getData ();


    }
    private void getData()
    {
        Call<List<CartModel>> call=RetrofitIntance.getService ().getCartData ( username );
        call.enqueue ( new Callback<List<CartModel>> () {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                if (response.isSuccessful ())
                {
                    cartModelList=response.body ();
                    adapter=new CartProductSowAdapter ( CartProductShowActivity.this,cartModelList );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {
                Toast.makeText ( CartProductShowActivity.this, t.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
                dialog.dismiss ();

            }
        } );
    }
    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged ();
        Intent intent=new Intent (CartProductShowActivity.this,CartActivity.class);
        startActivity ( intent );
        super.onBackPressed ();
    }
}