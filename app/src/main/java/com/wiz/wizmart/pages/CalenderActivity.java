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
import com.wiz.wizmart.Utilities.CalenderResponce;
import com.wiz.wizmart.adapters.CalenderAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderActivity extends AppCompatActivity {
    private List<CalenderResponce> calenderResponceList;
    private RecyclerView recyclerView;
    private CalenderAdapter adapter;
    private String oid,pid,username;
    private SharedPreferences sharedPreferences;
    ImageView imageView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_calender );

        oid=getIntent ().getStringExtra ( "oid" );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );

        recyclerView=findViewById ( R.id.recycleCalender );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );
        recyclerView.setHasFixedSize ( true );
        calenderResponceList=new ArrayList<> ();
        imageView=findViewById ( R.id.imgbtn );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (CalenderActivity.this,MyOrderActivity.class) );
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
        Call<List<CalenderResponce>> call=RetrofitIntance.getService ().getCalenderData ( username,oid );
        call.enqueue ( new Callback<List<CalenderResponce>> () {
            @Override
            public void onResponse(Call<List<CalenderResponce>> call, Response<List<CalenderResponce>> response) {
                if (response.isSuccessful ())
                {
                    calenderResponceList=response.body ();
                    adapter=new CalenderAdapter ( CalenderActivity.this,calenderResponceList );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }
            }

            @Override
            public void onFailure(Call<List<CalenderResponce>> call, Throwable t) {
                dialog.dismiss ();

            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (CalenderActivity.this,MyOrderActivity.class) );
        super.onBackPressed ();
    }
}