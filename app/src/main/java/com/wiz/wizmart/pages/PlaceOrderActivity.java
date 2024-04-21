package com.wiz.wizmart.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.wiz.wizmart.R;


public class PlaceOrderActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_place_order );

        new Handler ().postDelayed ( new Runnable () {
            @Override
            public void run() {
                Intent intent=new Intent (PlaceOrderActivity.this,MyOrderActivity.class);
                startActivity ( intent );
            }
        },3000 );
    }
}