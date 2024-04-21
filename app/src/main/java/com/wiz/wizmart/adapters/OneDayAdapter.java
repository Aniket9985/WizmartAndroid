package com.wiz.wizmart.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.ChangeStatusResponse;
import com.wiz.wizmart.Utilities.OneDayResponse;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneDayAdapter extends RecyclerView.Adapter<OneDayAdapter.OneDayViewHolder> {
    private Context context;
    private List<OneDayResponse> oneDayResponses;
    private ArrayAdapter<String> adapterStatus;
    private ArrayAdapter<String> adapterDboy;
    private String[] status;
    private String[] dMethod;
    List<String> spinnerValues = new ArrayList<>();
    private String deliveryBoy, deliveryStatus;
    List<String> list=new ArrayList<> ();
    private ProgressDialog progressDialog;

    public OneDayAdapter(Context context, List<OneDayResponse> oneDayResponses) {
        this.context=context;
        this.oneDayResponses=oneDayResponses;
    }

    @NonNull
    @Override
    public OneDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.one_day_single_row, parent, false );
        return new OneDayViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull OneDayViewHolder holder, int position) {
        OneDayResponse response=oneDayResponses.get ( position );
        holder.oid.setText ( response.getOrderId () );
        holder.orderdate.setText ( response.getOrderDate () );
        holder.name.setText ( response.getName () );
        holder.dAddress.setText ( response.getDeliveryAdress () );
        holder.dtime.setText ( response.getOrderTime () );
        holder.dstate.setText ( response.getState () );
        holder.dphn.setText ( response.getPhone () );

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);


        status=context.getResources ().getStringArray ( R.array.onestatus );
        adapterStatus=new ArrayAdapter<String> ( context, android.R.layout.simple_spinner_item, status );
        adapterStatus.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        assert holder.spinner != null;
        holder.spinner.setAdapter ( adapterStatus );
        holder.spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deliveryStatus=parent.getItemAtPosition ( position ).toString ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        String value=response.getDboy ();
        if (value == null || value.equals("null")) {
            dMethod=new String[]{};
            deliveryBoy="";
        } else {
            dMethod=new String[]{response.getDboy ()};
        }


        adapterDboy=new ArrayAdapter<String> ( holder.dboyspinner.getContext (), android.R.layout.simple_spinner_item, dMethod );
        adapterDboy.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        assert holder.dboyspinner != null;
        holder.dboyspinner.setAdapter ( adapterDboy );
        holder.dboyspinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deliveryBoy=parent.getItemAtPosition ( position ).toString ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        holder.btnChange.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                progressDialog.show ();
                String oid=oneDayResponses.get ( position ).getOrderId ();
                String username=response.getVusername ();
                Call<ChangeStatusResponse> call=RetrofitIntance.getService ().statusChange ( username, deliveryStatus, oid, deliveryBoy );
                call.enqueue ( new Callback<ChangeStatusResponse> () {
                    @Override
                    public void onResponse(Call<ChangeStatusResponse> call, Response<ChangeStatusResponse> response) {
                        if (response.isSuccessful ()) {
                            progressDialog.dismiss ();
                            ChangeStatusResponse change=response.body ();
                            holder.btnChange.setVisibility ( View.GONE );
                            Toast.makeText ( context, "Status Change Successfully..", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangeStatusResponse> call, Throwable t) {

                    }
                } );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return oneDayResponses.size ();
    }

    class OneDayViewHolder extends RecyclerView.ViewHolder {
        TextView oid, orderdate, name, dtime, dAddress, dstate, dphn;
        Spinner spinner, dboyspinner;
        AppCompatButton btnChange;

        public OneDayViewHolder(@NonNull View itemView) {
            super ( itemView );
            oid=itemView.findViewById ( R.id.oid );
            orderdate=itemView.findViewById ( R.id.orderdate );
            name=itemView.findViewById ( R.id.name );
            dtime=itemView.findViewById ( R.id.dtime );
            dAddress=itemView.findViewById ( R.id.dAddress );
            dstate=itemView.findViewById ( R.id.dstate );
            dphn=itemView.findViewById ( R.id.dphn );
            spinner=itemView.findViewById ( R.id.spinner );
            btnChange=itemView.findViewById ( R.id.btnChange );
            dboyspinner=itemView.findViewById ( R.id.dboyspinner );
        }
    }
}
