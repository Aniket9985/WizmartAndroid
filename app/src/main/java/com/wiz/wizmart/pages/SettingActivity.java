package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiz.wizmart.R;


public class SettingActivity extends AppCompatActivity {
    RelativeLayout order,profile,policies,terms,refund;
    TextView txtLog;
    ImageView imgbtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_setting );
        order=findViewById ( R.id.order );
        profile=findViewById ( R.id.profile );
        policies=findViewById ( R.id.policies );
        imgbtn=findViewById ( R.id.imgbtn );
        terms=findViewById ( R.id.terms );
        refund=findViewById ( R.id.refund );
        txtLog=findViewById ( R.id.txtLog );

        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );

        imgbtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (SettingActivity.this,CategoryActivity.class) );
            }
        } );
        terms.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (SettingActivity.this,TearmsActivity.class) );
            }
        } );
        refund.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (SettingActivity.this,RefundActivity.class) );
            }
        } );

        order.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (SettingActivity.this,MyOrderActivity.class) );
            }
        } );
        profile.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (SettingActivity.this,AccountInfoActivity.class) );
            }
        } );
        policies.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (SettingActivity.this,PoliciesActivity.class) );
            }
        } );

        txtLog.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                deleteSession ();
                startActivity ( new Intent (SettingActivity.this,LoginActivity.class) );
            }
        } );

    }
    private void deleteSession()
    {
        SharedPreferences.Editor prefsEditor =sharedPreferences.edit ();
        prefsEditor.clear ();
        prefsEditor.commit ();
    }
}