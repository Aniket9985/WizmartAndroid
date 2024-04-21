package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.GetOtpResponce;
import com.wiz.wizmart.Utilities.SetPassResponce;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    CardView emailpass,conpass;
    EditText edOtpEmail,newPass,newPassC,otp;
    TextView fUsername;
    AppCompatButton btngetOtp,btnSubmit;
    SharedPreferences sharedPreferences;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_forgot_password );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );
        ImageView imageView=findViewById ( R.id.imgbtn );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (ForgotPasswordActivity.this,LoginActivity.class) );
            }
        } );

        init();
    }
    private void init()
    {
        emailpass=findViewById ( R.id.emailpass );
        conpass=findViewById ( R.id.conpass );
        edOtpEmail=findViewById ( R.id.edOtpEmail );
        newPass=findViewById ( R.id.newPass );
        newPassC=findViewById ( R.id.newPassC );
        otp=findViewById ( R.id.otp );
        fUsername=findViewById ( R.id.fUsername );
        btngetOtp=findViewById ( R.id.btngetOtp );
        btnSubmit=findViewById ( R.id.txtNo );

        fUsername.setText ( username );
        btngetOtp.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String email=edOtpEmail.getText ().toString ();
                getOtp ( email );

            }
        } );
        btnSubmit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (isValidation ())
                {
                    String passNew=newPass.getText ().toString ();
                    String passCom=newPassC.getText ().toString ();
                    String o=otp.getText ().toString ();
                    setPassword ( username,passNew,passCom,o );
                }

            }
        } );

    }
    private void getOtp(String email)
    {
        Call<GetOtpResponce> call=RetrofitIntance.getService ().callWeb ( email );
        call.enqueue ( new Callback<GetOtpResponce> () {
            @Override
            public void onResponse(Call<GetOtpResponce> call, Response<GetOtpResponce> response) {
                if (response.isSuccessful ())
                {
                    GetOtpResponce otpResponce=response.body ();
                    Toast.makeText ( ForgotPasswordActivity.this, "Otp Has Sent To THe Register Email Address......", Toast.LENGTH_SHORT ).show ();
                    conpass.setVisibility ( View.VISIBLE );
                    emailpass.setVisibility ( View.GONE );
                }
            }

            @Override
            public void onFailure(Call<GetOtpResponce> call, Throwable t) {

            }
        } );
    }
    private Boolean isValidation()
    {
        String passNew=newPass.getText ().toString ();
        String passCom=newPassC.getText ().toString ();
        String o=otp.getText ().toString ();
        if (!passCom.equals ( passNew ))
        {
            newPassC.setError ( "Please Enter Correct Password" );
            newPassC.requestFocus ();
            return false;
        }else if (passNew.equals ( "" ))
        {
            newPass.setError ( "Please Enter A Password" );
            newPass.requestFocus ();
            return false;
        }else if (passCom.equals ( "" ))
        {
            newPassC.setError ( "Please Enter Confirm Password" );
            newPassC.requestFocus ();
            return false;
        }else if (o.equals ( "" ))
        {
            otp.setError ( "Please Enter Otp" );
            otp.requestFocus ();
            return false;
        }else {
            newPass.setError ( null );
            newPassC.setError ( null );
            otp.setError ( null );
            return true;
        }
    }
    private void setPassword(String username,String newpass,String conpass,String otp)
    {
        Call<SetPassResponce> call=RetrofitIntance.getService ().setPassword ( username, newpass, conpass, otp );
        call.enqueue ( new Callback<SetPassResponce> () {
            @Override
            public void onResponse(Call<SetPassResponce> call, Response<SetPassResponce> response) {
                String s=response.message ();
                if (response.isSuccessful ())
                {
                    SetPassResponce setPassResponce=response.body ();
                    Toast.makeText ( ForgotPasswordActivity.this, "Forgot Password Successfully", Toast.LENGTH_SHORT ).show ();
                    Intent intent=new Intent (ForgotPasswordActivity.this,LoginActivity.class);
                    startActivity ( intent );
                }else {
                    Toast.makeText ( ForgotPasswordActivity.this,s, Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<SetPassResponce> call, Throwable t) {

            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (ForgotPasswordActivity.this,LoginActivity.class) );
        super.onBackPressed ();
    }
}