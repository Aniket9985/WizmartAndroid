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


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.MyOrderResponce;
import com.wiz.wizmart.adapters.OrderAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MyOrderResponce> myOrderResponceList;
    private OrderAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String username;
    ImageView imgOrder;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_my_order );

        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );
        imgOrder=findViewById ( R.id.imgbtn );
        imgOrder.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (MyOrderActivity.this,SettingActivity.class);
                startActivity ( intent );
            }
        } );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();

        recyclerView=findViewById ( R.id.recy );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this,RecyclerView.VERTICAL,false ) );
        recyclerView.setHasFixedSize ( true );
        myOrderResponceList=new ArrayList<> ();
        getData ();
    }
    private void getData()
    {
        Call<List<MyOrderResponce>> call=RetrofitIntance.getService ().getMyOrder ( username );
        call.enqueue ( new Callback<List<MyOrderResponce>> () {
            @Override
            public void onResponse(Call<List<MyOrderResponce>> call, Response<List<MyOrderResponce>> response) {
                if (response.isSuccessful ())
                {
                    myOrderResponceList=response.body ();
                    adapter=new OrderAdapter ( MyOrderActivity.this,myOrderResponceList );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }
            }

            @Override
            public void onFailure(Call<List<MyOrderResponce>> call, Throwable t) {
                dialog.dismiss ();

            }
        } );
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (MyOrderActivity.this,CategoryActivity.class);
        startActivity ( intent );
        super.onBackPressed ();
    }
}