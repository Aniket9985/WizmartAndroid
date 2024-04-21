package com.wiz.wizmart.adapters;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.AddToCartResponce;
import com.wiz.wizmart.Utilities.CartModel;
import com.wiz.wizmart.Utilities.DetailsModel;
import com.wiz.wizmart.pages.CartActivity;
import com.wiz.wizmart.retrofit.ProductDetailListener;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.DetailsViewHolder> {
    private Context context;
    private List<DetailsModel> detailsModels;
    private String order_type;
    private SharedPreferences sharedPreferences;
    private String username, uMom, id, vuser, selectedTime, selectedDate;
    private String[] array;
    private String[] dMethod;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private DetailsModel model;
    private String size;
    private String name;
    public  static  String KEYPRODUCT="product";
    public  static  String KEYPRODUCTNAME="pname";
    SharedPreferences preferences;

    public ProductDetailsAdapter(Context context, List<DetailsModel> detailsModels) {
        this.context=context;
        this.detailsModels=detailsModels;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.product_details_single_row, parent, false );
        return new DetailsViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        model=detailsModels.get ( position );
        holder.cart_item.setText ( model.getItem () );
        holder.cart_description.setText ( model.getDescription () );
        holder.cart_price.setText ( "RS." + model.getPrice () );
        holder.cart_category.setText ( model.getCategory () );
        Picasso.get ().load ( model.getImg () ).into ( holder.imageView );
        sharedPreferences=context.getSharedPreferences ( SHARED_NAME, Context.MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME, "" );

        vuser=model.getVusername ();
        name=model.getCategory ();

        if (model.getInstock ().equals ( "out stock" )) {
            holder.btnCart.setVisibility ( View.GONE );
            holder.txtOut.setVisibility ( View.VISIBLE );
            holder.txtOut.setText ( "Out Of Stock" );
        }

        id=model.getId ();
        array=new String[]{model.getPsize () + model.getUom (), model.getPsize2 () + model.getUom2 (), model.getPsize3 () + model.getUom3 ()};
        assert holder.detailsSpinner != null;
        adapter=new ArrayAdapter<String> ( holder.detailsSpinner.getContext (), android.R.layout.simple_spinner_item, array );
        adapter.setDropDownViewResource ( R.layout.spinner_text_bg );
        holder.detailsSpinner.setAdapter ( adapter );
        holder.detailsSpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos=position + 1;
                switch (pos) {
                    case 1:
                        holder.cart_price.setText ( "Rs." + model.getPrice () );
                        uMom=model.getUom ();
                        size=model.getPsize ();
                        break;
                    case 2:
                        holder.cart_price.setText ( "Rs." + model.getPrice2 () );
                        uMom=model.getUom2 ();
                        size=model.getPsize2 ();
                        break;
                    case 3:
                        holder.cart_price.setText ( "Rs." + model.getPrice3 () );
                        uMom=model.getUom3 ();
                        size=model.getPsize3 ();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        holder.btnCart.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                View alertDialog=LayoutInflater.from ( context ).inflate ( R.layout.custom_dialog, null );
                AlertDialog.Builder builder=new AlertDialog.Builder ( context );
                builder.setView ( alertDialog );

                ImageButton cancle=(ImageButton) alertDialog.findViewById ( R.id.img_delete );
                RadioGroup rd1=(RadioGroup) alertDialog.findViewById ( R.id.radioGro1 );
                RadioGroup rd2=(RadioGroup) alertDialog.findViewById ( R.id.radioGro2 );
                RadioButton radioButton=(RadioButton) alertDialog.findViewById ( R.id.rd1 );
                RadioButton radioButton1=(RadioButton) alertDialog.findViewById ( R.id.rd2 );
                EditText qty, date;
                Spinner packSize;
                LinearLayout lytTime, lytDate;
                AppCompatButton cart;
                qty=(EditText) alertDialog.findViewById ( R.id.qty );
                date=alertDialog.findViewById ( R.id.date );
                packSize=alertDialog.findViewById ( R.id.deliveryTime );
                lytDate=alertDialog.findViewById ( R.id.lytDate );
                lytTime=alertDialog.findViewById ( R.id.lytTime );
                setDate ( date );
                cart=alertDialog.findViewById ( R.id.alert_cart );
                rd1.setOnCheckedChangeListener ( new RadioGroup.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId != -1) {
                            rd2.clearCheck ();
                            rd2.setVisibility ( View.GONE );
                            order_type=radioButton.getText ().toString ();
                            lytDate.setVisibility ( View.VISIBLE );
                            lytTime.setVisibility ( View.VISIBLE );
                        }
                    }
                } );
                rd2.setOnCheckedChangeListener ( new RadioGroup.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId != -1) {
                            rd1.clearCheck ();
                            rd1.setVisibility ( View.GONE );
                            order_type=radioButton1.getText ().toString ();
                            lytTime.setVisibility ( View.VISIBLE );
                        }
                    }
                } );
                dMethod=context.getResources ().getStringArray ( R.array.time );
                ArrayAdapter<String> newAdapter=new ArrayAdapter<String> ( context, android.R.layout.simple_spinner_item, dMethod );
                newAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                assert packSize != null;
                packSize.setAdapter ( newAdapter );
                packSize.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedTime=parent.getItemAtPosition ( position ).toString ();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                } );


                final AlertDialog dialog=builder.create ();
                dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );
                dialog.show ();

                cancle.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss ();
                    }
                } );
                cart.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        String qut=qty.getText ().toString ();
                        if (qut.equals ( "" )) {
                            qty.setError ( "Please enter Quantity" );
                            qty.requestFocus ();
                        } else {
                            qty.setError ( null );
                            if (rd1.getCheckedRadioButtonId () != -1) {
                                selectedDate=date.getText ().toString ();

                            } else if (rd2.getCheckedRadioButtonId () != -1) {
                                selectedDate="";

                            }
                            Call<AddToCartResponce> call=RetrofitIntance.getService ().addCartData ( username, order_type, id, size, uMom, qut, selectedDate, vuser, selectedTime );
                            call.enqueue ( new Callback<AddToCartResponce> () {
                                @Override
                                public void onResponse(Call<AddToCartResponce> call, Response<AddToCartResponce> response) {
                                    if (response.isSuccessful ()) {
                                        AddToCartResponce toCartResponce=response.body ();
                                        if (toCartResponce.getMessage ().equals ( "Added to cart" )) {
                                            Intent intent=new Intent ( context, CartActivity.class );
                                            context.startActivity ( intent );
                                        } else if (toCartResponce.getMessage ().equals ( "This product is already added" )) {
                                            Toast.makeText ( context, "This product is already added Please Check Cart Items..", Toast.LENGTH_LONG ).show ();
                                            dialog.dismiss ();
                                        } else if (toCartResponce.getMessage ().equals ( "This product is not deliverable at your pincode" )) {
                                            Toast.makeText ( context, "This product is not deliverable at your pincode...", Toast.LENGTH_LONG ).show ();
                                            dialog.dismiss ();
                                        }
                                    } else {
                                        Toast.makeText ( context, "An error occurred please try again......", Toast.LENGTH_SHORT ).show ();
                                    }
                                }

                                @Override
                                public void onFailure(Call<AddToCartResponce> call, Throwable t) {
                                    Toast.makeText ( context, t.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();

                                }
                            } );
                            dialog.dismiss ();
                        }

                    }
                } );
            }
        } );

    }

    public void setDate(EditText edDate) {
        Calendar calendar=Calendar.getInstance ();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set ( Calendar.YEAR, year );
                calendar.set ( Calendar.MONTH, month );
                calendar.set ( Calendar.DAY_OF_MONTH, dayOfMonth );
                updateCalender ();
            }

            private void updateCalender() {
                String format="dd-MM-yyyy";
                SimpleDateFormat sdf=new SimpleDateFormat ( format, Locale.US );
                edDate.setText ( sdf.format ( calendar.getTime () ) );
            }
        };
        edDate.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                new DatePickerDialog ( context, date, calendar.get ( Calendar.YEAR ), calendar.get ( Calendar.MONTH ), calendar.get ( Calendar.DAY_OF_MONTH ) ).show ();
            }
        } );
    }
    public String  grandTotal() {
        String name="";
        for (DetailsModel model : detailsModels) {
           name=model.getCategory ();
        }
        return name;
    }

    @Override
    public int getItemCount() {
        return detailsModels.size ();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView cart_item, cart_category, cart_price, cart_description, txtOut;
        ImageView imageView;
        AppCompatButton btnCart;
        Spinner detailsSpinner, packSizeSpinner;

        public DetailsViewHolder(@NonNull View itemView) {
            super ( itemView );
            cart_item=itemView.findViewById ( R.id.cart_item );
            cart_category=itemView.findViewById ( R.id.cart_category );
            cart_price=itemView.findViewById ( R.id.cart_price );
            cart_description=itemView.findViewById ( R.id.cart_description );
            imageView=itemView.findViewById ( R.id.cart_image );
            btnCart=itemView.findViewById ( R.id.btnCart );
            detailsSpinner=itemView.findViewById ( R.id.detailsSpinner );
            txtOut=itemView.findViewById ( R.id.txtOut );
        }
    }


}
