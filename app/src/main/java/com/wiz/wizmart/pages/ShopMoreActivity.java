package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CatModel;
import com.wiz.wizmart.Utilities.ProductUtilits;
import com.wiz.wizmart.adapters.ShopMoreAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopMoreActivity extends AppCompatActivity {
    Spinner spinnerShop;
    RecyclerView recyclerShop;
    private String username;
    private SharedPreferences sharedPreferences;
    private List<CatModel> catModelList;
    private ArrayAdapter<String> arrayAdapter;
    List<String> list=new ArrayList<> ();
    private String string;
    private ShopMoreAdapter adapter;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_shop_more );

        spinnerShop=findViewById ( R.id.spinnerShop );
        recyclerShop=findViewById ( R.id.recyclerShop );
        ImageView imageView=findViewById ( R.id.imgbtn );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (ShopMoreActivity.this,CategoryActivity.class) );
            }
        } );

        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );
        GridLayoutManager layoutManager=new GridLayoutManager ( this,2 );
        recyclerShop.setLayoutManager ( layoutManager );
        recyclerShop.hasFixedSize ();
        getData ();
        init ();
    }
    private  void  getData()
    {

        Call<List<CatModel>> call=RetrofitIntance.getService ().getCat ();
        call.enqueue ( new Callback<List<CatModel>> () {
            @Override
            public void onResponse(Call<List<CatModel>> call, Response<List<CatModel>> response) {
                if (response.isSuccessful ())
                {

                   List<CatModel> catModels=response.body ();
                    for (int i=0;i<catModels.size ();i++)
                    {

                        list.add (catModels.get ( i ).getServiceName ());
                    }
                    arrayAdapter=new ArrayAdapter<> ( ShopMoreActivity.this,android.R.layout.simple_spinner_item,list  );
                    arrayAdapter.setDropDownViewResource (android.R.layout.simple_spinner_item );
                    spinnerShop.setAdapter ( arrayAdapter );


                }

            }
            @Override
            public void onFailure(Call<List<CatModel>> call, Throwable t) {
                dialog.dismiss ();

            }
        } );
    }
    private void init()
    {
        spinnerShop.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string=parent.getItemAtPosition ( position ).toString ();
                getProduct ();
                dialog=new Dialog ( ShopMoreActivity.this);
                dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
                dialog.setContentView ( R.layout.progress_bar );
                dialog.setCanceledOnTouchOutside ( false );
                dialog.show ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }
    private  void getProduct()
    {
        Call<List<ProductUtilits>> call=RetrofitIntance.getService ().getProduct ( username,string);
        call.enqueue ( new Callback<List<ProductUtilits>> () {
            @Override
            public void onResponse(Call<List<ProductUtilits>> call, Response<List<ProductUtilits>> response) {
                if (response.isSuccessful ())
                {
                    List<ProductUtilits> productUtilitsList=response.body ();
                    adapter=new ShopMoreAdapter ( ShopMoreActivity.this,productUtilitsList );
                    recyclerShop.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }else {
                    Toast.makeText ( ShopMoreActivity.this, "No Product Available In Your Location...", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent (ShopMoreActivity.this,CategoryActivity.class);
                    startActivity ( intent );
                }
            }

            @Override
            public void onFailure(Call<List<ProductUtilits>> call, Throwable t) {
                dialog.dismiss ();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (ShopMoreActivity.this,CategoryActivity.class) );
        super.onBackPressed ();
    }
}