package com.wiz.wizmart.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.LoginResponce;
import com.wiz.wizmart.retrofit.RetrofitIntance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edUserName,edPassword;
    Button btn_login;
    LinearLayout layout;
    SharedPreferences sharedPreferences;
    public static final String SHARED_NAME="mypref";
    public static final String KEY_NAME="username";
    public static final String KEY_VUserName="vusername";
    public static final String KEY_ID="id";
    private TextView txtForgot,txtVendor,txtLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        sharedPreferences=getSharedPreferences (SHARED_NAME,MODE_PRIVATE);
        check ();
        init ();
    }
    private void init()
    {
      edUserName=findViewById ( R.id.edt_username );
      edPassword=findViewById ( R.id.edt_password );
      btn_login=findViewById ( R.id.btn_login );
      layout=findViewById ( R.id.lytRegister );
      txtVendor=findViewById ( R.id.txtVendor );
      txtLogin=findViewById ( R.id.txtLogin );
      progressBar=findViewById ( R.id.progressBar );
      layout.setOnClickListener ( new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent ( LoginActivity.this,RegisterActivity.class );
              startActivity ( intent );
          }
      } );
      btn_login.setOnClickListener ( new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              if (validation ())
              {
                  progressBar.setVisibility ( View.VISIBLE );
                  btn_login.setVisibility ( View.GONE );
                  String name=edUserName.getText ().toString ();
                  String pass=edPassword.getText ().toString ();
                  loginUser(name,pass);

              }

          }
      } );
      txtLogin.setOnClickListener ( new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              startActivity ( new Intent (LoginActivity.this,VendorLoginActivity.class) );
          }
      } );

      txtForgot=findViewById ( R.id.txtForgot );
      txtForgot.setOnClickListener ( new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              startActivity ( new Intent (LoginActivity.this,ForgotPasswordActivity.class) );
          }
      } );
      txtVendor.setOnClickListener ( new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              setupHyperlink ();
          }
      } );
    }
    private void setupHyperlink() {
        txtVendor.setMovementMethod( LinkMovementMethod.getInstance());
    }


    private void loginUser(String username,String password) {
        Call<LoginResponce> call=RetrofitIntance.getService ().loginData ( username, password );
        call.enqueue ( new Callback<LoginResponce> () {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                if (response.isSuccessful()){
                    LoginResponce loginResponce=response.body ();
                    if (loginResponce.getStatus ().equals ( "success" ))
                    {
                        progressBar.setVisibility ( View.GONE );
                        share ( edUserName.getText ().toString () );
                        Intent intent = new Intent(LoginActivity.this,CategoryActivity.class);
                        startActivity(intent);
                    }else if (loginResponce.getMessage ().equals ( "Invalid username or password" ))
                    {
                        progressBar.setVisibility ( View.GONE );
                        Toast.makeText ( LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG ).show ();
                    }
                    else {
                        progressBar.setVisibility ( View.GONE );
                        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                        startActivity(intent);
                    }


                }else {
                    progressBar.setVisibility ( View.GONE );
                    String message = "An error occurred please try again......";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponce> call, Throwable t) {
                progressBar.setVisibility ( View.GONE );
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        } );
    }

    private void share(String username)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.putString ( KEY_NAME,username );
        editor.commit ();
    }
    private Boolean validation()
    {
        String user=edUserName.getText ().toString ();
        String pass=edPassword.getText ().toString ();
        if (user.isEmpty ())
        {
            edUserName.setError ( "Please Enter User Name" );
            edUserName.requestFocus ();
            return false;
        }else if (pass.isEmpty ())
        {
            edPassword.setError ( "Please Enter Password" );
            edPassword.requestFocus ();
            return false;
        }else {
            edUserName.setError ( null );
            edPassword.setError ( null );
            return true;
        }
    }
    private void check()
    {
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        String check=sharedPreferences.getString (KEY_NAME,"" );
        if (check.equals ( KEY_NAME ))
        {
            Intent intent=new Intent (LoginActivity.this,CategoryActivity.class);
            startActivity ( intent );

        }

    }
}