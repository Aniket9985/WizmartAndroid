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
import com.wiz.wizmart.Utilities.DuesResponce;
import com.wiz.wizmart.adapters.DuesAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private String username,oid;
    private DuesAdapter adapter;
    private List<DuesResponce> duesResponceList;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_dues );
        recyclerView=findViewById ( R.id.recycleDues );

        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );
        oid=getIntent ().getStringExtra ( "oid" );

        duesResponceList=new ArrayList<> ();
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );
        recyclerView.setHasFixedSize ( true );
        ImageView imageView=findViewById ( R.id.imgbtnD );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (DuesActivity.this,MyOrderActivity.class) );
            }
        } );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();


        getData();

    }

    private void getData() {
        Call<List<DuesResponce>> call=RetrofitIntance.getService ().getDues ( username,oid );
        call.enqueue ( new Callback<List<DuesResponce>> () {
            @Override
            public void onResponse(Call<List<DuesResponce>> call, Response<List<DuesResponce>> response) {
                if (response.isSuccessful ())
                {
                    duesResponceList=response.body ();
                    adapter=new DuesAdapter ( DuesActivity.this,duesResponceList );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }else {
                    Toast.makeText ( DuesActivity.this, "No Dues On Order...", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<List<DuesResponce>> call, Throwable t) {
                dialog.dismiss ();

            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (DuesActivity.this,MyOrderActivity.class) );
        super.onBackPressed ();
    }
}