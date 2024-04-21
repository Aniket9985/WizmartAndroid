package com.wiz.wizmart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.DuesResponce;

import java.util.List;

public class DuesAdapter extends RecyclerView.Adapter<DuesAdapter.DuesViewHolder> {
    private Context context;
    private List<DuesResponce> duesResponceList;

    public DuesAdapter(Context context, List<DuesResponce> duesResponceList) {
        this.context=context;
        this.duesResponceList=duesResponceList;
    }

    @NonNull
    @Override
    public DuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.dues_single_row, parent, false );
        return new DuesViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull DuesViewHolder holder, int position) {
        DuesResponce responce=duesResponceList.get ( position );
        holder.due_Order_id.setText ( String.valueOf ( responce.getOrderId () ) );
        holder.amount_pay.setText ( String.valueOf ( responce.getTotal () ) );
        holder.amount_paid.setText ( String.valueOf ( responce.getPaidAmount () ) );
        holder.payment_mode.setText ( responce.getPaymentMode () );
        holder.on_date.setText ( String.valueOf ( responce.getOnDate () ) );
        int d=responce.getTotal () - responce.getPaidAmount ();
        holder.dues.setText ( String.valueOf ( d ) );


    }

    @Override
    public int getItemCount() {
        return duesResponceList.size ();
    }

    class DuesViewHolder extends RecyclerView.ViewHolder {
        TextView due_Order_id, amount_pay, amount_paid, dues, payment_mode, on_date;

        public DuesViewHolder(@NonNull View itemView) {
            super ( itemView );
            due_Order_id=itemView.findViewById ( R.id.due_Order_id );
            amount_pay=itemView.findViewById ( R.id.amount_pay );
            amount_paid=itemView.findViewById ( R.id.amount_paid );
            dues=itemView.findViewById ( R.id.dues );
            payment_mode=itemView.findViewById ( R.id.payment_mode );
            on_date=itemView.findViewById ( R.id.on_date );
        }
    }
}
