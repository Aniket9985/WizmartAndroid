package com.wiz.wizmart.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.MontStatusResponse;
import com.wiz.wizmart.Utilities.MonthelyResponce;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> {
    private Context context;
    private List<MonthelyResponce> monthelyResponces;
    private ProgressDialog progressDialog;

    public MonthAdapter(Context context, List<MonthelyResponce> monthelyResponces) {
        this.context=context;
        this.monthelyResponces=monthelyResponces;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.month_single_row,parent,false );
        return new MonthViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {
        MonthelyResponce response=monthelyResponces.get ( position );
        holder.moid.setText ( response.getOrderId () );
        holder.mname.setText ( response.getName () );
        holder.mdtime.setText ( response.getOrderTime () );
        holder.mdAddress.setText ( response.getDeliveryAdress () );
        holder.mdstate.setText ( response.getState () );
        holder.mdphn.setText ( response.getPhone () );
        holder.morderdate.setText ( response.getOrderDate () );

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        holder.btnMChange.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                progressDialog.show ();
                String username=response.getVusername ();
                String oid=monthelyResponces.get ( position ).getOrderId ();
                String date=monthelyResponces.get ( position ).getOrderDate ();
                Call<MontStatusResponse> call=RetrofitIntance.getService ().statusMonth ( username,date,oid );
                call.enqueue ( new Callback<MontStatusResponse> () {
                    @Override
                    public void onResponse(Call<MontStatusResponse> call, Response<MontStatusResponse> response) {
                        if (response.isSuccessful ())
                        {
                            progressDialog.dismiss ();
                            MontStatusResponse r=response.body ();
                            holder.btnMChange.setVisibility ( View.GONE );
                            Toast.makeText ( context, "Delivered..", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<MontStatusResponse> call, Throwable t) {

                    }
                } );


            }
        } );

    }

    @Override
    public int getItemCount() {
        return monthelyResponces.size ();
    }

    class MonthViewHolder extends RecyclerView.ViewHolder
    {
        TextView moid,mname,mdtime,mdAddress,mdstate,mdphn,morderdate;
        AppCompatButton btnMChange;


        public MonthViewHolder(@NonNull View itemView) {
            super ( itemView );
            moid=itemView.findViewById ( R.id.moid );
            mname=itemView.findViewById ( R.id.mname );
            mdtime=itemView.findViewById ( R.id.mdtime );
            mdAddress=itemView.findViewById ( R.id.mdAddress );
            mdstate=itemView.findViewById ( R.id.mdstate );
            mdphn=itemView.findViewById ( R.id.mdphn );
            btnMChange=itemView.findViewById ( R.id.btnMChange );
            morderdate=itemView.findViewById ( R.id.morderdate );
        }
    }
}
