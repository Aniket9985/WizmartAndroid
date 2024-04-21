package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.UpdateModel;
import com.wiz.wizmart.adapters.UpdateInfoAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfoActivity extends AppCompatActivity {
    RecyclerView uRecycler;
    UpdateInfoAdapter adapter;
    SharedPreferences sharedPreferences;
    String username;
    ImageView imginfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_account_info );
        uRecycler=findViewById ( R.id.uRecycler );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );

        uRecycler.setLayoutManager ( new LinearLayoutManager ( this ) );
        uRecycler.setHasFixedSize ( true );

        imginfo=findViewById ( R.id.imgcart );
        imginfo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AccountInfoActivity.this,SettingActivity.class);
                startActivity ( intent );
            }
        } );
        getData ();
    }

    private void getData()
    {
        Call<List<UpdateModel>> call=RetrofitIntance.getService ().getUpdateData ( username );
        call.enqueue ( new Callback<List<UpdateModel>> () {
            @Override
            public void onResponse(Call<List<UpdateModel>> call, Response<List<UpdateModel>> response) {
                if (response.isSuccessful ())
                {
                    List<UpdateModel> updateModelList=response.body ();
                    adapter=new UpdateInfoAdapter ( AccountInfoActivity.this,updateModelList );
                    uRecycler.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                }
            }

            @Override
            public void onFailure(Call<List<UpdateModel>> call, Throwable t) {

            }
        } );
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (AccountInfoActivity.this,SettingActivity.class);
        startActivity ( intent );
        super.onBackPressed ();
    }
}