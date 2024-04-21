package com.wiz.wizmart.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.VendorLoginResponse;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorLoginActivity extends AppCompatActivity {
    EditText edt_Vpassword,edt_Vusername;
    Button btn_Vlogin;
    public static final String KEY_SHARED_NAME="vendorPref";
    public static final String KEY_USERNAME="username";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_vendor_login );

        sharedPreferences=getSharedPreferences ( KEY_SHARED_NAME,MODE_PRIVATE );

        edt_Vpassword=findViewById ( R.id.edt_Vpassword );
        edt_Vusername=findViewById ( R.id.edt_Vusername );
        btn_Vlogin=findViewById ( R.id.btn_Vlogin );
        btn_Vlogin.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (validation ())
                {
                    login ();
                }
            }
        } );


    }
    private Boolean validation()
    {
        String user=edt_Vusername.getText ().toString ();
        String pass=edt_Vpassword.getText ().toString ();
        if (user.isEmpty ())
        {
            edt_Vusername.setError ( "Please Enter User Name" );
            edt_Vusername.requestFocus ();
            return false;
        }else if (pass.isEmpty ())
        {
            edt_Vpassword.setError ( "Please Enter Password" );
            edt_Vpassword.requestFocus ();
            return false;
        }else {
            edt_Vpassword.setError ( null );
            edt_Vpassword.setError ( null );
            return true;
        }
    }

    private void share(String username)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.putString ( KEY_USERNAME,username );
        editor.commit ();
    }
    private  void  login()
    {
        String username=edt_Vusername.getText ().toString ();
        String password=edt_Vpassword.getText ().toString ();
        Call<VendorLoginResponse> call=RetrofitIntance.getService ().vendorLogin ( username,password );
        call.enqueue ( new Callback<VendorLoginResponse> () {
            @Override
            public void onResponse(Call<VendorLoginResponse> call, Response<VendorLoginResponse> response) {
                if (response.isSuccessful ())
                {
                    VendorLoginResponse loginResponse=response.body ();
                    if (loginResponse.getMessage ().equals ( " loged in " ))
                    {
                        share ( edt_Vusername.getText ().toString () );
                       Intent intent= new Intent (VendorLoginActivity.this,VendorOrderActivity.class);
                       startActivity ( intent );
                    }
                    else if (loginResponse.getMessage ().equals ( " Error: invalid username password" ))
                    {
                        Toast.makeText ( VendorLoginActivity.this, "invalid username password", Toast.LENGTH_SHORT ).show ();
                    }
                }
            }

            @Override
            public void onFailure(Call<VendorLoginResponse> call, Throwable t) {

            }
        } );
    }
}