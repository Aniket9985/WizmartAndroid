package com.wiz.wizmart.adapters;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CalenderResponce;
import com.wiz.wizmart.Utilities.SetStatusResponce;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder> {
    private Context context;
    private List<CalenderResponce> calenderResponceList;
    private ArrayAdapter<String> adapter;
    private String setstatus, username, oid, pid, packsize, uom, date, qty;
    private String[] array;
    private SharedPreferences sharedPreferences;
    Boolean temp=false;
    Boolean check=false;

    public CalenderAdapter(Context context, List<CalenderResponce> calenderResponceList) {
        this.context=context;
        this.calenderResponceList=calenderResponceList;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.calender_single_row, parent, false );
        return new CalenderViewHolder ( view );
    }


    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        CalenderResponce calenderResponce=calenderResponceList.get ( position );
        holder.order_id.setText ( calenderResponce.getOrderId () );
        holder.cQty.setText ( calenderResponce.getQty () );
        holder.cPackSize.setText ( calenderResponce.getPacksize () + calenderResponce.getMyuom () );
        holder.order_date.setText ( calenderResponce.getOrdersDate () );
        holder.delivery_status.setText ( calenderResponce.getStatusis () );

        oid=calenderResponce.getOrderId ();
        pid=calenderResponce.getPid ();
        packsize=calenderResponce.getPacksize ();
        uom=calenderResponce.getMyuom ();
        qty=calenderResponce.getQty ();
        sharedPreferences=context.getSharedPreferences ( SHARED_NAME, Context.MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME, "" );

        if (calenderResponce.getIs_delivered ().equals ( "true" )) {
            holder.set_required.setVisibility ( View.GONE );
            holder.set_status.setVisibility ( View.GONE );
            holder.tp.setVisibility ( View.VISIBLE );
        } else if (calenderResponce.getStatusis ().equals ( "not required" )) {
            holder.set_required.setVisibility ( View.VISIBLE );
        } else if (calenderResponce.getStatusis ().equals ( "required" )) {
            holder.set_status.setVisibility ( View.VISIBLE );
        }

        holder.set_status.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                date=calenderResponceList.get ( position ).getOrdersDate ();
                setstatus=holder.set_status.getText ().toString ();
                getData ( username, oid, pid, packsize, uom, date, qty, setstatus );
                holder.set_status.setBackgroundColor ( ContextCompat.getColor ( context, R.color.navy ) );
            }
        } );
        holder.set_required.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                date=calenderResponceList.get ( position ).getOrdersDate ();
                String status=holder.set_status.getText ().toString ();
                getData ( username, oid, pid, packsize, uom, date, qty, status );
                holder.set_status.setBackgroundColor ( ContextCompat.getColor ( context, R.color.navy ) );
            }
        } );
    }

    private void getData(String username, String oid, String pid, String packsize, String uom, String date, String qty, String setstatus) {
        Call<SetStatusResponce> call=RetrofitIntance.getService ().setStatus ( username, oid, pid, packsize, uom, date, qty, setstatus );
        call.enqueue ( new Callback<SetStatusResponce> () {
            @Override
            public void onResponse(Call<SetStatusResponce> call, Response<SetStatusResponce> response) {
                if (response.isSuccessful ()) {
                    SetStatusResponce setStatusResponce=response.body ();
                    Toast.makeText ( context, "updated SUCCESSFULLY ", Toast.LENGTH_LONG ).show ();
                }
            }

            @Override
            public void onFailure(Call<SetStatusResponce> call, Throwable t) {

            }
        } );
    }

    @Override
    public int getItemCount() {
        return calenderResponceList.size ();
    }

    class CalenderViewHolder extends RecyclerView.ViewHolder {
        TextView order_id, cQty, cPackSize, order_date, delivery_status, set_status, set_required, tp;

        public CalenderViewHolder(@NonNull View itemView) {
            super ( itemView );
            order_id=itemView.findViewById ( R.id.order_id );
            cQty=itemView.findViewById ( R.id.cQty );
            cPackSize=itemView.findViewById ( R.id.cPackSize );
            order_date=itemView.findViewById ( R.id.order_date );
            delivery_status=itemView.findViewById ( R.id.delivery_status );
            set_status=itemView.findViewById ( R.id.set_not );
            set_required=itemView.findViewById ( R.id.set_required );
            tp=itemView.findViewById ( R.id.tp );

        }
    }
}
