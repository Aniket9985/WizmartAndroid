package com.wiz.wizmart;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.wiz.wizmart.pages.CategoryActivity;

public class MainActivity extends AppCompatActivity {
    Animation topAnimantion,bottomAnimation;
    ImageView imageView;
    TextView textView;
    SharedPreferences sharedPreferences;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        imageView=findViewById ( R.id.imgv );
        textView=findViewById (  R.id.txtV);
        topAnimantion = AnimationUtils.loadAnimation(this, R.anim.anim);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        imageView.setAnimation ( topAnimantion );
        textView.setAnimation ( bottomAnimation );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );





        Handler handler=new Handler ();
        handler.postDelayed ( new Runnable () {
            @Override
            public void run() {
                Intent intent1=new Intent (MainActivity.this, CategoryActivity.class);
                startActivity ( intent1 );
                finish ();
            }
        },3000);
    }

}