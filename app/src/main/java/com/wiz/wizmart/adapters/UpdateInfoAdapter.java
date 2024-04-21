package com.wiz.wizmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.DataUpdate;
import com.wiz.wizmart.Utilities.UpdateModel;
import com.wiz.wizmart.pages.CategoryActivity;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInfoAdapter extends RecyclerView.Adapter<UpdateInfoAdapter.MyViewHolder> {
    private Context context;
    private List<UpdateModel> updateModelList;

    public UpdateInfoAdapter(Context context, List<UpdateModel> updateModelList) {
        this.context=context;
        this.updateModelList=updateModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.update_single_row, parent, false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UpdateModel updateModel=updateModelList.get ( position );
        holder.uName.setText ( updateModel.getName () );
        holder.uCity.setText ( updateModel.getCity () );
        holder.uEmail.setText ( updateModel.getEmail () );
        holder.uPhone.setText ( updateModel.getPhone () );
        holder.uPin.setText ( updateModel.getPin () );
        holder.uSector.setText ( updateModel.getSector () );
        holder.uState.setText ( updateModel.getState () );
        holder.uApt.setText ( updateModel.getAdress () );
        holder.uArea.setText ( updateModel.getArea () );


        holder.btnUpdate.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String username=updateModelList.get ( position ).getUsername ();
                String area=holder.uArea.getText ().toString ();
                String sector=holder.uSector.getText ().toString ();
                String pin=holder.uPin.getText ().toString ();
                String address=holder.uApt.getText ().toString ();
                String state=holder.uState.getText ().toString ();
                String city=holder.uCity.getText ().toString ();
                String name=holder.uName.getText ().toString ();
                String email=holder.uEmail.getText ().toString ();
                String phone=holder.uPhone.getText ().toString ();
                String adress=holder.uApt.getText ().toString ();

                updateData ( username, area, sector, pin, address, state, city, name, email, phone, adress );

            }
        } );

    }

    private void updateData(String username, String area, String sector, String pin, String address, String state, String city, String name, String email, String phone, String adress) {
        Call<DataUpdate> call=RetrofitIntance.getService ().updateData ( username, area, sector, pin, address, state, city, name, email, phone, adress );
        call.enqueue ( new Callback<DataUpdate> () {
            @Override
            public void onResponse(Call<DataUpdate> call, Response<DataUpdate> response) {
                if (response.isSuccessful ()) {
                    DataUpdate update=response.body ();
                    Toast.makeText ( context, "Profile update Successfully", Toast.LENGTH_SHORT ).show ();
                    Intent intent=new Intent ( context, CategoryActivity.class );
                    context.startActivity ( intent );
                }
            }

            @Override
            public void onFailure(Call<DataUpdate> call, Throwable t) {

            }
        } );
    }

    @Override
    public int getItemCount() {
        return updateModelList.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        EditText uName, uEmail, uPhone, uApt, uSector, uCity, uPin, uState, uArea;
        AppCompatButton btnUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            uName=itemView.findViewById ( R.id.uName );
            uEmail=itemView.findViewById ( R.id.uEmail );
            uPhone=itemView.findViewById ( R.id.uPhone );
            uApt=itemView.findViewById ( R.id.uApt );
            uSector=itemView.findViewById ( R.id.uSector );
            uCity=itemView.findViewById ( R.id.uCity );
            uPin=itemView.findViewById ( R.id.uPin );
            uState=itemView.findViewById ( R.id.uState );
            uArea=itemView.findViewById ( R.id.uArea );
            btnUpdate=itemView.findViewById ( R.id.btnUpdate );
        }
    }

}
