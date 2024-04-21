package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.wiz.wizmart.CustomDialog;

import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.ProductUtilits;
import com.wiz.wizmart.adapters.ProductAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity  {
    private static final int REQUEST_PRODUCT_DETAILS = 1;
    private RecyclerView recyclerView;
    private List<ProductUtilits> productUtilitsList;
    private ProductAdapter adapter;
    private TextView textView;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private String username;
    private String productName;
    private CustomDialog customDialog;
    ImageView imageButton;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_product );

        imageButton=findViewById ( R.id.imgbtn );
        imageButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (ProductActivity.this,CategoryActivity.class);
                startActivity ( intent );
            }
        } );
        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();

        textView=findViewById ( R.id.txtName );
        productName=getIntent ().getStringExtra ( "name" );
        textView.setText ( productName );

        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );



        recyclerView=findViewById ( R.id.product_recycler );
        GridLayoutManager layoutManager=new GridLayoutManager ( this,2 );
        recyclerView.setLayoutManager ( layoutManager );
        recyclerView.hasFixedSize ();

        customDialog=new CustomDialog ( this );
        customDialog.show ();
        getData (productName);
    }

    private  void getData(String pname)
    {
        customDialog.dismiss ();
       Call<List<ProductUtilits>> call=RetrofitIntance.getService ().getProduct ( username,pname);
        call.enqueue ( new Callback<List<ProductUtilits>> () {
            @Override
            public void onResponse(Call<List<ProductUtilits>> call, Response<List<ProductUtilits>> response) {
               if (response.isSuccessful ())
               {
                   productUtilitsList=response.body ();
                   adapter=new ProductAdapter ( ProductActivity.this,productUtilitsList );
                   recyclerView.setAdapter ( adapter );
                   adapter.notifyDataSetChanged ();
                   dialog.dismiss ();
               }else {
                   Toast.makeText ( ProductActivity.this, "No Product Available In Your Location Currently...", Toast.LENGTH_LONG ).show ();
                   Intent intent=new Intent (ProductActivity.this,CategoryActivity.class);
                   startActivity ( intent );
               }
            }

            @Override
            public void onFailure(Call<List<ProductUtilits>> call, Throwable t) {
                Toast.makeText ( ProductActivity.this, "An Error Occupied Please Try After Some Time", Toast.LENGTH_LONG ).show ();
                dialog.dismiss ();
                Intent intent=new Intent (ProductActivity.this,CategoryActivity.class);
                startActivity ( intent );

            }
        } );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PRODUCT_DETAILS && resultCode == RESULT_OK) {
            String name = data.getStringExtra("cat_name");
            Toast.makeText ( this, name, Toast.LENGTH_SHORT ).show ();
            getData (name);

            textView.setText(name);
        }
    }

}