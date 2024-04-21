package com.wiz.wizmart.pages.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.MonthelyResponce;
import com.wiz.wizmart.adapters.MonthAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.wiz.wizmart.pages.VendorLoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthalyFragment extends Fragment {
    private View monthView;
    private List<MonthelyResponce> monthelyResponces;
    private RecyclerView recyclerView;
    private MonthAdapter adapter;
    Dialog dialog;
    private SharedPreferences sharedPreferences;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        monthView= inflater.inflate ( R.layout.fragment_monthaly, container, false );

        sharedPreferences=getContext ().getSharedPreferences ( VendorLoginActivity.KEY_SHARED_NAME, Context.MODE_PRIVATE);
        username=sharedPreferences.getString ( VendorLoginActivity.KEY_USERNAME,"" );

        recyclerView=monthView.findViewById ( R.id.recyclerViewMonth );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        recyclerView.setHasFixedSize ( true );
        monthelyResponces=new ArrayList<> ();
        dialog=new Dialog ( getContext ());
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();
        mothelyOrder ();

        return monthView;
    }
    private void mothelyOrder()
    {
        Call<List<MonthelyResponce>> call=RetrofitIntance.getService ().monthelyOrder ( username );
        call.enqueue ( new Callback<List<MonthelyResponce>> () {
            @Override
            public void onResponse(Call<List<MonthelyResponce>> call, Response<List<MonthelyResponce>> response) {
                if (response.isSuccessful ())
                {
                    dialog.dismiss ();
                    monthelyResponces=response.body ();
                    adapter=new MonthAdapter ( getContext (),monthelyResponces );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                }
            }

            @Override
            public void onFailure(Call<List<MonthelyResponce>> call, Throwable t) {

            }
        } );
    }
}