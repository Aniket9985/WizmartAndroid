package com.wiz.wizmart.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiz.wizmart.NetworkUtil;

import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CatModel;
import com.wiz.wizmart.adapters.CategoryAdapter;
import com.wiz.wizmart.adapters.SliderAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
   private CatModel catModel;
   private List<CatModel> catModelList;
   private CategoryAdapter adapter;
   private BottomNavigationView bottomBar;
   private SliderView sliderView;
   String TAG = this.getClass().getSimpleName();
   private ProgressBar progressBar;
   private SearchView searchView;
   TextView username,net;
   String user;
   SharedPreferences sharedPreferences;
   ImageView imgPerson;
   Dialog dialog;
   RelativeLayout mainLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_category );
        mainLayout=findViewById ( R.id.mainLayout );
        net=findViewById ( R.id.net );
        if (NetworkUtil.isNetworkAvailable(CategoryActivity.this)) {
            // Internet connection is available
            mainLayout.setVisibility ( View.VISIBLE );
        } else {
            // No internet connection
            net.setVisibility ( View.VISIBLE );
        }


        sharedPreferences=getSharedPreferences ( LoginActivity.SHARED_NAME,MODE_PRIVATE );
        user=sharedPreferences.getString ( LoginActivity.KEY_NAME,"" );

        catModelList=new ArrayList<> ();
        searchView=findViewById ( R.id.serchviw );
        searchView.clearFocus ();
        searchView.setOnQueryTextListener ( new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter ( newText );
                return true;
            }
        } );

        dialog=new Dialog ( this);
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();

        init ();
        getData ();
    }

    private void filter(String text)
    {
        List<CatModel> filterList =new ArrayList<> ();
        for (CatModel catModel:catModelList)
        {
            if (catModel.getServiceName ().toLowerCase( ).contains (text.toLowerCase ()))
            {
               filterList.add ( catModel );
               adapter.notifyDataSetChanged ();
            }
        }
        adapter.filterList ( filterList );
    }

    private void init()
    {
        username=findViewById ( R.id.username );
        username.setText ( user );
        imgPerson=findViewById ( R.id.imgPerson );
        imgPerson.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (CategoryActivity.this,SettingActivity.class) );
            }
        } );
        bottomBar=findViewById ( R.id.bottomBar );
        bottomBar.setOnItemSelectedListener ( new NavigationBarView.OnItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId ())
                {
                    case R.id.menu_home:
                        startActivity ( new Intent (CategoryActivity.this,CategoryActivity.class) );
                        overridePendingTransition ( R.anim.slide_in_right,R.anim.slider_out_left );
                        finish ();
                        return true;
                    case R.id.menu_shop:
                        startActivity ( new Intent (CategoryActivity.this,ShopMoreActivity.class) );
                        overridePendingTransition ( R.anim.slide_in_right,R.anim.slider_out_left );
                        finish ();
                        return true;
                    case R.id.menu_cart:
                        startActivity ( new Intent (CategoryActivity.this,CartActivity.class) );
                        overridePendingTransition ( R.anim.slide_in_right,R.anim.slider_out_left );
                        finish ();
                        return true;
                }
                return true;
            }
        } );
        int [] images={
                R.drawable.img1,
                R.drawable.img2
        };
        sliderView=findViewById ( R.id.slider );
        SliderAdapter adapter=new SliderAdapter ( images );
        sliderView.setSliderAdapter ( adapter );
        sliderView.setIndicatorAnimation ( IndicatorAnimationType.WORM );
        sliderView.setSliderTransformAnimation ( SliderAnimations.DEPTHTRANSFORMATION );
        sliderView.startAutoCycle ();

        recyclerView=findViewById ( R.id.recyclerCat );
        recyclerView.hasFixedSize ();

    }
    private  void  getData()
    {

       Call<List<CatModel>> call=RetrofitIntance.getService ().getCat ();
        call.enqueue ( new Callback<List<CatModel>> () {
            @Override
            public void onResponse(Call<List<CatModel>> call, Response<List<CatModel>> response) {
                catModelList=response.body ();
                recyclerView.setLayoutManager ( new GridLayoutManager ( CategoryActivity.this,3 ) );
                adapter=new CategoryAdapter ( getApplicationContext (),catModelList );
                recyclerView.setAdapter ( adapter );
                adapter.notifyDataSetChanged ();
                dialog.dismiss ();

            }
            @Override
            public void onFailure(Call<List<CatModel>> call, Throwable t) {
                dialog.dismiss ();

            }
        } );
    }

    @Override
    protected void onRestart() {
        adapter.notifyDataSetChanged ();
        super.onRestart ();
    }
}