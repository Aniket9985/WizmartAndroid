package com.wiz.wizmart.pages;

import static com.wiz.wizmart.adapters.ProductDetailsAdapter.KEYPRODUCT;
import static com.wiz.wizmart.adapters.ProductDetailsAdapter.KEYPRODUCTNAME;
import static com.wiz.wizmart.pages.LoginActivity.KEY_ID;
import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.wiz.wizmart.Utilities.DetailsModel;
import com.wiz.wizmart.adapters.ProductDetailsAdapter;
import com.wiz.wizmart.retrofit.ProductDetailListener;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity  {
    private static final int REQUEST_PRODUCT_DETAILS = 1;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProductDetailsAdapter adapter;
    private List<DetailsModel> detailsModelList;
    private SharedPreferences sharedPreferences;
    private String username;
    private String pid;
    private ImageView imageButton;
    DetailsModel detailsModel;
    Dialog dialog;
    String name;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_product_details );

        pid=getIntent ().getStringExtra ( "id" );

        imageButton=findViewById ( R.id.imgbtnPD );
        imageButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (ProductDetailsActivity.this,CategoryActivity.class);
                startActivity ( intent );
            }
        } );
        detailsModelList=new ArrayList<> ();
        recyclerView=findViewById ( R.id.details_recycler );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.putString ( KEY_ID,pid );
        editor.commit ();

        username=sharedPreferences.getString ( KEY_NAME,"" );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();
        getData ();
    }
    private void getData()
    {
        Call<List<DetailsModel>> call=RetrofitIntance.getService ().getCart ( username,pid );
        call.enqueue ( new Callback<List<DetailsModel>> () {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                if (response.isSuccessful ()){
                    detailsModelList=response.body ();
                    recyclerView.setLayoutManager ( new LinearLayoutManager ( ProductDetailsActivity.this ) );
                    recyclerView.setHasFixedSize (true);
                    adapter=new ProductDetailsAdapter ( ProductDetailsActivity.this,detailsModelList );
                    recyclerView.setAdapter ( adapter );
                    name=adapter.grandTotal ();
                    Toast.makeText ( ProductDetailsActivity.this, name, Toast.LENGTH_SHORT ).show ();
                    adapter.notifyDataSetChanged ();
                    dialog.dismiss ();
                }


            }

            @Override
            public void onFailure(Call<List<DetailsModel>> call, Throwable t) {
                dialog.dismiss ();

            }
        } );
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent (ProductDetailsActivity.this,ProductActivity.class);
        intent.putExtra ( "cat_name",name );
       setResult ( RESULT_OK,intent );
        super.onBackPressed ();
    }

}