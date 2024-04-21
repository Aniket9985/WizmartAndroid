package com.wiz.wizmart.adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CheckOutResponce;
import com.wiz.wizmart.Utilities.PlaceOederResponce;
import com.wiz.wizmart.pages.PlaceOrderActivity;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckViewHolder> {
    private Context context;
    private List<CheckOutResponce> checkOutResponceList;
    private String[] dMethod;
    String s, username, address, zip_code, state, city;
    private SharedPreferences sharedPreferences;

    public CheckOutAdapter(Context context, List<CheckOutResponce> checkOutResponceList) {
        this.context=context;
        this.checkOutResponceList=checkOutResponceList;
    }

    @NonNull
    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.checkout_single_row, parent, false );
        return new CheckViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CheckViewHolder holder, int position) {
        CheckOutResponce checkOutResponce=checkOutResponceList.get ( position );
        holder.check_address.setText ( checkOutResponce.getAdress () );
        holder.check_city.setText ( checkOutResponce.getCity () );
        holder.check_email.setText ( checkOutResponce.getEmail () );
        holder.check_phone.setText ( checkOutResponce.getPhone () );
        holder.check_state.setText ( checkOutResponce.getState () );
        holder.check_zip.setText ( checkOutResponce.getPin () );

        sharedPreferences=context.getSharedPreferences ( SHARED_NAME, MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME, "" );

        address=holder.check_address.getText ().toString ();
        state=holder.check_state.getText ().toString ();
        city=holder.check_city.getText ().toString ();
        zip_code=holder.check_zip.getText ().toString ();


        dMethod=context.getResources ().getStringArray ( R.array.checkout );
        ArrayAdapter<String> adapter=new ArrayAdapter<String> ( context, android.R.layout.simple_spinner_item, dMethod );
        adapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        assert holder.checkout != null;
        holder.checkout.setAdapter ( adapter );
        holder.checkout.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s=parent.getItemAtPosition ( position ).toString ();
                int pos=position + 1;
                switch (pos) {
                    case 1:
                        break;
                    case 2:
                        holder.scanner.setVisibility ( View.VISIBLE );
                        Picasso.get ().load ( checkOutResponce.getScanner () ).into ( holder.scanner );
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        holder.btnPlaceOrder.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Call<PlaceOederResponce> call=RetrofitIntance.getService ().getPlaceOrder ( username, address, s, zip_code, state, city );
                call.enqueue ( new Callback<PlaceOederResponce> () {
                    @Override
                    public void onResponse(Call<PlaceOederResponce> call, Response<PlaceOederResponce> response) {
                        if (response.isSuccessful ()) {
                            PlaceOederResponce placeOederResponce=response.body ();
                            Intent intent=new Intent ( context, PlaceOrderActivity.class );
                            context.startActivity ( intent );

                        }
                    }

                    @Override
                    public void onFailure(Call<PlaceOederResponce> call, Throwable t) {

                    }
                } );

            }
        } );
        limitSpinnerHeight ( holder.checkout );

    }

    private void limitSpinnerHeight(Spinner spinner) {
        Field popup=null;
        try {
            popup=Spinner.class.getDeclaredField ( "mPopup" );
            popup.setAccessible ( true );

            android.widget.ListPopupWindow popupWindow=(android.widget.ListPopupWindow) popup.get ( spinner );

            popupWindow.setHeight ( 400 );
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    @Override
    public int getItemCount() {
        return checkOutResponceList.size ();
    }

    class CheckViewHolder extends RecyclerView.ViewHolder {
        TextView check_address, check_city, check_state, check_zip, check_phone, check_email;
        Spinner checkout;
        ImageView scanner;
        AppCompatButton btnPlaceOrder;

        public CheckViewHolder(@NonNull View itemView) {
            super ( itemView );
            check_address=itemView.findViewById ( R.id.check_address );
            check_city=itemView.findViewById ( R.id.check_city );
            check_state=itemView.findViewById ( R.id.check_state );
            check_zip=itemView.findViewById ( R.id.check_zip );
            check_phone=itemView.findViewById ( R.id.check_phone );
            check_email=itemView.findViewById ( R.id.check_email );
            checkout=itemView.findViewById ( R.id.checkout );
            scanner=itemView.findViewById ( R.id.scanner );
            btnPlaceOrder=itemView.findViewById ( R.id.btnPlaceOrder );
        }
    }

}
