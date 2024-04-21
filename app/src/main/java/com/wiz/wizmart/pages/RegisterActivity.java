package com.wiz.wizmart.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.RegisterResponce;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppCompatButton register;
    private EditText edName,edEmail,edPhone,edApt,edSector,edCity,edNearBy,edPin,edState,edUserName,edPass,edConPass,edAddress;
    private ProgressBar progressBarReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
        ImageView imageButton=findViewById ( R.id.imgbtn );
        imageButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (RegisterActivity.this,LoginActivity.class) );
            }
        } );
        init ();

    }

    private  void init()
    {
        register=findViewById ( R.id.btnRegister );
        edName=findViewById ( R.id.edName );
        edEmail=findViewById ( R.id.edEmail );
        edPhone=findViewById ( R.id.edPhone );
        edApt=findViewById ( R.id.edApt );
        edSector=findViewById ( R.id.edSector );
        edCity=findViewById ( R.id.edCity );
        edNearBy=findViewById ( R.id.edNearBy );
        edPin=findViewById ( R.id.edPin );
        edState=findViewById ( R.id.edState );
        edUserName=findViewById ( R.id.user );
        edPass=findViewById ( R.id.edPass );
        edConPass=findViewById ( R.id.edConPass );
        edAddress=findViewById ( R.id.edAddress );
        progressBarReg=findViewById ( R.id.progressBarReg );

        register.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (validation ())
                {
                    progressBarReg.setVisibility ( View.VISIBLE );
                    register.setVisibility ( View.GONE );
                    String name=edName.getText ().toString ();
                    String email=edEmail.getText ().toString ().trim ();
                    String phone=edPhone.getText ().toString ();
                    String apt=edApt.getText ().toString ();
                    String sector=edSector.getText ().toString ();
                    String city=edCity.getText ().toString ();
                    String nearBy=edNearBy.getText ().toString ();
                    String pin=edPin.getText ().toString ();
                    String state=edState.getText ().toString ();
                    String userName=edUserName.getText ().toString ();
                    String password=edPass.getText ().toString ();
                    String cpass=edConPass.getText ().toString ();
                    String address=edAddress.getText ().toString ();
                    registerData ( name,email,userName,password,pin,address,phone,city,nearBy,sector,"02-05-2023",state,cpass);

                }




            }
        } );
    }
    private Boolean validation()
    {
        String name=edName.getText ().toString ();
        String email=edEmail.getText ().toString ().trim ();
        String phone=edPhone.getText ().toString ();
        String apt=edApt.getText ().toString ();
        String sector=edSector.getText ().toString ();
        String city=edCity.getText ().toString ();
        String nearBy=edNearBy.getText ().toString ();
        String pin=edPin.getText ().toString ();
        String state=edState.getText ().toString ();
        String userName=edUserName.getText ().toString ();
        String password=edPass.getText ().toString ();
        String Cpassword=edConPass.getText ().toString ();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (name.isEmpty ())
        {
            edName.setError ( "Please Enter A name" );
            edName.requestFocus ();
            return false;
        }else if(!email.matches ( emailPattern ))
        {
            edEmail.setError ( "Please Enter valid Email Address" );
            edEmail.requestFocus ();
            return false;
        }else if (email.isEmpty ())
        {
            edEmail.setError ( "Please Enter Email Address" );
            edEmail.requestFocus ();
            return false;
        }else if (!android.util.Patterns.PHONE.matcher(phone).matches())
        {
            edPhone.setError ( "Please Enter Valid Phone Number" );
            edPhone.requestFocus ();
            return false;
        }else if (phone.length ()<10)
        {
            edPhone.setError ( "Phone Number Should Be 10 Digit" );
            edPhone.requestFocus ();
            return false;
        }else if (phone.isEmpty ())
        {
            edPhone.setError ( "Please Enter Phone Number" );
            edPhone.requestFocus ();
            return false;
        }else if (apt.isEmpty ())
        {
            edApt.setError ( "Please Enter Apartment or Flat No or Plot" );
            edApt.requestFocus ();
            return false;
        }else if (sector.isEmpty ())
        {
            edSector.setError ( "Please Enter Sector Name" );
            edSector.requestFocus ();
            return false;
        }else if (city.isEmpty ())
        {
            edCity.setError ( "Please Enter City Name" );
            edCity.requestFocus ();
            return false;
        }
        else if (pin.isEmpty ())
        {
            edPin.setError ( "Please Enter Pin Code" );
            edPin.requestFocus ();
            return false;
        }else if (state.isEmpty ())
        {
            edState.setError ( "Please Enter State" );
            edState.requestFocus ();
            return false;
        }else if (userName.isEmpty ())
        {
            edUserName.setError ( "Please Enter User Name" );
            edUserName.requestFocus ();
            return false;
        }else if (password.isEmpty () || password.length()<6 || password.length()>15)
        {
            edPass.setError ( "Please enter  Password" );
            edPass.requestFocus ();
            return  false;
        }else if (!Cpassword.matches ( password )){
            edConPass.setError ( "Please Enter Correct Password" );
            edConPass.requestFocus ();
            return false;
        }else if (Cpassword.isEmpty ())
        {
            edConPass.setError ( "Please Enter Password" );
            edConPass.requestFocus ();
            return false;
        }
        else {
            edName.setError ( null );
            edPhone.setError ( null );
            edEmail.setError ( null );
            edApt.setError ( null );
            edSector.setError ( null );
            edCity.setError ( null );
            //edNearBy.setError ( null );
            edPin.setError ( null );
            edState.setError ( null );
            edUserName.setError ( null );
            edPass.setError ( null );
            edConPass.setError ( null );
            return  true;
        }

    }
    private void registerData(String name, String email, String username, String password, String pin, String adress, String phone, String city, String area, String sector, String d, String state, String cpass)
    {
        Call<RegisterResponce> call=RetrofitIntance.getService ().dataRegister ( name, email, username, password, pin, adress, phone, city, area, sector, d, state, cpass );
        call.enqueue ( new Callback<RegisterResponce> () {
            @Override
            public void onResponse(Call<RegisterResponce> call, Response<RegisterResponce> response) {
                if (response.isSuccessful ())
                {
                    progressBarReg.setVisibility ( View.GONE );
                    RegisterResponce registerResponce=response.body ();
                    Toast.makeText ( RegisterActivity.this, "Registration successful..", Toast.LENGTH_SHORT ).show ();
                    Intent intent=new Intent (RegisterActivity.this,LoginActivity.class);
                    startActivity ( intent );
                }
                else {
                    progressBarReg.setVisibility ( View.GONE );
                    Toast.makeText ( RegisterActivity.this, "error occupied please try agin later...", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponce> call, Throwable t) {
                progressBarReg.setVisibility ( View.GONE );
                Toast.makeText ( RegisterActivity.this, "error occupied please try agin later...", Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (RegisterActivity.this, LoginActivity.class ) );
        super.onBackPressed ();
    }
}