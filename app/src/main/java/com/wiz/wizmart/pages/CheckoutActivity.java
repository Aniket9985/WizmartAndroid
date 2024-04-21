package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.KEY_VUserName;
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
import com.wiz.wizmart.Utilities.CheckOutResponce;
import com.wiz.wizmart.adapters.CheckOutAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private String[] dMethod;
    private RecyclerView recyclerView;
    private CheckOutAdapter adapter;
    private String username,vUsername;
    private SharedPreferences sharedPreferences;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_checkout );

       recyclerView =findViewById ( R.id.checoutRv );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );
        vUsername=sharedPreferences.getString ( KEY_VUserName,"" );
        ImageView imageView=findViewById ( R.id.imgbtnC );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (CheckoutActivity.this,CartActivity.class) );
            }
        } );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();

        getCheckOut ( username,vUsername );

    }

    private void getCheckOut(String username,String vusername)
    {
        Call<List<CheckOutResponce>> call=RetrofitIntance.getService ().chechoutData ( username,vusername );
        call.enqueue ( new Callback<List<CheckOutResponce>> () {
            @Override
            public void onResponse(Call<List<CheckOutResponce>> call, Response<List<CheckOutResponce>> response) {
                if (response.isSuccessful ())
                {
                    List<CheckOutResponce> check=response.body ();
                    recyclerView.setLayoutManager ( new LinearLayoutManager ( CheckoutActivity.this ) );
                    recyclerView.setHasFixedSize (true);
                    adapter=new CheckOutAdapter ( CheckoutActivity.this,check );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }
            }

            @Override
            public void onFailure(Call<List<CheckOutResponce>> call, Throwable t) {
                dialog.dismiss ();
                Toast.makeText ( CheckoutActivity.this, "An Error Occupied Please Try After Some Time", Toast.LENGTH_LONG ).show ();
                startActivity ( new Intent (CheckoutActivity.this,CartActivity.class) );

            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (CheckoutActivity.this,CartActivity.class) );
        super.onBackPressed ();
    }
}