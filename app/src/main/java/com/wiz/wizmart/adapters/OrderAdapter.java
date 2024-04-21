package com.wiz.wizmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.ConfirmOrderResponce;
import com.wiz.wizmart.Utilities.MyOrderResponce;
import com.wiz.wizmart.pages.CalenderActivity;
import com.wiz.wizmart.pages.DuesActivity;
import com.wiz.wizmart.pages.InvoiceWEbActivity;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.wiz.wizmart.pages.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<MyOrderResponce> myOrderResponceList;
    SharedPreferences sharedPreferences;
    String username;

    public OrderAdapter(Context context, List<MyOrderResponce> myOrderResponceList) {
        this.context=context;
        this.myOrderResponceList=myOrderResponceList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.order_single_row, parent, false );
        return new OrderViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        MyOrderResponce response=myOrderResponceList.get ( position );
        holder.orderId.setText ( response.getOid () );
        holder.oDate.setText ( response.getOrderDate () );
        holder.venderName.setText ( response.getVendor () );
        holder.txtStatus.setText ( response.getStatus () );
        holder.o_time.setText ( response.getDevtime () );

        sharedPreferences=context.getSharedPreferences ( LoginActivity.SHARED_NAME, Context.MODE_PRIVATE );
        username=sharedPreferences.getString ( LoginActivity.KEY_NAME, "" );

        if (response.getStatus ().equals ( "delivered" )) {
            holder.conBtn.setVisibility ( View.VISIBLE );
            holder.txts.setVisibility ( View.GONE );
            holder.conBtn.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    String string=myOrderResponceList.get ( position ).getOid ();
                    Call<ConfirmOrderResponce> call=RetrofitIntance.getService ().getResponse ( username, string );
                    call.enqueue ( new Callback<ConfirmOrderResponce> () {
                        @Override
                        public void onResponse(Call<ConfirmOrderResponce> call, Response<ConfirmOrderResponce> response) {
                            if (response.isSuccessful ()) {
                                ConfirmOrderResponce orderResponce=response.body ();
                                holder.conBtn.setVisibility ( View.GONE );
                            }
                        }

                        @Override
                        public void onFailure(Call<ConfirmOrderResponce> call, Throwable t) {

                        }
                    } );
                }
            } );

        }
        holder.invoice.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( context, InvoiceWEbActivity.class );
                intent.putExtra ( "oid", response.getOid () );
                context.startActivity ( intent );
            }
        } );
        if (response.getOrderType ().equals ( "monthly" )) {
            holder.calender.setVisibility ( View.VISIBLE );
        } else {
            holder.calender.setVisibility ( View.GONE );
        }
        if (response.getOrderType ().equals ( "oneday" )) {
            holder.dues.setVisibility ( View.GONE );
        } else {
            holder.dues.setVisibility ( View.VISIBLE );
        }
        holder.calender.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( context, CalenderActivity.class );
                intent.putExtra ( "oid", response.getOid () );
                context.startActivity ( intent );
            }
        } );
        holder.dues.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( context, DuesActivity.class );
                intent.putExtra ( "oid", response.getOid () );
                context.startActivity ( intent );

            }
        } );


    }

    @Override
    public int getItemCount() {
        return myOrderResponceList.size ();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, oDate, pName, venderName, txtStatus,txts,o_time;
        AppCompatButton conBtn, invoice, calender, dues;

        public OrderViewHolder(@NonNull View itemView) {
            super ( itemView );
            orderId=itemView.findViewById ( R.id.orderId );
            oDate=itemView.findViewById ( R.id.oDate );
            venderName=itemView.findViewById ( R.id.venderName );
            txtStatus=itemView.findViewById ( R.id.txtStatus );
            conBtn=itemView.findViewById ( R.id.conBtn );
            invoice=itemView.findViewById ( R.id.invoice );
            calender=itemView.findViewById ( R.id.calender );
            dues=itemView.findViewById ( R.id.dues );
            txts=itemView.findViewById ( R.id.txts );
            o_time=itemView.findViewById ( R.id.o_time );
        }
    }
}
