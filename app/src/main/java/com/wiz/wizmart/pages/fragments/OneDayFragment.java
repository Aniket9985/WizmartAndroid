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
import com.wiz.wizmart.Utilities.OneDayResponse;
import com.wiz.wizmart.adapters.OneDayAdapter;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.wiz.wizmart.pages.VendorLoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneDayFragment extends Fragment {
    private View oneDayView;
    private List<OneDayResponse> oneDayResponses;
    private OneDayAdapter adapter;
    private RecyclerView recyclerView;
    Dialog dialog;
    private SharedPreferences sharedPreferences;
    private String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        oneDayView= inflater.inflate ( R.layout.fragment_one_day, container, false );

        sharedPreferences=getContext ().getSharedPreferences ( VendorLoginActivity.KEY_SHARED_NAME, Context.MODE_PRIVATE);
        username=sharedPreferences.getString ( VendorLoginActivity.KEY_USERNAME,"" );

        recyclerView=oneDayView.findViewById ( R.id.recyclerViewHome );
        oneDayResponses=new ArrayList<> ();
        recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        recyclerView.setHasFixedSize ( true );

        dialog=new Dialog ( getContext ());
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.progress_bar );
        dialog.setCanceledOnTouchOutside ( false );
        dialog.show ();

        findData ();


        return oneDayView;
    }
    private void findData()
    {
        Call<List<OneDayResponse>> call=RetrofitIntance.getService ().oneday ( username );
        call.enqueue ( new Callback<List<OneDayResponse>> () {
            @Override
            public void onResponse(Call<List<OneDayResponse>> call, Response<List<OneDayResponse>> response) {
                if (response.isSuccessful ())
                {
                    dialog.dismiss ();
                    oneDayResponses=response.body ();
                    adapter=new OneDayAdapter ( getContext (),oneDayResponses );
                    recyclerView.setAdapter ( adapter );
                    adapter.notifyDataSetChanged ();
                }
            }

            @Override
            public void onFailure(Call<List<OneDayResponse>> call, Throwable t) {

            }
        } );
    }
}