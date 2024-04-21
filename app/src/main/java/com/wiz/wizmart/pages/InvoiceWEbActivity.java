package com.wiz.wizmart.pages;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wiz.wizmart.R;


public class InvoiceWEbActivity extends AppCompatActivity {
    private String oid,username;
    private SharedPreferences sharedPreferences;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_invoice_web );

        oid=getIntent ().getStringExtra ( "oid" );
        sharedPreferences=getSharedPreferences ( SHARED_NAME,MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );

        webView=findViewById ( R.id.webView );
        webView.getSettings ().setJavaScriptEnabled ( true );
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient ( new WebViewClient () );
        String url="https://wiz-mart.com/api/bill.php" +"?"+"username="+username+"&"+"orderid="+oid;
        webView.loadUrl ( url );

    }
}